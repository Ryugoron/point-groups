package pointGroups.util;

import java.util.concurrent.ExecutionException;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.TimeoutException;


public abstract class AbstractTransformer<E>
  implements Transformer<E>
{

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
