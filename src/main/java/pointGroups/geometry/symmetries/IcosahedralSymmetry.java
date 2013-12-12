package pointGroups.geometry.symmetries;

import java.util.Collection;
import java.util.Collections;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

import pointGroups.geometry.Point3D;
import pointGroups.geometry.Symmetry;
import pointGroups.geometry.UnitQuaternion;


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

  /**
   * identity rotation
   */
  final static UnitQuaternion id;

  {
    subgroupTable =
        new HashMap<Subgroup<IcosahedralSymmetry>, Collection<UnitQuaternion>>();
  }

  static {
    sym = new IcosahedralSymmetry();

    id = new UnitQuaternion(1, 0, 0, 0);
    // ...list symmetries
  }

  static {
    sym.subgroupTable.put(Subgroups.Id, Collections.singleton(id));
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
    Id("Trivial group"), Full("Full icosahedral group");

    private final String name;

    private Subgroups(String s) {
      this.name = s;
    }

    @Override
    public String getName() {
      return this.name;
    }

    @Override
    public int order() {
      return sym.order(this);
    }
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
      pointGroups.geometry.Symmetry.Subgroup<IcosahedralSymmetry> s) {
    return sym.subgroupTable.get(s);
  }

  @Override
  public Collection<UnitQuaternion> getSymmetriesByName(String subgroup) {
    return getSymmetries(Subgroups.valueOf(subgroup));
  }

  @Override
  public Collection<pointGroups.geometry.Symmetry.Subgroup<IcosahedralSymmetry>>
      getSubgroups() {
    return sym.subgroupTable.keySet();
  }

  @Override
  public int order() {
    return order(Subgroups.Full);
  }

  @Override
  public Collection<Point3D> imagesByName(Point3D p, String subgroup) {
    Subgroups s = Subgroups.valueOf(subgroup);

    return images(p, s);
  }

  @Override
  public pointGroups.geometry.Symmetry.Subgroup<IcosahedralSymmetry>
      getSubgroupByName(String subgroup) {
    return Subgroups.valueOf(subgroup);
  }

}
