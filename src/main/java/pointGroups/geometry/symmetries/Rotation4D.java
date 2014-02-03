package pointGroups.geometry.symmetries;

import pointGroups.geometry.Quaternion;

public class Rotation4D
{
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
}
