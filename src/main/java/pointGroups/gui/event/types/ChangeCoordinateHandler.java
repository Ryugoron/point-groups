package pointGroups.gui.event.types;

import pointGroups.gui.event.EventHandler;


public interface ChangeCoordinateHandler
  extends EventHandler
{
  public void onChangeCoordinateEvent(final ChangeCoordinateEvent event);
}
