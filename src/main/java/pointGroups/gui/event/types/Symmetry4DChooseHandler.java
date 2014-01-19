package pointGroups.gui.event.types;

import pointGroups.gui.event.EventHandler;


/**
 * Implementing classes are a {@link EventHandler} for the
 * {@link Symmetry4DChooseEvent}. This handlers a invoked when a symmetry of 4D
 * point type was chosen.
 * 
 * @author Alex
 */
public interface Symmetry4DChooseHandler
  extends EventHandler
{

  /**
   * This method is invoked while dispatching a previously fired
   * {@link Symmetry4DChooseEvent}.
   * 
   * @param event The event received
   */
  public void onSymmetry4DChooseEvent(final Symmetry4DChooseEvent event);

}
