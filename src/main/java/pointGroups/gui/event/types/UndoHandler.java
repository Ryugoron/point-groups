package pointGroups.gui.event.types;

import pointGroups.gui.event.EventHandler;


/**
 * @author Oliver, Nadja
 */
public interface UndoHandler
  extends EventHandler
{
  /**
   * This method is invoked while dispatching a previously fired
   * {@link UndoEvent}.
   * 
   * @param event The event received
   */
  public void onUndoEvent(final UndoEvent event);

}
