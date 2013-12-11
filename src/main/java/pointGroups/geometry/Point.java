package pointGroups.geometry;

/**
 * Marker interface for point classes. This class only exists since we need some
 * general type for points regardless whether they live in R<sup>3</sup> or
 * R<sup>4</sup>.
 * 
 * @author Alex
 */
public interface Point
{
  /**
   * @return Ordered coordinates of the point.
   */
  public double[] getComponents();

}
