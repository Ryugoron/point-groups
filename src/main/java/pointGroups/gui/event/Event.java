package pointGroups.gui.event;

/**
 * Objects of type {@link Event} capture system state changes and lead to
 * reactions at their appropriate {@link EventHandler} whose type is passed as
 * generic argument. Events get dispatched by the {@link EventDispatcher}.
 * 
 * @author Marcel, Alex
 * @param <H> The {@link EventHandler} type responsible for dealing with the
 *          event
 * @see EventDispatcher
 */
public abstract class Event<H extends EventHandler>
{
  protected Event() {
  }

  /**
   * Returns the type of the {@link Event}s associated {@link EventHandler}.
   * 
   * @return The {@link Class} representing the handler for this event
   */
  public abstract Class<H> getType();

  /**
   * Dispatches the event to the passed {@link EventHandler}. This method is
   * usually called by {@link EventDispatcher}.
   * 
   * @param handler The handler that is invoked on dispatch
   */
  protected abstract void dispatch(final H handler);
}
