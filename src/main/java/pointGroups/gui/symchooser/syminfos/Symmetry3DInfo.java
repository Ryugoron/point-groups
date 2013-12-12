package pointGroups.gui.symchooser.syminfos;

import java.util.Collection;
import java.util.HashSet;

import pointGroups.geometry.Point3D;
import pointGroups.geometry.Symmetry;
import pointGroups.geometry.Symmetry.Subgroup;
import pointGroups.gui.symchooser.SubgroupInfo;
import pointGroups.gui.symchooser.SymmetryInfo;


public abstract class Symmetry3DInfo
  implements SymmetryInfo<Point3D>
{
  private final Symmetry<Point3D, ? extends Symmetry<Point3D, ?>> symmetry;

  public Symmetry3DInfo(Symmetry<Point3D, ?> symmetry) {
    this.symmetry = symmetry;
  }

  @Override
  public Class<Point3D> getType() {
    return Point3D.class;
  }

  @Override
  public int getOrder() {
    return symmetry.order();
  }

  @Override
  public Collection<? extends Subgroup<? extends Symmetry<Point3D, ?>>>
      getSubgroups() {
    return symmetry.getSubgroups();
  }

  @Override
  public Symmetry<Point3D, ?> get() {
    return this.symmetry;
  }

  @Override
  public Collection<SubgroupInfo> getSubgroupInfo() {
    Collection<SubgroupInfo> c = new HashSet<SubgroupInfo>();

    for (Subgroup<?> subgroup : this.getSubgroups()) {
      c.add(new SubgroupInfo(subgroup, this));
    }
    return c;
  }
}
