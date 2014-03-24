package pointGroups.gui.event.types;

import pointGroups.geometry.Point3D;
import pointGroups.gui.event.Event;


public class ChangeCoordinate3DPointEvent
  extends Event<ChangeCoordinate3DPointHandler>
{
  private final Point3D pickedPoint;
  private final Object src;

  public final static Class<ChangeCoordinate3DPointHandler> TYPE =
      ChangeCoordinate3DPointHandler.class;

  public ChangeCoordinate3DPointEvent(final Point3D pickedPoint,
      final Object src) {
    this.pickedPoint = pickedPoint;
    this.src = src;
  }

  public Point3D getPickedPoint() {
    return pickedPoint;
  }

  public Object getSource() {
    return src;
  }

  public boolean isSource(final Object candidate) {
    return src.equals(candidate);
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
