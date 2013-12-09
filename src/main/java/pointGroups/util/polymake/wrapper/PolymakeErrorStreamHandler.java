package pointGroups.util.polymake.wrapper;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.logging.Logger;


class PolymakeErrorStreamHandler
  implements Runnable
{
  final Logger logger =
      Logger.getLogger(PolymakeErrorStreamHandler.class.getName());
  private final BufferedReader err;
  private final PolymakeWrapper wrapper;

  PolymakeErrorStreamHandler(PolymakeWrapper polymakeWrapper,
      InputStream errorStream) {
    this.err = new BufferedReader(new InputStreamReader(errorStream));
    this.wrapper = polymakeWrapper;
  }

  @Override
  public void run() {
    String err;
    try {
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
        logger.info("Socket was closed itentionally, Resulthandler is terminating");
      }
    }
  }
}
