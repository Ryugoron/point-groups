package pointGroups.gui.symchooser;

import java.util.Collection;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

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

    List<Symmetry3DInfo> symmetries3D = new LinkedList<Symmetry3DInfo>();
    symmetries3D.add(new TetrahedralSymmetryInfo());
    symmetries3D.add(new OctahedralSymmetryInfo());
    symmetries3D.add(new IcosahedralSymmetryInfo());

    List<Symmetry4DInfo> symmetries4D = new LinkedList<Symmetry4DInfo>();
    // todo

    this.symmetryInfos.put(Point3D.class, symmetries3D);
    this.symmetryInfos.put(Point4D.class, symmetries4D);
  }

  public Collection<? extends SymmetryInfo<? extends Point>> getSymmetryInfo(
      Class<? extends Point> clazz) {
    return this.symmetryInfos.get(clazz);
  }

  @SuppressWarnings("unchecked")
  public <A extends Point> Collection<SymmetryInfo<A>> get(Class<A> clazz) {
    return (Collection<SymmetryInfo<A>>) this.symmetryInfos.get(clazz);
  }

}
