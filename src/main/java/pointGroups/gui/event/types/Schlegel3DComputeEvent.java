package pointGroups.gui.event.types;

import pointGroups.geometry.Symmetry;
import pointGroups.geometry.Point3D;
import pointGroups.gui.event.Event;


public class Schlegel3DComputeEvent
  extends Event<Schlegel3DComputeHandler>
{
  public final static Class<Schlegel3DComputeHandler> TYPE =
      Schlegel3DComputeHandler.class;
  private final Symmetry3DChooseEvent symmetryEvent;
  private final Point3D pickedPoint;

  public Schlegel3DComputeEvent(
      final Symmetry3DChooseEvent symmetry3dChooseEvent,
      final Point3D pickedPoint) {
    this.pickedPoint = pickedPoint;
    symmetryEvent = symmetry3dChooseEvent;
  }

  @Override
  public Class<Schlegel3DComputeHandler> getType() {
    return TYPE;
  }

  @Override
  protected void dispatch(final Schlegel3DComputeHandler handler) {
    handler.onSchlegel3DComputeEvent(this);
  }

  public Point3D getPickedPoint() {
    return pickedPoint;
  }

  public Symmetry<Point3D> getSymmetry3D() {
    return symmetryEvent.getSymmetry3D();
  }
}
