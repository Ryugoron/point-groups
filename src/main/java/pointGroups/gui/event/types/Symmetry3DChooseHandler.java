package pointGroups.gui.event.types;

import pointGroups.gui.event.Event;
import pointGroups.gui.event.EventHandler;


public final class Symmetry3DChooseHandler
  implements EventHandler
{
  Symmetry3DChooseListener listener;

  public Symmetry3DChooseHandler(Symmetry3DChooseListener listener) {
    this.listener = listener;
  }

  @Override
  public Class<? extends Event> getEventType() {
    return Symmetry3DChooseEvent.class;
  }

  @Override
  public void dispatchEvent(Event e) {
    listener.onSymmetry3DChooseEvent((Symmetry3DChooseEvent) e);
  }
}
