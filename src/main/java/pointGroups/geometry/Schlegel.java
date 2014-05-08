package pointGroups.geometry;

/**
 * Representation of a schlegeldiagram
 * 
 */
public class Schlegel
{
  public final Polytope<Point3D> polytope;
  public final Point3D[] points;

  /**
   * 
   */
  public final Edge[] edges;

  public Schlegel(Point3D[] points, Edge[] edges, Polytope<Point3D> polytope) {
    this.points = points;
    this.edges = edges;
    this.polytope = polytope;
  }
}
