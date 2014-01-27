package pointGroups.gui.event.types;

import pointGroups.geometry.Point4D;
import pointGroups.geometry.Symmetry;
import pointGroups.gui.event.Event;


public class ChangeCoordinate4DPointEvent
  extends Event<ChangeCoordinate4DPointHandler>
{
  private final Symmetry4DChooseEvent symmetryEvent;
  private final Point4D pickedPoint;
  private final Object src;

  public final static Class<ChangeCoordinate4DPointHandler> TYPE =
      ChangeCoordinate4DPointHandler.class;

  public ChangeCoordinate4DPointEvent(final Symmetry4DChooseEvent symmetryEvent,
      final Point4D pickedPoint, Object src) {
    this.symmetryEvent = symmetryEvent;
    this.pickedPoint = pickedPoint;
    this.src = src;
  }

  public Point4D getPickedPoint() {
    return pickedPoint;
  }

  public Symmetry<Point4D, ?> getSymmetry4D() {
    return symmetryEvent.getSymmetry4D();
  }

  public String getSubgroup() {
    return this.symmetryEvent.getSubgroup();
  }
  public Object getSource(){
    return src;
  }
  
  public boolean isSource(Object candidate){
    return src.equals(candidate);
  }
  @Override
  public final Class<ChangeCoordinate4DPointHandler> getType() {
    return TYPE;
  }

  @Override
  protected void dispatch(final ChangeCoordinate4DPointHandler handler) {
    handler.onChangeCoordinate4DPointEvent(this);
  }
}

