package pointGroups.geometry;

/**
 * Right now this only contains a String. We may change this to a propper
 * representation of the result like points and edges.
 * 
 * @author nadjascharf
 */
public class Schlegel
{
  public final Polytope<Point3D> polytope;
  public final Point3D[] points;

  /**
   * holds edges as pairs of Integers referring to indices of <code> points
   * </code>
   */
  public final Edge[] edges;

  public Schlegel(Point3D[] points, Edge[] edges, Polytope<Point3D> polytope) {
    this.points = points;
    this.edges = edges;
    this.polytope = polytope;
  }
}
