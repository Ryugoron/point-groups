/**
 * 
 */
package pointGroups.gui.event.types;

import pointGroups.gui.event.Event;


/**
 * @author Nadja
 */
public class ShowLogEvent
  extends Event<ShowLogHandler>
{
  public final static Class<ShowLogHandler> TYPE = ShowLogHandler.class;

  @Override
  public Class<ShowLogHandler> getType() {
    return TYPE;
  }

  @Override
  protected void dispatch(ShowLogHandler handler) {
    handler.onShowLogEvent(this);
  }
}
