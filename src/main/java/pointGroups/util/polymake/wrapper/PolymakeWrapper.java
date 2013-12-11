package pointGroups.util.polymake.wrapper;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.net.Socket;
import java.util.LinkedList;
import java.util.Queue;
import java.util.concurrent.Future;
import java.util.logging.Logger;

import pointGroups.util.polymake.PolymakeException;
import pointGroups.util.polymake.PolymakeTransformer;


/**
 * This class wraps a polymake process and coordinates the communication from
 * and to this process. A polymake process is started via {@link #start()} and
 * shut down via {@link #stop()}. A request can be sent to polymake via
 * {@link #sendRequest(PolymakeTransformer)}.
 * 
 * @author Alex
 * @see PolymakeTransformer
 */
public class PolymakeWrapper
{
  final Logger logger = Logger.getLogger(PolymakeWrapper.class.getName());
  final String polymakePath;
  final String polymakeDriverPath;

  private Process polymakeInstance;
  private final Queue<PolymakeTransformer> pending;

  private Socket polymakeSocket;
  private BufferedWriter toPolymake;

  private volatile boolean isRunning = false;

  /**
   * Magicstring that marks the end of responses.
   */
  public static final String END_OF_RESPONSE = "__END__";
  /**
   * Magicstring that marks the end of the communication with polymake. Polymake
   * will shut down after receiving this as a message.
   */
  public static final String END_OF_COMMUNICATION = "__EXIT__";

  // private boolean isRunning_ = false;

  /**
   * Creates a new {@link PolymakeWrapper} with the given information about
   * polymake.
   * 
   * @param polymakePath The path where polymake executable can be found
   * @param polymakeDriverPath The path where the polymake driver is located
   */
  public PolymakeWrapper(final String polymakePath,
      final String polymakeDriverPath) {
    this.polymakePath = polymakePath;
    this.polymakeDriverPath = polymakeDriverPath;
    this.pending = new LinkedList<PolymakeTransformer>();
  }

  /**
   * Attempts to start a polymake instance as a new process which is then
   * wrapped the by {@link PolymakeWrapper} under consideration (i.e.
   * <code>this</code>). The polymake process can be shut down by the
   * {@link #stop()} method.
   * 
   * @throws PolymakeException This exception is thrown at runtime if the
   *           creation of the polymake process did not succeed.
   */
  public void start() {
    try {
      if (!isRunning) {
        this.isRunning = true;

        this.polymakeInstance = Runtime.getRuntime().exec(getRunCommand());
        logger.info("Starting Polymake...");
        new Thread(new PolymakeErrorStreamHandler(this,
            this.polymakeInstance.getErrorStream())).start();

        fetchInitialOutput();
      }
      else {
        logger.warning("Polymake process already started, but start was invoked.");
      }
    }
    catch (IOException e) {
      logger.severe(PolymakeException.CANNOT_START + e.getMessage());
      throw new PolymakeException(PolymakeException.CANNOT_START +
          e.getMessage());
    }
  }

  // Waits for the first output of polymake, that is, the port polymake is
  // listening on. If the port was reveiced, a socket is opened via
  // #openConnection.
  private void fetchInitialOutput() {
    BufferedReader polymakeStdOut =
        new BufferedReader(new InputStreamReader(
            this.polymakeInstance.getInputStream()));

    try {
      logger.info("Waiting for Polymake to start up...");
      String input;
      // First line is the port polymake is listening on
      input = polymakeStdOut.readLine();
      int polymakePort = Integer.parseInt(input);
      logger.info("Polymake is running on port " + polymakePort);

      openConnection(polymakePort);
    }
    catch (IOException e) {
      throw new PolymakeException(PolymakeException.CANNOT_START +
          e.getMessage());
    }
    catch (NumberFormatException e) {
      throw new PolymakeException(PolymakeException.CANNOT_START +
          e.getMessage());
    }
  }

  // Creates a socket connection to polymake and starts the asynchronous result
  // handler as a new thread.
  private void openConnection(int port) {
    try {
      this.polymakeSocket = new Socket("localhost", port);
      logger.info("Connection established with Polymake on port " + port);

      this.toPolymake =
          new BufferedWriter(new OutputStreamWriter(
              this.polymakeSocket.getOutputStream()));

      new Thread(new PolymakeResultHandler(this,
          this.polymakeSocket.getInputStream())).start();
    }
    catch (IOException e) {
      throw new PolymakeException(PolymakeException.CANNOT_START +
          e.getMessage());
    }
  }

  /**
   * Attempts to shut down the wrapped polymake proceess as well as to close the
   * existing stream from and to the process. If some error occurrs during the
   * shutdown procedure, the process is forcefully destroyed. Any errors will be
   * logged as warning to {@link #logger}.
   */
  public void stop() {
    if (isRunning) {
      try {
        logger.info("Attempting to stop polymake wrapper");
        this.isRunning = false;
        send(END_OF_COMMUNICATION);
        this.polymakeInstance.getErrorStream().close();
      }
      catch (IOException e) {
        logger.warning("Could not close error stream to polymake.");
        logger.fine(e.getMessage());
        this.polymakeInstance.destroy();
      }
      finally {
        try {
          this.polymakeSocket.close();
        }
        catch (IOException e) {
          logger.warning("Closing the socket connection threw an error: " +
              e.getMessage());
        }
      }
    }
    else {
      logger.warning("Polymake process was not started, but stop() was invoked.");
    }
  }

  /**
   * Returns whether the polymake process was attempted to start. Note that this
   * method may already return <code>true</code> when the method
   * {@link #start()} was invoked and polymake was not yet fully started.
   * 
   * @return false if {@link #stop()} was invoked or {@link #start()} was never
   *         invoked.
   */
  public boolean isRunning() {
    return this.isRunning;
  }

  /**
   * Submits the request represented by {@link PolymakeTransformer}
   * <code>req</code> to the wrapped polymake process. The result of this
   * request will be asynchronously bound to <code>req</code>. See
   * {@link Future} or {@link PolymakeTransformer} for further information.
   * 
   * @param req The {@link PolymakeTransformer} to be submitted.
   * @see PolymakeTransformer
   */
  public void sendRequest(PolymakeTransformer req) {
    if (isRunning) {
      send(req.toScript().replaceAll("\n", ""));
      this.pending.add(req);
    }
    else {
      logger.warning("Polymake process was not started, but sendRequest was invoked.");
    }

  }

  /**
   * This method is to be invoked when a response to a
   * {@link PolymakeTransformer} request, which was sent by
   * {@link #sendRequest(PolymakeTransformer)}, was received. The response is
   * then bound to according the {@link PolymakeTransformer}.
   * 
   * @param msg The response received by polymake
   */
  void onMessageReceived(String msg) {
    logger.info("Got answer from polymake");
    PolymakeTransformer pt = this.pending.poll();
    if (pt != null) {
      pt.setResult(msg);
    }
    else {
      logger.warning("Received message from polymake, but no request was pending.");
    }
  }

  protected void send(String msg) {
    final String finalMsg = msg + "\n";
    try {
      this.toPolymake.write(finalMsg);
      this.toPolymake.flush();
      logger.info("Writing request to Polymake. ");
      logger.fine("Request: " + finalMsg);
    }
    catch (IOException e) {
      throw new PolymakeException("Cannot send request to polymake: " +
          e.getMessage());
    }
  }

  private String getRunCommand() {
    return this.polymakePath + " --script=" + polymakeDriverPath;
  }
}
