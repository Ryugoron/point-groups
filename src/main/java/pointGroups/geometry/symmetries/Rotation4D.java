package pointGroups.geometry.symmetries;

import java.io.Serializable;

import pointGroups.geometry.Quaternion;

public class Rotation4D implements Serializable
{
  /**
   * 
   */
  private static final long serialVersionUID = -6485384305152700916L;
  public final Quaternion left;
  public final Quaternion right;
  public final Quaternion rightInvers;
  
  public Rotation4D(Quaternion left, Quaternion right){
    this.left = left;
    this.right = right;
    rightInvers = right.inverse();
  }
  
  
  public Rotation4D nextRotation(Rotation4D r2){
    Rotation4D r1 = this;
    return new Rotation4D(r1.left.mult(r2.left), r1.right.mult(r2.right));
  }
  
  
  public Quaternion rotate(Quaternion q){
    return left.mult(q).mult(rightInvers); 
  }
  
  public double distance(Rotation4D r2){
    Rotation4D r1 = this;
    return Quaternion.distance(r1.left, r2.left) + Quaternion.distance(r1.right, r2.right);
  }
  
  @Override
  public int hashCode(){
    return left.hashCode()^right.hashCode();
  }
  
  
  @Override
  public boolean equals(Object o){
    if(this == o) return true;
    if(!(o.getClass() == getClass())) return false;
    final Rotation4D r = (Rotation4D) o;
    return r.left.equals(this.left) && r.right.equals(this.right);
    
  }  
}
