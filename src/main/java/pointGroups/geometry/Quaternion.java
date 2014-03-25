package pointGroups.geometry;

import java.io.Serializable;


/**
 * A quaternion is a number from the set a+bi+cj+dk with a,b,c,d real numbers.
 * The imaginary units i, j, k follow haliton's rules i^2 = j^2 = k^2 = ijk =
 * -1. Quaternions can be used to represent rotations in R^3 and R^4 (see
 * {@link UnitQuaternion}).
 * 
 * @author Alex
 */
public class Quaternion
  implements Point, Serializable
{
  /**
   * 
   */
  private static final long serialVersionUID = -2584314271811803851L;


  public final static Quaternion ONE = new Quaternion(1,0,0,0);
  public final static Quaternion I = new Quaternion(0,1,0,0);
  public final static Quaternion J = new Quaternion(0,0,1,0);

  private final static int digits = 5;
  private final static double r = Math.pow(10, digits);
  
  public final double i, j, k, re;

  /**
   * Creates a {@link Quaternion} q with given coordinates.
   * 
   * @param re real coordinate of q
   * @param i i-coordinate of q
   * @param j j-coordinate of q
   * @param k k-coordinate of q
   */
  public Quaternion(final double re, final double i, final double j,
      final double k) {
    this.re = re;
    this.i = i;
    this.j = j;
    this.k = k;
  }

  protected Quaternion(final double re, final double i, final double j,
      final double k, final boolean assumeUnit) {
    double norm = 1d;
    if (!assumeUnit) norm = 1d / Math.sqrt(i * i + j * j + k * k + re * re);
    this.re = norm * re;
    this.i = norm * i;
    this.j = norm * j;
    this.k = norm * k;
  }

  public Quaternion plus(final Quaternion b) {
    return new Quaternion(this.re + b.re, this.i + b.i, this.j + b.j, this.k +
        b.k);
  }

  public Quaternion mult(final Quaternion b) {
    Quaternion a = this;
    double y0, y1, y2, y3;

    y0 = a.re * b.re - a.i * b.i - a.j * b.j - a.k * b.k;
    y1 = a.re * b.i + a.i * b.re + a.j * b.k - a.k * b.j;
    y2 = a.re * b.j - a.i * b.k + a.j * b.re + a.k * b.i;
    y3 = a.re * b.k + a.i * b.j - a.j * b.i + a.k * b.re;
    return new Quaternion(y0, y1, y2, y3);
  }

  public Quaternion conjugate() {
    return new Quaternion(this.re, -this.i, -this.j, -this.k);
  }

  public Quaternion inverse() {
    double mron = 1d / norm();
    return new Quaternion(this.re * mron, -this.i * mron, -this.j * mron,
        -this.k * mron);
  }

  public double norm() {
    return Math.sqrt(this.i * this.i + this.j * this.j + this.k * this.k +
        this.re * this.re);
  }

  public double normSquared() {
    return this.i * this.i + this.j * this.j + this.k * this.k + this.re *
        this.re;
  }

  public UnitQuaternion asUnit() {
    double y0, y1, y2, y3;
    double mron = 1d / norm();
    y0 = mron * this.re;
    y1 = mron * this.i;
    y2 = mron * this.j;
    y3 = mron * this.k;
    return new UnitQuaternion(y0, y1, y2, y3);
  }

  public Point3D asPoint3D() {
    return new Point3D(this.i, this.j, this.k);
  }

  public Point4D asPoint4D() {
    return new Point4D(this.re, this.i, this.j, this.k);
  }

  @Override
  public String toString() {
    return this.re + " + " + this.i + "i " + this.j + " j" + this.k + " k";
  }

  public static Quaternion fromDouble(final double d) {
    return new Quaternion(d, 0, 0, 0);
  }

  /**
   * @param a
   * @param b
   * @return euclidean distance between a und b
   */
  public static double distance(final Quaternion a, final Quaternion b) {
    double re = (a.re - b.re) * (a.re - b.re);
    double i = Math.pow((a.i - b.i), 2);
    double j = Math.pow((a.j - b.j), 2);
    double k = Math.pow((a.k - b.k), 2);
    return Math.sqrt(re + i + j + k);
  }

  /**
   * @return components of the quaternion represented in an array of doubles
   */
  @Override
  public double[] getComponents() {
    double[] components = { this.re, this.i, this.j, this.k };

    return components;
  }


  /**
   * source code from Jreality
   */
  @Override
  public int hashCode() {

    double x = Math.round(r * re) / r;
    double y = Math.round(r * i) / r;
    double z = Math.round(r * j) / r;
    double w = Math.round(r * k) / r;

    final int PRIME = 31;
    int result = 1;
    long temp;
    temp = Double.doubleToLongBits(w);
    result = PRIME * result + (int) (temp ^ (temp >>> 32));
    temp = Double.doubleToLongBits(x);
    result = PRIME * result + (int) (temp ^ (temp >>> 32));
    temp = Double.doubleToLongBits(y);
    result = PRIME * result + (int) (temp ^ (temp >>> 32));
    temp = Double.doubleToLongBits(z);
    result = PRIME * result + (int) (temp ^ (temp >>> 32));
    return result;
  }


  
  /**
   * source code from Jreality
   */
  @Override
  public boolean equals(final Object obj) {
    if (this == obj) return true;
    if (obj == null) return false;
    if (getClass() != obj.getClass()) return false;
    final Quaternion other = (Quaternion) obj;
    double x1 = Math.round(r * re) / r;
    double y1 = Math.round(r * i) / r;
    double z1 = Math.round(r * j) / r;
    double w1 = Math.round(r * k) / r;
    double x2 = Math.round(r * other.re) / r;
    double y2 = Math.round(r * other.i) / r;
    double z2 = Math.round(r * other.j) / r;
    double w2 = Math.round(r * other.k) / r;

    if (x1 == x2 && y1 == y2 && z1 == z2 && w1 == w2) return true;
    return false;
  }

}
