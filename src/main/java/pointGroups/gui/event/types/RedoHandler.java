package pointGroups.gui.event.types;

import pointGroups.gui.event.EventHandler;


public interface RedoHandler
  extends EventHandler
{
  public void onRedoEvent(RedoEvent event);
}
