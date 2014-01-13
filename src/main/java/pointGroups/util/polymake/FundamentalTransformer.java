package pointGroups.util.polymake;

import java.util.Collection;
import java.util.logging.Logger;

import pointGroups.geometry.Fundamental;
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
public class FundamentalTransformer<PS extends Point, PF extends Point> extends
		AbstractTransformer<Fundamental<PS, PF>> {

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
		// The more points the larger the scirpt
		StringBuilder sb = new StringBuilder(1000);
		sb.append("my points = new Matrix<Rational>([");
		boolean first = true;
		for (Point point : this.points) {
			if (!first) {
				sb.append(",");
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
		sb.append("my $vert = $v-VERTICES_IN_FACETS->[0]");
		sb.append("for ($count = 0; $count < $vert->size; $count++) {");
		sb.append("print $v->VERTICES->[$vert[$count]]");
		this.script = sb.toString();
		return this.script;
	}

	@Override
	protected Fundamental<PS, PF> transformResultString() {
		StringBuilder regex = new StringBuilder();
		// minimum one 3D point followed by...
		regex.append("([-+]?[0-9]*\\.?[0-9]+([eE][-+]?[0-9]+)? [-+]?[0-9]*\\.?[0-9]+([eE][-+]?[0-9]+)? [-+]?[0-9]*\\.?[0-9]+([eE][-+]?[0-9]+)?\\n)+|");
		// or one 4D point
		regex.append("([-+]?[0-9]*\\.?[0-9]+([eE][-+]?[0-9]+)? [-+]?[0-9]*\\.?[0-9]+([eE][-+]?[0-9]+)? [-+]?[0-9]*\\.?[0-9]+([eE][-+]?[0-9]+)? [-+]?[0-9]*\\.?[0-9]+([eE][-+]?[0-9]+)?\\n)+)");
		
		if (!resultString.matches(regex.toString())){
			throw new PolymakeOutputException("String set by setResultString() does not match defined format for fundamental domain.");
		}
		
		// Extracting the points from the result
		String[] pointString = this.resultString.split("\n");
		double[][] points = new double[pointString.length][pointString[0].length()];
		for (int i = 0; i < pointString.length; i++){
			String[] cords = pointString[i].split(" ");
			//Drop first coordinate for it is only one
			for (int j = 1; j < cords.length; j++){
				points[i][j] = Double.parseDouble(cords[j]); 
			}
		}
		
		//Establishing a transformation matrix:
		double transform
		return null;
	}

}
