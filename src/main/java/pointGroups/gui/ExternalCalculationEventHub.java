package pointGroups.gui;

import java.util.Collection;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.LinkedBlockingQueue;
import java.util.logging.Logger;

import pointGroups.geometry.Fundamental;
import pointGroups.geometry.Point3D;
import pointGroups.geometry.Point4D;
import pointGroups.geometry.UnknownFundamental;
import pointGroups.gui.event.Event;
import pointGroups.gui.event.EventDispatcher;
import pointGroups.gui.event.types.FundamentalResultEvent;
import pointGroups.gui.event.types.Schlegel3DComputeEvent;
import pointGroups.gui.event.types.Schlegel3DComputeHandler;
import pointGroups.gui.event.types.Schlegel4DComputeEvent;
import pointGroups.gui.event.types.Schlegel4DComputeHandler;
import pointGroups.gui.event.types.SchlegelResultEvent;
import pointGroups.gui.event.types.Symmetry3DChooseEvent;
import pointGroups.gui.event.types.Symmetry3DChooseHandler;
import pointGroups.gui.event.types.Symmetry4DChooseEvent;
import pointGroups.gui.event.types.Symmetry4DChooseHandler;
import pointGroups.util.ExternalCalculationWrapper;
import pointGroups.util.LoggerFactory;
import pointGroups.util.Transformer;
import pointGroups.util.polymake.FundamentalTransformer;
import pointGroups.util.polymake.FundamentalTransformerTransformer;
import pointGroups.util.polymake.PolymakeOutputException;
import pointGroups.util.polymake.SchlegelTransformer;
import pointGroups.util.polymake.SchlegelTransformer.SchlegelCompound;


/**
 * Coordination object in the gui that listens to certain events that contribute
 * to the next calculation. If a {@link RunEvent} is received, a calculation
 * request will be sent to the {@link ExternalCalculationWrapper}. Finally, for
 * each calculation request, a {@link Event} that contains the result, is fired.
 * 
 * @author Alex
 */
