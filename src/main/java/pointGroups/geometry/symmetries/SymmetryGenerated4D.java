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
import java.util.Set;

import pointGroups.geometry.Quaternion;


public class SymmetryGenerated4D
{
  // See On Quaternions and Ocontions John H. Conway, Derek A. Smith page 33
  public static final double sigma = (Math.sqrt(5) - 1) / 2;
  public static final double tau = (Math.sqrt(5) + 1) / 2;
  public static final Quaternion qw = new Quaternion(-0.5, 0.5, 0.5, 0.5);

  public static final Quaternion qI = new Quaternion(0, 0.5, sigma * 0.5,
      tau * 0.5);
  public static final Quaternion qO = new Quaternion(0, 0, 1 / Math.sqrt(2),
      1 / Math.sqrt(2));
  public static final Quaternion qT = new Quaternion(0, 1, 0, 0); 
  
  public static final String dir = ""; //TODO: dir?!
  
  public final static Collection<Rotation4D> generatorIxI60(){
    Collection<Rotation4D> generator = new ArrayList<>();
    generator.add(new Rotation4D(GeneratorCreator.qw, GeneratorCreator.qw));     
    generator.add(new Rotation4D(GeneratorCreator.qI, GeneratorCreator.qI));
    return generator;
  }
  
  public final static Collection<Rotation4D> generatorOxO24(){
    Collection<Rotation4D> generator = new ArrayList<>();
    generator.add(new Rotation4D(GeneratorCreator.qw, GeneratorCreator.qw));          
    generator.add(new Rotation4D(GeneratorCreator.qO,GeneratorCreator.qO));     
    return generator;
  }
  
  public final static Collection<Rotation4D> generatorTxT3(){
    Collection<Rotation4D> generator = new ArrayList<>();
    generator.add(new Rotation4D(Quaternion.I, Quaternion.ONE));
    generator.add(new Rotation4D(Quaternion.ONE, Quaternion.I));
    generator.add(new Rotation4D(Quaternion.J, Quaternion.ONE));
    generator.add(new Rotation4D(Quaternion.ONE, Quaternion.J));
    generator.add(new Rotation4D(SymmetryGenerated4D.qw, SymmetryGenerated4D.qw));
    return generator;
  }
  
  public final static Collection<Rotation4D> generatorTxT12(){
    Collection<Rotation4D> generator = new ArrayList<>();
    generator.add(new Rotation4D(Quaternion.I, Quaternion.I));
    generator.add(new Rotation4D(SymmetryGenerated4D.qw, SymmetryGenerated4D.qw));
    return generator;
  }
  
  
  /**
   * Add here new symmetry groups
   */
  protected static void createSymgroups(){
    System.out.println("TxT Start: " + Calendar.getInstance().getTime());
    createSymgroup(TxTSymmetry.getSym().getName(), TxTSymmetry.TxTGenerator());
    
    System.out.println("OxO Start: " + Calendar.getInstance().getTime());
    createSymgroup(OxOSymmetry.getSym().getName(), OxOSymmetry.generator());
    System.out.println("OxO2 Start: " + Calendar.getInstance().getTime());
    createSymgroup(OxO2Symmetry.getSym().getName(), OxO2Symmetry.generator());
    System.out.println("OxO6 Start: " + Calendar.getInstance().getTime());
    createSymgroup(OxO6Symmetry.getSym().getName(), OxO6Symmetry.generator());
    System.out.println("TxT12 Start: " + Calendar.getInstance().getTime());
    createSymgroup("TxT12", generatorTxT12());
    System.out.println("TxT3 Start: " + Calendar.getInstance().getTime());
    createSymgroup("TxT3", generatorTxT3());
    System.out.println("OxO24 Start: " + Calendar.getInstance().getTime());
    createSymgroup("OxO24", generatorOxO24());
    System.out.println("IxI60 Start: " + Calendar.getInstance().getTime());
    createSymgroup("IxI60", generatorIxI60());
    System.out.println("IxI Start: " + Calendar.getInstance().getTime());
    createSymgroup(IxISymmetry.getSym().getName(), IxISymmetry.generator());

    System.out.println("finish: " + Calendar.getInstance().getTime());

  }
  
  
  
  private static void createSymgroup(String groupname, Collection<Rotation4D> generator){
    Collection<Rotation4D> group = generateSymmetryGroup4D(generator);
    try {
      writeSymmetryGroup(groupname,group);
    }
    catch (IOException e) {
      // TODO Auto-generated catch block
      e.printStackTrace();
    }    
  }
  
  
  
  
  
  /**
   * Find new group elem x s.t. x not in group, arg1, arg2 and it exists y in arg1, z in arg2 s.t. x = y*z. Add x in dist.
   * @param group
   * @param arg1
   * @param arg2
   * @param dist
   * @return #new group elems
   */
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

  
  
  
  private static Collection <Rotation4D> generateSymmetryGroup4D(Collection<Rotation4D> generator) {
    int newElems = 0;
    // All known group elems
    Set <Rotation4D> groupElems = new HashSet<>();
    // All new group elems. Test them for new elems.
    Set<Rotation4D> currentToTest = new HashSet<>(generator);
    // All new found group elems.
    Set<Rotation4D> newGroupelem = new HashSet<>();
    
    // Test for new elems since there are no more new elems....
    do {
      // x in Group, y in CurrentToText-> x*y new Elem?
      newElems = calculate(groupElems, groupElems, currentToTest, newGroupelem);
      // x in CurrentToText, y in Group-> x*y new Elem?
      newElems += calculate(groupElems, currentToTest, groupElems, newGroupelem);
      // x in CurrentToText, y in CurrentToText-> x*y new Elem?
      newElems += calculate(groupElems, currentToTest, currentToTest, newGroupelem);
      
      // Add all tested elems to the group
      for (Rotation4D g : currentToTest) {
        groupElems.add(g);
      }      
      currentToTest.clear();
      
      // Now test all new found group elems
      for (Rotation4D g : newGroupelem) {
        currentToTest.add(g);
      }
      newGroupelem.clear();      
    }
   while (newElems != 0);
   return groupElems;
  } 
  
  
  
  private static void writeSymmetryGroup(String groupname, Collection<Rotation4D> groupElems)
    throws IOException {
    // create a new file with an ObjectOutputStream
    FileOutputStream out = new FileOutputStream(dir+groupname+".sym");
    ObjectOutputStream oout = new ObjectOutputStream(out);

    oout.writeObject(groupElems);

    oout.close();
  }
  
  public static Set<Rotation4D> readSymmetryGroup(final String groupname)
      throws FileNotFoundException, IOException, ClassNotFoundException {
      File f = new File(dir+groupname+".sym");
      ObjectInputStream ois =
          new ObjectInputStream(new FileInputStream(f));
      Object o = ois.readObject();
      // TODO: ugly
      Set<Rotation4D> group = ((Set<Rotation4D>) o);
      ois.close();
      return group;
    }
  
  public static void main(String[] args){
    createSymgroups();
  }
  
}
