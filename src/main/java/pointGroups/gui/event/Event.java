package pointGroups.gui.event;

public abstract class Event<H extends EventHandler>
{
  protected Event() {
  }

  public abstract Class<H> getType();

  protected abstract void dispatch(H handler);
}
