package pointGroups.gui.event.types;

import pointGroups.geometry.Point3D;
import pointGroups.geometry.Symmetry;
import pointGroups.gui.event.Event;


public class Symmetry3DChooseEvent
  extends Event<Symmetry3DChooseHandler>
{
  protected final Symmetry<Point3D, ?> symmetry;
  public final static Class<Symmetry3DChooseHandler> TYPE =
      Symmetry3DChooseHandler.class;

  public Symmetry3DChooseEvent(final Symmetry<Point3D, ?> symmetry) {
    this.symmetry = symmetry;
  }

  public Symmetry<Point3D, ?> getSymmetry3D() {
    return symmetry;
  }

  @Override
  public final Class<Symmetry3DChooseHandler> getType() {
    return TYPE;
  }

  @Override
  protected void dispatch(final Symmetry3DChooseHandler handler) {
    handler.onSymmetry3DChooseEvent(this);
  }
}
