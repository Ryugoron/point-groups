package pointGroups.gui.event.types;

import pointGroups.geometry.Point3D;
import pointGroups.geometry.Point4D;
import pointGroups.geometry.Symmetry;
import pointGroups.gui.event.Event;


public class Point4DPickedEvent
  extends Event<Point4DPickedHandler>
{
  private final Symmetry4DChooseEvent symmetryEvent;
  private final Point3D pickedPoint;

  public final static Class<Point4DPickedHandler> TYPE =
      Point4DPickedHandler.class;

  public Point4DPickedEvent(final Symmetry4DChooseEvent symmetryEvent,
      final Point3D pickedPoint) {
    this.symmetryEvent = symmetryEvent;
    this.pickedPoint = pickedPoint;
  }

  public Point3D getPickedPoint() {
    return pickedPoint;
  }

  public Symmetry<Point4D, ?> getSymmetry4D() {
    return symmetryEvent.getSymmetry4D();
  }

  public String getSubgroup() {
    return this.symmetryEvent.getSubgroup();
  }

  @Override
  public final Class<Point4DPickedHandler> getType() {
    return TYPE;
  }

  @Override
  protected void dispatch(final Point4DPickedHandler handler) {
    handler.onPoint4DPickedEvent(this);
  }
}
