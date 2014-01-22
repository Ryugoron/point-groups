package pointGroups.gui.event.types;

import pointGroups.gui.PolymakeHub;
import pointGroups.gui.event.Event;
import pointGroups.util.polymake.SchlegelTransformer;


/**
 * This {@link Event} is fired if a calculation for a previously selected
 * symmetry and point is supposed to be started. This event is ordinarily fired
 * by the {@link PolymakeHub}.
 * 
 * @author Alex
 */
public class SchlegelResultEvent
  extends Event<SchlegelResultHandler>
{
  public final static Class<SchlegelResultHandler> TYPE =
      SchlegelResultHandler.class;

  private final SchlegelTransformer st;

  /**
   * Create a new {@link SchlegelResultEvent} with given
   * {@link SchlegelTransformer}. The actual calculation may not be finished at
   * the time of arrival. {@link SchlegelTransformer#get()} can be used to get
   * the desired result itself.
   * 
   * @param st the schlegel transformer
   */
  public SchlegelResultEvent(final SchlegelTransformer st) {
    this.st = st;
  }

  /**
   * Returns the {@link SchlegelTransformer} that has been submitted for
   * calculation to polymake.
   * 
   * @return the schlegel transformer
   */
  public SchlegelTransformer getSchlegelTransformer() {
    return st;
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
