package pointGroups.util.polymake;

import java.util.Collection;

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

  public FundamentalTransformer(Collection<? extends Point> points) {
    this.points = points;
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
    sb.append("print \"\\n\";");
    sb.append("my $adj = $v->DUAL_GRAPH->ADJACENCY->adjacent_nodes(0);");
    sb.append("my $s0 = $points->[0];");
    sb.append("my $s1 = $points->[$adj->[0]];");
    sb.append("my $s2 = $points->[$adj->[1]];");
    sb.append("my $s3 = $points->[$adj->[2]];");
    if (size == 4) sb.append("my $s4 = $points->[$adj->[3]];");
    sb.append("print \"&\\n\";");
    sb.append("print $s0;print \"\\n\";");
    sb.append("print $s1; print \"\\n\";");
    sb.append("print $s2; print \"\\n\";");
    sb.append("print $s3; print \"\\n\";");
    if (size == 4) sb.append("print $s4; print \"\\n\";");
    this.script = sb.toString();
    return this.script;
  }

  @Override
  public Fundamental transformResultString() {
    // StringBuilder regex = new StringBuilder();
    // minimum one 3D point followed by...
    // regex.append("([-+]?[0-9]*\\.?[0-9]+([eE][-+]?[0-9]+)? [-+]?[0-9]*\\.?[0-9]+([eE][-+]?[0-9]+)? [-+]?[0-9]*\\.?[0-9]+([eE][-+]?[0-9]+)?\\n)+|");
    // or one 4D point
    // regex.append("([-+]?[0-9]*\\.?[0-9]+([eE][-+]?[0-9]+)? [-+]?[0-9]*\\.?[0-9]+([eE][-+]?[0-9]+)? [-+]?[0-9]*\\.?[0-9]+([eE][-+]?[0-9]+)? [-+]?[0-9]*\\.?[0-9]+([eE][-+]?[0-9]+)?\\n)+)");

    // TODO Should be extended to the following matrix

    // if (!resultString.matches(regex.toString())) { throw new
    // PolymakeOutputException(
    // "String set by setResultString() does not match defined format for fundamental domain.");
    // }

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

  private Fundamental transformHelper() {
    String[] pointString = this.resultString.split("\n");
    double[][] prePoints = new double[pointString.length - 1][];

    // First point
    String[] coords = pointString[0].split(" ");
    prePoints[0] = new double[coords.length - 1];
    for (int j = 1; j < coords.length; j++) {
      prePoints[0][j - 1] = this.parseCoordinate(coords[j]);
    }

    // The next points
    for (int i = 1; i < prePoints.length; i++) {
      String[] cords = pointString[i + 1].split(" ");
      prePoints[i] = new double[cords.length - 1];
      // Drop first coordinate for it is only one
      for (int j = 1; j < cords.length; j++) {
        prePoints[i][j - 1] = this.parseCoordinate(cords[j]);
      }
    }
    // The first point point can be used to check, if the Voronoi Was
    // Correct.
    //
    // if (nearNull(prePoints[0]))
    // throw new Exception("Voronoi not correct");
    //

    double[][] points = new double[prePoints.length - 2][];

    // Build every dimension - 1 kombination of the later points with the first
    // one

    if (this.dim == 3) {
      points[0] =
          PointUtil.div(3, PointUtil.add(
              PointUtil.add(prePoints[1], prePoints[2]), prePoints[3]));
      points[1] =
          PointUtil.div(3, PointUtil.add(
              PointUtil.add(prePoints[1], prePoints[2]), prePoints[4]));

      points[2] =
          PointUtil.div(3, PointUtil.add(
              PointUtil.add(prePoints[1], prePoints[3]), prePoints[4]));

    }
    else {
      points[0] =
          PointUtil.add(PointUtil.add(
              PointUtil.add(prePoints[1], prePoints[2]), prePoints[3]),
              prePoints[4]);
      points[1] =
          PointUtil.add(PointUtil.add(
              PointUtil.add(prePoints[1], prePoints[2]), prePoints[3]),
              prePoints[5]);
      points[2] =
          PointUtil.add(
              PointUtil.add(PointUtil.add(points[1], points[2]), points[4]),
              points[5]);
      points[3] =
          PointUtil.mult(1 / 4, PointUtil.add(
              PointUtil.add(PointUtil.add(points[1], points[3]), points[4]),
              points[5]));

    }

    //
    // The second point is the affine translation
    //
    double[] aff = points[0];
    //
    // The Matrix is build of the last n-1 points
    // The first one is the voronoi vertex
    // the second one is the affine translation
    // the last one is a point at infinity
    //
    double[][] mat = new double[points.length - 1][];
    for (int i = 1; i < points.length; i++) {
      mat[i - 1] = new double[points[i].length];
      mat[i - 1] = PointUtil.subtract(points[i], aff);
    }

    //
    // Lastly the boundary points of this construction are the unit base for
    // the
    // goal dimension
    //
    double[][] boundary;
    boundary = new double[points.length][points[1].length - 1];

    for (int i = 0; i < boundary.length; i++) {
      boundary[i] = PointUtil.unit(i, points[1].length - 1);// points[i + 2];
      // System.out.println((showPoint(boundary[i])));
    }

    return new KnownFundamental(boundary, PointUtil.transpose(mat), aff);
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
