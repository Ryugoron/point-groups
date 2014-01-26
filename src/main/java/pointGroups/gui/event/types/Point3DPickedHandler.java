package pointGroups.gui.event.types;

import pointGroups.gui.event.EventHandler;

public interface Point3DPickedHandler
  extends EventHandler
{

  public void onPoint3DPickedEvent(final Point3DPickedEvent event);

}
