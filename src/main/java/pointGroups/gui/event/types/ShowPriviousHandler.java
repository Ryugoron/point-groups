package pointGroups.gui.event.types;

import pointGroups.gui.event.EventHandler;


/**
 * @author Oliver, Nadja
 */
public interface ShowPriviousHandler
  extends EventHandler
{
  /**
   * This method is invoked while dispatching a previously fired
   * {@link ShowPreviousEvent}.
   * 
   * @param event The event received
   */
  public void onUndoEvent(final ShowPreviousEvent event);

}
