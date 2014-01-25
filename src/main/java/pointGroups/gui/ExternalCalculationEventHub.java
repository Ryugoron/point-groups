package pointGroups.gui;

import java.util.Collection;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.LinkedBlockingQueue;
import java.util.logging.Logger;

import pointGroups.geometry.Point;
import pointGroups.geometry.Point3D;
import pointGroups.geometry.Point4D;
import pointGroups.geometry.Schlegel;
import pointGroups.geometry.Symmetry;
import pointGroups.gui.event.Event;
import pointGroups.gui.event.EventDispatcher;
import pointGroups.gui.event.EventHandler;
import pointGroups.gui.event.types.RunEvent;
import pointGroups.gui.event.types.RunHandler;
import pointGroups.gui.event.types.SchlegelResultEvent;
import pointGroups.gui.event.types.SchlegelResultHandler;
import pointGroups.gui.event.types.Symmetry3DChooseEvent;
import pointGroups.gui.event.types.Symmetry3DChooseHandler;
import pointGroups.gui.event.types.Symmetry4DChooseEvent;
import pointGroups.gui.event.types.Symmetry4DChooseHandler;
import pointGroups.util.ExternalCalculationWrapper;
import pointGroups.util.Transformer;
import pointGroups.util.polymake.SchlegelTransformer;


/**
 * Coordination object in the gui that listens to certain events that contribute
 * to the next calculation. If a {@link RunEvent} is received, a calculation
 * request will be sent to the {@link ExternalCalculationWrapper}. Finally, for
 * each calculation request, a {@link Event} that contains the result, is fired.
 * 
 * @author Alex
 */
public class ExternalCalculationEventHub
  implements Symmetry3DChooseHandler, Symmetry4DChooseHandler, RunHandler
{
  /**
   * An instance of the {@link EventDispatcher}.
   */
  protected final EventDispatcher dispatcher = EventDispatcher.get();
  /**
   * The wrapped external process.
   */
  protected final ExternalCalculationWrapper wrapper;
  protected final Logger logger = Logger.getLogger(this.getClass().getName());

  private final Map<Class<? extends Transformer<?>>, Class<? extends EventHandler>> transformerToEventType =
      new HashMap<>();

  // Producer-Consumer-Patterh for asynchronous external calculation tasks
  private final BlockingQueue<Transformer<?>> buf = new LinkedBlockingQueue<>();
  private final ResultProducer resProducer = new ResultProducer();
  private final ResultConsumer resConsumer = new ResultConsumer();

  // Additional fields for events registered to
  private Symmetry<Point3D, ?> last3DSymmetry;
  private Symmetry<Point4D, ?> last4DSymmetry;
  private String lastSubgroup;

  public ExternalCalculationEventHub(final ExternalCalculationWrapper wrapper) {
    // To log properly, we need to set the logger as child of the global logger
    logger.setParent(Logger.getGlobal());

    fillEventMap();
    this.wrapper = wrapper;
    this.wrapper.start();

    resProducer.start();
    resConsumer.start();

    // Add other handlers of interest here! If you add a handler for some
    // kind of result, you need to add it to the transformerToEventType map in
    // fillEventMap().
    // Additionally, a construction in the ResultEventFactory has to be added
    // (at the end of this class).
    dispatcher.addHandler(Symmetry3DChooseHandler.class, this);
    dispatcher.addHandler(Symmetry4DChooseHandler.class, this);
    dispatcher.addHandler(RunHandler.class, this);
  }

  // Put the event Type of each event that is fired for the result of a
  // corresponding transformer type
  private void fillEventMap() {
    this.transformerToEventType.put(SchlegelTransformer.class,
        SchlegelResultHandler.class);
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
  public void onRunEvent(final RunEvent event) {
    final int dimension = event.getDimension();
    final double[] coords = event.getCoordinates();

    Collection<? extends Point> images = null;
    if (dimension == 3) {
      if (last3DSymmetry != null) {
        Point3D p = new Point3D(coords[0], coords[1], coords[2]);
        images = last3DSymmetry.images(p, lastSubgroup);
      }
    }
    else if (dimension == 4) {
      if (last4DSymmetry != null) {
        Point4D p = new Point4D(coords[0], coords[1], coords[2], coords[3]);
        images = last4DSymmetry.images(p, lastSubgroup);
      }
    }

    if (images != null) {
      submit(new SchlegelTransformer(images));
    }
  }

  @Override
  public void onSymmetry4DChooseEvent(final Symmetry4DChooseEvent event) {
    this.last4DSymmetry = event.getSymmetry4D();
    this.lastSubgroup = event.getSubgroup();
  }

  @Override
  public void onSymmetry3DChooseEvent(final Symmetry3DChooseEvent event) {
    this.last3DSymmetry = event.getSymmetry3D();
    this.lastSubgroup = event.getSubgroup();
  }


  protected class ResultProducer
    extends Thread
  {
    private final BlockingQueue<Transformer<?>> pending =
        new LinkedBlockingQueue<>();

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
          dispatcher.fireEvent(createEvent(transformerToEventType.get(t),
              t.get()));
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

    @SuppressWarnings("unchecked")
    protected <H extends EventHandler> Event<? extends H> createEvent(
        final Class<H> eventType, final Object result) {
      try {
        if (eventType == SchlegelResultHandler.class) {
          SchlegelResultEvent sre = new SchlegelResultEvent((Schlegel) result);
          // cast is safe, we check the type
          return (Event<? extends H>) sre;
        }

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
