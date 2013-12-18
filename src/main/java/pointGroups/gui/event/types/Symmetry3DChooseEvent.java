package pointGroups.gui.event.types;

import pointGroups.geometry.Point3D;
import pointGroups.geometry.Symmetry;
import pointGroups.gui.event.Event;

public class Symmetry3DChooseEvent
  implements Event
{

  protected Symmetry<Point3D, ?> symmetry;

  public Symmetry3DChooseEvent(Symmetry<Point3D, ?> symmetry) {
    this.symmetry = symmetry;
  }

  public Symmetry<Point3D, ?> getSymmetry3D() {
    return symmetry;
  }
}
