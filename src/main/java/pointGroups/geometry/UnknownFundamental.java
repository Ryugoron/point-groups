package pointGroups.geometry;

import pointGroups.util.point.PointUtil;


public class UnknownFundamental
  implements Fundamental
{

  private static final double EPSILON = 1e-09;

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

  @Override
  public Edge[] getEdges() {
    return null;
  }

  /**
   * Lifts the point to the unit sphere, upper half. It is assumed, that the
   * points lies inside the unit sphere of dimension one less then the symmetry.
   */
  @Override
  public double[] revertPoint(double[] point) {
    double[] p = point;
    double[] lift = new double[p.length + 1];
    lift[0] = 0;
    for (int i = 1; i <= p.length; i++) {
      lift[i] = p[i];
      lift[0] += p[i] * p[i];
    }
    lift[0] += 1;
    lift[0] = Math.sqrt(lift[0]);
    // Nur zur sicherheit
    p = PointUtil.normalize(p);

    return lift;
  }

  @Override
  public boolean inFundamental(double[] point) {
    return PointUtil.length(point) <= 1 + EPSILON;
  }

  @Override
  public double[] toFundamental(double[] point) {
    return PointUtil.rmFst(point);
  }

}
