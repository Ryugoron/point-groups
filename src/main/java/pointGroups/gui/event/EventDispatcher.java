package pointGroups.gui.event;

import java.util.Collection;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.Map;

import pointGroups.gui.event.types.HasSymmetry3DChooseHandlers;
import pointGroups.gui.event.types.Symmetry3DChooseHandler;


public class EventDispatcher
  implements HasSymmetry3DChooseHandlers
{

  public Map<Class<? extends Event>, Collection<EventHandler>> eventTypeHandlerMap =
      new HashMap<>();
  
  protected void add(EventHandler handler) {
    Class<? extends Event> clazz = handler.getEventType();
    Collection<EventHandler> handlers = eventTypeHandlerMap.get(clazz);

    if (handlers == null) {
      handlers = new LinkedList<EventHandler>();
      eventTypeHandlerMap.put(clazz, handlers);
    }

    handlers.add(handler);
  }

  protected void remove(EventHandler handler) {
    Class<? extends Event> clazz = handler.getEventType();
    Collection<EventHandler> handlers = eventTypeHandlerMap.get(clazz);

    if (handlers == null) return;

    handlers.remove(handler);
  }

  @Override
  public void addSymmetry3DChooseHandler(Symmetry3DChooseHandler handler) {
    add(handler);
  }

  @Override
  public void removeSymmetry3DChooseHandler(Symmetry3DChooseHandler handler) {
    remove(handler);
  }

  @Override
  public void dispatchEvent(Event e) {
    for (EventHandler handler : eventTypeHandlerMap.get(e.getClass())) {
      handler.dispatchEvent(e);
    }
  }
}
