package pointGroups.geometry;

/**
 * 
 * Interface to the Fundamental Domain. It supports the translation of a point
 * in the Domain PF to the symmetry domain PS.
 * 
 * @author max
 * 
 * @param <PS>
 *            Points of the Dimension of the Symmetry
 * @param <PF>
 *            Points of the Dimension fo the Fundamental Domain
 */
public interface Fundamental<PS extends Point, PF extends Point> {

	/**
	 * This flag differenciates between known and unknown fundamental domain.
	 * 
	 * If the region is not known, it can always be a circle.
	 * 
	 * @return true -- if the fundamental domain is known.
	 */
	public boolean isKnown();

	/**
	 * Returns the Points on the boundary for the fundamental domain. This is a
	 * representation as a V-Polytope. Should be emtpy, if the fundamental
	 * domain is not known.
	 * 
	 * @return Points on the boundary of the fundamental domain.
	 */
	public PF[] getVertices();

	/**
	 * 
	 * Accepts a point of the fundamental domain representation and translates
	 * it back to the symmetry.
	 * 
	 * @param Point
	 *            in the fundemental domain.
	 * @return the represented point in the fundamental domain.
	 */
	public PS revertPoint(PF Point);

}
