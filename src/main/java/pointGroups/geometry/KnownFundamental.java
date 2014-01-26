package pointGroups.geometry;

import java.util.LinkedList;
import java.util.List;
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
    finPoints = new double[points.length][];

    for (int i = 0; i < points.length; i++) {
      finPoints[i] = new double[points[i].length];
      for (int j = 0; j < points[i].length; j++) {
        finPoints[i][j] = points[i][j];
      }
    }

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
    double[][] res = new double[finPoints.length][];
    for (int i = 0; i < res.length; i++) {
      res[i] = new double[finPoints[i].length];
      for (int j = 0; j < finPoints[i].length; j++) {
        res[i][j] = finPoints[i][j];
      }
    }
    return res;
  }

  /**
   * This method assumes, that a fundamental region is a simplex.
   */
  @Override
  public boolean inFundamental(double[] point) {
    for (double[][] hp : this.hPolytope) {
      if (this.stScalarProd(this.subtract(point, hp[0]), hp[1]) < -1 * EPSILON)
        return false;
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
        if (stScalarProd(tVec, n) < 0) {
          n[0] *= -1;
          n[1] *= -1;
        }
        a[1] = n;
        erg.add(a);
      }
    }

    return erg;
  }

  /**
   * Vectorprodukt in 3 Dimensions
   * 
   * @param a first Vector
   * @param b second Vector
   * @return a x b
   */
  private double[] vProd(double[] a, double[] b) {
    if (a.length != 3 || b.length != 3)
      throw new IllegalArgumentException("Got one vector, not of dimension 3");
    double[] erg = new double[a.length];

    erg[0] = a[1] * b[2] - a[2] * b[1];
    erg[1] = a[2] * b[0] - a[0] * b[2];
    erg[2] = a[0] * b[1] - a[1] * b[0];

    return erg;
  }

  private double stScalarProd(double[] a, double[] b) {
    if (a.length != b.length)
      throw new IllegalArgumentException(
          "Multiplied two Vectors of different length.");

    double sum = 0;
    for (int i = 0; i < a.length; i++) {
      sum += a[i] * b[i];
    }
    return sum;
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
          y = subtract(y, x);
          z = subtract(z, x);
          double[] n = vProd(y, z);

          // Test if it is the right side
          int tV;
          if (i != 0 && j != 0 && w != 0) tV = 0;
          else if (i != 1 && j != 1 && w != 1) tV = 1;
          else if (i != 2 && j != 2 && w != 2) tV = 2;
          else tV = 3;

          if (stScalarProd(points[tV], n) < 0) {
            n[0] *= -1;
            n[1] *= -1;
            n[2] *= -1;
          }
          toAdd[0] = x;
          toAdd[1] = n;
          erg.add(toAdd);
        }
      }
    }
    return erg;
  }

  private double[] subtract(double[] a, double[] b) {
    if (a.length != b.length)
      throw new IllegalArgumentException("Subtracted two not matching Vectors.");
    double[] erg = new double[a.length];
    for (int i = 0; i < a.length; i++) {
      erg[i] = a[i] - b[i];
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
    double[] p = applyMatrix(revertMatrix, point);
    p = addAffine(p);
    p = normalize(p);
    if (Math.abs(length(p) - 1) > 0.01) {
      logger.info("A reverted point is not on the unit sphere.");
    }
    return p;
  }

  private double length(double[] point) {
    return Math.sqrt(stScalarProd(point, point));
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
