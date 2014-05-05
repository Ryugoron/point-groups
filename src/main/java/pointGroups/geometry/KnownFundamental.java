package pointGroups.geometry;

import java.util.ArrayList;
import java.util.List;
import java.util.logging.Logger;

import pointGroups.util.LoggerFactory;
import pointGroups.util.point.PointUtil;


/**
 * Representation for the fundamental domain of a {@link Symmetry}. The
 * fundamental domain is the quotient of the {@link Symmetry} to all unit points
 * {@link http://en.wikipedia.org/wiki/Fundamental_domain} The fundamental
 * domain is projected from dimension of the symmetry to one less dimension.
 * 
 * @author Max
 */
public class KnownFundamental
  implements Fundamental
{

  private static final double EPSILON = 0.1;

  final protected Logger logger = LoggerFactory.get(KnownFundamental.class);

  /**
   * Stores the inverted projection matrix from the PS dimension to the PF
   * dimension, extracted from polymake. The matrix is stored collum for collum.
   * In extra, the affine point for the matrix is given.
   */
  private final double[][] revertMatrix;
  public final double[] affine;

  /**
   * A list with hyperplanes. The first one is a point on the plane and the
   * second component is a normal Vector which points torwards the inside.
   */
  private final double[][] hPolytope;

  /**
   * V-Polytope of the fundamental domain.
   */
  private final double[][] finPoints;
  private final List<Edge> edges;

  public KnownFundamental(double[][] points, double[][] revertMatrix,
      double[][] hyperplanes, double[] affine, List<Edge> edges) {

    // Punkte kopieren
    finPoints = PointUtil.copyPoints(points);

    this.revertMatrix = revertMatrix;
    this.affine = affine;
    this.hPolytope = hyperplanes;
    this.edges = (new ArrayList<Edge>());
    this.edges.addAll(edges);
  }

  /**
   * If this class is constructed, the fundamental domain should be known.
   */
  @Override
  public boolean isKnown() {
    return true;
  }

  /**
   * Returns the V-Polytope representation of the projected fundamental domain.
   * 
   * @return All vertices on the convex hull of the V-Polytope
   */
  @Override
  public double[][] getVertices() {
    // Resgegebene Punkte noch kopieren
    return PointUtil.copyPoints(finPoints);
  }

  @Override
  public Edge[] getEdges() {
    // Copying for safety
    Edge[] ed = this.edges.toArray(new Edge[this.edges.size()]);
    return ed;
  }
  
  /**
   * Checks for every hyperplane after back transformation
   */
  @Override
  public boolean inFundamental(double[] point) {
    double[] p = this.affinePolytope(point);
    for (double[] hp : this.hPolytope) {
      double val = hp[0];
      for (int i = 1; i < hp.length; i++) {
        val += p[i - 1] * hp[i];
      }
      if (val <= EPSILON) return false;
    }
    return true;
  }

  /**
   * Given a point in the fundamental domain, this method will lift it back to
   * the unit ball in PS dimension.
   * 
   * @param point in the projected fundamental domain
   * @return the point reprojected to the unit ball
   */
  @Override
  public double[] revertPoint(double[] point) {
    double[] p = affinePolytope(point);
    p = PointUtil.normalize(p);
    if (Math.abs(PointUtil.length(p) - 1) > 0.01) {
      logger.info("A reverted point is not on the unit sphere.");
    }
    return p;
  }

  /**
   * Returns the coordinates in the lifted polytope.
   * 
   * @param point - in fundamental
   * @return point in liftet fundamental
   */
  public double[] affinePolytope(double[] point) {
    double[] p = PointUtil.addZero(point);
    p = PointUtil.applyMatrix(revertMatrix, p);
    p = PointUtil.add(p, this.affine);
    return p;
  }
}
