package pointGroups.gui.event;

import java.util.Collection;
import java.util.Collections;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.Map;


/**
 * The EventDispatcher is the central object in the event handling flow that
 * accepts {@link Event}s that get fired and dispatches these events to their
 * according (registered) {@link EventHandler}. Event handler can be added and
 * removed using the {@link #addHandler(Class, EventHandler)} and
 * {@link #removeHandler(Class, EventHandler)} methods (respectively).
 * 
 * @author Marcel, Alex
 */
public class EventDispatcher
{
  protected final static EventDispatcher dispatcher = new EventDispatcher();

  public static EventDispatcher get() {
    return dispatcher;
  }

  public Map<Class<? extends EventHandler>, Collection<? extends EventHandler>> eventTypeHandlerMap =
      new HashMap<>();

  /**
   * Adds the specified {@link EventHandler} to be invoked on firing the
   * according {@link Event} with {@link #fireEvent(Event)}.
   * 
   * @param handlerType The type of the event handler
   * @param handler The handler to be added
   */
  public <H extends EventHandler> void addHandler(final Class<H> handlerType,
      final H handler) {
    Collection<H> handlers = forceHandlerCollection(handlerType);
    handlers.add(handler);
  }

  /**
   * Removes the specified {@link EventHandler} from the registered handlers.
   * After calling this method, that handler will not be invoked on firing the
   * according {@link Event} with {@link #fireEvent(Event)} anymore.
   * 
   * @param handlerType The type of the event handler
   * @param handler The handler to be removed
   */
  public <H extends EventHandler> void removeHandler(
      final Class<H> handlerType, final H handler) {
    Collection<H> handlers = forceHandlerCollection(handlerType);
    handlers.remove(handler);
  }

  /**
   * Fires an {@link Event} which will cause the {@link EventDispatcher} to
   * dispatch this event to all registered {@link EventHandler}s (of that
   * according type).
   * 
   * @param event The event to be fired
   */
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
    // Cast is safe, since addHandler checks the types
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
    // Cast is safe, since addHandler checks the types
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
