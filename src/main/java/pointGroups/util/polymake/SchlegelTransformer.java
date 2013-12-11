package pointGroups.util.polymake;

import pointGroups.geometry.Pair;
import pointGroups.geometry.Point;
import pointGroups.util.AbstractTransformer;

import java.util.Collection;

/**
 * Transforms points into a script as string for computing schlegel vertices and edges
 * @author Nadja, Simon
 */
public class SchlegelTransformer<Schlegel>
  extends AbstractTransformer<Schlegel>
{
  
  private final Collection<? extends Point> points;
  private final int facet;
  
  public SchlegelTransformer(Collection<? extends Point> points)
  {
    this.points = points;
    this.facet = -1;
  }
  
  public SchlegelTransformer(Collection<? extends Point> points, int facet)
  {
    this.points = points;
    this.facet = facet;
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

    script.append("print $v.$e");
    return script.toString();
  }

  private String pointsToString(){
    StringBuilder matrix = new StringBuilder(200);
    double[] pointComps;
    
    matrix.append("[");
    
    for(Point point : points){
      matrix.append("[1");
      pointComps = point.getComponents();
      for (double comp : pointComps) {
        matrix.append("," + comp);
      }
      // for simplicity always appending a comma after each transformation of a point
      // afterwards the last comma will be replaced by a closing bracket ']' of the matrix 
      matrix.append("],");
    }
    // replacing last comma of the for-loop with a closing bracket of the matrix
    matrix.setCharAt(matrix.length()-1, ']');
    return matrix.toString();
  }

  @Override
  protected Schlegel transformResultString() {
    // TODO Auto-generated method stub
    return null;
  }

	@Override
	public Schlegel getResult() throws PolymakeOutputException {
		while (result == null) {
			try {
				wait();
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		StringBuilder regex = new StringBuilder();
		// minimum one 2D point or...
		regex.append("([-+]?[0-9]*\\.?[0-9]+([eE][-+]?[0-9]+)?\\s[-+]?[0-9]*\\.?[0-9]+([eE][-+]?[0-9]+)?\\n)+|");
		// minimum one 3D point followed by...
		regex.append("[-+]?[0-9]*\\.?[0-9]+([eE][-+]?[0-9]+)?\\s[-+]?[0-9]*\\.?[0-9]+([eE][-+]?[0-9]+)?\\s[-+]?[0-9]*\\.?[0-9]+([eE][-+]?[0-9]+)?\\n)+");
		// a $\n...
		regex.append("\\$\\n");
		// minimum one edge
		regex.append("(\\{[0-9]+\\s[0-9]+\\}\\n)+");

		// test if result is formatted correct
		if (!result.matches(regex.toString())) {
			throw new PolymakeOutputException(
					"String set by setResult() does not match defined format for schlegel.");
		} else {
		}
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
			String[] compStr = str.substring(1, str.length() - 2).split("\\,");
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
		// TODO das mit den Generics ist Mist. Geht das besser?
		Pair<Point3D, Point3D>[] edges = new Pair[splittedEdgesString.length];
		// start iteration with i = 1 because the first string after splitting
		// is
		// empty caused by leading \n
		for (int i = 1; i < splittedEdgesString.length; i++) {
			String str = splittedEdgesString[i];
			// ignore brackets and split into components
			String[] compStr = str.substring(1, str.length() - 2).split("\\,");
			// TODO what if there are more or less than two components for an
			// edge?
			Point3D from = points[Integer.valueOf(compStr[0])];
			Point3D to = points[Integer.valueOf(compStr[1])];
			edges[i] = new Pair<Point3D, Point3D>(from, to);
		}

		return new Schlegel(points, edges);
	}
}
