package pointGroups.gui.event.types.gui;

import pointGroups.gui.SchlegelView.SchlegelViewMode;
import pointGroups.gui.event.Event;


/**
 * @author Marcel
 */
public class SchlegelViewModeChangedEvent
  extends Event<SchlegelViewModeChangedHandler>
{

  protected SchlegelViewMode schlegelViewMode;

  public final static Class<SchlegelViewModeChangedHandler> TYPE =
      SchlegelViewModeChangedHandler.class;

  public SchlegelViewModeChangedEvent(final SchlegelViewMode schlegelViewMode) {
    this.schlegelViewMode = schlegelViewMode;
  }

  public SchlegelViewMode getViewMode() {
    return schlegelViewMode;
  }

  @Override
  public final Class<SchlegelViewModeChangedHandler> getType() {
    return TYPE;
  }

  @Override
  protected void dispatch(final SchlegelViewModeChangedHandler handler) {
    handler.onSchlegelViewModeChanged(this);
  }
}
