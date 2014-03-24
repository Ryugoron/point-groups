package pointGroups.gui.event.types;

import pointGroups.geometry.Symmetry;
import pointGroups.geometry.Point;
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
  private final Point p;
  private final Symmetry<?> sym;

  /**
   * Creates a new {@link SchlegelResultEvent} with given {@link Schlegel},
   * {@link Point} and {@link Symmetry} objects.
   * 
   * @param s the schlegel result
   * @param p the initial point
   * @param sym the associated symmetry
   */
  public SchlegelResultEvent(final Schlegel s, final Point p,
      final Symmetry<?> sym) {
    this.s = s;
    this.p = p;
    this.sym = sym;
  }

  /**
   * Returns the {@link Schlegel} object that was calculated by polymake.
   * 
   * @return the schlegel result
   */
  public Schlegel getResult() {
    return s;
  }

  /**
   * Returns the {@link Point} that resulted in the {@link Schlegel} diagram
   * given by {@link #getSchlegel()}.
   * 
   * @return The initial point
   */
  public Point getPickedPoint() {
    return p;
  }

  /**
   * Returns the {@link Symmetry} that was used to construct the images of
   * the {@link Point} given by {@link #getPickedPoint()}.
   * 
   * @return The chosen symmetry
   */
  public Symmetry<?> getPickedSymmetry() {
    return sym;
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
