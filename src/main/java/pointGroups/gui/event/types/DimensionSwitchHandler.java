package pointGroups.gui.event.types;

import pointGroups.gui.event.EventHandler;


/**
 * Implementing classes are a {@link EventHandler} for the
 * {@link DimensionSwitchEvent}. This handlers are invoked when the user changes
 * the dimension in the gui.
 * 
 * @author Alex
 */
public interface DimensionSwitchHandler
  extends EventHandler
{

  /**
   * This method is invoked while dispatching a previously fired
   * {@link DimensionSwitchEvent}.
   * 
   * @param event The event received
   */
  public void onDimensionSwitchEvent(final DimensionSwitchEvent event);

}