package pointGroups.geometry;

import java.util.List;

import pointGroups.util.point.PointUtil;


public class UnknownFundamental
  implements Fundamental
{

  private static final double EPSILON = 0.001;

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
  public Edge<Integer,Integer>[] getEdges(){
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
    for (int i = 0; i < p.length; i++) {
      lift[i] = p[i];
      lift[p.length] += p[i] * p[i];
    }
    lift[p.length] += 1;
    lift[p.length] = Math.sqrt(lift[p.length]);
    // Nur zur sicherheit
    p = PointUtil.normalize(p);

    return lift;
  }

  @Override
  public boolean inFundamental(double[] point) {
    return PointUtil.length(point) <= 1 + EPSILON;
  }

}
