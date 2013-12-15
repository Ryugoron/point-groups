package pointGroups.gui.symchooser;

import pointGroups.geometry.Point;
import pointGroups.geometry.Symmetry;


public class SubgroupInfo<P extends Point>
{

  Symmetry.Subgroup<? extends Symmetry<P, ?>> subgroup;
  SymmetryInfo<P> sym;

  public SubgroupInfo(Symmetry.Subgroup<? extends Symmetry<P, ?>> subgroup,
      SymmetryInfo<P> sym) {
    this.subgroup = subgroup;
    this.sym = sym;
  }

  public String getName() {
    return this.subgroup.getName();
  }

  public int getOrder() {
    return this.subgroup.order();
  }

  public Symmetry.Subgroup<? extends Symmetry<P, ?>> get() {
    return subgroup;
  }

  public SymmetryInfo<P> getSymmetryInfo() {
    return this.sym;
  }
}
