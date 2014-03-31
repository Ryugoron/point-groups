package pointGroups.geometry;

/**
 * A unit quaternion is a {@link Quaternion} q with norm(q) = 1. Unit
 * quaternions represent rotations in R^3 (by conjugation) and can be used to
 * rotate {@link Point}s.
 * 
 * @author Alex
 * @see Quaternion
 */
public class UnitQuaternion
  extends Quaternion
{

  /**
   * 
   */
  private static final long serialVersionUID = 5898503233807475707L;

  /**
   * Creates a new {@link UnitQuaternion} object, which represents a normalized
   * quaternion q, that is, norm(q) = 1. The parameters used to construct this
   * object do not necessarily have to be normalized, normalization is done
   * automatically.
   * 
   * @param re Real part of q
   * @param i i-coordinate of q
   * @param j j-coordinate of q
   * @param k k-coordinate of q
   */
  public UnitQuaternion(final double re, final double i, final double j,
      final double k) {
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
  protected UnitQuaternion(final double re, final double i, final double j,
      final double k, final boolean unit) {
    super(i, j, k, re, unit);
  }

  @Override
  public Quaternion inverse() {
    return this.conjugate();
  }

  /**
   * Due to euler's theorem, we can represent any rotation in R^3 using a single
   * unit quaternion which is uniquely determined by the rotation axis and the
   * rotation angle. This method generates a {@link UnitQuaternion} representing
   * the given rotation.
   * 
   * @param angle The rotation angle in radians
   * @param axis The rotation axis (as Vector in R^3)
   * @return A {@link UnitQuaternion} representing the given rotation
   */
  public static UnitQuaternion from3DRotation(final double angle,
      final Quaternion axis) {
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
  public static UnitQuaternion from3DRotation(final double angle,
      final UnitQuaternion axis) {
    final double halfangle = angle / 2d;

    Quaternion p = Quaternion.fromDouble(Math.cos(halfangle));
    Quaternion q = Quaternion.fromDouble(Math.sin(halfangle)).mult(axis);
    Quaternion res = p.plus(q);

    return new UnitQuaternion(res.re, res.i, res.j, res.k, true);
  }
}
