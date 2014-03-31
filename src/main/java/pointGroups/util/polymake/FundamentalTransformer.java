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
 * and a projection into (n-1) dimensions.
TODO (31.3)

Besprechung (24.3)
 * 
 * @author Max
 */
public class FundamentalTransformer
  extends AbstractTransformer<Fundamental>
{

  private String script = null;
  private final List<? extends Point> points;
  private final Point center;
  private final int dim;
  
  private Fundamental f;


  public FundamentalTransformer(Point center, List<? extends Point> points) {
    this.points = points;
    this.center = center;
    this.dim = this.points.iterator().next().getComponents().length;
  }

  @Override
  public String toScript() {
    if (script != null) return script;
    
    StringBuilder sb = new StringBuilder(1000);
    
    // Build all hyperplanes x*(p-q) >= 0
    sb.append("my $hyper = new Matrix<Rational>([");
    
    boolean first = true;
    for (Point point : this.points) {
      // Corrects some problems for bad voronoi cells
      if(!this.orientationCheck(point)) continue;
      
      if (!first) {
        sb.append(",");
      }
      else {
        first = false;
      }
      sb.append("[0.0");
      for (int i = 0; i < dim; i++) {
        
        sb.append(", " + (this.center.getComponents()[i] - point.getComponents()[i]));
      }
      sb.append("]");
    }
    sb.append("]);");
    
    
    // Build the affine hyperplane (x-p)*p = 0
    sb.append("my $aff = new Matrix<Rational>([");
    double val = 0;
    for(double c : this.center.getComponents()) val += c*c;
    sb.append("[" + val);
    for(int i = 0; i < this.center.getComponents().length; i++) {
      sb.append(" , "+this.center.getComponents()[i]);
    }
    sb.append("]);");
    
    sb.append("my $poly = new Polytope(INEQUALITIES=>$hyper, EQUATIONS=>$aff);");
    sb.append("print $poly->VERTICES;");
    sb.append("print \"-----\\n\"");
    sb.append("print $poly->GRAPH->EDGES;");
    sb.append("print \"-----\\n\"");
    sb.append("print $poly->FACETS");
    
    this.script = sb.toString();
    return this.script;
  }
  
  /**
   * 
   * This method checks, whether the point lies on the right side of the hyperplane
   * 
   * @param p - The point to check
   * @return true if c*p > 0
   */
  private boolean orientationCheck(Point p){
    double val = 0;
    for(int j = 0; j < this.dim; j++){
      val += this.center.getComponents()[j] * p.getComponents()[j];
    }
    return val > 0;
  }

  @Override
  public Fundamental transformResultString() {

    if(this.f != null) return this.f;
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
    this.f = res;
    return res;
  }

  private Fundamental transformHelper() {
    String[] answer = this.resultString.split("\n");
    
    List<double[]> points = new LinkedList<double[]>();
    List<Edge<Integer, Integer>> edges = new LinkedList<Edge<Integer, Integer>>();
    List<double[]> hyperPlanes = new LinkedList<double[]>();
    
    int pos = 0;
    // The first ones are Vertices
    while(answer[pos] != "-----"){
      points.add(parsePoint(answer[pos]));
      pos++;
    }
    
    //The second ones are Edges
    while(answer[pos] != "-----"){
      edges.add(parseEdge(answer[pos]));
      pos++;
    }
    
    //The last ones are hyperplanes for testing bounds
    while(pos < answer.length) {
      hyperPlanes.add(parseHyper(answer[pos]));
      pos++;
    }
    
    double[][] f2n = baseTransformation();
    double[][] n2f = PointUtil.transpose(f2n);
    
    // Points normalizing
    double[][] normPoints = new double[points.size()][];
    for(int i = 0; i < points.size(); i++){
      double[] p = PointUtil.applyMatrix(n2f, points.get(i));
      p = PointUtil.rmFst(p);
      normPoints[i] = p;
    }
    
    // Hyperplanes to right format
    double[][] hyper = new double[hyperPlanes.size()][];
    for(int i = 0; i < hyper.length; i++){
      hyper[i] = hyperPlanes.get(i);
    }
    
    return new KnownFundamental(normPoints, f2n, hyper, center.getComponents());
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
  
  /**
   * <p>
   * Parses a polymake point in String representation.
   * A point as the form n a b c, where n is a normalizing faktor.
   * </p>
   * @param s - Stringrepresentation of the point
   * @return the point as double string
   */
  private double[] parsePoint(String s) {
    String[] pS = s.split(" ");
    
    //Normalising Factor
    double norm = parseCoordinate(pS[0]);
    
    double[] point = new double[pS.length-1];
    for(int i = 1; i < pS.length; i++){
      point[i-1] = parseCoordinate(pS[i]) / norm;
    }
    return point;
  }
  
  /**
   * 
   * <p>
   * Parses a Stringrepresentation of polymake in the form
   * { a b }
   * </p>
   * 
   * @param s - Stringrepresentation of an edge
   * @return an edge object
   */
  private Edge<Integer, Integer> parseEdge(String s){
    String[] ends = s.substring(1,s.length()-1).split(" ");
    int[] endi = new int[2];
    endi[0] = Integer.parseInt(ends[0]);
    endi[1] = Integer.parseInt(ends[1]);
    
    return new Edge<Integer, Integer>(endi[0], endi[1]);
  }
  
  /**
   * 
   * <p>
   * Returns a hyperplane representation in the form
   * a0 a1 a2 a3
   * with the meaning a0 + a1x1 + a2x2 + a3x3 >= 0.
   * </p>
   * 
   * @param s - Stringrepresentation of a hyperplane
   * @return the hyperplane as double list
   */
  private double[] parseHyper(String s){
    String[] pS = s.split(" ");
    
    double[] point = new double[pS.length];
    for(int i = 0; i < pS.length; i++){
      point[i] = parseCoordinate(pS[i]);
    }
    return point;
  }
  
  /**
   * Calculates a base transformation for the current normalvector this.center.
   * Due to the fact, that this is a orthogonal Matrix, it can be inverted by transposing.
   * 
   * It calculates the transformation back into the standard basis.
   * 
   * @return Base transformation matrix
   */
  private double[][] baseTransformation() {
    //Will be hard coded for performance reasons.
    
    return null;
  }

}
