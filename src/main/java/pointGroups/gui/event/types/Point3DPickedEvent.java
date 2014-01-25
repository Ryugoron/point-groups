package pointGroups.gui.event.types;

import pointGroups.geometry.Point2D;
import pointGroups.geometry.Point3D;
import pointGroups.geometry.Symmetry;
import pointGroups.gui.event.Event;


public class Point3DPickedEvent
  extends Event<Point3DPickedHandler>
{
  private final Symmetry3DChooseEvent symmetryEvent;
  private final Point2D pickedPoint;

  public final static Class<Point3DPickedHandler> TYPE =
      Point3DPickedHandler.class;

  public Point3DPickedEvent(final Symmetry3DChooseEvent symmetryEvent,
      final Point2D pickedPoint) {
    this.symmetryEvent = symmetryEvent;
    this.pickedPoint = pickedPoint;
  }

  public Point2D getPickedPoint() {
    return pickedPoint;
  }

  public Symmetry<Point3D, ?> getSymmetry3D() {
    return symmetryEvent.getSymmetry3D();
  }

  public String getSubgroup() {
    return this.symmetryEvent.getSubgroup();
  }

  @Override
  public final Class<Point3DPickedHandler> getType() {
    return TYPE;
  }

  @Override
  protected void dispatch(final Point3DPickedHandler handler) {
    handler.onPoint3DPickedEvent(this);
  }
}
