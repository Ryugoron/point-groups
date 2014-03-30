package pointGroups.util.polymake;

import java.util.ArrayList;
import java.util.Collection;
import java.util.LinkedList;

import pointGroups.geometry.Edge;
import pointGroups.geometry.Fundamental;
import pointGroups.geometry.KnownFundamental;
import pointGroups.geometry.Point;
import pointGroups.geometry.UnknownFundamental;
import pointGroups.util.AbstractTransformer;
import pointGroups.util.point.PointUtil;


/**
 * Takes a Collection of points and builds a script to compute one Voronoi cell
 * and a projection into (n-1) dimensions.
 * 
 * @author Max
 */
public class FundamentalTransformer
  extends AbstractTransformer<Fundamental>
{

  private String script = null;
  private final Collection<? extends Point> points;
  private final int dim;

  // To ensure the same order
  private ArrayList<Point> pointsInOrder;

  public FundamentalTransformer(Collection<? extends Point> points) {
    this.points = points;
    pointsInOrder = new ArrayList<>(this.points.size());
    this.dim = this.points.iterator().next().getComponents().length;
  }

  @Override
  public String toScript() {
    if (script != null) return script;
    int size = 0;
    // The more points the larger the scirpt
    StringBuilder sb = new StringBuilder(1000);
    sb.append("my $points = new Matrix<Rational>([");
    boolean first = true;
    for (Point point : this.points) {
      this.pointsInOrder.add(point);
      if (!first) {
        sb.append(",");
        size = point.getComponents().length;
      }
      else {
        first = false;
      }
      sb.append("[1.0");
      for (double comp : point.getComponents()) {
        sb.append(", " + comp);
      }
      sb.append("]");
    }
    sb.append("]);");
    sb.append("my $v = new VoronoiDiagram(SITES=>$points);");
    sb.append("print $v->VORONOI_VERTICES->[0];");
    sb.append("print $v->DUAL_GRAPH->ADJACENCY;");
    this.script = sb.toString();
    return this.script;
  }

  @Override
  public Fundamental transformResultString() {

    Fundamental res;
    try {
      // Tries to transform a Fundamental Region.
      // System.out.println(this.script);
      res = transformHelper();
    }
    catch (Exception e) {
      logger.info("Could not transform Fundamental Result String, given trivial Fundamental instead.");
      res = new UnknownFundamental();
    }
    return res;
  }
  
  /**
   * Copmutes the edges between the indices, if the origin is has a 
   * Hemmingdistance of 1.
   *  
   * @param origin Indices the point origened in
   * @return list of edges
   */
  private LinkedList<Edge<Integer, Integer>> getEdges(LinkedList<int[]> origin){
    LinkedList<Edge<Integer, Integer>> res = new LinkedList<Edge<Integer, Integer>>();
    for(int i = 0; i < origin.size(); i++){
      for(int j = 0; j < origin.size(); j++) {
        if (hemmingDist(origin.get(i), origin.get(j)) == 1){
          res.add(new Edge<Integer, Integer>(i, j));
        }
      }
    }
    return res;
  }
  
  private int hemmingDist(int[] a, int [] b) {
    int res = 0;
    int akta = 0, aktb = 0;
    while (akta < a.length && aktb < b.length){
      if(a[akta] == b[aktb]){
        res++;
        akta++;
        aktb++;
      } else if(a[akta] < b[aktb] ) {
        akta++;
      } else {
        aktb++;
      }
    }
    return res;
  }

  private Fundamental transformHelper() {
    String[] answer = this.resultString.split("\n");

    // Last line is infinity
    int[][] adjMat = new int[answer.length - 1][];

    // Parse the adjacency matrix
    for (int i = 0; i < answer.length - 1; i++) {
      String[] line = answer[i].substring(1, answer.length - 1).split(" ");
      adjMat[i] = new int[line.length - 1];
      // Again the last point should be the infinity point
      for (int j = 0; j < line.length - 1; j++) {
        adjMat[i][j] = Integer.parseInt(line[j]);
      }
    }

    // Building the circle
    // Go from the fist point in order through the points
    LinkedList<Point> fundPoints = new LinkedList<Point>();
    // Sorted origin of the fund points (same indices)
    LinkedList<int[]> origin = new LinkedList<int[]>();

    if (dim == 3) {
      // 2 Adjacent nodes make a voronoi edge
      for (int i = 0; i < adjMat[0].length; i++) {
        for (int j = 0; j < adjMat[0].length; j++) {
          // For two adjecent nodes look if they are connected (and ordered)
          for (int k = 0; k < adjMat[j].length; k++) {
            if (adjMat[0][i] == adjMat[adjMat[0][j]][k] &&
                adjMat[0][i] < adjMat[0][j]) {
              origin.add(new int[] { adjMat[0][i], adjMat[0][j] });

              // Copmute Mass Point of triangle
              Point p =
                  PointUtil.doubleToPoint(PointUtil.add(
                      this.pointsInOrder.get(0).getComponents(), PointUtil.add(
                          this.pointsInOrder.get(adjMat[0][j]).getComponents(),
                          this.pointsInOrder.get(adjMat[0][i]).getComponents())));
              fundPoints.add(p);

            }
          }
        }
      }
    }
    else {
      // 3 Adjecent nodes make a voronoi edge
      for (int i = 0; i < adjMat[0].length; i++) {
        for (int j = 0; j < adjMat[0].length; j++) {
          for (int k = 0; k < adjMat[0].length; k++) {
            // Test if they see each other
            boolean one = false;
            boolean two = false;
            boolean three = false;
            for (int l = 0; l < adjMat[adjMat[0][j]].length; l++) {
              if (adjMat[0][i] == adjMat[adjMat[0][j]][l]) {
                one = true;
                break;
              }
            }
            for (int l = 0; l < adjMat[adjMat[0][k]].length; l++) {
              if (adjMat[0][j] == adjMat[adjMat[0][k]][l]) {
                two = true;
                break;
              }
            }
            for (int l = 0; l < adjMat[adjMat[0][i]].length; l++) {
              if (adjMat[0][k] == adjMat[adjMat[0][i]][l]) {
                three = true;
                break;
              }
            }
            if (one && two && three && adjMat[0][i] < adjMat[0][j] &&
                adjMat[0][j] < adjMat[0][k]) {
              origin.add(new int[] { adjMat[0][i], adjMat[0][j], adjMat[0][k] });
              Point p =
                  PointUtil.doubleToPoint(PointUtil.add(
                      this.pointsInOrder.get(adjMat[0][k]).getComponents(),
                      PointUtil.add(
                          this.pointsInOrder.get(0).getComponents(),
                          PointUtil.add(
                              this.pointsInOrder.get(adjMat[0][j]).getComponents(),
                              this.pointsInOrder.get(adjMat[0][i]).getComponents()))));
              fundPoints.add(p);
            }
          }
        }
      }
    }
    
    // Reconstruct the edges
    LinkedList<Edge<Integer, Integer>> edges = getEdges(origin);

    
    // Calculate hit with hyperplane
    // normal and origin vektor is points[0]
    
    
    // Gram Schmitt to Orthonormalize Hyperplanes normal vektor + rest of base
    
    
    // Base Transformation Matrix
    
    // Move points to origin
    
    // Express in new coordinates
    
    
    return null;
  }

  private double parseCoordinate(String s) {
    if (s.contains("/")) {
      String[] both = s.split("/");
      if (both.length != 2)
        throw new IllegalArgumentException("Tried to create a double from : " +
            s);
      return (Double.parseDouble(both[0]) / Double.parseDouble(both[1]));
    }
    else {
      return Double.parseDouble(s);
    }
  }

}
