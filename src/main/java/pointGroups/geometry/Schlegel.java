package pointGroups.geometry;

/**
 * Right now this only contains a String. We may change this to a propper
 * representation of the result like points and edges.
 * 
 * @author nadjascharf
 */
public class Schlegel
{
  public final Point3D[] points;
  
  /**
   * holds edges as pairs of Point3D
   */
  public final Edge<Point3D,Point3D>[] edgesViaPoints;
  
  /**
   * holds edges as pairs of Integers referring to indices of <code> points </code>
   */
  public final Edge<Integer, Integer>[] edgesViaIndices;

  public Schlegel(Point3D[] points, Edge<Point3D,Point3D>[] edgespoints, Edge<Integer, Integer>[] edgesindices) {
    this.points = points;
    this.edgesViaPoints = edgespoints;
    this.edgesViaIndices = edgesindices;
  }
}