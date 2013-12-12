package pointGroups.gui.symchooser.syminfos;

import javax.swing.JToolTip;

import pointGroups.geometry.symmetries.IcosahedralSymmetry;


public class IcosahedralSymmetryInfo
  extends Symmetry3DInfo
{

  public IcosahedralSymmetryInfo() {
    super(IcosahedralSymmetry.get());
  }

  @Override
  public JToolTip getToolTip() {
    // TODO Auto-generated method stub
    return null;
  }

  @Override
  public String getTitle() {
    return "Icosahedral Symmetry";
  }

  @Override
  public String getDescription() {
    // TODO Auto-generated method stub
    return null;
  }

}
