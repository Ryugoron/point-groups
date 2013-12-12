package pointGroups.gui.symchooser.syminfos;

import java.util.Collection;

import pointGroups.geometry.Point4D;
import pointGroups.geometry.Symmetry;
import pointGroups.geometry.Symmetry.Subgroup;
import pointGroups.gui.symchooser.SymmetryInfo;


public abstract class Symmetry4DInfo
  implements SymmetryInfo<Point4D>
{
  private final Symmetry<Point4D, ? extends Symmetry<Point4D, ?>> symmetry;

  public Symmetry4DInfo(Symmetry<Point4D, ?> symmetry) {
    this.symmetry = symmetry;
  }

  @Override
  public Class<Point4D> getType() {
    return Point4D.class;
  }

  @Override
  public int getOrder() {
    return symmetry.order();
  }

  @Override
  public Collection<? extends Subgroup<? extends Symmetry<Point4D, ?>>>
      getSubgroups() {
    return symmetry.getSubgroups();
  }
}
