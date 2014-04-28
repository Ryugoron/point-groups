package pointGroups.util.polymake;

import java.util.Collection;

import pointGroups.geometry.Edge;
import pointGroups.geometry.Face;
import pointGroups.geometry.Point;
import pointGroups.geometry.Point3D;
import pointGroups.geometry.Polytope;
import pointGroups.geometry.Schlegel;
import pointGroups.geometry.Symmetry;
import pointGroups.util.AbstractTransformer;
import pointGroups.util.LoggerFactory;
import pointGroups.util.Utility;
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
  private final Symmetry<?> sym;
  protected boolean is3D = true;

  public SchlegelTransformer(final Collection<? extends Point> points,
      final Symmetry<?> sym, final Point p) {
    this.points = points;
    this.sym = sym;
    this.p = p;

    // determine the dimension of the points
    if (points.size() > 0) {
      Point point = points.iterator().next();
      is3D = point.getComponents().length == 3;
    }
  }

  public SchlegelTransformer(final Collection<? extends Point> points) {
    this(points, null, null);
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
    script.append("my $sep = \"\\$\\n\";");

    script.append("my $n = $p->N_VERTICES;");

    // use RIF_CYCLIC_NORMAL only id 3d mode, because it is not available
    // otherwise
    if (is3D) {
      // this retrieves all the faces of the polyhedron in a order, which
      // can be handled by jReality
      script.append("my $f = $p->RIF_CYCLIC_NORMAL;");
    }
    else {
      script.append("my $f = \"\";");
    }
    script.append("my $v = $schlegelverts;");
    script.append("my $e = $edges;");

    script.append("print $n.$sep.$v.$sep.$e.$sep.$f.$sep;");

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
      // for simplicity always appending a comma after each transformation of a
      // point afterwards the last comma will be replaced by a closing bracket
      // ']' of the matrix
      matrix.append("],");
    }
    // replacing last comma of the for-loop with a closing bracket of the
    // matrix
    matrix.setCharAt(matrix.length() - 1, ']');
    return matrix.toString();
  }

  protected Point3D[] parsePoints(String pointsString) {
    Point3D[] points;

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
        logger.severe("Point in resultString split in: " + compStr.length +
            "components");
        logger.fine("resultString was: " + resultString);
      }
    }

    return points;
  }

  protected Edge[] parseEdges(String edgesString) {
    Edge[] edges;

    // Store Edges as Array von Pair<Point3D,Point3D> and as Array von
    // Pair<Integer,Integer>
    String[] splittedEdgesString = edgesString.split("\n");

    edges = new Edge[splittedEdgesString.length];

    for (int i = 0; i < splittedEdgesString.length; i++) {
      String str = splittedEdgesString[i];
      // ignore brackets and split into components
      String[] compStr = str.substring(1, str.length() - 1).split(" ");
      int fromIndex = Integer.valueOf(compStr[0]);
      int toIndex = Integer.valueOf(compStr[1]);

      edges[i] = new Edge(fromIndex, toIndex);
    }

    return edges;
  }

  protected Face[] parseFaces(String facesString) {
    Face[] faces;

    String[] splittedString = facesString.split("\n");

    faces = new Face[splittedString.length];

    int i = 0;
    for (String faceString : splittedString) {

      if (faceString.isEmpty()) {
        continue;
      }

      Face face = new Face();

      for (String index : faceString.split(" ")) {
        face.addIndex(Integer.parseInt(index));
      }

      faces[i++] = face;
    }

    return faces;
  }

  /**
   * Build the original polytope of the schlegel-diagram. But only in the 3D
   * case.
   * 
   * @param edges
   * @param faces
   * @param numberOfPoints
   * @return
   */
  protected Polytope<Point3D> buildPolytope(Edge[] edges, Face[] faces,
      int numberOfPoints) {

    if (faces == null) return null;

    @SuppressWarnings("unchecked")
    Collection<Point3D> points = (Collection<Point3D>) this.points;

    if (numberOfPoints != points.size()) {
      LoggerFactory.get(getClass()).warning(
          String.format(
              "There are duplicated points, because the number of given points (%d) and the number of points (%d) returned by polymake mismatches.",
              points.size(), numberOfPoints));

      points = Utility.uniqueList(points);

      if (numberOfPoints != points.size()) {
        String msg =
            String.format(
                "Even after removing duplicated points the size mismatches (new size: %d).",
                points.size());

        throw new RuntimeException(msg);
      }
    }

    Point3D[] polytopePoints = points.toArray(new Point3D[] { });
    return new Polytope<Point3D>(polytopePoints, edges, faces);
  }

  @Override
  public SchlegelCompound transformResultString() {

    // splitting result string into two. One contains the points, the other
    // the edges.

    int numberOfPoints = 0;
    String pointsString;
    String edgesString;
    String facesString = "";
    try {
      String[] pointsAndEdges = resultString.split("\\$\n");
      numberOfPoints = Integer.parseInt(pointsAndEdges[0]);
      pointsString = pointsAndEdges[1];
      edgesString = pointsAndEdges[2];

      // faces are only available in 3d
      if (is3D) {
        facesString = pointsAndEdges[3];
      }
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
      points = parsePoints(pointsString);
    }
    catch (Exception e) {
      logger.severe("Can't parse points in resultString.");
      logger.fine(e.getMessage());
      logger.fine("resultString was: " + resultString);
      throw new PolymakeOutputException("Wrong Format of resultString.");
    }

    Edge[] edges;
    try {
      edges = parseEdges(edgesString);
    }
    catch (Exception e) {
      logger.severe("Can't parse edges in resultString.");
      logger.fine(e.getMessage());
      logger.fine("resultString was: " + resultString);
      throw new PolymakeOutputException("Wrong Format of resultString.");
    }

    Face[] faces = null;
    try {
      // faces are only available in 3d
      if (is3D) {
        faces = parseFaces(facesString);
      }
    }
    catch (Exception e) {
      logger.severe("Can't parse faces in resultString.");
      logger.fine(e.getMessage());
      logger.fine("resultString was: " + resultString);
      throw new PolymakeOutputException("Wrong Format of resultString.");
    }

    Polytope<Point3D> polytope = buildPolytope(edges, faces, numberOfPoints);
    return new SchlegelCompound(new Schlegel(points, edges, polytope),
        this.sym, this.p);
  }


  public static class SchlegelCompound
  {
    private final Schlegel s;
    private final Symmetry<?> sym;
    private final Point p;

    public SchlegelCompound(final Schlegel s, final Symmetry<?> sym,
        final Point p) {
      this.s = s;
      this.sym = sym;
      this.p = p;
    }

    public Schlegel getSchlegel() {
      return this.s;
    }

    public Symmetry<?> getSymmetry() {
      return this.sym;
    }

    public Point getPoint() {
      return this.p;
    }

  }
}
