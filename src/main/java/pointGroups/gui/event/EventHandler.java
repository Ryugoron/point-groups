package pointGroups.gui.event;

public interface EventHandler
{
  public Class<? extends Event> getEventType();

  public void dispatchEvent(Event e);
}
