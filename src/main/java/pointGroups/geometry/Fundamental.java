package pointGroups.geometry;

/**
 * Interface to the Fundamental Domain. It supports the translation of a point
 * in the Domain to the symmetry domain, because on event level we operate on
 * double[][] we only use these ones here.
 * 
 * @author max
 */
public interface Fundamental
{

  /**
   * This flag differenciates between known and unknown fundamental domain. If
   * the region is not known, it can always be a circle.
   * 
   * @return true -- if the fundamental domain is known.
   */
  public boolean isKnown();

  /**
   * Returns the Points on the boundary for the fundamental domain. This is a
   * representation as a V-Polytope. Should be emtpy, if the fundamental domain
   * is not known.
   * 
   * @return Points on the boundary of the fundamental domain.
   */
  public double[][] getVertices();

  /**
   * Accepts a point of the fundamental domain representation and translates it
   * back to the symmetry.
   * 
   * @param Point in the fundemental domain.
   * @return the represented point in the fundamental domain.
   */
  public double[] revertPoint(double[] point);

}
