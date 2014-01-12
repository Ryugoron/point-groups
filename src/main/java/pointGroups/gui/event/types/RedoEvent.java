package pointGroups.gui.event.types;

import pointGroups.gui.event.Event;


public class RedoEvent
  extends Event<RedoHandler>
{
  public final static Class<RedoHandler> TYPE = RedoHandler.class;

  @Override
  public Class<RedoHandler> getType() {
    return TYPE;
  }

  @Override
  protected void dispatch(RedoHandler handler) {
    handler.onRedoEvent(this);
  }
}
