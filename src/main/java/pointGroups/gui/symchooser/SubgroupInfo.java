package pointGroups.gui.symchooser;

import pointGroups.geometry.Symmetry;


public class SubgroupInfo
{

  Symmetry.Subgroup<?> subgroup;
  SymmetryInfo<?> sym;

  public SubgroupInfo(Symmetry.Subgroup<?> subgroup, SymmetryInfo<?> sym) {
    this.subgroup = subgroup;
    this.sym = sym;
  }

  public String getName() {
    return this.subgroup.getName();
  }

  public int getOrder() {
    return this.subgroup.order();
  }

  public Symmetry.Subgroup<?> get() {
    return subgroup;
  }

  public SymmetryInfo<?> getSymmetryInfo() {
    return this.sym;
  }
}
