package pointGroups.gui.event;

import java.util.Collection;
import java.util.Collections;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.Map;


public class EventDispatcher
{

  public Map<Class<? extends EventHandler>, Collection<? extends EventHandler>> eventTypeHandlerMap =
      new HashMap<>();

  public <H extends EventHandler> void addHandler(final Class<H> handlerType,
      final H handler) {
    Collection<H> handlers = forceHandlerCollection(handlerType);
    handlers.add(handler);
  }

  public <H extends EventHandler> void removeHandler(
      final Class<H> handlerType, final H handler) {
    Collection<H> handlers = forceHandlerCollection(handlerType);
    handlers.remove(handler);
  }

  public void fireEvent(final Event<?> event) {
    fireEvent0(event);
  }

  private <H extends EventHandler> void fireEvent0(final Event<H> event) {
    Collection<H> handlers = this.getHandlers(event.getType());

    for (H h : handlers) {
      event.dispatch(h);
    }
  }

  private <H extends EventHandler> Collection<H> forceHandlerCollection(
      final Class<H> handlerType) {
    @SuppressWarnings("unchecked")
    Collection<H> existingHandlers =
        (Collection<H>) eventTypeHandlerMap.get(handlerType);

    if (existingHandlers == null) {
      existingHandlers = new LinkedList<>();
      eventTypeHandlerMap.put(handlerType, existingHandlers);
    }

    return existingHandlers;
  }

  private <H extends EventHandler> Collection<H> getHandlers(
      final Class<H> handlerType) {
    @SuppressWarnings("unchecked")
    Collection<H> handlers =
        (Collection<H>) eventTypeHandlerMap.get(handlerType);
    if (handlers == null) {
      return Collections.emptyList();
    }
    else {
      return handlers;
    }
  }
}
