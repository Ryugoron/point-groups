package pointGroups.util.point;

import pointGroups.geometry.Point;
import pointGroups.geometry.Point3D;
import pointGroups.geometry.Point4D;


public class PointUtil
{
  /**
   * Makes a deep copy of a point.
   * 
   * @param point the point
   * @return a copy of the point
   */
  public static double[] copyPoint(double[] point) {
    double[] erg = new double[point.length];
    for (int j = 0; j < point.length; j++) {
      erg[j] = point[j];
    }
    return erg;
  }

  /**
   * Makes a deep copy of a list of points
   * 
   * @param points to be copied
   * @return the copie
   */
  public static double[][] copyPoints(double[][] points) {
    double[][] erg = new double[points.length][];
    for (int i = 0; i < points.length; i++) {
      erg[i] = copyPoint(points[i]);
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
  public static double[] vecProd(double[] a, double[] b) {
    if (a.length != 3 || b.length != 3)
      throw new IllegalArgumentException("Got one vector, not of dimension 3");
    double[] erg = new double[a.length];

    erg[0] = a[1] * b[2] - a[2] * b[1];
    erg[1] = a[2] * b[0] - a[0] * b[2];
    erg[2] = a[0] * b[1] - a[1] * b[0];

    return erg;
  }

  /**
   * Standard Skalarprodukt to UnitMatrix.
   * 
   * @param a first vector
   * @param b second vector
   * @return <a,b>
   */
  public static double stScalarProd(double[] a, double[] b) {
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
   * Subtracts two points
   * 
   * @param a
   * @param b
   * @return a-b
   */
  public static double[] subtract(double[] a, double[] b) {
    if (a.length != b.length)
      throw new IllegalArgumentException("Subtracted two not matching Vectors.");
    double[] erg = new double[a.length];
    for (int i = 0; i < a.length; i++) {
      erg[i] = a[i] - b[i];
    }
    return erg;
  }

  /**
   * Adds two points
   * 
   * @param a
   * @param b
   * @return a+b
   */
  public static double[] add(double[] a, double[] b) {
    if (a.length != b.length)
      throw new IllegalArgumentException("Subtracted two not matching Vectors.");
    double[] erg = new double[a.length];
    for (int i = 0; i < a.length; i++) {
      erg[i] = a[i] + b[i];
    }
    return erg;
  }

  public static double[] mult(double a, double[] b) {
    double[] erg = new double[b.length];
    for (int i = 0; i < b.length; i++) {
      erg[i] = a * b[i];
    }
    return erg;
  }

  public static double[] div(double a, double[] b) {
    if (a == 0) throw new IllegalArgumentException("Divison by zero");
    double[] erg = new double[b.length];
    for (int i = 0; i < b.length; i++) {
      erg[i] = b[i] / a;
    }
    return erg;
  }

  public static double length(double[] point) {
    return Math.sqrt(stScalarProd(point, point));
  }

  /**
   * Normalizes a vector
   * 
   * @param points
   * @return points / || points ||
   */
  public static double[] normalize(double[] points) {
    double length = length(points);
    for (int i = 0; i < points.length; i++) {
      points[i] = points[i] / length;
    }
    return points;
  }

  /**
   * Applies a matrix to a vector
   * 
   * @param mat
   * @param point
   * @return
   */
  public static double[] applyMatrix(double[][] mat, double[] point) {
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

  public static double[] unit(int i, int dim) {
    double[] p = new double[dim];
    for (int j = 0; j < dim; j++) {
      p[j] = i == (j + 1) ? 1 : 0;
    }
    return p;
  }

  public static double[][] transpose(double[][] m1) {
    if (m1.length == 0)
      throw new IllegalArgumentException(
          "The matrix cannot not be empty at this point.");
    double[][] m2 = new double[m1[0].length][m1.length];
    for (int i = 0; i < m1.length; i++) {
      for (int j = 0; j < m1[0].length; j++) {
        m2[j][i] = m1[i][j];
      }
    }
    return m2;
  }

  public static double[] addZero(double[] p) {
    double[] p1 = new double[p.length + 1];
    p1[0] = 0;
    for (int i = 0; i < p.length; i++) {
      p1[i + 1] = p[i];
    }
    return p1;
  }

  public static double[] rmFst(double[] p) {
    double[] p1 = new double[p.length - 1];
    for (int i = 1; i < p.length; i++) {
      p1[i - 1] = p[i];
    }
    return p1;
  }

  public static String showPoint(double[] p) {
    String erg = "(" + p[0];
    for (int i = 1; i < p.length; i++) {
      erg += "," + p[i];
    }
    erg += ")";
    return erg;
  }

  /*
   * public static Point[] gramSchmitt(Point[] base){ double[][] gram = new
   * double[base.length][]; Point[] res = new Point[base.length]; for(int i = 0;
   * i < base.length; i++){ double[] p = base[i].getComponents(); for(int j = 0;
   * j < i; j++){ p = add(p, mult(stScalarProd(p, gram[j]), gram[j])); } p =
   * normalize(p); gram[i] = p; res[i] = doubleToPoint(p); } return res; }
   */

  public static double[][] gramSchmitt(double[][] base) {
    double[][] gram = new double[base.length][];
    for (int i = 0; i < base.length; i++) {
      double[] p = base[i];
      for (int j = 0; j < i; j++) {
        p = subtract(p, mult(stScalarProd(p, gram[j]), gram[j]));
      }
      p = normalize(p);
      gram[i] = p;
    }
    return gram;
  }

  public static Point doubleToPoint(double[] p) {
    Point ret;
    if (p.length == 3) {
      ret = new Point3D(p);
    }
    else {
      ret = new Point4D(p[0], p[1], p[2], p[3]);
    }
    return ret;
  }
  
  /**
   * Copmutes the euclidean diameter of a given set of points.
   * 
   * @param points - Set of Points.
   * @return euclidean diamter
   */
  public static double diameter(double[][] points) {
    double dia = 0.0;
    
    for(double[] p1 : points) {
      for(double[] p2 : points) {
        dia = Math.max(dia, length(subtract(p1, p2)));
      }
    }
    
    return dia;
  }
}
