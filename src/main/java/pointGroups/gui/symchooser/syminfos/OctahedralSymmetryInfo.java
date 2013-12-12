package pointGroups.gui.symchooser.syminfos;

import javax.swing.JToolTip;

import pointGroups.geometry.symmetries.OctahedralSymmetry;


public class OctahedralSymmetryInfo
  extends Symmetry3DInfo
{

  public OctahedralSymmetryInfo() {
    super(OctahedralSymmetry.get());
  }

  @Override
  public JToolTip getToolTip() {
    // TODO Auto-generated method stub
    return null;
  }

  @Override
  public String getTitle() {
    return "Octahedral Symmetry";
  }

  @Override
  public String getDescription() {
    // TODO Auto-generated method stub
    return null;
  }
}
