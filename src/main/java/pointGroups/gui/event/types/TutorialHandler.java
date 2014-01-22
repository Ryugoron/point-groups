/**
 * 
 */
package pointGroups.gui.event.types;

import pointGroups.gui.event.EventHandler;


/**
 * @author Nadja
 */
public interface TutorialHandler
  extends EventHandler
{
  public void onTutorialEvent(TutorialEvent event);
}
