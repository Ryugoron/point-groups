package pointGroups.util.polymake.wrapper;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.logging.Logger;

import pointGroups.util.LoggerFactory;


/**
 * A {@link Runnable} that allows to continously log errors from the polymake
 * process encapsulated by the {@link PolymakeWrapper} class. Uses
 * {@link Logger} to log the errors recieved.
 * 
 * @author Alex
 * @see Logger
 * @see PolymakeWrapper
 */
class PolymakeErrorStreamHandler
  implements Runnable
{
  final Logger logger = LoggerFactory.get(this.getClass());
  private final BufferedReader err;
  private final PolymakeWrapper wrapper;

  /**
   * Creates a new {@link Runnable} of type {@link PolymakeErrorStreamHandler}
   * that will continously listen on the error stream <code>errorStream</code>
   * from the polymake process wrapper by <code>polymakeWrapper</code>.
   * 
   * @param polymakeWrapper The wrapped source of the error messages
   * @param errorStream The error stream to listen on
   */
  PolymakeErrorStreamHandler(final PolymakeWrapper polymakeWrapper,
      final InputStream errorStream) {
    this.err = new BufferedReader(new InputStreamReader(errorStream));
    this.wrapper = polymakeWrapper;
  }

  @Override
  public void run() {
    String err;
    try {
      // Log all output as 'severe' log message
      while ((err = this.err.readLine()) != null) {
        logger.severe(err);
      }
    }
    catch (IOException e) {
      if (this.wrapper.isRunning()) {
        // Socket should not be closed (or some other error occured)
        logger.severe("Socket was closed or another IO error occured");
        logger.fine(e.getMessage());
      }
      else {
        // No Error occured, polymake was shut down
        logger.info("Socket was closed itentionally, Resulthandler is terminating");
      }
    }
  }
}
