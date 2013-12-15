package pointGroups.gui.symchooser;

import java.util.Collection;

import javax.swing.JToolTip;

import pointGroups.geometry.Point;
import pointGroups.geometry.Symmetry;


public interface SymmetryInfo<P extends Point>
{

  public Class<P> getType();

  public JToolTip getToolTip();

  public String getTitle();

  public String getDescription();

  public int getOrder();

  public Symmetry<P, ?> get();

  public Collection<? extends Symmetry.Subgroup<? extends Symmetry<P, ?>>>
      getSubgroups();

  public Collection<SubgroupInfo<P>> getSubgroupInfo();

}
