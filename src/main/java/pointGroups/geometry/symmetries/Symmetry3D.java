package pointGroups.geometry.symmetries;

import java.util.Arrays;
import java.util.Collection;
import java.util.Collections;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

import pointGroups.geometry.NewSymmetry;
import pointGroups.geometry.Point3D;
import pointGroups.geometry.UnitQuaternion;


public enum Symmetry3D
  implements NewSymmetry<Point3D> {

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

    groups.put(C1, Collections.singleton(id));
    groups.put(C2, Arrays.asList(id, faceAxisJ_180));
    groups.put(D2,
        Arrays.asList(id, faceAxisJ_180, faceAxisI_180, faceAxisK_180));
    // groups.put(C3, Arrays.asList(id,));

    groups.put(C4,
        Arrays.asList(id, faceAxisK_180, faceAxisK_90, faceAxisK_270));

    groups.put(D4, new HashSet<UnitQuaternion>());
    groups.get(D4).addAll(groups.get(D2));
    groups.get(D4).addAll(groups.get(C4));
    groups.get(D4).addAll(Arrays.asList(edgeAxis_1, edgeAxis_2));
    // groups.put(D3, Arrays.asList(id,));
    groups.put(T, Arrays.asList(id, faceAxisI_180, faceAxisJ_180,
        faceAxisK_180, diagonalAxis1_120, diagonalAxis1_240, diagonalAxis2_120,
        diagonalAxis2_240, diagonalAxis3_120, diagonalAxis3_240,
        diagonalAxis4_120, diagonalAxis4_240));

    groups.put(O, new HashSet<UnitQuaternion>());
    groups.get(O).addAll(groups.get(T));
    groups.get(O).addAll(
        Arrays.asList(faceAxisI_90, faceAxisI_270, faceAxisJ_90, faceAxisJ_270,
            faceAxisK_270, faceAxisK_270, edgeAxis_1, edgeAxis_2, edgeAxis_3,
            edgeAxis_4, edgeAxis_5, edgeAxis_6));

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

}
