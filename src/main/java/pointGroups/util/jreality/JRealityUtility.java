package pointGroups.util.jreality;

import java.util.ArrayList;
import java.util.List;

import pointGroups.geometry.Edge;
import pointGroups.geometry.Point;
import pointGroups.geometry.Point2D;
import pointGroups.geometry.Point3D;
import de.jreality.geometry.IndexedFaceSetFactory;
import de.jreality.scene.Geometry;


public class JRealityUtility
{
  private static String printPoint(double[] ds) {
    List<Double> list = new ArrayList<Double>(ds.length);
    for (double d : ds) {
      list.add(d);
    }
    return list.toString();
  }

  public static Point2D asPoint2D(double[] point) {
    if (point == null || point.length != 2)
      throw new RuntimeException("could not convert the given point(" +
          printPoint(point) + ") into a Point2D");

    return new Point2D(point[0], point[1]);
  }

  public static Point3D asPoint3D(double[] point) {
    if (point == null || point.length != 3)
      throw new RuntimeException("could not convert the given point(" +
          printPoint(point) + ") into a Point3D");

    return new Point3D(point[0], point[1], point[2]);
  }

  public static double[][] convertPoints(Point[] points) {
    double[][] points_ = new double[points.length][];

    int i = 0;
    for (Point point : points) {
      points_[i++] = point.getComponents();
    }

    return points_;
  }

  public static int[][] convertEdges(Edge<Integer, Integer>[] edges) {
    int[][] edges_ = new int[edges.length][];

    int i = 0;
    for (Edge<Integer, Integer> edge : edges) {
      int u = edge.left, v = edge.right;

      edges_[i++] = new int[] { u, v };
    }

    return edges_;
  }

  public static Geometry generateGraph(Point[] points,
      Edge<Integer, Integer>[] edges) {
    return generateGraph(convertPoints(points), convertEdges(edges));
  }

  public static Geometry generateGraph(double[][] points, int[][] edges) {
    IndexedFaceSetFactory ifsf = new IndexedFaceSetFactory();

    ifsf.setVertexCount(points.length);
    ifsf.setVertexCoordinates(points);

    ifsf.setEdgeCount(edges.length);
    ifsf.setEdgeIndices(edges);
    ifsf.update();

    return ifsf.getGeometry();
  }

  
  public static Geometry generateCompleteGraph(double[][] points){
	  // Enumerate all edges
	  int[][] edges = new int[points.length*(points.length-1) / 2][2];
	  int at = 0;
	  for(int i = 0; i < points.length; i++){
		  for(int j = (i+1); j < points.length; j++){
			  edges[at++] = new int[] {i, j};
		  }
	  }
	  return generateGraph(points,edges);
  }
}