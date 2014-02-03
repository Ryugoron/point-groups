package pointGroups.geometry.symmetries;

import pointGroups.geometry.Quaternion;

import java.util.ArrayList;
import java.util.List;


public class GeneratorCreator
{
  public static final double epsilon = 1E-10;
  //See On Quaternions and Ocontions John H. Conway, Derek A. Smith page 33
  public static final  double sigma = (Math.sqrt(5) - 1) / 2;
  public static final double tau = (Math.sqrt(5) + 1) / 2;
  public static final Quaternion qw = new Quaternion(-0.5, 0.5, 0.5, 0.5);

  public static final Quaternion qI = new Quaternion(0, 0.5, sigma * 0.5, tau * 0.5);
  public static final Quaternion qO = new Quaternion(0, 0, 1/Math.sqrt(2), 1/Math.sqrt(2));
  public static final Quaternion qT = new Quaternion(0, 1, 0, 0);

  
/**
 * 
 * @return Groupelems of iscosahedral symmetry group
 */
  public static List<Quaternion> IcosahedralSymmetryGroup(){
    List<Quaternion> gen = new ArrayList<Quaternion>();
    gen.add(qI);
    gen.add(qw);
    return generateSymmetryGroup(gen);
  }  
  
  /**
   * 
   * @return Groupelems of octahedral symmetry group
   */
    public static List<Quaternion> OctahedralSymmetryGroup(){
      List<Quaternion> gen = new ArrayList<Quaternion>();
      gen.add(qO);
      gen.add(qI);
      return generateSymmetryGroup(gen);
    } 
    
    
    
    /**
     * 
     * @return Groupelems of tetrahedral symmetry group
     */
      public static List<Quaternion> TetrahedralSymmetryGroup(){
        List<Quaternion> gen = new ArrayList<Quaternion>();
        gen.add(qT);
        gen.add(qw);
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
  
  public boolean equalGroups(List<Quaternion> g1 , List<Quaternion> g2){
    int notFoundInG2 = 0;
    int notFoundInG1 = 0;

    for(Quaternion q : g1){
      if(!containsApprox(g2, q)){
        notFoundInG2++;
      }    
    }
    for(Quaternion q : g2){
      if(!containsApprox(g1, q)){
        notFoundInG1++;
      }    
    }
    System.out.println("not in G2: #"+notFoundInG2);
    System.out.println("not in G1: #"+notFoundInG1);
    return notFoundInG1 == 0 && notFoundInG2 == 0;

  }
  
 
}
