package pointGroups.gui.event.types;

import pointGroups.gui.event.Event;


public class ShowCoordinateEvent
  extends Event<ShowCoordinateHandler>
{

  private boolean visible;

  public ShowCoordinateEvent(boolean visible) {
    this.visible = visible;
  }

  public final static Class<ShowCoordinateHandler> TYPE =
      ShowCoordinateHandler.class;

  @Override
  public Class<ShowCoordinateHandler> getType() {
    return TYPE;
  }

  @Override
  protected void dispatch(ShowCoordinateHandler handler) {
    handler.onShowCoordinateEvent(this);
  }

  /**
   * @return true if CoordinateView shall be shown, false otherwise
   */
  public boolean getVisible() {
    return visible;
  }

}
