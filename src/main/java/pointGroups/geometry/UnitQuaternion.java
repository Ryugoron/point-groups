package pointGroups.geometry;

/**
 * A unit quaternions is a quaternion q with norm(q) = 1. Unit quaternions
 * represent rotations in R^3 (by conjugation) and can be used to rotate
 * {@link Point}s.
 * 
 * @author Alex
 * @see Quaternion
 */
public class UnitQuaternion
  extends Quaternion
{

  /**
   * Creates a new {@link UnitQuaternion} object, which represents a normalized
   * quaternions q, that is, norm(q) = 1. The parameters used to construct this
   * object does not necessarily have to be normalized.
   * 
   * @param re Real part of q
   * @param i i-coordinate of q
   * @param j j-coordinate of q
   * @param k k-coordinate of q
   */
  public UnitQuaternion(double re, double i, double j, double k) {
    super(i, j, k, re, false);
  }

  /**
   * Constructor for internal use; create {@link UnitQuaternion} directly
   * without automatic normalization iff <code>unit</code> is set to
   * <code>true</code>.
   * 
   * @param re Real part of q
   * @param i i-coordinate of q
   * @param j j-coordinate of q
   * @param k k-coordinate of q
   * @param unit set to true, if no normalization is required (i.e. the
   *          coordinates are a unit)
   */
  protected UnitQuaternion(double re, double i, double j, double k, boolean unit) {
    super(i, j, k, re, unit);
  }

  @Override
  public Quaternion inverse() {
    return this.conjugate();
  }

  /**
   * Due to eulers theorem, we can represent any rotation in R^3 using a single
   * unit quaternion which is uniquely determined by the rotation axis and the
   * rotation angle. This method generates a {@link UnitQuaternion} representing
   * the given rotation.
   * 
   * @param angle The rotation angle in radians
   * @param axis The rotation axis (as Vector in R^3)
   * @return A {@link UnitQuaternion} representing the given rotation
   */
  public static UnitQuaternion from3DRotation(double angle, Quaternion axis) {
    final double halfangle = angle / 2d;

    Quaternion p = Quaternion.fromDouble(Math.cos(halfangle));
    Quaternion q =
        Quaternion.fromDouble(Math.sin(halfangle)).mult(axis.asUnit());
    Quaternion res = p.plus(q);

    return new UnitQuaternion(res.re, res.i, res.j, res.k, true);
  }

  /**
   * See {@link UnitQuaternion#from3DRotation(double, Quaternion)}
   * 
   * @param angle The rotation angle in radians
   * @param axis The rotation axis (as Vector in R^3)
   * @return A {@link UnitQuaternion} representing the given rotation
   */
  public static UnitQuaternion
      from3DRotation(double angle, UnitQuaternion axis) {
    final double halfangle = angle / 2d;

    Quaternion p = Quaternion.fromDouble(Math.cos(halfangle));
    Quaternion q = Quaternion.fromDouble(Math.sin(halfangle)).mult(axis);
    Quaternion res = p.plus(q);

    return new UnitQuaternion(res.re, res.i, res.j, res.k, true);
  }

  /*
   * Still no idea how this works, have to look it up. public static
   * Pair<UnitQuaternion, UnitQuaternion> from4DRotation(double angle) { return
   * null; }
   */

}
