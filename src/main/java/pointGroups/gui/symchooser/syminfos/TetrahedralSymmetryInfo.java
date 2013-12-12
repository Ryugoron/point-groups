package pointGroups.gui.symchooser.syminfos;

import javax.swing.JToolTip;

import pointGroups.geometry.symmetries.TetrahedralSymmetry;


public class TetrahedralSymmetryInfo
  extends Symmetry3DInfo
{

  public TetrahedralSymmetryInfo() {
    super(TetrahedralSymmetry.get());
  }

  @Override
  public JToolTip getToolTip() {
    // TODO Auto-generated method stub
    return null;
  }

  @Override
  public String getTitle() {
    return "Tetrahedral Symmetry";
  }

  @Override
  public String getDescription() {
    // TODO Auto-generated method stub
    return null;
  }

}
