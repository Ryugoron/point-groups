package geometry.symmetries;

import geometry.Point3D;
import geometry.Symmetry;
import geometry.UnitQuaternion;
import geometry.Symmetry.Subgroup;

import java.util.Collection;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;


/**
 * The icosahedral group of order 60, rotational symmetry group of the regular
 * dodecahedron and the regular icosahedron.
 * 
 * @author Alex
 */
public class IcosahedralSymmetry
  implements Symmetry<Point3D, IcosahedralSymmetry>
{
  /**
   * The singleton instance of the icosahedron's symmetry class.
   */
  private final static IcosahedralSymmetry sym;
  /**
   * Stores the membership relation of the subgroups of the symmetry group
   * represented by {@link IcosahedralSymmetry}. Subgroups are identified by
   * {@link Subgroups}.
   */
  private final Map<Subgroup<IcosahedralSymmetry>, Collection<UnitQuaternion>> subgroupTable;

  {
    subgroupTable = new HashMap<Subgroup<IcosahedralSymmetry>, Collection<UnitQuaternion>>();
  }

  static {
    sym = new IcosahedralSymmetry();

    // ...list symmetries
  }


  /**
   * Identifiers for the nine subgroups of the icosahedral group. See
   * {@link http://en.wikipedia.org/wiki/Icosahedral_symmetry#
   * Subgroups_of_chiral_icosahedral_symmetry} for further information.
   * 
   * @author Alex
   * @see Subgroup
   */
  public enum Subgroups
    implements Subgroup<IcosahedralSymmetry> {
    Id, Full;
  }

  /**
   * Objects of this type cannot be constructed directly. Instead, use the
   * singleton instance returned by {@link #get()}.
   */
  protected IcosahedralSymmetry() {
  }

  /**
   * Returns the singleton instance of the {@link IcosahedralSymmetry} class.
   * 
   * @return Singleton instance
   */
  public static IcosahedralSymmetry get() {
    return sym;
  }

  @Override
  public Collection<Point3D> images(Point3D p, Subgroup<IcosahedralSymmetry> s) {
    Set<Point3D> res = new HashSet<Point3D>(24);

    for (UnitQuaternion q : subgroupTable.get(s)) {
      res.add(p.rotate(q).asPoint3D());
    }

    return res;
  }

  @Override
  public int order(Subgroup<IcosahedralSymmetry> s) {
    return sym.subgroupTable.get(s).size();
  }

  @Override
  public Collection<UnitQuaternion> getSymmetries(
      geometry.Symmetry.Subgroup<IcosahedralSymmetry> s) {
    // TODO Auto-generated method stub
    return null;
  }

}
