package pointGroups.gui.event.types;

import pointGroups.gui.event.Event;


public class ShowCoordinateEvent
  extends Event<ShowCoordinateHandler>
{
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

}
