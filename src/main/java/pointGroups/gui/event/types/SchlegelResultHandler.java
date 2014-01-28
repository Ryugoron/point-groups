package pointGroups.gui.event.types;

import pointGroups.gui.event.EventHandler;


/**
 * Classes that implement this interface handle {@link SchlegelResultEvent}s
 * that contain a previously started computation.
 * 
 * @author Alex
 */
public interface SchlegelResultHandler
  extends EventHandler
{
  /**
   * This method is executed if a {@link SchlegelResultEvent} is dispatched at
   * the {@link SchlegelResultHandler}.
   * 
   * @param event The event that has been fired
   */
  public void onSchlegelResultEvent(final SchlegelResultEvent event);

}
