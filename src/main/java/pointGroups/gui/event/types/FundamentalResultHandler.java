package pointGroups.gui.event.types;

import pointGroups.gui.event.EventHandler;


/**
 * 
 * Classes that implement this interface handle {@link FundamentalResultEvent}s
 * that contain a previously started computation of the Fundamental Domain.
 * 
 * @author max
 *
 */
public interface FundamentalResultHandler 
  extends EventHandler 
{
	
	public void onSchlegelResultEvent(final FundamentalResultEvent event);

}
