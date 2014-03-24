package pointGroups.gui.event.types;

import pointGroups.geometry.Symmetry;
import pointGroups.geometry.Point4D;
import pointGroups.gui.event.Event;


public class Schlegel4DComputeEvent
  extends Event<Schlegel4DComputeHandler>
{

  public final static Class<Schlegel4DComputeHandler> TYPE =
      Schlegel4DComputeHandler.class;
  private final Symmetry4DChooseEvent symmetryEvent;
  private final Point4D pickedPoint;

  public Schlegel4DComputeEvent(
      final Symmetry4DChooseEvent symmetry3dChooseEvent,
      final Point4D pickedPoint) {
    this.pickedPoint = pickedPoint;
    symmetryEvent = symmetry3dChooseEvent;
  }

  @Override
  public Class<Schlegel4DComputeHandler> getType() {
    return TYPE;
  }

  @Override
  protected void dispatch(final Schlegel4DComputeHandler handler) {
    handler.onSchlegel4DComputeEvent(this);
  }

  public Point4D getPickedPoint() {
    return pickedPoint;
  }

  public Symmetry<Point4D> getSymmetry4D() {
    return symmetryEvent.getSymmetry4D();
  }
}
