package pointGroups.util.polymake.wrapper;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.net.Socket;
import java.util.LinkedList;
import java.util.Queue;
import java.util.logging.Logger;

import pointGroups.util.polymake.PolymakeException;
import pointGroups.util.polymake.PolymakeTransformer;

public class PolymakeWrapper {
	final Logger logger = Logger.getLogger(PolymakeWrapper.class.getName());
	final String polymakePath;
	final String polymakeDriverPath;

	private Process polymakeInstance;
	private final Queue<PolymakeTransformer> pending;

	private Socket polymakeSocket;
	private BufferedWriter toPolymake;

	private boolean isRunning = false;

	// private boolean isRunning_ = false;

	public PolymakeWrapper(final String polymakePath,
			final String polymakeDriverPath) {
		this.polymakePath = polymakePath;
		this.polymakeDriverPath = polymakeDriverPath;
		this.pending = new LinkedList<PolymakeTransformer>();
	}

	private String getRunCommand() {
		return this.polymakePath + " --script=" + polymakeDriverPath;
	}

	public void start() {
		try {
			if (!isRunning) {
				this.isRunning = true;

				this.polymakeInstance = Runtime.getRuntime().exec(
						getRunCommand());
				new Thread(new PolymakeErrorStreamHandler(
						this.polymakeInstance.getErrorStream())).start();

				fetchInitialOutput();
			} else {
				logger.warning("Polymake process already started, but start was invoked.");
			}
		} catch (IOException e) {
			logger.severe(PolymakeException.CANNOT_START + e.getMessage());
			throw new PolymakeException(PolymakeException.CANNOT_START
					+ e.getMessage());
		}
	}

	private void fetchInitialOutput() {
		BufferedReader polymakeStdOut = new BufferedReader(
				new InputStreamReader(this.polymakeInstance.getInputStream()));
		try {
			String input;
			// First line is the port polymake is listening on
			input = polymakeStdOut.readLine();
			int polymakePort = Integer.parseInt(input);
			logger.info("Polymake is running on port " + polymakePort);

			openConnection(polymakePort);
		} catch (IOException e) {
			throw new PolymakeException(PolymakeException.CANNOT_START
					+ e.getMessage());
		} catch (NumberFormatException e) {
			throw new PolymakeException(PolymakeException.CANNOT_START
					+ e.getMessage());
		}
	}

	private void openConnection(int port) {
		try {
			this.polymakeSocket = new Socket("localhost", port);
			logger.info("Connection established with Polymake on port " + port);

			this.toPolymake = new BufferedWriter(new OutputStreamWriter(
					this.polymakeSocket.getOutputStream()));

			new Thread(new PolymakeResultHandler(this,
					this.polymakeSocket.getInputStream())).start();
		} catch (IOException e) {
			throw new PolymakeException(PolymakeException.CANNOT_START
					+ e.getMessage());
		}
	}

	public void stop() {
		if (isRunning) {
			try {
				this.isRunning = false;
				// TODO: Zeichenkonstanten
				this.toPolymake.write("__END__" + "\n");
				this.toPolymake.flush();
			} catch (IOException e) {
				logger.warning("Could not write closing string to Polymake: "
						+ e.getMessage());
				this.polymakeInstance.destroy();
			} finally {
				try {
					this.polymakeSocket.close();
				} catch (IOException e) {
					logger.warning("Closing the socket connection threw an error: "
							+ e.getMessage());
				}
			}
		} else {
			logger.warning("Polymake process was not started, but stop() was invoked.");
		}
	}

	public boolean isRunning() {
		return this.isRunning;
	}

	public void sendRequest(PolymakeTransformer req) {
		if (isRunning) {
			try {
				this.toPolymake.write(req.toScript() + "\n");
				this.toPolymake.flush();
				logger.info("Writing Transformerrequest to Polymake: "
						+ req.toScript());
				this.pending.add(req);
			} catch (IOException e) {
				throw new PolymakeException(
						"Cannot send transformer request to polymake: "
								+ e.getMessage());
			}
		} else {
			logger.warning("Polymake process was not started, but sendRequest was invoked.");
		}

	}

	void onMessageReceived(String msg) {
		PolymakeTransformer pt = this.pending.poll();
		if (pt != null) {
			pt.setResult(msg);
		} else {
			logger.warning("Received message from polymake, but no request was pending.");
		}
	}

}
