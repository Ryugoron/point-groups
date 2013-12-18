package pointGroups.gui.event.types;

import pointGroups.gui.event.Event;
import pointGroups.gui.event.EventHandler;


public abstract class Symmetry3DChooseHandler
  implements EventHandler
{

  @Override
  public Class<? extends Event> getEventType() {
    return Symmetry3DChooseEvent.class;
  }

  public abstract void onSymmetry3DChooseEvent(Symmetry3DChooseEvent event);
}
