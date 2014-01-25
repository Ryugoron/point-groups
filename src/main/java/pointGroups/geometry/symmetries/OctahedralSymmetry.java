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
 * The octahedral group of order 24, rotational symmetry group of the cube and
 * the regular octahedron.
 * 
 * @author Alex
 */
public class OctahedralSymmetry
  implements Symmetry<Point3D, OctahedralSymmetry>
{
  /**
   * The singleton instance of the octahedron's symmetry class.
   */
  private final static OctahedralSymmetry sym;
  /**
   * Stores the membership relation of the subgroups of the symmetry group
   * represented by {@link OctahedralSymmetry}. Subgroups are identified by
   * {@link Subgroups}.
   */
  private final Map<Subgroup<OctahedralSymmetry>, Collection<UnitQuaternion>> subgroupTable;

  /**
   * identity rotation
   */
  final static UnitQuaternion id;
  /**
   * rotation about an axis from the center of a face to the center of the
   * opposite face by an angle of 90°, 180°, 270° (3 axis)
   */
  final static UnitQuaternion faceAxisI_90, faceAxisJ_90, faceAxisK_90,
      faceAxisI_180, faceAxisJ_180, faceAxisK_180, faceAxisI_270,
      faceAxisJ_270, faceAxisK_270;

  /**
   * rotation about a body diagonal by an angle of 120°, 240° (4 axes)
   */
  final static UnitQuaternion diagonalAxis1_120, diagonalAxis1_240,
      diagonalAxis2_120, diagonalAxis2_240, diagonalAxis3_120,
      diagonalAxis3_240, diagonalAxis4_120, diagonalAxis4_240;

  /**
   * rotation about an axis from the center of an edge to the center of the
   * opposite edge by an angle of 180°
   */
  final static UnitQuaternion edgeAxis_1, edgeAxis_2, edgeAxis_3, edgeAxis_4,
      edgeAxis_5, edgeAxis_6;

  {
    subgroupTable =
        new HashMap<Subgroup<OctahedralSymmetry>, Collection<UnitQuaternion>>();
  }

  static {
    sym = new OctahedralSymmetry();

    id = new UnitQuaternion(1, 0, 0, 0);

    faceAxisI_90 = new UnitQuaternion(1 / Math.sqrt(2), 1 / Math.sqrt(2), 0, 0);
    faceAxisJ_90 = new UnitQuaternion(1 / Math.sqrt(2), 0, 1 / Math.sqrt(2), 0);
    faceAxisK_90 = new UnitQuaternion(1 / Math.sqrt(2), 0, 0, 1 / Math.sqrt(2));
    faceAxisI_180 = new UnitQuaternion(0, 1, 0, 0);
    faceAxisJ_180 = new UnitQuaternion(0, 0, 1, 0);
    faceAxisK_180 = new UnitQuaternion(0, 0, 0, 1);
    faceAxisI_270 =
        new UnitQuaternion(1 / Math.sqrt(2), -1 / Math.sqrt(2), 0, 0);
    faceAxisJ_270 =
        new UnitQuaternion(1 / Math.sqrt(2), 0, -1 / Math.sqrt(2), 0);
    faceAxisK_270 =
        new UnitQuaternion(1 / Math.sqrt(2), 0, 0, -1 / Math.sqrt(2));

    diagonalAxis1_120 = new UnitQuaternion(0.5d, 0.5d, 0.5d, 0.5d);
    diagonalAxis1_240 = new UnitQuaternion(0.5d, -0.5d, -0.5d, -0.5d);
    diagonalAxis2_120 = new UnitQuaternion(0.5d, -0.5d, 0.5d, 0.5d);
    diagonalAxis2_240 = new UnitQuaternion(0.5d, 0.5d, -0.5d, -0.5d);
    diagonalAxis3_120 = new UnitQuaternion(0.5d, -0.5d, -0.5d, 0.5d);
    diagonalAxis3_240 = new UnitQuaternion(0.5d, 0.5d, 0.5d, -0.5d);
    diagonalAxis4_120 = new UnitQuaternion(0.5d, 0.5d, -0.5d, 0.5d);
    diagonalAxis4_240 = new UnitQuaternion(0.5d, -0.5d, 0.5d, -0.5d);

    edgeAxis_1 = new UnitQuaternion(0, 1 / Math.sqrt(2), 1 / Math.sqrt(2), 0);
    edgeAxis_2 = new UnitQuaternion(0, 1 / Math.sqrt(2), -1 / Math.sqrt(2), 0);
    edgeAxis_3 = new UnitQuaternion(0, 0, 1 / Math.sqrt(2), 1 / Math.sqrt(2));
    edgeAxis_4 = new UnitQuaternion(0, 0, 1 / Math.sqrt(2), -1 / Math.sqrt(2));
    edgeAxis_5 = new UnitQuaternion(0, 1 / Math.sqrt(2), 0, 1 / Math.sqrt(2));
    edgeAxis_6 = new UnitQuaternion(0, 1 / Math.sqrt(2), 0, -1 / Math.sqrt(2));
  }

  /*
   * Initialize subgroup relations, see
   * http://en.wikipedia.org/wiki/Octahedral_symmetry
   * #Subgroups_of_chiral_octahedral_symmetry.
   */
  static {
    sym.subgroupTable.put(Subgroups.Id, Collections.singleton(id));

    // temporal
    Collection<UnitQuaternion> full = new HashSet<UnitQuaternion>();
    full.add(id);
    full.add(faceAxisI_90);
    full.add(faceAxisJ_90);
    full.add(faceAxisK_90);
    full.add(faceAxisI_180);
    full.add(faceAxisJ_180);
    full.add(faceAxisK_180);
    full.add(faceAxisI_270);
    full.add(faceAxisJ_270);
    full.add(faceAxisK_270);
    full.add(diagonalAxis1_120);
    full.add(diagonalAxis1_240);
    full.add(diagonalAxis2_120);
    full.add(diagonalAxis2_240);
    full.add(diagonalAxis3_120);
    full.add(diagonalAxis3_240);
    full.add(diagonalAxis4_120);
    full.add(diagonalAxis4_240);
    full.add(edgeAxis_1);
    full.add(edgeAxis_2);
    full.add(edgeAxis_3);
    full.add(edgeAxis_4);
    full.add(edgeAxis_5);
    full.add(edgeAxis_6);
    sym.subgroupTable.put(Subgroups.Full, full);
  }


  /**
   * Identifiers for the nine subgroups of the Octahedral group. See
   * {@link http://en.wikipedia.org/wiki/Octahedral_symmetry#
   * Subgroups_of_chiral_octahedral_symmetry} for further information.
   * 
   * @author Alex
   * @see Subgroup
   */
  public enum Subgroups
    implements Subgroup<OctahedralSymmetry> {
    Id("Trivial group"), Full("Full octahedral symmetry");

    private final String name;

    Subgroups(final String name) {
      this.name = name;
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
  protected OctahedralSymmetry() {
  }

  /**
   * Returns the singleton instance of the {@link OctahedralSymmetry} class.
   * 
   * @return Singleton instance
   */
  public static OctahedralSymmetry get() {
    return sym;
  }

  @Override
  public Collection<Point3D> images(final Point3D p,
      final Subgroup<OctahedralSymmetry> s) {
    Set<Point3D> res = new HashSet<Point3D>(24);

    for (UnitQuaternion q : subgroupTable.get(s)) {
      res.add(p.rotate(q).asPoint3D());
    }

    return res;
  }

  @Override
  public Collection<Point3D> images(final Point3D p, final String s) {
    return images(p, this.getSubgroupByName(s));
  }

  @Override
  public int order(final Subgroup<OctahedralSymmetry> s) {
    return sym.subgroupTable.get(s).size();
  }

  @Override
  public String getName() {
    return "Octahedral Symmetry";
  }

  @Override
  public Subgroup<OctahedralSymmetry> getSubgroupByName(final String subgroup) {
    return Subgroups.valueOf(subgroup);
  }

  @Override
  public Collection<Subgroup<OctahedralSymmetry>> getSubgroups() {
    return sym.subgroupTable.keySet();
  }

  @Override
  public Class<Point3D> getType() {
    return Point3D.class;
  }

@Override
public Point3D getNormalPoint() {
	// TODO Look for a point not on the symmetry axis (normalized)
	return new Point3D(0.8, 0.6, 0.0);
}

}
