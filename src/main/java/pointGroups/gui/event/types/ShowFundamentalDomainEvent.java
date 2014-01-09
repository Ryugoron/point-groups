package pointGroups.gui.event.types;

import pointGroups.gui.event.Event;


public class ShowFundamentalDomainEvent
  extends Event<ShowFundamentalDomainHandler>
{
  private boolean visible;

  public ShowFundamentalDomainEvent(boolean visible) {
    this.visible = visible;
  }

  public final static Class<ShowFundamentalDomainHandler> TYPE =
      ShowFundamentalDomainHandler.class;

  @Override
  public Class<ShowFundamentalDomainHandler> getType() {
    return TYPE;
  }

  @Override
  protected void dispatch(ShowFundamentalDomainHandler handler) {
    handler.onShowFundamentalDomainEvent(this);
  }

  public boolean getVisible() {
    return visible;
  }

}
