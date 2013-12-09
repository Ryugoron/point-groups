package pointGroups.util.polymake.wrapper;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.logging.Logger;


class PolymakeResultHandler
  implements Runnable
{
  private final Logger logger =
      Logger.getLogger(PolymakeResultHandler.class.getName());
  private final BufferedReader res;
  private final PolymakeWrapper wrapper;

  public PolymakeResultHandler(PolymakeWrapper polymakeWrapper,
      InputStream inputStream) {
    this.res = new BufferedReader(new InputStreamReader(inputStream));
    this.wrapper = polymakeWrapper;
  }

  @Override
  public void run() {
    String output;
    StringBuilder sb = new StringBuilder();
    try {
      while ((output = this.res.readLine()) != null) {
        // TODO: String constants
        if (output.equals("__END__")) {
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
        logger.info("Socket was closed itentionally, Resulthandler is terminating");
      }
    }
  }
}
