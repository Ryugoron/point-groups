package pointGroups.gui.symchooser;

import java.util.Collection;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

import pointGroups.geometry.Point;
import pointGroups.geometry.Point3D;
import pointGroups.geometry.Point4D;
import pointGroups.gui.symchooser.syminfos.IcosahedralSymmetryInfo;
import pointGroups.gui.symchooser.syminfos.OctahedralSymmetryInfo;
import pointGroups.gui.symchooser.syminfos.Symmetry3DInfo;
import pointGroups.gui.symchooser.syminfos.Symmetry4DInfo;
import pointGroups.gui.symchooser.syminfos.TetrahedralSymmetryInfo;


public class SymmetryInfoProvider
{
  private final Map<Class<? extends Point>, Collection<? extends SymmetryInfo<? extends Point>>> symmetryInfos;

  public SymmetryInfoProvider() {
    this.symmetryInfos =
        new HashMap<Class<? extends Point>, Collection<? extends SymmetryInfo<? extends Point>>>();

    Set<Symmetry3DInfo> symmetries3D = new HashSet<Symmetry3DInfo>();
    symmetries3D.add(new TetrahedralSymmetryInfo());
    symmetries3D.add(new OctahedralSymmetryInfo());
    symmetries3D.add(new IcosahedralSymmetryInfo());

    Set<Symmetry4DInfo> symmetries4D = new HashSet<Symmetry4DInfo>();
    // todo

    this.symmetryInfos.put(Point3D.class, symmetries3D);
    this.symmetryInfos.put(Point4D.class, symmetries4D);
  }

  public Collection<? extends SymmetryInfo<? extends Point>> getSymmetryInfo(
      Class<? extends Point> clazz) {
    return this.symmetryInfos.get(clazz);
  }

}
