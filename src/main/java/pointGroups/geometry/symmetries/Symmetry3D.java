package pointGroups.geometry.symmetries;

import java.util.Arrays;
import java.util.Collection;
import java.util.Collections;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

import pointGroups.geometry.Point3D;
import pointGroups.geometry.Symmetry;
import pointGroups.geometry.UnitQuaternion;


public enum Symmetry3D
  implements Symmetry<Point3D> {

  C1("C1", "[]+", "11"), //
  S2("S2", "[2+,2+]", "x"), // Ci = S2
  C2("C2", "[2]+", "22"), //
  Cs("Cs", "[]", "*"), //
  C2v("C2v", "[2]", "*22"), //
  C2h("C2h", "[2,2+]", "2*"), //
  D2("D2", "[2,2]+", "222"), //
  C5("C5", "[5]+", "55"), //
  S6("S6", "[2+,6+]", "3x"), // S6 = C3i
  C3v("C3v", "[3]", "*33"), //
  D3("D3", "[3,2]+", "322"), //
  D2h("D2h", "[2,2]", "*222"), //
  S10("S10", "[2+,10+]", "5x"), //
  C5v("C5v", "[5]", "*55"), //
  D5("D5", "[5,2]+", "522"), //
  D3d("D3d", "[2+,6]", "2*3"), //
  T("T", "[3,3]+", "332"), //
  Th("Th", "[4,3+]", "3*2"), //
  D5d("D5d", "[2+,10]", "2*5"), //
  I("I", "[5,3]+", "532"), //
  Ih("Ih", "[5,3]", "*532"), //
  C4h("C4h", "[2,4+]", "4*"), //
  C4("C4", "[4]+", "44"), //
  S4("S4", "[2+,4+]", "2x"), //
  D4("D4", "[4,2]+", "422"), //
  C4v("C4v", "[4]", "*44"), //
  D2d("D2d", "[2+,4]", "2*2"), //
  D4h("D4h", "[4,2]", "*422"), //
  C3("C3", "[3]+", "33"), //
  C6("C6", "[6]+", "66"), //
  C3h("C3h", "[2,3+]", "3*"), //
  C6h("C6h", "[2,6+]", "6*"), //
  D6("D6", "[6,2]+", "622"), //
  C6v("C6v", "[6]", "*66"), //
  D3h("D3h", "[3,2]", "*322"), //
  D6h("D6h", "[6,2]", "*622"), //
  O("O", "[4,3]+", "432"), //
  Td("Td", "[3,3]", "*332"), //
  Oh("Oh", "[4,3]", "*432");//

  // Symmetries as quaternions
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
   * opposite edge by an angle of 180°
   */
  final static UnitQuaternion edgeAxis_1, edgeAxis_2, edgeAxis_3, edgeAxis_4,
      edgeAxis_5, edgeAxis_6;
  /**
   * rotation about an axis from the center of a face to the center of the
   * opposite face by an angle of 90°, 180°, 270° (3 axis)
   */
  final static UnitQuaternion faceAxisI_90, faceAxisJ_90, faceAxisK_90,
      faceAxisI_180, faceAxisJ_180, faceAxisK_180, faceAxisI_270,
      faceAxisJ_270, faceAxisK_270;

  /**
   * other rotation axes of the icosahedron
   */
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

  static {
    id = new UnitQuaternion(1, 0, 0, 0); // x,y,z
    faceAxisI_180 = new UnitQuaternion(0, 1, 0, 0); // x,-y,-z
    faceAxisJ_180 = new UnitQuaternion(0, 0, 1, 0); // -x,y,-z
    faceAxisK_180 = new UnitQuaternion(0, 0, 0, 1); // -x,-y,z

    diagonalAxis1_120 = new UnitQuaternion(0.5d, 0.5d, 0.5d, 0.5d); // z,x,y
    diagonalAxis1_240 = new UnitQuaternion(0.5d, -0.5d, -0.5d, -0.5d); // y,z,x
    diagonalAxis2_120 = new UnitQuaternion(0.5d, -0.5d, 0.5d, 0.5d); // -y,z,-x
    diagonalAxis2_240 = new UnitQuaternion(0.5d, 0.5d, -0.5d, -0.5d); // -z,-x,y
    diagonalAxis3_120 = new UnitQuaternion(0.5d, -0.5d, -0.5d, 0.5d);// -z,x,-y
    diagonalAxis3_240 = new UnitQuaternion(0.5d, 0.5d, 0.5d, -0.5d); // y,-z,-x
    diagonalAxis4_120 = new UnitQuaternion(0.5d, 0.5d, -0.5d, 0.5d);// -y,-z,x
    diagonalAxis4_240 = new UnitQuaternion(0.5d, -0.5d, 0.5d, -0.5d); // z,-x,-y

    faceAxisI_90 = new UnitQuaternion(1 / Math.sqrt(2), 1 / Math.sqrt(2), 0, 0);// x,-z,y
    faceAxisJ_90 = new UnitQuaternion(1 / Math.sqrt(2), 0, 1 / Math.sqrt(2), 0);// z,y,-x
    faceAxisK_90 = new UnitQuaternion(1 / Math.sqrt(2), 0, 0, 1 / Math.sqrt(2));// -y,x,z

    faceAxisI_270 =
        new UnitQuaternion(1 / Math.sqrt(2), -1 / Math.sqrt(2), 0, 0);// x,z,-y
    faceAxisJ_270 =
        new UnitQuaternion(1 / Math.sqrt(2), 0, -1 / Math.sqrt(2), 0);// -z,y,x
    faceAxisK_270 =
        new UnitQuaternion(1 / Math.sqrt(2), 0, 0, -1 / Math.sqrt(2));// y,-x,z

    edgeAxis_1 = new UnitQuaternion(0, 1 / Math.sqrt(2), 1 / Math.sqrt(2), 0); // y,x,-z
    edgeAxis_2 = new UnitQuaternion(0, 1 / Math.sqrt(2), -1 / Math.sqrt(2), 0);// -y,-x,-z
    edgeAxis_3 = new UnitQuaternion(0, 0, 1 / Math.sqrt(2), 1 / Math.sqrt(2));// -x,z,y
    edgeAxis_4 = new UnitQuaternion(0, 0, 1 / Math.sqrt(2), -1 / Math.sqrt(2));// -x,-z,-y
    edgeAxis_5 = new UnitQuaternion(0, 1 / Math.sqrt(2), 0, 1 / Math.sqrt(2)); // z,-y,x
    edgeAxis_6 = new UnitQuaternion(0, -1 / Math.sqrt(2), 0, 1 / Math.sqrt(2)); // -z,-y,-x

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
    // Th, Td, Oh fehlen noch. Und alle die in I aber nicht in O sind.
  }

  // Tables
  static final Map<Symmetry3D, Collection<UnitQuaternion>> groups;
  static final Map<Symmetry3D, Collection<Symmetry3D>> subgroups;
  static final Map<Symmetry3D, Point3D> normalPoints;

  static {
    groups = new HashMap<>();
    subgroups = new HashMap<>();
    normalPoints = new HashMap<>();

    // C1 group
    groups.put(C1, Collections.singleton(id));
    subgroups.put(C1, Arrays.asList(C1));
    normalPoints.put(C1, new Point3D(0.8, 0.6, 0.0));

    // C2 group
    groups.put(C2, Arrays.asList(id, faceAxisJ_180));
    subgroups.put(C2, Arrays.asList(C1, C2));
    normalPoints.put(C2, new Point3D(0.8, 0.6, 0.0));

    // D2 group
    groups.put(D2,
        Arrays.asList(id, faceAxisJ_180, faceAxisI_180, faceAxisK_180));
    subgroups.put(D2, Arrays.asList(D2));
    normalPoints.put(D2, new Point3D(0.8, 0.6, 0.0));

    // C3
    // groups.put(C3, Arrays.asList(id,));
    // ...

    // C4
    groups.put(C4,
        Arrays.asList(id, faceAxisK_180, faceAxisK_90, faceAxisK_270));
    subgroups.put(C4, Arrays.asList(C4));
    normalPoints.put(C4, new Point3D(0.8, 0.6, 0.0));

    // D4
    groups.put(D4, new HashSet<UnitQuaternion>());
    groups.get(D4).addAll(groups.get(D2));
    groups.get(D4).addAll(groups.get(C4));
    groups.get(D4).addAll(Arrays.asList(edgeAxis_1, edgeAxis_2));
    subgroups.put(D4, Arrays.asList(D4));
    normalPoints.put(D4, new Point3D(0.8, 0.6, 0.0));

    // D3
    // groups.put(D3, Arrays.asList(id,));
    // ...

    // T
    groups.put(T, Arrays.asList(id, faceAxisI_180, faceAxisJ_180,
        faceAxisK_180, diagonalAxis1_120, diagonalAxis1_240, diagonalAxis2_120,
        diagonalAxis2_240, diagonalAxis3_120, diagonalAxis3_240,
        diagonalAxis4_120, diagonalAxis4_240));
    subgroups.put(T, Arrays.asList(T));
    normalPoints.put(T, new Point3D(0.8, 0.6, 0.0));

    // 0
    groups.put(O, new HashSet<UnitQuaternion>());
    groups.get(O).addAll(groups.get(T));
    groups.get(O).addAll(
        Arrays.asList(faceAxisI_90, faceAxisI_270, faceAxisJ_90, faceAxisJ_270,
            faceAxisK_270, faceAxisK_270, edgeAxis_1, edgeAxis_2, edgeAxis_3,
            edgeAxis_4, edgeAxis_5, edgeAxis_6));
    subgroups.put(O, Arrays.asList(O));
    normalPoints.put(O, new Point3D(0.8, 0.6, 0.0));

    // I
    groups.put(I, new HashSet<UnitQuaternion>());
    groups.get(I).addAll(groups.get(T));
    groups.get(I).addAll(
        Arrays.asList(snubAxis_1, snubAxis_2, snubAxis_3, snubAxis_4,
            snubAxis_5, snubAxis_6, snubAxis_7, snubAxis_8, snubAxis_9,
            snubAxis_10, snubAxis_11, snubAxis_12, snubAxis_13, snubAxis_14,
            snubAxis_15, snubAxis_16, snubAxis_17, snubAxis_18, snubAxis_19,
            snubAxis_20, snubAxis_21, snubAxis_22, snubAxis_23, snubAxis_24,
            snubAxis_25, snubAxis_26, snubAxis_27, snubAxis_28, snubAxis_29,
            snubAxis_30, snubAxis_31, snubAxis_32, snubAxis_33, snubAxis_34,
            snubAxis_35, snubAxis_36, snubAxis_37, snubAxis_38, snubAxis_39,
            snubAxis_40, snubAxis_41, snubAxis_42, snubAxis_43, snubAxis_44,
            snubAxis_45, snubAxis_46, snubAxis_47, snubAxis_48));
    subgroups.put(I, Arrays.asList(I));
    normalPoints.put(I, new Point3D(0.8, 0.6, 0.0));
  }

  private final String coxeter;
  private final String schoenflies;
  private final String orbifold;

  private Symmetry3D(final String schoenflies, final String coxeter,
      final String orbifold) {
    this.schoenflies = schoenflies;
    this.coxeter = coxeter;
    this.orbifold = orbifold;
  }

  @Override
  public String coxeter() {
    return this.coxeter;
  }

  @Override
  public String schoenflies() {
    return this.schoenflies;
  }

  @Override
  public String orbifold() {
    return this.orbifold;
  }

  @Override
  public Collection<Symmetry3D> subgroups() {
    return subgroups.get(this);
  }

  @Override
  public int order() {
    return groups.get(this).size();
  }

  @Override
  public Collection<Point3D> images(final Point3D p) {
    Set<Point3D> res = new HashSet<Point3D>();

    for (UnitQuaternion q : groups.get(this)) {
      res.add(p.rotate(q).asPoint3D());
    }

    return res;
  }

  @Override
  public Class<Point3D> getType() {
    return Point3D.class;
  }

  @Override
  public Point3D getNormalPoint() {
    return normalPoints.get(this);
  }

  public static Collection<Symmetry3D> getSymmetries() {
    return groups.keySet();
  }
}
