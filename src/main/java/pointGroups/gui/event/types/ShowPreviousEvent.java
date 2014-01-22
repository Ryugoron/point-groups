package pointGroups.gui.event.types;

import pointGroups.gui.event.Event;


public class ShowPreviousEvent
  extends Event<ShowPreviousHandler>
{

  public final static Class<ShowPreviousHandler> TYPE =
      ShowPreviousHandler.class;

  @Override
  public Class<ShowPreviousHandler> getType() {
    return TYPE;
  }

  @Override
  protected void dispatch(ShowPreviousHandler handler) {
    handler.onPreviousEvent(this);

  }

}
