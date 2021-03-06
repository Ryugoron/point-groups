package pointGroups.util.polymake;

import java.util.LinkedList;
import java.util.List;

import pointGroups.geometry.Edge;
import pointGroups.geometry.Fundamental;
import pointGroups.geometry.KnownFundamental;
import pointGroups.geometry.Point;
import pointGroups.geometry.UnknownFundamental;
import pointGroups.util.AbstractTransformer;
import pointGroups.util.point.PointUtil;


/**
 * Takes a Collection of points and builds a script to compute one Voronoi cell
 * and a projection into (n-1) dimensions. TODO (31.3) Besprechung (24.3)
 * 
 * @author Max
 */
public class FundamentalTransformer
  extends AbstractTransformer<Fundamental>
{

  private String script = null;
  private final List<? extends Point> points;
  protected final Point center;
  private final int dim;

  private Fundamental f;

  // For testing issus
  protected double[][] n2f;
  protected double[][] f2n;

  protected List<double[]> clalcPoints;

  public FundamentalTransformer(Point center, List<? extends Point> points) {
    this.points = points;
    this.center = center;
    this.dim = this.points.iterator().next().getComponents().length;
  }

  /**
   * Builds a script for polymake, that first initializes the voronoicell of
   * center in points and then returns the vertices and edges of the cell,
   * leading a bit flag, that says, whether the polytope is bounded.
   */
  @Override
  public String toScript() {
    if (script != null) return script;

    StringBuilder sb = new StringBuilder(1000);

    // Build all hyperplanes x*(p-q) >= 0
    sb.append("my $hyper = new Matrix<Rational>([");

    boolean first = true;
    for (Point point : this.points) {
      // Corrects some problems for bad voronoi cells
      if (!this.orientationCheck(point)) continue;

      if (!first) {
        sb.append(",");
      }
      else {
        first = false;
      }
      sb.append("[0.0");
      for (int i = 0; i < dim; i++) {

        sb.append(", " +
            (this.center.getComponents()[i] - point.getComponents()[i]));
      }
      sb.append("]");
    }
    sb.append("]);");

    // Build the affine hyperplane (x-p)*p = 0
    sb.append("my $aff = new Matrix<Rational>([");
    double val = 0;
    for (double c : this.center.getComponents())
      val -= c * c;
    sb.append("[" + val);
    for (int i = 0; i < this.center.getComponents().length; i++) {
      sb.append(" , " + this.center.getComponents()[i]);
    }
    sb.append("]]);");

    sb.append("my $poly = new Polytope(INEQUALITIES=>$hyper, EQUATIONS=>$aff);");
    sb.append("print $poly->BOUNDED;print \"\\n\";");
    sb.append("print $poly->VERTICES;");
    sb.append("print \"-----\\n\";");
    sb.append("print $poly->GRAPH->EDGES;");
    sb.append("print \"-----\\n\";");
    sb.append("print $poly->FACETS;");

    this.script = sb.toString();
    return this.script;
  }

  /**
   * This method checks, whether the point lies on the right side of the
   * hyperplane
   * 
   * @param p - The point to check
   * @return true if c*p > 0
   */
  private boolean orientationCheck(Point p) {
    double val = 0;
    for (int j = 0; j < this.dim; j++) {
      val += this.center.getComponents()[j] * p.getComponents()[j];
    }
    return val > 0;
  }

  @Override
  public Fundamental transformResultString() {

    if (this.f != null) return this.f;
    Fundamental res;
    try {
      // Tries to transform a Fundamental Region.
      // System.out.println(this.script);
      res = transformHelper();
      if (res.isKnown()) {
        if (res.getEdges().length < 3) {
          logger.info("To less edges for a polytope. Returning an Unkown Fundamental Region.");
          res = new UnknownFundamental();
        }
      }
    }
    catch (Exception e) {
      logger.info("Could not transform Fundamental Result String, given trivial Fundamental instead.");
      res = new UnknownFundamental();
    }
    this.f = res;
    return res;
  }

  private Fundamental transformHelper() {
    String[] answer = this.resultString.split("\n");

    // The first flag says if it is bounded, 0 -> Unbounded so we want a
    // UnknwonFundamental
    if (answer[0].equals("0")) {
      logger.info("Unbounded Fundamental Region computed. Returning UnkownFundamental instead");
      return new UnknownFundamental();
    }

    List<double[]> points = new LinkedList<double[]>();
    List<Edge> edges = new LinkedList<Edge>();
    List<double[]> hyperPlanes = new LinkedList<double[]>();
    int pos = 1;
    // The first ones are Vertices
    while (!answer[pos].equals("-----")) {
      points.add(parsePoint(answer[pos]));
      pos++;
    }
    pos++;
    // The second ones are Edges
    while (!answer[pos].equals("-----")) {
      edges.add(parseEdge(answer[pos]));
      pos++;
    }
    pos++;
    // The last ones are hyperplanes for testing bounds
    while (pos < answer.length) {
      hyperPlanes.add(parseHyper(answer[pos]));
      pos++;
    }
    n2f = baseTransformation();
    f2n = PointUtil.transpose(n2f);

    // Points normalizing
    double[][] normPoints = new double[points.size()][];
    for (int i = 0; i < points.size(); i++) {
      double[] p = PointUtil.applyMatrix(n2f, points.get(i));
      p = PointUtil.rmFst(p);
      normPoints[i] = p;
    }

    // Hyperplanes to right format
    double[][] hyper = new double[hyperPlanes.size()][];
    for (int i = 0; i < hyper.length; i++) {
      hyper[i] = hyperPlanes.get(i);
    }

    this.clalcPoints = points;

    return new KnownFundamental(normPoints, f2n, hyper, center.getComponents(),
        edges, n2f);
  }

  /**
   * Parses a coordinate
   */
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

  /**
   * <p> Parses a polymake point in String representation. A point as the form n
   * a b c, where n is a normalizing faktor. </p>
   * 
   * @param s - Stringrepresentation of the point
   * @return the point as double string
   */
  private double[] parsePoint(String s) {
    String[] pS = s.split(" ");

    // Normalising Factor
    double norm = parseCoordinate(pS[0]);

    double[] point = new double[pS.length - 1];
    for (int i = 1; i < pS.length; i++) {
      point[i - 1] = parseCoordinate(pS[i]) / norm;
    }
    return point;
  }

  /**
   * <p> Parses a Stringrepresentation of polymake in the form { a b } </p>
   * 
   * @param s - Stringrepresentation of an edge
   * @return an edge object
   */
  private Edge parseEdge(String s) {
    String[] ends = s.substring(1, s.length() - 1).split(" ");
    int[] endi = new int[2];
    endi[0] = Integer.parseInt(ends[0]);
    endi[1] = Integer.parseInt(ends[1]);

    return new Edge(endi[0], endi[1]);
  }

  /**
   * <p> Returns a hyperplane representation in the form a0 a1 a2 a3 with the
   * meaning a0 + a1x1 + a2x2 + a3x3 >= 0. </p>
   * 
   * @param s - Stringrepresentation of a hyperplane
   * @return the hyperplane as double list
   */
  private double[] parseHyper(String s) {
    String[] pS = s.split(" ");

    double[] point = new double[pS.length];
    for (int i = 0; i < pS.length; i++) {
      point[i] = parseCoordinate(pS[i]);
    }
    return point;
  }

  /**
   * Calculates a base transformation for the current normalvector this.center.
   * Due to the fact, that this is a orthogonal Matrix, it can be inverted by
   * transposing. It calculates the transformation back into the standard basis.
   * 
   * @return Base transformation matrix
   */
  protected double[][] baseTransformation() {
    double scal = 0;
    int i = 0;
    while (scal == 0) {
      i++;
      scal =
          PointUtil.stScalarProd(PointUtil.unit(i, this.dim),
              center.getComponents());
    }
    double[][] base = new double[this.dim][];
    base[0] = center.getComponents();
    for (int j = 1; j < this.dim; j++) {
      base[j] =
          (j < i) ? PointUtil.unit(j, this.dim) : PointUtil.unit(j + 1,
              this.dim);
    }
    return PointUtil.gramSchmitt(base);
  }

}
