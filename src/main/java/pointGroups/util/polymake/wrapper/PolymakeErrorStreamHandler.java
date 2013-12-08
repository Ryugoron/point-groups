package pointGroups.util.polymake.wrapper;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.logging.Logger;

class PolymakeErrorStreamHandler implements Runnable {
	final Logger logger = Logger.getLogger(PolymakeErrorStreamHandler.class.getName());
	private final BufferedReader err;

	PolymakeErrorStreamHandler(InputStream errorStream) {
		this.err = new BufferedReader(new InputStreamReader(errorStream));
	}

	@Override
	public void run() {
		String err;
		try {
			while ((err = this.err.readLine()) != null) {
				logger.severe(err);
			}
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

}
