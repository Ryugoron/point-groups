package pointGroups.geometry;

/**
 * 
 * Representation for the fundamental domain of a {@link Symmetry}. The
 * fundamental domain is the quotient of the {@link Symmetry} to all unit points
 * {@link http://en.wikipedia.org/wiki/Fundamental_domain}
 * 
 * The fundamental domain is projected from dimension PS to dimension PF.
 * 
 * @author Max
 * @param <PS>
 *            The dimension of the symmetry group
 * @param <PF>
 *            The dimension of the fundamental domain
 * 
 */
public class Fundamental<PS extends Point, PF extends Point> {

	/**
	 * Stores the inverted projection matrix from the PS dimension to the PF
	 * dimension, extracted from polymake. The matrix is stored collum for
	 * collum.
	 */
	private double[][] revertMatrix;

	/**
	 * V-Polytope of the fundamental domain.
	 */
	private PF[] points;

	public Fundamental(PF[] points, double[][] revertMatrix) {
		this.points = points.clone();
	}

	/**
	 * Returns the V-Polytope representation of the projected fundamental
	 * domain.
	 * 
	 * @return All vertices on the convex hull of the V-Polytope
	 */
	public PF[] getVertices() {
		return this.points.clone();
	}

	/**
	 * 
	 * Given a point in the fundamental domain, this method will lift it back to
	 * the unit ball in PS dimension.
	 * 
	 * @param point
	 *            in the projected fundamental domain
	 * @return the point reprojected to the unit ball
	 */
	@SuppressWarnings("unchecked")
	public PS revertPoint(PF point) {
		double[] p = applyMatrix(revertMatrix, point.getComponents());
		p = normalize(p);
		//TODO Wie kann man das hier schön verpacken...
		switch(p.length){
			case 3:
				return (PS) new Point3D(p[0],p[1],p[2]);
			case 4:
				return (PS) new Point4D(p[0],p[1],p[2],p[3]);
		}
		return null;
	}

	/*
	 * TODO Doch in Polymake???
	 */
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

	private double[] applyMatrix(double[][] mat, double[] point){
		//TODO Eigene Exception schreiben
		double[] erg = new double[mat.length];
		if (mat[0].length != point.length) throw new RuntimeException("Dimension does not match.");
		for(int i = 0; i<mat.length; i++){
			erg[i] = 0;
			for(int j = 0; j<mat[i].length; i++){
				erg[i] += mat[i][j] * point[j];
			}
		}
		return erg;
	}
}
