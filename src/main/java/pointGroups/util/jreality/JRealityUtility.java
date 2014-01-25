package pointGroups.util.jreality;

import pointGroups.geometry.Edge;
import pointGroups.geometry.Point;
import de.jreality.geometry.IndexedFaceSetFactory;
import de.jreality.scene.Geometry;


public class JRealityUtility
{

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
