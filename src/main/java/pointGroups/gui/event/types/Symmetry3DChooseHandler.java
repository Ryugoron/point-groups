package pointGroups.gui.event.types;

import pointGroups.gui.event.EventHandler;


/**
 * Implementing classes are a {@link EventHandler} for the
 * {@link Symmetry3DChooseEvent}. This handlers a invoked when a symmetry of 3D
 * point type was chosen.
 * 
 * @author Marcel, Alex
 */
public interface Symmetry3DChooseHandler
  extends EventHandler
{

  /**
   * This method is invoked while dispatching a previously fired
   * {@link Symmetry3DChooseEvent}.
   * 
   * @param event The event received
   */
  public void onSymmetry3DChooseEvent(final Symmetry3DChooseEvent event);

}
