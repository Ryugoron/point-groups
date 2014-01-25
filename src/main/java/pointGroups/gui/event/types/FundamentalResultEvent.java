package pointGroups.gui.event.types;

import pointGroups.geometry.Fundamental;
import pointGroups.gui.event.Event;

public class FundamentalResultEvent extends Event<FundamentalResultHandler> {
	
	public final static Class<FundamentalResultHandler> TYPE = FundamentalResultHandler.class;

	private final Fundamental f;
	
	public FundamentalResultEvent(final Fundamental f){
		this.f = f;
	}
	
	@Override
	public Class<FundamentalResultHandler> getType() {
		return TYPE;
	}

	@Override
	protected void dispatch(FundamentalResultHandler handler) {
		handler.onSchlegelResultEvent(this);

	}
	
	public Fundamental getResult(){
		return this.f;
	}

}
