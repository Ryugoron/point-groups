package pointGroups.gui.event;

import static org.junit.Assert.assertEquals;

import org.junit.Test;

import pointGroups.geometry.Point3D;
import pointGroups.geometry.Symmetry;
import pointGroups.geometry.symmetries.Symmetry3D;
import pointGroups.gui.event.types.Symmetry3DChooseEvent;
import pointGroups.gui.event.types.Symmetry3DChooseHandler;


public class EventFlowTest
{
  static int onSymmetry3DChooseEventCalled = 0;


  public static class Main
  {
    EventDispatcher dispatcher = new EventDispatcher();
  }


  public static class SymmetryChooser
  {
    EventDispatcher dispatcher;

    public SymmetryChooser(final EventDispatcher dispatcher) {
      this.dispatcher = dispatcher;
    }

    public void changeSymmetry(final Symmetry<Point3D> symmetry) {
      dispatcher.fireEvent(new Symmetry3DChooseEvent(symmetry));
    }
  }


  public static class PointPicker
    implements Symmetry3DChooseHandler
  {
    EventDispatcher dispatcher;
    Symmetry<Point3D> symmetry;

    public PointPicker(final EventDispatcher dispatcher) {
      dispatcher.addHandler(Symmetry3DChooseEvent.TYPE, this);

      this.dispatcher = dispatcher;
    }

    @Override
    public void onSymmetry3DChooseEvent(final Symmetry3DChooseEvent event) {
      onSymmetry3DChooseEventCalled++;

      symmetry = event.getSymmetry3D();

      assertEquals("the event should contain the right symmetry", Symmetry3D.T,
          symmetry);
    }
  }

  @Test
  public void testWorkflow() {
    Main main = new Main();
    SymmetryChooser chooser = new SymmetryChooser(main.dispatcher);
    PointPicker picker = new PointPicker(main.dispatcher);

    chooser.changeSymmetry(Symmetry3D.T);

    assertEquals(
        "PointPicker#onSymmetry3DChooseEvent should be called at least once",
        1, onSymmetry3DChooseEventCalled);

    assertEquals("PointPicker should have the right symmetry assigned",
        Symmetry3D.T, picker.symmetry);
  }
}
