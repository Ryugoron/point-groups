/**
 * 
 */
package pointGroups.gui.event.types;

import pointGroups.gui.event.Event;


/**
 * @author Nadja
 */
public class TutorialEvent
  extends Event<TutorialHandler>
{
  public final static Class<TutorialHandler> TYPE = TutorialHandler.class;

  @Override
  public Class<TutorialHandler> getType() {
    return TYPE;
  }

  @Override
  protected void dispatch(TutorialHandler handler) {
    handler.onTutorialEvent(this);
  }
}
