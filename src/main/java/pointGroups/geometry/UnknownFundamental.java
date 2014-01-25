package pointGroups.geometry;

public class UnknownFundamental implements
		Fundamental {

	/**
	 * If this class is instanciated the fundamental domain should not be known.
	 */
	@Override
	public boolean isKnown() {
		return false;
	}

	/**
	 * As the interface says, an unknown domain has no boundary points.
	 */
	@Override
	public double[][] getVertices() {
		return null;
	}

	/**
	 * Lifts the point to the unit sphere, upper half.
	 * It is assumed, that the points lies inside the unit sphere of dimension PF.
	 */
	@Override
	public double[] revertPoint(double[] point) {
		double[] p = point;
		double[] lift = new double[p.length + 1];
		for (int i = 0; i < p.length; i++) {
			lift[i] = p[i];
			lift[p.length] += p[i] * p[i];
		}
		lift[p.length] += 1;
		lift[p.length] = Math.sqrt(lift[p.length]);
		// Nur zur sicherheit
		p = normalize(p);

		return p;
	}

	private double[] normalize(double[] points) {
		double sum = 0;
		for (int i = 0; i < points.length; i++) {
			sum += points[i] * points[i];
		}
		sum = Math.sqrt(sum);
		for (int i = 0; i < points.length; i++) {
			points[i] = points[i] / sum;
		}
		return points;
	}

}
