package pointGroups.util.polymake;

import java.util.Collection;

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

	private String script = null;
	private Collection<PS> points;

	public FundamentalTransformer(Collection<PS> points) {
		this.points = points;
	}

	@Override
	public String toScript() {
		if (script != null)
			return script;
		// TODO Script bauen
		return null;
	}

	@Override
	protected Fundamental transformResultString() {
		// TODO Auto-generated method stub
		return null;
	}

}
