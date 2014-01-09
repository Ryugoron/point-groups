package pointGroups.gui.event.types;

import pointGroups.gui.event.Event;


public class UndoEvent
  extends Event<UndoHandler>
{

  public final static Class<UndoHandler> TYPE = UndoHandler.class;

  @Override
  public Class<UndoHandler> getType() {
    return TYPE;
  }

  @Override
  protected void dispatch(UndoHandler handler) {
    handler.onUndoEvent(this);

  }

}
