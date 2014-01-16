package pointGroups.util.polymake;

import java.util.Collection;

import pointGroups.geometry.Edge;
import pointGroups.geometry.Point3D;
import pointGroups.geometry.Schlegel;
import pointGroups.geometry.symmetries.OctahedralSymmetry;
import pointGroups.geometry.symmetries.OctahedralSymmetry.Subgroups;

public class TestSchlegel {
  private static Point3D p = new Point3D(3d, 4d, 5d);  
  private static String polymakeOutoutOfP = "0.8437623627 -1.292570457\n0.8744031341 -1.216996789\n0.7274936437 -0.8235120897\n1.386639712 -0.7696765175\n1.236233604 -1.499008464\n1.187266895 -1.223144633\n1.939108599 -1.495802348\n-0.144540678 -1.755977304\n0.8481405759 -1.608926214\n0.6312420834 -0.9628780747\n3.604735448 -0.1399320659\n1.142563161 -1.298740231\n0.9240828371 -1.284067364\n-0.283297282 -1.269684652\n0.4733054534 -0.7125207813\n0.8472886644 -1.50938016\n2.684377461 -2.409941136\n0.7343130268 -1.551689144\n1.138288042 -1.553784165\n1.226286079 -1.617723205\n1.19715935 -0.5948058424\n1.106800258 -1.228670623\n-3.374645951 -1.274936601\n1.213485474 -0.8743141702\n$\n{0 1}\n{1 2}\n{3 4}\n{3 5}\n{4 5}\n{3 6}\n{4 6}\n{7 8}\n{0 9}\n{1 9}\n{2 9}\n{3 10}\n{6 10}\n{4 11}\n{5 11}\n{0 12}\n{1 12}\n{11 12}\n{0 13}\n{7 13}\n{9 13}\n{2 14}\n{9 14}\n{13 14}\n{0 15}\n{8 15}\n{12 15}\n{6 16}\n{7 16}\n{10 16}\n{0 17}\n{7 17}\n{8 17}\n{13 17}\n{15 17}\n{4 18}\n{8 18}\n{11 18}\n{12 18}\n{15 18}\n{4 19}\n{6 19}\n{7 19}\n{8 19}\n{16 19}\n{18 19}\n{2 20}\n{3 20}\n{10 20}\n{14 20}\n{1 21}\n{5 21}\n{11 21}\n{12 21}\n{7 22}\n{10 22}\n{13 22}\n{14 22}\n{16 22}\n{20 22}\n{1 23}\n{2 23}\n{3 23}\n{5 23}\n{20 23}\n{21 23}\n";   
  private static OctahedralSymmetry octa = OctahedralSymmetry.get();
  
  
  /**
   * 
   * @return Schlegeldiagram of images of point p in octahedral sym group 
   */
	public static Schlegel getTestSchlegelOctahedralSymmery(){	  
	  return splitToSchlegel(polymakeOutoutOfP);
	}
	
	public static Point3D getPoint(){
		return p;
	}
	
	/**
	 * 
	 * @return of point p
	 */
	public static Collection<Point3D> getImagesOctahedral(){
	  return octa.images(p, Subgroups.Full);
	}
	
	/**
	 * copy from Schlegeltransformer
	 * @param result
	 * @return
	 */
	private static Schlegel splitToSchlegel(String result){
    // splitting result string into two. One contains the points, the other
    // the
    // edges.
    String[] pointsAndEdges = result.split("\\$\n");
    String pointsString = pointsAndEdges[0];
    String edgesString = pointsAndEdges[1];

    // splitting string containing points into one per point
    String[] splittedPointsString = pointsString.split("\n");
    // transforming strings into Point-Objects
    Point3D[] points = new Point3D[splittedPointsString.length];
    for (int i = 0; i < splittedPointsString.length; i++) {
            String str = splittedPointsString[i];
            // ignore brackets and split into components
            String[] compStr = str.substring(1, str.length() - 2).split(" ");
            if (compStr.length == 2) {
                    points[i] = new Point3D(Double.parseDouble(compStr[0]),
                                    Double.parseDouble(compStr[1]), 0);
            } else if (compStr.length == 3) {
                    points[i] = new Point3D(Double.parseDouble(compStr[0]),
                                    Double.parseDouble(compStr[1]),
                                    Double.parseDouble(compStr[2]));
            } else {
                    // TODO Fehler? Vorher? Try-Catch? versehentliche Mischung aus
                    // 3D und 2D
                    // Punkten?
            }
    }
    // Store Edges as Array von Pair<Point3D,Point3D>
    String[] splittedEdgesString = edgesString.split("\n");
    Edge<Point3D, Point3D>[] edges = new Edge[splittedEdgesString.length] ;
    Edge<Integer, Integer>[] edgesindices = new Edge[splittedEdgesString.length];
    // start iteration with i = 1 because the first string after splitting
    // is
    // empty caused by leading \n
    for (int i = 0; i < splittedEdgesString.length; i++) {
      String str = splittedEdgesString[i];
      // ignore brackets and split into components
      String[] compStr = str.substring(1, str.length() - 1).split(" ");
      int fromIndex = Integer.valueOf(compStr[0]);
      int toIndex = Integer.valueOf(compStr[0]);
      Point3D from = points[fromIndex];
      Point3D to = points[toIndex];
      edges[i] = new Edge<Point3D, Point3D>(from, to);
      edgesindices[i] = new Edge<Integer, Integer>(fromIndex, toIndex);
    }

    return new Schlegel(points, edges, edgesindices);
  }

}
