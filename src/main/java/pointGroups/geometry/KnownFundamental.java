package pointGroups.geometry;

import java.util.LinkedList;
import java.util.List;
import java.util.logging.Logger;

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

  private static final double EPSILON = 0.01;

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
   * A list with hyperplanes. The first one is a point on the plane and the
   * second component is a normal Vector which points torwards the inside.
   */
  private List<double[][]> hPolytope;

  /**
   * V-Polytope of the fundamental domain.
   */
  private final double[][] finPoints;

  public KnownFundamental(double[][] points, double[][] revertMatrix,
      double[] affine) {

    // Punkte kopieren
    finPoints = PointUtil.copyPoints(points);

    this.revertMatrix = revertMatrix;
    this.affine = affine;
    if (points[0].length == 2) this.hPolytope = getLines();
    else this.hPolytope = getPlanes();
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

  /**
   * This method assumes, that a fundamental region is a simplex.
   */
  @Override
  public boolean inFundamental(double[] point) {
    for (double[][] hp : this.hPolytope) {
      if (PointUtil.stScalarProd(PointUtil.subtract(point, hp[0]), hp[1]) < -1 *
          EPSILON) return false;
    }
    return true;
  }

  /**
   * A hyperplane in 2D is represented by a normal vector and one point on the
   * plane.
   * 
   * @return List of all hyperplanes
   */
  private List<double[][]> getLines() {
    LinkedList<double[][]> erg = new LinkedList<double[][]>();
    double[][] points = this.getVertices();
    for (int i = 0; i < points.length - 1; i++) {
      for (int j = i + 1; j < points.length; j++) {
        double[][] a = new double[2][2];
        a[0] = points[i].clone();
        double[] n = new double[2];
        n[0] = points[j][0] - points[i][0];
        n[1] = -1 * 1 / (points[j][1] - points[i][1]);

        // Test if the normalvector has to be inverted
        int tV;
        if (i != 0 && j != 0) tV = 0;
        else if (i != 1 && j != 1) tV = 1;
        else tV = 2;

        double[] tVec = points[tV];
        tVec[0] = tVec[0] - points[i][0];
        tVec[1] = tVec[1] - points[i][1];
        if (PointUtil.stScalarProd(tVec, n) < 0) {
          n = PointUtil.mult(-1, n);
        }
        a[1] = n;
        erg.add(a);
      }
    }

    return erg;
  }

  /**
   * As the 2D case.
   * 
   * @return List of all hyperplanes
   */
  private List<double[][]> getPlanes() {
    List<double[][]> erg = new LinkedList<double[][]>();
    double[][] points = this.getVertices();
    for (int i = 0; i < points.length - 2; i++) {
      for (int j = i + 1; j < points.length - 1; j++) {
        for (int w = j + 1; w < points.length; w++) {
          double[][] toAdd = new double[2][3];
          double[] x = points[i];
          double[] y = points[j];
          double[] z = points[w];
          y = PointUtil.subtract(y, x);
          z = PointUtil.subtract(z, x);
          double[] n = PointUtil.vecProd(y, z);

          // Test if it is the right side
          int tV;
          if (i != 0 && j != 0 && w != 0) tV = 0;
          else if (i != 1 && j != 1 && w != 1) tV = 1;
          else if (i != 2 && j != 2 && w != 2) tV = 2;
          else tV = 3;

          if (PointUtil.stScalarProd(points[tV], n) < 0)
            n = PointUtil.mult(-1, n);
          toAdd[0] = x;
          toAdd[1] = n;
          erg.add(toAdd);
        }
      }
    }
    return erg;
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
    double[] p = PointUtil.applyMatrix(revertMatrix, point);
    p = PointUtil.add(p, this.affine);
    p = PointUtil.normalize(p);
    if (Math.abs(PointUtil.length(p) - 1) > 0.01) {
      logger.info("A reverted point is not on the unit sphere.");
    }
    return p;
  }
}
