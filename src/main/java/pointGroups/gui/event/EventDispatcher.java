package pointGroups.gui.event;

import java.util.Collection;
import java.util.LinkedList;
import java.util.Map;

import pointGroups.gui.event.types.HasSymmetry3DChooseHandlers;
import pointGroups.gui.event.types.Symmetry3DChooseEvent;
import pointGroups.gui.event.types.Symmetry3DChooseHandler;


public class EventDispatcher
  implements HasSymmetry3DChooseHandlers
{

  public Map<Class<? extends Event>, Collection<EventHandler>> eventTypeMap;
  
  protected void add(Class<? extends Event> clazz, EventHandler handler) {
    Collection<EventHandler> handlers = eventTypeMap.get(clazz);

    if (handlers == null) {
      handlers = new LinkedList<EventHandler>();
    }

    handlers.add(handler);

    eventTypeMap.put(clazz, handlers);
  }

  protected void remove(Class<? extends Event> clazz, EventHandler handler) {
    // TODO
  }

  @Override
  public void addSymmetry3DChooseHandler(Symmetry3DChooseHandler handler) {
    // TODO: get the event type from the handler, which delivers the class of
    // Symmetry3DChooseEvent
    add(Symmetry3DChooseEvent.class, handler);
  }

  @Override
  public void removeSymmetry3DChooseHandler(Symmetry3DChooseHandler handler) {
    // TODO @see addSymmetry3DChooseHandler
    remove(Symmetry3DChooseEvent.class, handler);
  }

  @Override
  public void dispatchEvent(Event e) {
    for (EventHandler handler : eventTypeMap.get(e.getClass())) {
      // want to call onSymmetryChanged somehow
    }
  }
}
