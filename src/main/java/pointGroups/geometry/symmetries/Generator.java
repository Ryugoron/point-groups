package pointGroups.geometry.symmetries;

import java.util.ArrayList;
import java.util.List;

import pointGroups.geometry.Quaternion;
import pointGroups.geometry.UnitQuaternion;

/**
 * 
 * Tuple of unitquaternions are a generator of 3D symmetry group
 * 4D generator is a list of some 3D generators
 * @author Oliver
 *
 */
public class Generator
{
  public static final double epsilon = 1E-10;

  public final Quaternion left;
  public final Quaternion right;
  private final Quaternion rightInvers; //TODO: nicht Unitqu., weil invers nicht?!
  
  private List<Quaternion> group = null;
  
  public Generator(Quaternion p, Quaternion q){
    this.left = p;
    this.right = q;
    rightInvers = right.conjugate();
  }
  
  /**
   * q -> l*q*(r^-1)
   * See: Homographies quaternions and rotations by Patrick Du Val (Oxford, 1964) p. 42
   * @param q an known group element of the symmetry group
   * @return a new group element
   */
  public Quaternion getNewElem(Quaternion q){
    Quaternion p = left.mult(q);
    return p.mult(rightInvers);
  }
  
  public Quaternion getNewElemn(int n){
    Quaternion l = left;
    Quaternion r = right;
    for(int i = 0; i < n; i++){
      l = l.mult(left);
      r = r.mult(right);
    }
    r = r.conjugate();
    return l.mult(r);
  }
  
  public Quaternion getNewElem(){
    return left.mult(rightInvers);
  }
  
  
  public List<Quaternion> getAllElems2(){
    group = new ArrayList<>();
    Quaternion z;
    for (int i = 0; i <300; i++){
      z = getNewElemn(i);
      if(!containsApprox(group,z)){
        group.add(z);
      }
    }
    return group;
  }
  
  public List<Quaternion> getAllElems(){
    if(group == null){
      group = new ArrayList<>();
      Quaternion z = getNewElem();
      group.add(z);
      int i = 1;
      System.out.println("start: "+z.toString());
      while(i < 300) {
        z = getNewElem(z);
        i++;
        if(!containsApprox(group, z)){
          System.out.println(i+" : "+z.toString());
          group.add(z);
        }
      }
    }
    return group;    
  }
  private static boolean containsApprox(List<Quaternion> list, Quaternion x) {
    for (Quaternion y : list) {
      if (equalApprox(x, y)) { return true; }
    }
    return false;
  }

  private static boolean equalApprox(Quaternion a, Quaternion b) {
    double distance = Quaternion.distance(a, b);
    return distance < epsilon;
  } 
  
  public static void main(String[] args){
    double sigma = (Math.sqrt(5) - 1) / 2;
    double tau = (Math.sqrt(5) + 1) / 2;// p.52
    UnitQuaternion qi = new UnitQuaternion(0, 0.5, sigma * 0.5, tau * 0.5);
    UnitQuaternion qw = new UnitQuaternion(-0.5, 0.5, 0.5, 0.5);
    System.out.println("Qw: "+qw.toString());
    System.out.println("qI: "+qi.toString());
    Generator icso = new Generator(qw, qi);
    List<Quaternion> symgroup = icso.getAllElems2();
    System.out.println("# "+symgroup.size()); 
  }
  
}
