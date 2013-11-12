package geometry;

public class UnitQuaternion
  extends Quaternion
{

  public UnitQuaternion(double re, double i, double j, double k) {
    super(i, j, k, re, false);
  }

  protected UnitQuaternion(double re, double i, double j, double k, boolean unit) {
    super(i, j, k, re, unit);
  }

  /**
   * Gives the conjugate p* for p = this, this is, p with its vector parts
   * flipped in sign. For a unit quaternion, the inverse equals its conjugate.
   */
  @Override
  public Quaternion inverse() {
    return this.conjugate();
  }

  public static UnitQuaternion from3DRotation(double angle, Quaternion axis) {
    final double halfangle = angle / 2d;

    Quaternion p = Quaternion.fromDouble(Math.cos(halfangle));
    Quaternion q =
        Quaternion.fromDouble(Math.sin(halfangle)).mult(axis.asUnit());
    Quaternion res = p.plus(q);

    return new UnitQuaternion(res.re, res.i, res.j, res.k, true);
  }

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
