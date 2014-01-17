package pointGroups.gui.event.types;

import pointGroups.gui.event.Event;


public class ShowNextEvent
  extends Event<ShowNextHandler>
{
  public final static Class<ShowNextHandler> TYPE = ShowNextHandler.class;

  @Override
  public Class<ShowNextHandler> getType() {
    return TYPE;
  }

  @Override
  protected void dispatch(ShowNextHandler handler) {
    handler.onNextEvent(this);
  }
}
