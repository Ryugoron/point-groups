package pointGroups.gui.event.types;

import pointGroups.gui.event.Event;


public class ShowPreviousEvent
  extends Event<ShowPriviousHandler>
{

  public final static Class<ShowPriviousHandler> TYPE = ShowPriviousHandler.class;

  @Override
  public Class<ShowPriviousHandler> getType() {
    return TYPE;
  }

  @Override
  protected void dispatch(ShowPriviousHandler handler) {
    handler.onUndoEvent(this);

  }

}
