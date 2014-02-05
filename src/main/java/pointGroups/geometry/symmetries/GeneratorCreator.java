package pointGroups.geometry.symmetries;

import pointGroups.geometry.Quaternion;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collection;
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
    return generateSymmetryGroup3D(gen);
  }  
  
  
  
  
  public static List<Rotation4D> TxTSymGroup(){
    List<Rotation4D> gen = new ArrayList<Rotation4D>();
    gen.add(new Rotation4D(Quaternion.I, Quaternion.ONE));
    gen.add(new Rotation4D(qw, Quaternion.ONE));
    gen.add(new Rotation4D(Quaternion.ONE,Quaternion.I));
    gen.add(new Rotation4D(Quaternion.ONE, qw));
    return generateSymmetryGroup4D(gen);


    
  }
 
  public static List<Rotation4D> IcosahedralXIcosahedralSymmetryGroup(){
     List<Rotation4D> gen = new ArrayList<Rotation4D>();
     gen.add(new Rotation4D(qI, Quaternion.ONE));
     gen.add(new Rotation4D(qw, Quaternion.ONE));
     
     gen.add(new Rotation4D(Quaternion.ONE,qI));
     gen.add(new Rotation4D(Quaternion.ONE,qw));
     return generateSymmetryGroup4D(gen);
   }  
  
  /**
   * 
   * @return Groupelems of octahedral symmetry group
   */
    public static List<Quaternion> OctahedralSymmetryGroup(){
      List<Quaternion> gen = new ArrayList<Quaternion>();
      gen.add(qO);
      gen.add(qI);
      return generateSymmetryGroup3D(gen);
    } 
    
    
    
    /**
     * 
     * @return Groupelems of tetrahedral symmetry group
     */
      public static List<Quaternion> TetrahedralSymmetryGroup(){
        List<Quaternion> gen = new ArrayList<Quaternion>();
        gen.add(qT);
        gen.add(qw);
        return generateSymmetryGroup3D(gen);
      }  
  

  /**
   * 
   * @param generators
   * @return Groupelem generate by generators
   */
  public static List<Quaternion> generateSymmetryGroup3D(List<Quaternion> generators) {
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
  
  
  public static List<Rotation4D> generateSymmetryGroup4D(List<Rotation4D> generators){
    int newElems = 0;
    List<Rotation4D> group = new ArrayList<>(generators);
    List<Rotation4D> newGroupelem = new ArrayList<>();
    Rotation4D z;
    int n = 0;
    do {
      newElems = 0;
      for (Rotation4D x : group) {
        for (Rotation4D y : group) {
          z = x.nextRotation(y);
          if (!containsApprox(group, z) && !containsApprox(newGroupelem, z)) {
            newGroupelem.add(z);
            newElems++;
            n++;
            if(n%100 == 0){
              System.out.println("n = "+n);
            }
          }
        }
      }
      for (Rotation4D g : newGroupelem) {
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
  
  private static boolean containsApprox(List<Rotation4D> list, Rotation4D x) {
    for (Rotation4D y : list) {
      if (equalApprox(x, y)) { return true; }
    }
    return false;
  }

  private static boolean equalApprox(Rotation4D a, Rotation4D b) {
    double distance = a.distance(b);
    return distance < epsilon;
  } 
  
  /**
   * count elems of g1 that are not in g2
   * @param g1
   * @param g2
   * @return
   */
  public static int notInG2(List<Quaternion> g1 , List<Quaternion> g2){
    int notFoundInG2 = 0;
    for(Quaternion q : g1){
      if(!containsApprox(g2, q)){
        notFoundInG2++;
      }    
    }
    return notFoundInG2;
  }
  
  public static boolean equalGroups(List<Quaternion> g1 , List<Quaternion> g2){
    int notFoundInG2 = notInG2(g1, g2);
    int notFoundInG1 = notInG2(g2,g1);

  
    return notFoundInG1 == 0 && notFoundInG2 == 0;

  }
  
  
  public static void writeSymmetryGroup(String filename, Collection<Rotation4D> group) throws IOException{
    // create a new file with an ObjectOutputStream
    FileOutputStream out = new FileOutputStream(filename);
    ObjectOutputStream oout = new ObjectOutputStream(out);
    
    oout.writeObject(group);
    
    oout.close();
  }
  
  
  public static Collection<Rotation4D> readSymmetryGroup(String filename) throws FileNotFoundException, IOException, ClassNotFoundException{
    ObjectInputStream ois = new ObjectInputStream(new FileInputStream(filename));
    Object o = ois.readObject();
    //TODO: ugly
    Collection<Rotation4D> group =  ((Collection<Rotation4D>)o);
    ois.close();
    return group;
  }
  
  public static void main(String[] args){
    System.out.println("Start: "+Calendar.getInstance().getTime());
//    Collection<Rotation4D> tXt = TxTSymGroup();
//    System.out.println("#Elem TxT: "+tXt.size());
//    try {
//      writeSymmetryGroup("TxT.sym", tXt);
//    }
//    catch (IOException e) {
//      // TODO Auto-generated catch block
//      e.printStackTrace();
//    }
    
    try {
      Collection<Rotation4D> t = readSymmetryGroup("tXt.sym");
      System.out.println("#Elemente = "+t.size());
    }
    catch (ClassNotFoundException | IOException e) {
      // TODO Auto-generated catch block
      e.printStackTrace();
    }
    System.out.println("Fertig: "+Calendar.getInstance().getTime());


  }
  
 
}
