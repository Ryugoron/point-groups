package pointGroups.gui.event.types;

import pointGroups.geometry.Point4D;
import pointGroups.geometry.Symmetry;
import pointGroups.geometry.Symmetry.Subgroup;
import pointGroups.gui.event.Event;


/**
 * This event is supposed to be fired, when the current symmetry was changed to
 * another {@link Symmetry} of {@link Point4D} type. The chosen symmetry can be
 * retrieved by {@link #getSymmetry4D()}, the subgroup by {@link #getSubgroup()}
 * 
 * @author Alex
 */
public class Symmetry4DChooseEvent
  extends Event<Symmetry4DChooseHandler>
{
  protected final Symmetry<Point4D, ?> symmetry;
  protected final String subgroup;

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
   * @param subgroup The subgroup of the symmetry
   */
  public Symmetry4DChooseEvent(final Symmetry<Point4D, ?> symmetry,
      final String subgroup) {
    this.symmetry = symmetry;
    this.subgroup = subgroup;
  }

  /**
   * The chosen {@link Symmetry} that caused this event to be fired.
   * 
   * @return The chosen symmetry.
   */
  public Symmetry<Point4D, ?> getSymmetry4D() {
    return symmetry;
  }

  /**
   * The chosen subgroup of the event's associated {@link Symmetry}
   * 
   * @return The chosen symmetry.
   */
  @SuppressWarnings("unchecked")
  public Symmetry.Subgroup<Symmetry<Point4D, ?>> getSubgroup() {
    // cast is 'safe' if constructor is called correctly
    return (Subgroup<Symmetry<Point4D, ?>>) symmetry.getSubgroupByName(this.subgroup);
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
