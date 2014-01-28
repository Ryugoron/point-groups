package pointGroups.util;

import java.util.concurrent.ExecutionException;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.TimeoutException;
import java.util.logging.Logger;


public abstract class AbstractTransformer<E>
  implements Transformer<E>
{
  //TODO maybe long is better? 
  public static int count = 0;
  public final int id = count ++;
  //TODO is it a good idea to name the logger this way?
  final protected Logger logger = LoggerFactory.get(this.getClass());
  
  private E result;
  private volatile boolean done;
  // has to be protected, otherwise transformResultString can't use it
  protected volatile String resultString;

  @Override
  public boolean cancel(boolean mayInterruptIfRunning) {
    throw new UnsupportedOperationException();
  }

  @Override
  public void setResultString(String resultString) {
    logger.fine(logger.getName() + ": received resultString");
    this.resultString = resultString;
    synchronized (this) {
      this.notifyAll();
    }
  }

  @Override
  public E get()
    throws InterruptedException, ExecutionException {
    synchronized (this) {
      while (resultString == null) {
        try {
          this.wait();
        }
        catch (InterruptedException e) {
          logger.warning(logger.getName() + ": InterruptedException while waiting for resultString: " + e.getMessage());
          // Nothing to do. Still waiting for setResult().
        }
      }
    }
    if (result == null) {
      this.result = transformResultString();
      this.done = true;
    }
    return result;
  }

  /**
   * Transforms result string of the {@link Transformer#setResultString(String)}
   * to the internal representation.
   * 
   * @return Internal representation of the external calculation.
   */
  abstract protected E transformResultString();

  @Override
  public E get(long timeout, TimeUnit unit)
    throws InterruptedException, ExecutionException, TimeoutException {
    throw new UnsupportedOperationException();
  }

  @Override
  public boolean isCancelled() {
    throw new UnsupportedOperationException();
  }

  @Override
  public boolean isDone() {
    return done;
  }

}
