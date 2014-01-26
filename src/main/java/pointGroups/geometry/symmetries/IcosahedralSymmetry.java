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

  /**
   * rotation about a height going through an corner of the tetrahedron and the
   * center of the opposite face by an angle of 120°, 240° (4 axes)
   */
  final static UnitQuaternion diagonalAxis1_120, diagonalAxis1_240,
      diagonalAxis2_120, diagonalAxis2_240, diagonalAxis3_120,
      diagonalAxis3_240, diagonalAxis4_120, diagonalAxis4_240;

  /**
   * rotation about an axis from the center of an edge to the center of the
   * opposite edge by an angle of 180° (3 axis)
   */
  final static UnitQuaternion edgeAxis_1, edgeAxis_2, edgeAxis_3;

  final static UnitQuaternion snubAxis_1, snubAxis_2, snubAxis_3, snubAxis_4,
      snubAxis_5, snubAxis_6, snubAxis_7, snubAxis_8, snubAxis_9, snubAxis_10,
      snubAxis_11, snubAxis_12, snubAxis_13, snubAxis_14, snubAxis_15,
      snubAxis_16, snubAxis_17, snubAxis_18, snubAxis_19, snubAxis_20,
      snubAxis_21, snubAxis_22, snubAxis_23, snubAxis_24, snubAxis_25,
      snubAxis_26, snubAxis_27, snubAxis_28, snubAxis_29, snubAxis_30,
      snubAxis_31, snubAxis_32, snubAxis_33, snubAxis_34, snubAxis_35,
      snubAxis_36, snubAxis_37, snubAxis_38, snubAxis_39, snubAxis_40,
      snubAxis_41, snubAxis_42, snubAxis_43, snubAxis_44, snubAxis_45,
      snubAxis_46, snubAxis_47, snubAxis_48;

  {
    subgroupTable =
        new HashMap<Subgroup<IcosahedralSymmetry>, Collection<UnitQuaternion>>();
  }

  static {
    sym = new IcosahedralSymmetry();
    // Symmetries taken from http://en.wikipedia.org/wiki/600-cell#Coordinates
    id = new UnitQuaternion(1, 0, 0, 0);

    edgeAxis_1 = new UnitQuaternion(0, 1, 0, 0);
    edgeAxis_2 = new UnitQuaternion(0, 0, 1, 0);
    edgeAxis_3 = new UnitQuaternion(0, 0, 0, 1);

    diagonalAxis1_120 = new UnitQuaternion(0.5d, 0.5d, 0.5d, 0.5d);
    diagonalAxis1_240 = new UnitQuaternion(0.5d, -0.5d, -0.5d, -0.5d);
    diagonalAxis2_120 = new UnitQuaternion(0.5d, -0.5d, 0.5d, 0.5d);
    diagonalAxis2_240 = new UnitQuaternion(0.5d, 0.5d, -0.5d, -0.5d);
    diagonalAxis3_120 = new UnitQuaternion(0.5d, -0.5d, -0.5d, 0.5d);
    diagonalAxis3_240 = new UnitQuaternion(0.5d, 0.5d, 0.5d, -0.5d);
    diagonalAxis4_120 = new UnitQuaternion(0.5d, 0.5d, -0.5d, 0.5d);
    diagonalAxis4_240 = new UnitQuaternion(0.5d, -0.5d, 0.5d, -0.5d);
    // even permutations of {1,2,3,4} are
    // {1,2,3,4} itself and
    // {1,3,4,2}, {1,4,2,3}, {2,1,4,3}, {2,3,1,4}, {2,4,3,1}, {3,1,2,4},
    // {3,2,4,1}, {3,4,1,2}, {4,1,3,2}, {4,2,1,3}, {4,3,2,1}
    // based on
    // ½(±φ,±1,±1/φ,0).
    // the four (of eight, since -p = p) needed permutations of this are:
    // (1) ½(φ,1,1/φ,0)
    // (2) ½(φ,1,-1/φ,0)
    // (3) ½(φ,-1,1/φ,0)
    // (4) ½(φ,-1,-1/φ,0)
    //
    // all even permutations of a 4-element set calculated by
    //
    // type Tuple4 = (String, String, String, String)
    // evenPerm4 :: Tuple4 -> [Tuple4]
    // evenPerm4 (a,b,c,d) = [(a,b,c,d),(a,c,d,b), (a,d,b,c), (b,a,d,c),
    // (b,c,a,d), (b,d,c,a), (c,a,b,d),(c,b,d,a), (c,d,a,b), (d,a,c,b),
    // (d,b,a,c), (d,c,b,a)]
    //
    // and then printed to java code by
    //
    // java :: [Tuple4] -> IO ()
    // java = mapM_ (\(a,b,c,d) -> putStrLn ("new UnitQuaternion(" ++ a ++ ","
    // ++ b ++"," ++ c ++","++d ++");"))
    //
    final double phiHalf = (1d + Math.sqrt(5d)) / 4d;
    final double phiInvertHalf = 1d + Math.sqrt(5d);

    snubAxis_1 = new UnitQuaternion(phiHalf, 0.5d, phiInvertHalf, 0); // (1)
    // all even permutations of (1) by
    // java $ tail $ evenPerm4 ("phiHalf", "0.5d", "phiInvertHalf", "0")
    snubAxis_2 = new UnitQuaternion(phiHalf, phiInvertHalf, 0, 0.5d);
    snubAxis_3 = new UnitQuaternion(phiHalf, 0, 0.5d, phiInvertHalf);
    snubAxis_4 = new UnitQuaternion(0.5d, phiHalf, 0, phiInvertHalf);
    snubAxis_5 = new UnitQuaternion(0.5d, phiInvertHalf, phiHalf, 0);
    snubAxis_6 = new UnitQuaternion(0.5d, 0, phiInvertHalf, phiHalf);
    snubAxis_7 = new UnitQuaternion(phiInvertHalf, phiHalf, 0.5d, 0);
    snubAxis_8 = new UnitQuaternion(phiInvertHalf, 0.5d, 0, phiHalf);
    snubAxis_9 = new UnitQuaternion(phiInvertHalf, 0, phiHalf, 0.5d);
    snubAxis_10 = new UnitQuaternion(0, phiHalf, phiInvertHalf, 0.5d);
    snubAxis_11 = new UnitQuaternion(0, 0.5d, phiHalf, phiInvertHalf);
    snubAxis_12 = new UnitQuaternion(0, phiInvertHalf, 0.5d, phiHalf);

    snubAxis_13 = new UnitQuaternion(phiHalf, 0.5d, -phiInvertHalf, 0); // (2)
    // all even permutations of (2) by
    // java $ tail $ evenPerm4 ("phiHalf", "0.5d", "-phiInvertHalf", "0")
    snubAxis_14 = new UnitQuaternion(phiHalf, -phiInvertHalf, 0, 0.5d);
    snubAxis_15 = new UnitQuaternion(phiHalf, 0, 0.5d, -phiInvertHalf);
    snubAxis_16 = new UnitQuaternion(0.5d, phiHalf, 0, -phiInvertHalf);
    snubAxis_17 = new UnitQuaternion(0.5d, -phiInvertHalf, phiHalf, 0);
    snubAxis_18 = new UnitQuaternion(0.5d, 0, -phiInvertHalf, phiHalf);
    snubAxis_19 = new UnitQuaternion(-phiInvertHalf, phiHalf, 0.5d, 0);
    snubAxis_20 = new UnitQuaternion(-phiInvertHalf, 0.5d, 0, phiHalf);
    snubAxis_21 = new UnitQuaternion(-phiInvertHalf, 0, phiHalf, 0.5d);
    snubAxis_22 = new UnitQuaternion(0, phiHalf, -phiInvertHalf, 0.5d);
    snubAxis_23 = new UnitQuaternion(0, 0.5d, phiHalf, -phiInvertHalf);
    snubAxis_24 = new UnitQuaternion(0, -phiInvertHalf, 0.5d, phiHalf);

    snubAxis_25 = new UnitQuaternion(phiHalf, -0.5d, phiInvertHalf, 0); // (3)
    // all even permutations of (3) by
    // java $ tail $ evenPerm4 ("phiHalf", "-0.5d", "phiInvertHalf", "0")
    snubAxis_26 = new UnitQuaternion(phiHalf, phiInvertHalf, 0, -0.5d);
    snubAxis_27 = new UnitQuaternion(phiHalf, 0, -0.5d, phiInvertHalf);
    snubAxis_28 = new UnitQuaternion(-0.5d, phiHalf, 0, phiInvertHalf);
    snubAxis_29 = new UnitQuaternion(-0.5d, phiInvertHalf, phiHalf, 0);
    snubAxis_30 = new UnitQuaternion(-0.5d, 0, phiInvertHalf, phiHalf);
    snubAxis_31 = new UnitQuaternion(phiInvertHalf, phiHalf, -0.5d, 0);
    snubAxis_32 = new UnitQuaternion(phiInvertHalf, -0.5d, 0, phiHalf);
    snubAxis_33 = new UnitQuaternion(phiInvertHalf, 0, phiHalf, -0.5d);
    snubAxis_34 = new UnitQuaternion(0, phiHalf, phiInvertHalf, -0.5d);
    snubAxis_35 = new UnitQuaternion(0, -0.5d, phiHalf, phiInvertHalf);
    snubAxis_36 = new UnitQuaternion(0, phiInvertHalf, -0.5d, phiHalf);

    snubAxis_37 = new UnitQuaternion(phiHalf, -0.5d, -phiInvertHalf, 0); // (4)
    // all even permutations of (4) by
    // java $ tail $ evenPerm4 ("phiHalf", "-0.5d", "-phiInvertHalf", "0")
    snubAxis_38 = new UnitQuaternion(phiHalf, -phiInvertHalf, 0, -0.5d);
    snubAxis_39 = new UnitQuaternion(phiHalf, 0, -0.5d, -phiInvertHalf);
    snubAxis_40 = new UnitQuaternion(-0.5d, phiHalf, 0, -phiInvertHalf);
    snubAxis_41 = new UnitQuaternion(-0.5d, -phiInvertHalf, phiHalf, 0);
    snubAxis_42 = new UnitQuaternion(-0.5d, 0, -phiInvertHalf, phiHalf);
    snubAxis_43 = new UnitQuaternion(-phiInvertHalf, phiHalf, -0.5d, 0);
    snubAxis_44 = new UnitQuaternion(-phiInvertHalf, -0.5d, 0, phiHalf);
    snubAxis_45 = new UnitQuaternion(-phiInvertHalf, 0, phiHalf, -0.5d);
    snubAxis_46 = new UnitQuaternion(0, phiHalf, -phiInvertHalf, -0.5d);
    snubAxis_47 = new UnitQuaternion(0, -0.5d, phiHalf, -phiInvertHalf);
    snubAxis_48 = new UnitQuaternion(0, -phiInvertHalf, -0.5d, phiHalf);
  }

  /*
   * Initialize subgroup relations
   */
  static {
    sym.subgroupTable.put(Subgroups.Id, Collections.singleton(id));
    // todo...

    // temporal
    Collection<UnitQuaternion> full = new HashSet<UnitQuaternion>();
    full.add(id);
    full.add(edgeAxis_1);
    full.add(edgeAxis_2);
    full.add(edgeAxis_3);
    full.add(diagonalAxis1_120);
    full.add(diagonalAxis1_240);
    full.add(diagonalAxis2_120);
    full.add(diagonalAxis2_240);
    full.add(diagonalAxis3_120);
    full.add(diagonalAxis3_240);
    full.add(snubAxis_1);
    full.add(snubAxis_2);
    full.add(snubAxis_3);
    full.add(snubAxis_4);
    full.add(snubAxis_5);
    full.add(snubAxis_6);
    full.add(snubAxis_7);
    full.add(snubAxis_8);
    full.add(snubAxis_9);
    full.add(snubAxis_10);
    full.add(snubAxis_11);
    full.add(snubAxis_12);
    full.add(snubAxis_13);
    full.add(snubAxis_14);
    full.add(snubAxis_15);
    full.add(snubAxis_16);
    full.add(snubAxis_17);
    full.add(snubAxis_18);
    full.add(snubAxis_19);
    full.add(snubAxis_20);
    full.add(snubAxis_21);
    full.add(snubAxis_22);
    full.add(snubAxis_23);
    full.add(snubAxis_24);
    full.add(snubAxis_25);
    full.add(snubAxis_26);
    full.add(snubAxis_27);
    full.add(snubAxis_28);
    full.add(snubAxis_29);
    full.add(snubAxis_30);
    full.add(snubAxis_31);
    full.add(snubAxis_32);
    full.add(snubAxis_33);
    full.add(snubAxis_34);
    full.add(snubAxis_35);
    full.add(snubAxis_36);
    full.add(snubAxis_37);
    full.add(snubAxis_38);
    full.add(snubAxis_39);
    full.add(snubAxis_40);
    full.add(snubAxis_41);
    full.add(snubAxis_42);
    full.add(snubAxis_43);
    full.add(snubAxis_44);
    full.add(snubAxis_45);
    full.add(snubAxis_46);
    full.add(snubAxis_47);
    full.add(snubAxis_48);
    sym.subgroupTable.put(Subgroups.Full, full);
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
    Id("Trivial group"), Full("Full icosahedral symmetry");

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
  public Collection<Point3D> images(final Point3D p,
      final Subgroup<IcosahedralSymmetry> s) {
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
  public int order(final Subgroup<IcosahedralSymmetry> s) {
    return sym.subgroupTable.get(s).size();
  }

  @Override
  public String getName() {
    return "Icosahedral Symmetry";
  }

  @Override
  public Subgroup<IcosahedralSymmetry> getSubgroupByName(final String subgroup) {
    return Subgroups.valueOf(subgroup);
  }

  @Override
  public Collection<Subgroup<IcosahedralSymmetry>> getSubgroups() {
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
