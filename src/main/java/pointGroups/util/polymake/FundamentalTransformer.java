package pointGroups.util.polymake;

import java.util.Collection;
import java.util.logging.Logger;

import pointGroups.geometry.Fundamental;
import pointGroups.geometry.KnownFundamental;
import pointGroups.geometry.Point;
import pointGroups.util.AbstractTransformer;

/**
 * 
 * Takes a Collection of points and builds a script to compute one Voronoi cell
 * and a projection into (n-1) dimensions.
 * 
 * @author Max
 * 
 */
public class FundamentalTransformer<PS extends Point> extends
		AbstractTransformer<Fundamental<PS>> {

	// TODO maybe long is better?
	public static int count = 0;
	public final int id = count++;
	// TODO is it a good idea to name the logger this way?
	final protected Logger logger = Logger.getLogger(this.getClass().getName()
			+ "(id: " + id + ")");

	private String script = null;
	private Collection<PS> points;

	public FundamentalTransformer(Collection<PS> points) {
		this.points = points;
	}

	@Override
	public String toScript() {
		if (script != null)
			return script;
		int size = 0;
		// The more points the larger the scirpt
		StringBuilder sb = new StringBuilder(1000);
		sb.append("my points = new Matrix<Rational>([");
		boolean first = true;
		for (Point point : this.points) {
			if (!first) {
				sb.append(",");
				size = point.getComponents().length;
			} else {
				first = false;
			}
			sb.append("[1,");
			for (double comp : point.getComponents()) {
				sb.append(", " + comp);
			}
			sb.append("]");
		}
		sb.append("]);");
		sb.append("my $v = new VoronoiDiagram(SITES=>$points);");
		sb.append("print $v->VORONOI_VERTICES;");
		sb.append("my $adj = $v->DUAL_GRAPH->ADJACENCY->adjacent_nodes[0];");
		sb.append("my $s1 = $points->[$adj->[0]];");
		sb.append("my $s2 = $points->[$adj->[1]];");
		sb.append("my $s3 = $points->[$adj->[2]];");
		if(size==4)
			sb.append("my $s4 = $points->[$adj->[3]];");
		
		sb.append("$p1 = $s1 + $s2;");
		sb.append("$p2 = $s2 + $s3;");
		sb.append("$p3 = $s3 + $s4;");
		if(size==4) //TODO check this one. does not seem quite right
			sb.append("$p4 = $s4 + $s1;");
		sb.append("my $poly = new Polytope(POINTS=>[$p1,$p2,$p3,");
		if(size==4){
			sb.append("p4,[2,0,0,0,0]]);");
		} else {
			sb.append("[2,0,0,0]]);");
		}
		sb.append("--------\n");
		sb.append("print $poly-VERTICES;");
		this.script = sb.toString();
		return this.script;
	}

	@Override
	protected Fundamental<PS> transformResultString() {
		StringBuilder regex = new StringBuilder();
		// minimum one 3D point followed by...
		regex.append("([-+]?[0-9]*\\.?[0-9]+([eE][-+]?[0-9]+)? [-+]?[0-9]*\\.?[0-9]+([eE][-+]?[0-9]+)? [-+]?[0-9]*\\.?[0-9]+([eE][-+]?[0-9]+)?\\n)+|");
		// or one 4D point
		regex.append("([-+]?[0-9]*\\.?[0-9]+([eE][-+]?[0-9]+)? [-+]?[0-9]*\\.?[0-9]+([eE][-+]?[0-9]+)? [-+]?[0-9]*\\.?[0-9]+([eE][-+]?[0-9]+)? [-+]?[0-9]*\\.?[0-9]+([eE][-+]?[0-9]+)?\\n)+)");

		// TODO Should be extended to the following matrix

		if (!resultString.matches(regex.toString())) {
			throw new PolymakeOutputException(
					"String set by setResultString() does not match defined format for fundamental domain.");
		}

		// Extracting the points from the result
		String[] pointString = this.resultString.split("\n");
		double[][] points = new double[pointString.length - 1][pointString[0]
				.length() - 1];
		for (int i = 0; i < pointString.length; i++) {
			if (i == 1)
				continue; // Die Leerzeile überspringen
			String[] cords = pointString[i].split(" ");
			// Drop first coordinate for it is only one
			for (int j = 1; j < cords.length; j++) {
				points[i][j - 1] = Double.parseDouble(cords[j]);
			}
		}
		// The first point point can be used to check, if the Voronoi Was
		// Correct.
		//
		// if (nearNull(points[0]))
		// throw new Exception("Voronoi not correct");
		//

		//
		// The second point is the affine translation
		//
		double[] aff = points[1];

		//
		// The Matrix is build of the last n-2 points
		//
		double[][] mat = new double[points.length - 2][points[2].length];
		for (int i = 2; i < points.length; i++) {
			mat[i - 2] = subtract(points[i], aff);
		}

		//
		// Lastly the boundary points of this construction are the unit base for
		// the
		// goal dimension
		//
		double[][] boundary;
		boundary = new double[points.length - 2][points[2].length];

		for (int i = 0; i < boundary.length; i++) {
			boundary[i] = points[i + 2];
		}

		return new KnownFundamental<PS>(boundary, transpose(mat), aff);
	}

	private double[][] transpose(double[][] m1) {
		if (m1.length == 0)
			throw new IllegalArgumentException(
					"The matrix cannot not be empty at this point.");
		double[][] m2 = new double[m1[0].length][m1.length];
		for (int i = 0; i < m1.length; i++) {
			for (int j = 0; j < m1[0].length; j++) {
				m2[j][i] = m1[i][j];
			}
		}
		return m2;
	}

	private double[] subtract(double[] p1, double[] p2) {
		if (p1.length != p2.length)
			throw new IllegalArgumentException(
					"Subtract two vectors of different size.");
		double[] p3 = new double[p1.length];
		for (int i = 0; i < p2.length; i++) {
			p3[i] = p1[i] - p2[i];
		}
		return p3;
	}

}
