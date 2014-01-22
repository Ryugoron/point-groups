package pointGroups.gui;

import java.util.Collection;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.LinkedBlockingQueue;

import pointGroups.geometry.Point;
import pointGroups.geometry.Point3D;
import pointGroups.geometry.Point4D;
import pointGroups.geometry.Schlegel;
import pointGroups.geometry.Symmetry;
import pointGroups.gui.event.EventDispatcher;
import pointGroups.gui.event.types.RunEvent;
import pointGroups.gui.event.types.RunHandler;
import pointGroups.gui.event.types.SchlegelResultEvent;
import pointGroups.gui.event.types.Symmetry3DChooseEvent;
import pointGroups.gui.event.types.Symmetry3DChooseHandler;
import pointGroups.gui.event.types.Symmetry4DChooseEvent;
import pointGroups.gui.event.types.Symmetry4DChooseHandler;
import pointGroups.util.polymake.SchlegelTransformer;
import pointGroups.util.polymake.wrapper.PolymakeWrapper;


/**
 * Coordination object in the gui that listens to certain events that contribute
 * to the next calculation. If a {@link RunEvent} is received, a calculation
 * request will be sent to the {@link PolymakeWrapper}. Finally, for each
 * calculation request, a {@link SchlegelResultEvent} that contains the request
 * is fired.
 * 
 * @author Alex
 */
public class PolymakeHub
  implements Symmetry3DChooseHandler, Symmetry4DChooseHandler, RunHandler
{
  protected final EventDispatcher dispatcher = EventDispatcher.get();
  protected final PolymakeWrapper pmWrapper;

  private final BlockingQueue<Schlegel> buf = new LinkedBlockingQueue<>();
  private final ResultProducer resProducer = new ResultProducer();
  private final ResultConsumer resConsumer = new ResultConsumer();
  private Symmetry<Point3D, ?> last3DSymmetry;
  private Symmetry<Point4D, ?> last4DSymmetry;
  private String lastSubgroup;

  public PolymakeHub(final String polyCmd, final String polyDriver) {
    this.pmWrapper = new PolymakeWrapper(polyCmd, polyDriver);
    this.pmWrapper.start();

    this.resConsumer.start();
    this.resProducer.start();

    dispatcher.addHandler(Symmetry3DChooseHandler.class, this);
    dispatcher.addHandler(Symmetry4DChooseHandler.class, this);
    dispatcher.addHandler(RunHandler.class, this);
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
      SchlegelTransformer st = new SchlegelTransformer(images);
      pmWrapper.sendRequest(st);
      resProducer.submit(st);
      // dispatcher.fireEvent(new SchlegelResultEvent(st));
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
    private final BlockingQueue<SchlegelTransformer> pending =
        new LinkedBlockingQueue<>();

    protected void submit(final SchlegelTransformer st) {
      this.pending.add(st);
    }

    @Override
    public void run() {
      // TODO: Termination
      SchlegelTransformer st;
      while (true) {
        try {
          st = pending.take();
          buf.add(st.get());
        }
        catch (InterruptedException e) {
          // Handle termination
        }
        catch (ExecutionException e) {
          e.printStackTrace();
        }
      }
    }
  }


  protected class ResultConsumer
    extends Thread
  {

    @Override
    public void run() {
      // TODO: Termination
      while (true) {
        try {
          dispatcher.fireEvent(new SchlegelResultEvent(buf.take()));
        }
        catch (InterruptedException e) {
          // TODO: Termination
        }
      }
    }
  }

}
