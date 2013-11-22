package geometry;

/**
 * A quaternion is a number from the set a+bi+cj+dk with a,b,c,d real numbers.
 * The imaginary units i, j, k follow haliton's rules i^2 = j^2 = k^2 = ijk =
 * -1. Quaternions can be used to represent rotations in R^3 and R^4 (see
 * {@link UnitQuaternion}).
 * 
 * @author Alex
 */
public class Quaternion
implements Point
{
  public final double i, j, k, re;

  /**
   * Creates a {@link Quaternion} q with given coordinates.
   * 
   * @param re real coordinate of q
   * @param i i-coordinate of q
   * @param j j-coordinate of q
   * @param k k-coordinate of q
   */
  public Quaternion(double re, double i, double j, double k) {
    this.re = re;
    this.i = i;
    this.j = j;
    this.k = k;
  }

  protected Quaternion(double re, double i, double j, double k,
      boolean assumeUnit) {
    double norm = 1d;
    if (assumeUnit) norm = 1d / Math.sqrt(i * i + j * j + k * k + re * re);
    this.re = norm * re;
    this.i = norm * i;
    this.j = norm * j;
    this.k = norm * k;
  }

  public Quaternion plus(Quaternion b) {
    return new Quaternion(re + b.re, i + b.i, j + b.j, k + b.k);
  }

  public Quaternion mult(Quaternion b) {
    Quaternion a = this;
    double y0, y1, y2, y3;

    y0 = a.re * b.re - a.i * b.i - a.j * b.j - a.k * b.k;
    y1 = a.re * b.i + a.i * b.re + a.j * b.k - a.k * b.j;
    y2 = a.re * b.j - a.i * b.k + a.j * b.re + a.k * b.i;
    y3 = a.re * b.k + a.i * b.j - a.j * b.i + a.k * b.re;
    return new Quaternion(y0, y1, y2, y3);
  }

  public Quaternion conjugate() {
    return new Quaternion(re, -i, -j, -k);
  }

  public Quaternion inverse() {
    double mron = 1d / this.norm();
    return new Quaternion(re * mron, -i * mron, -j * mron, -k * mron);
  }

  public double norm() {
    return Math.sqrt(i * i + j * j + k * k + re * re);
  }

  public double normSquared() {
    return i * i + j * j + k * k + re * re;
  }

  public UnitQuaternion asUnit() {
    double y0, y1, y2, y3;
    double mron = 1d / this.norm();
    y0 = mron * re;
    y1 = mron * i;
    y2 = mron * j;
    y3 = mron * k;
    return new UnitQuaternion(y0, y1, y2, y3);
  }

  public Point3D asPoint3D() {
    return new Point3D(i, j, k);
  }

  public Point4D asPoint4D() {
    return new Point4D(re, i, j, k);
  }

  @Override
  public String toString() {
    return re + " + " + i + "i " + j + " j" + k + " k";
  }

  public static Quaternion fromDouble(double d) {
    return new Quaternion(d, 0, 0, 0);
  }

  /**
   * 
   * @param a
   * @param b
   * @return euclidean distance between a und b
   */
  public static double distance(Quaternion a, Quaternion b){
    double re = (a.re - b.re)* (a.re - b.re); 
    double i = Math.pow((a.i - b.i),2);
    double j = Math.pow((a.j - b.j),2);
    double k = Math.pow((a.k - b.k),2);
    return Math.sqrt(re+i+j+k);
  }

  /**
   * @return components of the quaternion represented in an array of doubles
   */
  @Override
  public double[] getComponents() {
    double[] components = {re, i, j, k};

    return components;
  }
}
