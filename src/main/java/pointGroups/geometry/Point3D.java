package pointGroups.geometry;

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
  public Point3D(final double x, final double y, final double z) {
    super(0, x, y, z);
  }

  /**
   * Creates a new {@link Point3D} my throw an exception, if the components have
   * not the right size.
   * 
   * @param ds [x,y,z] - coordiantes of p
   */
  public Point3D(final double[] ds) {
    super(0, ds[0], ds[1], ds[2]);
  }

  /**
   * Rotates the point <code>this</code> around the rotation axis as described
   * by {@link UnitQuaternion} q and returns it as new {@link Point3D}. The
   * point <code>this</code> is not altered by this operation.
   * 
   * @param q The rotation quaternion
   * @return A new {@link Point3D} which is equivalent to <code>this</code>
   *         rotated with respect to q
   */
  public Point3D rotate(final UnitQuaternion q) {
    return q.mult(this).mult(q.inverse()).asPoint3D();
  }

  /**
   * Rotates the point <code>this</code> around the rotation axis
   * <code>axis</code> by <code>angle</code> degrees and returns it as new
   * {@link Point3D}. The point <code>this</code> is not altered by this
   * operation.
   * 
   * @param angle The rotation angle in radians
   * @param axis The rotation axis
   * @return A new {@link Point3D} which is equivalent to <code>this</code>
   *         rotated with respect to (axis, angle).
   */
  public Point3D rotate(final double angle, final Quaternion axis) {
    if (axis instanceof UnitQuaternion) return this.rotate(UnitQuaternion.from3DRotation(
        angle, (UnitQuaternion) axis));
    else return this.rotate(UnitQuaternion.from3DRotation(angle, axis));
  }

  /**
   * Reflects the point <code>this</code> at the plane with normal vector
   * (q.i,q.j,q.k) and returns it as new {@link Point3D}. The point
   * <code>this</code> is not altered by this operation.
   * 
   * @param q The reflection quaternion
   * @return A new {@link Point3D} which is equivalent to <code>this</code>
   *         reflected with respect to q
   */
  public Point3D reflect(final UnitQuaternion q) {
    return q.mult(this).mult(q).asPoint3D();
  }

  /**
   * @return Ordered coordinates (x,y,z) of the point.
   */
  @Override
  public double[] getComponents() {
    double[] components = { this.i, this.j, this.k };
    return components;
  }

  @Override
  public String toString() {
    return "(" + this.i + "," + this.j + "," + this.k + ")";
  }
}
