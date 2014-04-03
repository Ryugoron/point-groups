package pointGroups.geometry.symmetries;

import java.io.Serializable;

import pointGroups.geometry.Quaternion;


public class Reflection4D
  implements Serializable
{

  /**
   * 
   */
  private static final long serialVersionUID = -7276947384471524840L;
  public final Quaternion left;
  public final Quaternion right;
  private final Quaternion leftCon;

  public Reflection4D(Quaternion left, Quaternion right) {
    this.left = left;
    this.right = right;
    leftCon = left.conjugate();
  }

  public Quaternion reflect(Quaternion x) {
    Quaternion xCon = x.conjugate();
    return leftCon.mult(xCon).mult(right);
  }

}
