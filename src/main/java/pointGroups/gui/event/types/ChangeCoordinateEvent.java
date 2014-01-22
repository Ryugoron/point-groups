package pointGroups.gui.event.types;

import pointGroups.gui.event.Event;


public class ChangeCoordinateEvent
  extends Event<ChangeCoordinateHandler>
{
  public final static Class<ChangeCoordinateHandler> TYPE =
      ChangeCoordinateHandler.class;

  private double[] coords;

  public ChangeCoordinateEvent(double[] coords) {
    this.coords = coords;
  }

  public double[] getCoordinates() {
    return coords;
  }

  public int getDimension() {
    return coords.length;
  }

  @Override
  public Class<ChangeCoordinateHandler> getType() {
    return TYPE;
  }

  @Override
  protected void dispatch(ChangeCoordinateHandler handler) {
    handler.onChangeCoordinateEvent(this);
  }

}
