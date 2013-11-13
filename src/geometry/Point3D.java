package geometry;

/**
 * A {@link Point3D} is a point which lives in the R^3. It is modelled as a
 * special case of {@link Quaternion}, where the real part of the quaternion is
 * zero.
 * 
 * @author Alex
 */
public class Point3D
  extends Quaternion
{

  /**
   * Create a new {@link Point3D} p with coordinates (x,y,z).
   * 
   * @param x x-coordinate of p
   * @param y y-coordinate of p
   * @param z z-coordinate of p
   */
  public Point3D(double x, double y, double z) {
    super(0, x, y, z);
  }

  /**
   * Rotates the point <code>this</code> around the rotation axis as described
   * by {@link UnitQuaternion} q and returns it as new {@link Point3D}. The
   * point <code>this</code> is not altered by this operation.
   * 
   * @param q The rotation quaternion
   * @return A new {@link Point3D} which is equivalent to p rotated with respect
   *         to q
   */
  public Point3D rotate(UnitQuaternion q) {
    return q.mult(this).mult(q.inverse()).asPoint3D();
  }

  /**
   * Rotates the point <code>this</code> around the rotation axis <code>axis</code>
   * by <code>angle</code> degrees and returns it as new {@link Point3D}. The
   * point <code>this</code> is not altered by this operation.

   * @param angle The rotation angle in radians
   * @param axis The rotation axis
   * @return A new {@link Point3D} which is equivalent to p rotated with respect
   *         to (axis, angle).
   */
  public Point3D rotate(double angle, Quaternion axis) {
    if (axis instanceof UnitQuaternion) return this.rotate(UnitQuaternion.from3DRotation(
        angle, (UnitQuaternion) axis));
    else return this.rotate(UnitQuaternion.from3DRotation(angle, axis));
  }

  @Override
  public String toString() {
    return "(" + i + "," + j + "," + k + ")";
  }
}
