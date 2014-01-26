package pointGroups.geometry;

/**
 * A {@link Point2D} is a point which lives in the R^2. It is modelled as a
 * special case of {@link Quaternion}, where the real part of the quaternion is
 * zero.
 * 
 * @author Alex
 */
public class Point2D
  extends Quaternion
{

  /**
   * Create a new {@link Point2D} p with coordinates (x,y).
   * 
   * @param x x-coordinate of p
   * @param y y-coordinate of p
   */
  public Point2D(double x, double y) {
    super(0, x, y, 0);
  }

  /**
   * @return Ordered coordinates (x,y,z) of the point.
   */
  @Override
  public double[] getComponents() {
    double[] components = { this.i, this.j };
    return components;
  }

  @Override
  public String toString() {
    return "(" + this.i + "," + this.j + ")";
  }
}
