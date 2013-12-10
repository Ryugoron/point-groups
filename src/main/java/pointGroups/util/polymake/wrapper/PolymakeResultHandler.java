package pointGroups.util.polymake.wrapper;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.logging.Logger;


/**
 * A {@link Runnable} that is used by {@link PolymakeWrapper} to listen on
 * polymake's output and invoke
 * {@link PolymakeWrapper#onMessageReceived(String)} when a message was
 * recevied.
 * 
 * @author Alex
 * @see PolymakeWrapper
 */
class PolymakeResultHandler
  implements Runnable
{
  private final Logger logger =
      Logger.getLogger(PolymakeResultHandler.class.getName());
  private final BufferedReader res;
  private final PolymakeWrapper wrapper;

  /**
   * Create a new {@link PolymakeResultHandler} runnable to listen for
   * <code>polymakeWrapper</code> on the input stream <code>inputStream</code>.
   * 
   * @param polymakeWrapper The wrapped source of the responses
   * @param inputStream The stream to listen on
   */
  public PolymakeResultHandler(PolymakeWrapper polymakeWrapper,
      InputStream inputStream) {
    this.res = new BufferedReader(new InputStreamReader(inputStream));
    this.wrapper = polymakeWrapper;
  }

  @Override
  public void run() {
    // Continously listen on the input stream for responses by polymake.
    // Invoke PolymakeWrapper#onMessageReceived on new response.
    String output;
    StringBuilder sb = new StringBuilder();
    try {
      while ((output = this.res.readLine()) != null) {
        // TODO: String constants
        if (output.equals("__END__")) { // If the symbol __END__ occured, the
                                        // response message is complete and can
                                        // be forwarded.
          this.wrapper.onMessageReceived(sb.toString());
          sb = new StringBuilder();
        }
        else {
          sb.append(output + "\n");
        }
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
