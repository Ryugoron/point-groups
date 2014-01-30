package pointGroups.util.polymake;

import java.util.Collection;

import pointGroups.geometry.Edge;
import pointGroups.geometry.Point;
import pointGroups.geometry.Point3D;
import pointGroups.geometry.Schlegel;
import pointGroups.geometry.Symmetry;
import pointGroups.util.AbstractTransformer;
import pointGroups.util.polymake.SchlegelTransformer.SchlegelCompound;


/**
 * Transforms points into a script as string for computing schlegel vertices and
 * edges
 * 
 * @author Nadja, Simon
 */
public class SchlegelTransformer
  extends AbstractTransformer<SchlegelCompound>
{

  private final Collection<? extends Point> points;
  private final Point p;
  private final Symmetry<?, ?> sym;

  public SchlegelTransformer(final Collection<? extends Point> points,
      final Symmetry<?, ?> sym, final Point p) {
    this.points = points;
    this.sym = sym;
    this.p = p;
  }

  public SchlegelTransformer(final Collection<? extends Point> points) {
    this.points = points;
    this.p = null;
    this.sym = null;
  }

  @Override
  public String toScript() {

    // Suppose the script has around 1000 characters
    StringBuilder script = new StringBuilder(1000);
    script.append("use application \"polytope\";");

    script.append("my $mat=new Matrix<Rational>(");
    script.append(pointsToString());
    script.append(");");
    script.append("my $p = new Polytope(POINTS=>$mat);");

    script.append("my $schlegelverts = $p->SCHLEGEL_DIAGRAM->VERTICES;");
    script.append("my $edges = $p->GRAPH->EDGES;");

    script.append("my $v = \"$schlegelverts\";");
    script.append("my $e = \"$edges\";");

    script.append("print $v.\"\\$\\n\".$e");

    return script.toString();
  }

  private String pointsToString() {
    StringBuilder matrix = new StringBuilder(200);
    double[] pointComps;

    matrix.append("[");

    for (Point point : points) {
      matrix.append("[1");
      pointComps = point.getComponents();
      for (double comp : pointComps) {
        matrix.append("," + comp);
      }
      // for simplicity always appending a comma after each transformation
      // of a
      // point
      // afterwards the last comma will be replaced by a closing bracket
      // ']' of
      // the matrix
      matrix.append("],");
    }
    // replacing last comma of the for-loop with a closing bracket of the
    // matrix
    matrix.setCharAt(matrix.length() - 1, ']');
    return matrix.toString();
  }

  @Override
  public SchlegelCompound transformResultString() {
    
    // splitting result string into two. One contains the points, the other
    // the
    // edges.
    
    String pointsString;
    String edgesString;
    try {
      String[] pointsAndEdges = resultString.split("\\$\n");
      pointsString = pointsAndEdges[0];
      edgesString = pointsAndEdges[1];
    }
    catch (Exception e) {
      logger.severe("Wrong Format of resultString. Probably missing \\$\n");
      logger.fine(e.getMessage());
      logger.fine("resultString was: " + resultString);
      throw new PolymakeOutputException("Can't split Resultstring at \\$\n");
    }

    // transforming strings into Point-Objects
    Point3D[] points;
    try {
      // splitting string containing points into one per point
      String[] splittedPointsString = pointsString.split("\n");
      points = new Point3D[splittedPointsString.length];
      for (int i = 0; i < splittedPointsString.length; i++) {
        String str = splittedPointsString[i];
        // ignore brackets and split into components
        String[] compStr = str.substring(0, str.length()).split(" ");
        if (compStr.length == 2) {
          points[i] =
              new Point3D(Double.parseDouble(compStr[0]),
                  Double.parseDouble(compStr[1]), 0);
        }
        else if (compStr.length == 3) {
          points[i] =
              new Point3D(Double.parseDouble(compStr[0]),
                  Double.parseDouble(compStr[1]), Double.parseDouble(compStr[2]));
        }
        else {
          logger.severe("Point in resultString split in: " +
              compStr.length + "components");
          logger.fine("resultString was: " + resultString);
        }
      }
    }
    catch (Exception e) {
      logger.severe("Can't parse points in resultString.");
      logger.fine(e.getMessage());
      logger.fine("resultString was: " + resultString);
      throw new PolymakeOutputException("Wrong Format of resultString.");
    }
    Edge<Point3D, Point3D>[] edges;
    Edge<Integer, Integer>[] edgesindices;
    try {
      // Store Edges as Array von Pair<Point3D,Point3D> and as Array von Pair<Integer,Integer>
      String[] splittedEdgesString = edgesString.split("\n");
      edges = new Edge[splittedEdgesString.length];
      edgesindices = new Edge[splittedEdgesString.length];
      // start iteration with i = 1 because the first string after splitting
      // is
      // empty caused by leading \n
      for (int i = 0; i < splittedEdgesString.length; i++) {
        String str = splittedEdgesString[i];
        // ignore brackets and split into components
        String[] compStr = str.substring(1, str.length() - 1).split(" ");
        int fromIndex = Integer.valueOf(compStr[0]);
        int toIndex = Integer.valueOf(compStr[1]);
        Point3D from = points[fromIndex];
        Point3D to = points[toIndex];
        edges[i] = new Edge<Point3D, Point3D>(from, to);
        edgesindices[i] = new Edge<Integer, Integer>(fromIndex, toIndex);
      }
    }
    catch (Exception e) {
      logger.severe("Can't parse edges in resultString.");
      logger.fine(e.getMessage());
      logger.fine("resultString was: " + resultString);
      throw new PolymakeOutputException("Wrong Format of resultString.");
    }

    return new SchlegelCompound(new Schlegel(points, edges, edgesindices),
        this.sym, this.p);
  }


  public static class SchlegelCompound
  {
    private final Schlegel s;
    private final Symmetry<?, ?> sym;
    private final Point p;

    public SchlegelCompound(final Schlegel s, final Symmetry<?, ?> sym,
        final Point p) {
      this.s = s;
      this.sym = sym;
      this.p = p;
    }

    public Schlegel getSchlegel() {
      return this.s;
    }

    public Symmetry<?, ?> getSymmetry() {
      return this.sym;
    }

    public Point getPoint() {
      return this.p;
    }

  }
}
