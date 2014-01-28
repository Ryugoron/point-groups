package pointGroups.gui.event.types;

import pointGroups.geometry.Schlegel;
import pointGroups.gui.ExternalCalculationEventHub;
import pointGroups.gui.event.Event;


/**
 * This {@link Event} is fired if a calculation for a previously selected
 * symmetry and point is was finished. This event is ordinarily fired by the
 * {@link ExternalCalculationEventHub}.
 * 
 * @author Alex
 */
public class SchlegelResultEvent
  extends Event<SchlegelResultHandler>
{
  public final static Class<SchlegelResultHandler> TYPE =
      SchlegelResultHandler.class;

  private final Schlegel s;

  /**
   * Creates a new {@link SchlegelResultEvent} with given {@link Schlegel}
   * object.
   * 
   * @param st the schlegel result
   */
  public SchlegelResultEvent(final Schlegel s) {
    this.s = s;
  }

  /**
   * Returns the {@link Schlegel} object that was calculated by polymake.
   * 
   * @return the schlegel result
   */
  public Schlegel getResult() {
    return s;
  }

  @Override
  public Class<SchlegelResultHandler> getType() {
    return TYPE;
  }

  @Override
  protected void dispatch(final SchlegelResultHandler handler) {
    handler.onSchlegelResultEvent(this);
  }

}
