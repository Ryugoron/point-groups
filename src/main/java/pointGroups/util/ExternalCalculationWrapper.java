package pointGroups.util;

import java.util.concurrent.Future;


public interface ExternalCalculationWrapper
{

  /**
   * Attempts to start a instance of the underlying process which is then
   * wrapped. The process can be shut down by the {@link #stop()} method.
   * 
   * @throws RuntimeException May be thrown if any error during start-up occurs
   */
  public void start();

  /**
   * Attempts to shut down the wrapped process as well as to close the existing
   * stream from and to the process. If some error occurs during the shutdown
   * procedure, the process is forcefully destroyed.
   */
  public void stop();

  /**
   * Returns whether the process was attempted to start. Note that this method
   * may already return <code>true</code> when the method {@link #start()} was
   * invoked and the external process was not yet fully started.
   * 
   * @return false if {@link #stop()} was invoked or {@link #start()} was never
   *         invoked.
   */
  public boolean isRunning();

  /**
   * Submits the request represented by {@link Transformer} <code>req</code> to
   * the wrapped process. The result of this request will be asynchronously
   * bound to <code>req</code>. See {@link Future} or {@link Transformer} for
   * further information.
   * 
   * @param req The {@link Transformer} to be submitted.
   * @see Transformer
   */
  public void sendRequest(Transformer<?> req);

}
