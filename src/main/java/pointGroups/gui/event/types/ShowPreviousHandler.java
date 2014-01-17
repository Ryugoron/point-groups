package pointGroups.gui.event.types;

import pointGroups.gui.event.EventHandler;


/**
 * @author Oliver, Nadja
 */
public interface ShowPreviousHandler
  extends EventHandler
{
  /**
   * This method is invoked while dispatching a previously fired
   * {@link ShowPreviousEvent}.
   * 
   * @param event The event received
   */
  public void onPreviousEvent(final ShowPreviousEvent event);

}
