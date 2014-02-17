package pointGroups.geometry.symmetries;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collection;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import pointGroups.geometry.Quaternion;


public class GeneratorCreator
{
  public static final double epsilon = 1E-10;
  // See On Quaternions and Ocontions John H. Conway, Derek A. Smith page 33
  public static final double sigma = (Math.sqrt(5) - 1) / 2;
  public static final double tau = (Math.sqrt(5) + 1) / 2;
  public static final Quaternion qw = new Quaternion(-0.5, 0.5, 0.5, 0.5);

  public static final Quaternion qI = new Quaternion(0, 0.5, sigma * 0.5,
      tau * 0.5);
  public static final Quaternion qO = new Quaternion(0, 0, 1 / Math.sqrt(2),
      1 / Math.sqrt(2));
  public static final Quaternion qT = new Quaternion(0, 1, 0, 0);

  /**
   * @return Groupelems of iscosahedral symmetry group
   */
  public static Collection<Quaternion> IcosahedralSymmetryGroup() {
    List<Quaternion> gen = new ArrayList<Quaternion>();
    gen.add(qI);
    gen.add(qw);
    return generateSymmetryGroup3D(gen);
  }

  public static Collection<Rotation4D> TxTSymGroup() {
    List<Rotation4D> gen = new ArrayList<Rotation4D>();
    gen.add(new Rotation4D(Quaternion.I, Quaternion.ONE));
    gen.add(new Rotation4D(qw, Quaternion.ONE));
    gen.add(new Rotation4D(Quaternion.ONE, Quaternion.I));
    gen.add(new Rotation4D(Quaternion.ONE, qw));
    return generateSymmetryGroup4D(gen);

  }

  public static Collection<Rotation4D> IcosahedralXIcosahedralSymmetryGroup() {
    List<Rotation4D> gen = new ArrayList<Rotation4D>();
    gen.add(new Rotation4D(qI, Quaternion.ONE));
    gen.add(new Rotation4D(qw, Quaternion.ONE));

    gen.add(new Rotation4D(Quaternion.ONE, qI));
    gen.add(new Rotation4D(Quaternion.ONE, qw));
    return generateSymmetryGroup4D(gen);
  }

  /**
   * @return Groupelems of octahedral symmetry group
   */
  public static Collection<Quaternion> OctahedralSymmetryGroup() {
    List<Quaternion> gen = new ArrayList<Quaternion>();
    gen.add(qO);
    gen.add(qI);
    return generateSymmetryGroup3D(gen);
  }

  /**
   * @return Groupelems of tetrahedral symmetry group
   */
  public static Collection<Quaternion> TetrahedralSymmetryGroup() {
    List<Quaternion> gen = new ArrayList<Quaternion>();
    gen.add(qT);
    gen.add(qw);
    return generateSymmetryGroup3D(gen);
  }

  /**
   * @param generators
   * @return Groupelem generate by generators
   */
  public static Collection<Quaternion> generateSymmetryGroup3D(
      final List<Quaternion> generators) {
    int newElems = 0;
    Set <Quaternion> group = new HashSet<Quaternion>(generators);
    Set<Quaternion> newGroupelem = new HashSet<>();
    Quaternion z;
    do {
      newElems = 0;
      for (Quaternion x : group) {
        for (Quaternion y : group) {
          z = x.mult(y);
          // if (!containsApprox(group, z) && !containsApprox(newGroupelem, z)) {
          if(!group.contains(z) && !newGroupelem.contains(z)){
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

  public static Collection <Rotation4D> generateSymmetryGroup4D(
      final List<Rotation4D> generators) {
    int newElems = 0;
    // All known group elems
    Set <Rotation4D> group = new HashSet<>();
    // All new group elems. Test them for new elems.
    Set<Rotation4D> currentToTest = new HashSet<>(generators);
    // All new found group elems.
    Set<Rotation4D> newGroupelem = new HashSet<>();
    
    // Test for new elems since there are no more new elems....
    do {
      // x in Group, y in CurrentToText-> x*y new Elem?
      newElems = calculate(group, group, currentToTest, newGroupelem);
      // x in CurrentToText, y in Group-> x*y new Elem?
      newElems += calculate(group, currentToTest, group, newGroupelem);
      // x in CurrentToText, y in CurrentToText-> x*y new Elem?
      newElems += calculate(group, currentToTest, currentToTest, newGroupelem);
      
      // Add all tested elems to the group
      for (Rotation4D g : currentToTest) {
        group.add(g);
      }      
      currentToTest.clear();
      
      // Now test all new found group elems
      for (Rotation4D g : newGroupelem) {
        currentToTest.add(g);
      }
      newGroupelem.clear();      
    }
   while (newElems != 0);
   return group;
  }  
  
  
  private static int calculate(Collection<Rotation4D> group,Collection<Rotation4D> arg1, Collection<Rotation4D> arg2, Collection<Rotation4D> dist){
    int newElems = 0;
    Rotation4D z; 
    for (Rotation4D x : arg1) {
      for (Rotation4D y : arg2) {
        z = x.nextRotation(y);
        if(!group.contains(z) && !arg1.contains(z) && !arg2.contains(z)){
          dist.add(z);
          newElems++;
          }
        }
      }    
    return newElems;
  }

  public static void writeSymmetryGroup(final String filename,
      final Collection<Rotation4D> group)
    throws IOException {
    // create a new file with an ObjectOutputStream
    FileOutputStream out = new FileOutputStream(filename);
    ObjectOutputStream oout = new ObjectOutputStream(out);

    oout.writeObject(group);

    oout.close();
  }

  public static Collection<Rotation4D> readSymmetryGroup(final File filename)
    throws FileNotFoundException, IOException, ClassNotFoundException {
    ObjectInputStream ois =
        new ObjectInputStream(new FileInputStream(filename));
    Object o = ois.readObject();
    // TODO: ugly
    Collection<Rotation4D> group = ((Collection<Rotation4D>) o);
    ois.close();
    return group;
  }

  public static void main(final String[] args) {
    System.out.println("Start: " + Calendar.getInstance().getTime());
    Collection<Rotation4D> tXt = TxTSymGroup();
    System.out.println("#Elem TxT: "+tXt.size());
    System.out.println("Fertig mit TxT " + Calendar.getInstance().getTime());

    Collection<Rotation4D> iXi = IcosahedralXIcosahedralSymmetryGroup();
     System.out.println("#Elem IxI: "+iXi.size());
     try {
     writeSymmetryGroup("TxT.sym", iXi);
     }
     catch (IOException e) {
     // TODO Auto-generated catch block
     e.printStackTrace();
     }

//    try {
//      Collection<Rotation4D> t = readSymmetryGroup(new File("tXt.sym"));
//      System.out.println("#Elemente = " + t.size());
//    }
//    catch (ClassNotFoundException | IOException e) {
//      // TODO Auto-generated catch block
//      e.printStackTrace();
//    }
    System.out.println("Fertig: " + Calendar.getInstance().getTime());

  }

}