public class ExternalCalculationEventHub
  implements Symmetry3DChooseHandler, Symmetry4DChooseHandler,
  Schlegel3DComputeHandler, Schlegel4DComputeHandler
{
  /**
   * An instance of the {@link EventDispatcher}.
   */
  protected final EventDispatcher dispatcher = EventDispatcher.get();
  /**
   * The wrapped external process.
   */
  protected final ExternalCalculationWrapper wrapper;
  protected final Logger logger = LoggerFactory.get(this.getClass());

  // Producer-Consumer-Pattern for asynchronous external calculation tasks
  private final BlockingQueue<Transformer<?>> buf = new LinkedBlockingQueue<>();
  private final ResultProducer resProducer = new ResultProducer();
  private final ResultConsumer resConsumer = new ResultConsumer();

  public ExternalCalculationEventHub(final ExternalCalculationWrapper wrapper) {
    this.wrapper = wrapper;
    this.wrapper.start();

    resProducer.start();
    resConsumer.start();

    // Add other handlers of interest here! If you add a handler for some
    // kind of result, you need to add a construction in the Consumer-Thread
    // createEventFor method.
    // (at the end of this class).
    dispatcher.addHandler(Symmetry3DChooseHandler.class, this);
    dispatcher.addHandler(Symmetry4DChooseHandler.class, this);

    dispatcher.addHandler(Schlegel3DComputeHandler.class, this);
    dispatcher.addHandler(Schlegel4DComputeHandler.class, this);
  }

  /**
   * Start asynchronous calculation of the given {@link Transformer}.
   * 
   * @param t The transformer
   */
  private void submit(final Transformer<?> t) {
    this.resProducer.submit(t);
  }

  @Override
  public void onSchlegel4DComputeEvent(final Schlegel4DComputeEvent event) {
    Collection<Point4D> images =
        event.getSymmetry4D().images(event.getPickedPoint());
    submit(new SchlegelTransformer(images, event.getSymmetry4D(),
        event.getPickedPoint()));
  }

  @Override
  public void onSchlegel3DComputeEvent(final Schlegel3DComputeEvent event) {
    Collection<Point3D> images =
        event.getSymmetry3D().images(event.getPickedPoint());
    submit(new SchlegelTransformer(images, event.getSymmetry3D(),
        event.getPickedPoint()));
  }

  @Override
  public void onSymmetry4DChooseEvent(final Symmetry4DChooseEvent event) {
    Point4D p = event.getSymmetry4D().getNormalPoint();
    logger.info("Calculating new Fundamental Domain");
    submit(new FundamentalTransformerTransformer(event.getSymmetry4D().images(p)));
  }

  @Override
  public void onSymmetry3DChooseEvent(final Symmetry3DChooseEvent event) {
    Point3D p = event.getSymmetry3D().getNormalPoint();
    logger.info("Calculating new Fundamental Domain");
    submit(new FundamentalTransformerTransformer(event.getSymmetry3D().images(p)));
  }


  protected class ResultProducer
    extends Thread
  {
    private final BlockingQueue<Transformer<?>> pending =
        new LinkedBlockingQueue<>();

    /**
     * Offer transformer for calculation. The transformer is passed to the
     * {@link ExternalCalculationWrapper} after which the {@link ResultConsumer}
     * will fire an event if the calculation is finished.
     * 
     * @param t The transformer to be calculated
     */
    protected void submit(final Transformer<?> t) {
      wrapper.sendRequest(t);
      this.pending.add(t);
    }

    @Override
    public void run() {
      Transformer<?> t;
      while (wrapper.isRunning()) {
        try {
          t = pending.take();
          t.get();
          buf.add(t);
        }
        catch (InterruptedException | ExecutionException e) {
          if (wrapper.isRunning()) {
            logger.warning("ExternalCalculationEventHub: Interrupted/Aborted"
                + "while waiting of future value; Transformer skipped.");
          }
          else {
            // If wrapper is not running anymore,
            // the thread can terminate
            break;
          }
        }
        catch (PolymakeOutputException e) {
          logger.severe(e.getMessage());
        }
      }
    }
  }


  protected class ResultConsumer
    extends Thread
  {
    @Override
    public void run() {
      Transformer<?> t;
      while (wrapper.isRunning()) {
        try {
          t = buf.take();
          Event<?> event = createEventFor(t.getClass(), t.get());
          if (event != null) {
            dispatcher.fireEvent(event);
          }
        }
        catch (InterruptedException | ExecutionException e) {
          if (wrapper.isRunning()) {
            logger.info("ExternalCalculationEventHub: Interrupted while"
                + "waiting on transformer buffer.");
          }
          else {
            // If wrapper is not running anymore,
            // the thread can terminate
            break;
          }
        }
      }
    }

    // factory for events associated to calculation results
    protected <H extends Transformer<?>> Event<?> createEventFor(
        final Class<H> transformerType, final Object result) {
      try {
        if (transformerType == SchlegelTransformer.class) {
          // cast is safe if properly called.
          SchlegelCompound sc = (SchlegelCompound) result;

          return new SchlegelResultEvent(sc.getSchlegel(), sc.getPoint(),
              sc.getSymmetry());
        } else if (transformerType == FundamentalTransformerTransformer.class) {
          // Two step Transformer, unpack it and resend it
          submit((FundamentalTransformer) result);
          return null;
        }
        else if (transformerType == FundamentalTransformer.class) {
          // Second step Transform, broadcast real result
          return new FundamentalResultEvent((Fundamental) result); 
        }
        // add further cases here...

      }
      catch (ClassCastException e) {
        logger.severe("ExternalCalculationEventHub: A result objects did not match the"
            + "expected class for the appropriate result event.");
      }
      // If no case matched, something went terribly wrong!
      logger.severe("ExternalCalculationEventHub: Consumer-Thread could not create"
          + "appropriate result event. Maybe an if-else-clause for that result"
          + "event was forgotten?");
      return null;
    }
  }

}
