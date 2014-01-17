package pointGroups.gui.event.types;

import pointGroups.gui.event.Event;


public class RunEvent
  extends Event<RunHandler>
{
  public final static Class<RunHandler> TYPE = RunHandler.class;

  private double[] coords;

  public RunEvent(double[] coords) {
    this.coords = coords;
  }

  public double[] getCoordinates() {
    return coords;
  }

  public int getDimension() {
    return coords.length;
  }

  @Override
  public Class<RunHandler> getType() {
    return TYPE;
  }

  @Override
  protected void dispatch(RunHandler handler) {
    handler.onRunEvent(this);
  }

}
