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
 * The tetrahedral group of order 12, rotational symmetry group of the regular
 * tetrahedron.
 * 
 * @author Alex
 */
public class TetrahedralSymmetry
  implements Symmetry<Point3D, TetrahedralSymmetry>
{
  /**
   * The singleton instance of the tetrahedron's symmetry class.
   */
  private final static TetrahedralSymmetry sym;
  /**
   * Stores the membership relation of the subgroups of the symmetry group
   * represented by {@link TetrahedralSymmetry}. Subgroups are identified by
   * {@link Subgroups}.
   */
  private final Map<Subgroup<TetrahedralSymmetry>, Collection<UnitQuaternion>> subgroupTable;

  {
    subgroupTable = new HashMap<>();
  }

  static {
    sym = new TetrahedralSymmetry();

    // ...list symmetries
  }


  /**
   * Identifiers for the five subgroups of the Octahedral group. See
   * {@link http://en.wikipedia.org/wiki/Tetrahedral_symmetry#
   * Subgroups_of_chiral_tetrahedral_symmetry} for further information.
   * 
   * @author Alex
   * @see Subgroup
   */
  public enum Subgroups
    implements Subgroup<TetrahedralSymmetry> {
    Id, Full;
  }

  /**
   * Objects of this type cannot be constructed directly. Instead, use the
   * singleton instance returned by {@link #get()}.
   */
  protected TetrahedralSymmetry() {
  }

  /**
   * Returns the singleton instance of the {@link TetrahedralSymmetry} class.
   * 
   * @return Singleton instance
   */
  public static TetrahedralSymmetry get() {
    return sym;
  }

  @Override
  public Collection<Point3D> images(Point3D p, Subgroup<TetrahedralSymmetry> s) {
    Set<Point3D> res = new HashSet<Point3D>(24);

    for (UnitQuaternion q : subgroupTable.get(s)) {
      res.add(p.rotate(q).asPoint3D());
    }

    return res;
  }

  @Override
  public int order(Subgroup<TetrahedralSymmetry> s) {
    return sym.subgroupTable.get(s).size();
  }

  @Override
  public Collection<UnitQuaternion> getSymmetries(
      geometry.Symmetry.Subgroup<TetrahedralSymmetry> s) {
    // TODO Auto-generated method stub
    return null;
  }

}
