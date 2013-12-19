package pointGroups.gui.event.types;

import pointGroups.geometry.Point3D;
import pointGroups.geometry.Symmetry;
import pointGroups.gui.event.Event;


public class Symmetry3DChooseEvent
  extends Event<Symmetry3DChooseHandler>
{

  protected Symmetry<Point3D, ?> symmetry;

  public Symmetry3DChooseEvent(Symmetry<Point3D, ?> symmetry) {
    this.symmetry = symmetry;
  }

  public Symmetry<Point3D, ?> getSymmetry3D() {
    return symmetry;
  }

  @Override
  public Class<Symmetry3DChooseHandler> getType() {
    return Symmetry3DChooseHandler.class;
  }

  @Override
  protected void dispatch(Symmetry3DChooseHandler handler) {
    handler.onSymmetry3DChooseEvent(this);
  }
}
