package pointGroups.gui.event.types;

import pointGroups.gui.event.Event;

public class ScaleFundamentalDomainEvent extends Event<ScaleFundamentalDomainHandler>
{

  public final static Class<ScaleFundamentalDomainHandler> TYPE =
      ScaleFundamentalDomainHandler.class;
  private int scale;
  
  public ScaleFundamentalDomainEvent(int scale){
    this.scale = scale;
  }
  
  public int getScale(){
    return scale;
  }
  
  @Override
  public Class<ScaleFundamentalDomainHandler> getType() {
    return TYPE;
  }

  

  @Override
  protected void dispatch(ScaleFundamentalDomainHandler handler) {
    handler.onScaleFundamentalDomainEvent(this);
  }

}
