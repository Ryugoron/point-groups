package pointGroups.gui.event.types;

import pointGroups.geometry.Point3D;
import pointGroups.geometry.Symmetry;
import pointGroups.gui.event.Event;


public class ChangeCoordinate3DPointEvent
  extends Event<ChangeCoordinate3DPointHandler>
{
  private final Symmetry3DChooseEvent symmetryEvent;
  private final Point3D pickedPoint;

  public final static Class<ChangeCoordinate3DPointHandler> TYPE =
      ChangeCoordinate3DPointHandler.class;

  public ChangeCoordinate3DPointEvent(final Symmetry3DChooseEvent symmetryEvent,
      final Point3D pickedPoint) {
    this.symmetryEvent = symmetryEvent;
    this.pickedPoint = pickedPoint;
  }

  public Point3D getPickedPoint() {
    return pickedPoint;
  }

  public Symmetry<Point3D, ?> getSymmetry3D() {
    return symmetryEvent.getSymmetry3D();
  }

  public String getSubgroup() {
    return this.symmetryEvent.getSubgroup();
  }

  @Override
  public final Class<ChangeCoordinate3DPointHandler> getType() {
    return TYPE;
  }

  @Override
  protected void dispatch(final ChangeCoordinate3DPointHandler handler) {
    handler.onChangeCoordinate3DPointEvent(this);
  }
}
