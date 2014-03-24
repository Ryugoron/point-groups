package pointGroups.gui.event.types;

import pointGroups.geometry.Symmetry;
import pointGroups.geometry.Point4D;
import pointGroups.gui.event.Event;


/**
 * This event is supposed to be fired, when the current symmetry was changed to
 * another {@link Symmetry} of {@link Point4D} type. The chosen symmetry can
 * be retrieved by {@link #getSymmetry4D()}.
 * 
 * @author Alex
 */
public class Symmetry4DChooseEvent
  extends Event<Symmetry4DChooseHandler>
{
  protected final Symmetry<Point4D> symmetry;

  /**
   * The type of the corresponding {@link Symmetry4DChooseHandler}.
   */
  public final static Class<Symmetry4DChooseHandler> TYPE =
      Symmetry4DChooseHandler.class;

  /**
   * Creates a new {@link Symmetry4DChooseEvent} with given {@link Symmetry}.
   * The subgroup parameter must be set to a valid subgroup of the concrete
   * Symmetry symmetry.
   * 
   * @param symmetry The symmetry chosen
   */
  public Symmetry4DChooseEvent(final Symmetry<Point4D> symmetry) {
    this.symmetry = symmetry;
  }

  /**
   * The chosen {@link Symmetry} that caused this event to be fired.
   * 
   * @return The chosen symmetry.
   */
  public Symmetry<Point4D> getSymmetry4D() {
    return symmetry;
  }

  @Override
  public final Class<Symmetry4DChooseHandler> getType() {
    return TYPE;
  }

  @Override
  protected void dispatch(final Symmetry4DChooseHandler handler) {
    handler.onSymmetry4DChooseEvent(this);
  }
}
