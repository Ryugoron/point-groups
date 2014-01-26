package pointGroups.gui.event.types;

import pointGroups.gui.event.EventHandler;

public interface Point4DPickedHandler
  extends EventHandler
{

  public void onPoint4DPickedEvent(final Point4DPickedEvent event);

}
