package pointGroups.gui.event.types;

import pointGroups.gui.event.Event;

/**
 * @author Simon
 */
public class ShowTutorialEvent
  extends Event<ShowTutorialHandler>
{ 
  public final static Class<ShowTutorialHandler> TYPE = ShowTutorialHandler.class;

  @Override
  public Class<ShowTutorialHandler> getType() {
    return TYPE;
  }

  @Override
  protected void dispatch(ShowTutorialHandler handler) {
    handler.onShowTutorialEvent(this);
  }
}
