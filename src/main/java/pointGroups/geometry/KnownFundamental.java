package pointGroups.geometry;

import java.util.logging.Logger;


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

  public static int count = 0;
  public final int id = count++;
  // TODO is it a good idea to name the logger this way?
  final protected Logger logger = Logger.getLogger(this.getClass().getName() +
      "(id: " + id + ")");

  /**
   * Stores the inverted projection matrix from the PS dimension to the PF
   * dimension, extracted from polymake. The matrix is stored collum for collum.
   * In extra, the affine point for the matrix is given.
   */
  private final double[][] revertMatrix;
  public final double[] affine;

  /**
   * V-Polytope of the fundamental domain.
   */
  private final double[][] points;

  public KnownFundamental(double[][] points, double[][] revertMatrix,
      double[] affine) {
    this.points = points.clone();
    this.revertMatrix = revertMatrix;
    this.affine = affine;
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
    return this.points.clone();
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
    double[] p = applyMatrix(revertMatrix, point);
    p = addAffine(p);
    p = normalize(p);
    if (Math.abs(length(p) - 1) > 0.01) {
      logger.info("A reverted point is not on the unit sphere.");
    }
    return p;
  }

  private double length(double[] point) {
    double l = 0;
    for (int i = 0; i < points.length; i++) {
      l += point[i] * point[i];
    }
    return Math.sqrt(l);
  }

  /*
   * TODO Doch in Polymake???
   */
  private double[] normalize(double[] points) {
    double length = length(points);
    for (int i = 0; i < points.length; i++) {
      points[i] = points[i] / length;
    }
    return points;
  }

  private double[] applyMatrix(double[][] mat, double[] point) {
    // TODO Eigene Exception schreiben
    double[] erg = new double[mat.length];
    if (mat[0].length != point.length)
      throw new RuntimeException("Dimension does not match.");
    for (int i = 0; i < mat.length; i++) {
      erg[i] = 0;
      for (int j = 0; j < mat[i].length; j++) {
        erg[i] += mat[i][j] * point[j];
      }
    }
    return erg;
  }

  private double[] addAffine(double[] p) {
    for (int i = 0; i < p.length; i++) {
      p[i] += this.affine[i];
    }
    return p;
  }
}
