package pointGroups.geometry;

/**
 * A {@link Point4D} is a point which lives in the R^4. It is modelled as a
 * {@link Quaternion}.
 * 
 * @author Alex
 */
public class Point4D
  extends Quaternion
{

  /**
   * Create a new {@link Point4D} p with coordinates (w,x,y,z).
   * 
   * @param w w-coordinate of p
   * @param x x-coordinate of p
   * @param y y-coordinate of p
   * @param z z-coordinate of p
   */
  public Point4D(double w, double x, double y, double z) {
    super(w, x, y, z);
  }

  /**
   * Rotates the point <code>this</code> around the rotation axis as described
   * by {@link UnitQuaternion} p and q and returns it as new {@link Point4D}.
   * The point <code>this</code> is not altered by this operation.
   * 
   * @param p First rotation quaternion
   * @param q Second rotation quaternion
   * @returnA new {@link Point4D} which is equivalent to <code>this</code>
   *          rotated with respect to (p,q)
   * @deprecated This method is not known to function properly. No 4D-rotations
   *             have been simulated.
   */
  @Deprecated
  public Point4D rotate(UnitQuaternion p, UnitQuaternion q) {
    return p.mult(this).mult(q).asPoint4D();
  }

  @Override
  public String toString() {
    return "(" + re + "," + i + "," + j + "," + k + ")";
  }
}
