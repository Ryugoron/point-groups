package pointGroups.geometry.symmetries;

import java.io.Serializable;

import pointGroups.geometry.Quaternion;


public class Rotation4D
  implements Serializable
{
  /**
   * 
   */
  private static final long serialVersionUID = -6485384305152700916L;
  public final Quaternion left;
  public final Quaternion right;
  public final Quaternion leftCon;

  public Rotation4D(Quaternion left, Quaternion right) {
    this.left = left;
    this.right = right;
    leftCon = left.conjugate();
  }

  public Rotation4D nextRotation(Rotation4D r2) {
    Rotation4D r1 = this;
    return new Rotation4D(r1.left.mult(r2.left), r2.right.mult(r1.right));
  }

  public Quaternion rotate(Quaternion q) {
    return leftCon.mult(q).mult(right);
  }

  public double distance(Rotation4D r2) {
    Rotation4D r1 = this;
    return Quaternion.distance(r1.left, r2.left) +
        Quaternion.distance(r1.right, r2.right);
  }

  @Override
  public int hashCode() {
    /*
     * See On Quaternions and Ocontions John H. Conway, Derek A. Smith page 42
     * [-l,-r] = [l,r] and [-l,r]=[l,-r]=-[l,r]
     */
    double firstValUnequalZero = left.re;
    if (firstValUnequalZero == 0) firstValUnequalZero = left.i;
    else if (firstValUnequalZero == 0) firstValUnequalZero = left.j;
    else if (firstValUnequalZero == 0) firstValUnequalZero = left.k;
    else if (firstValUnequalZero == 0) firstValUnequalZero = right.re;
    else if (firstValUnequalZero == 0) firstValUnequalZero = right.i;
    else if (firstValUnequalZero == 0) firstValUnequalZero = right.j;
    else if (firstValUnequalZero == 0) firstValUnequalZero = right.k;
    
    if (firstValUnequalZero <= 0) { 
      return left.minus().hashCode() ^ right.minus().hashCode(); 
      }
    return left.hashCode() ^ right.hashCode();
  }

  @Override
  public boolean equals(Object o) {
    if (this == o) return true;
    if (!(o.getClass() == getClass())) return false;
    final Rotation4D r = (Rotation4D) o;
    /*
     * See On Quaternions and Ocontions John H. Conway, Derek A. Smith page 42
     * [-l,-r] = [l,r] and [-l,r]=[l,-r]=-[l,r]
     */
    return (r.left.equals(this.left) && r.right.equals(this.right)) ||
        (r.left.equals(this.left.minus()) && r.right.equals(this.right.minus()));

  }
}
