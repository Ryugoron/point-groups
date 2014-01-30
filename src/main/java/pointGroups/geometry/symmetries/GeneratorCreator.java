package pointGroups.geometry.symmetries;

import pointGroups.geometry.Quaternion;

import java.util.ArrayList;
import java.util.List;


public class GeneratorCreator
{
  public static final double epsilon = 1E-10;
  public static final  double sigma = (Math.sqrt(5) - 1) / 2;
  public static final double tau = (Math.sqrt(5) + 1) / 2;
  public static final Quaternion qi = new Quaternion(0, 0.5, sigma * 0.5, tau * 0.5);
  public static final Quaternion qw = new Quaternion(-0.5, 0.5, 0.5, 0.5);
  
/**
 * 
 * @return Groupelems of iscosahedrak symmetry group
 */
  public static List<Quaternion> IcosahedralSymmetryGroup(){
    List<Quaternion> gen = new ArrayList<Quaternion>();
    gen.add(qw);
    gen.add(qi);
    return generateSymmetryGroup(gen);
  }  
  

  /**
   * 
   * @param generators
   * @return Groupelem generate by generators
   */
  public static List<Quaternion> generateSymmetryGroup(List<Quaternion> generators) {
    int newElems = 0;
    List<Quaternion> group = new ArrayList<>(generators);
    List<Quaternion> newGroupelem = new ArrayList<>();
    Quaternion z;
    do {
      newElems = 0;
      for (Quaternion x : group) {
        for (Quaternion y : group) {
          z = x.mult(y);
          if (!containsApprox(group, z) && !containsApprox(newGroupelem, z)) {
            newGroupelem.add(z);
            newElems++;
          }
        }
      }
      for (Quaternion g : newGroupelem) {
        group.add(g);
      }
      newGroupelem.clear();
    }
    while (newElems != 0);
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
  
 
}
