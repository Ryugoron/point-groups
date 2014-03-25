package pointGroups.geometry.symmetries;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Calendar;
import java.util.Collection;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

import pointGroups.geometry.Point4D;
import pointGroups.geometry.Quaternion;
import pointGroups.geometry.Symmetry;

public enum Symmetry4D   implements Symmetry<Point4D> {
  
  IxI("IxI","",""),//
  IxI60("1/60 IxI","",""),//
  TxT("TxT","",""),//
  TxT3("1/3 TxT","",""),//
  TxT12("1/12 TxT","",""),//
  OxO("OxO","",""),//
  OxO2("1/2 OxO","",""),//
  OxO6("1/6 OxO","",""),//
  OxO24("1/24 OxO","","");//
  
  
  static final Map<Symmetry4D, Collection<Rotation4D>> groups;
  static final Map<Symmetry4D, Collection<Symmetry4D>> subgroups;
  static final Map<Symmetry4D, Point4D> normalPoints;
  
//See On Quaternions and Ocontions John H. Conway, Derek A. Smith page 33
 public static final double sigma = (Math.sqrt(5) - 1) / 2;
 public static final double tau = (Math.sqrt(5) + 1) / 2;
 public static final Quaternion qw = new Quaternion(-0.5, 0.5, 0.5, 0.5);

 public static final Quaternion qI = new Quaternion(0, 0.5, sigma * 0.5,
     tau * 0.5);
 public static final Quaternion qO = new Quaternion(0, 0, 1 / Math.sqrt(2),
     1 / Math.sqrt(2));
 public static final Quaternion qT = new Quaternion(0, 1, 0, 0); 
 
 public static final String dir = ""; //TODO: dir?!
  
 public final static Collection<Rotation4D> generatorIxI(){
   Collection<Rotation4D> generator = new ArrayList<>();
   generator.add(new Rotation4D(GeneratorCreator3D.qI, Quaternion.ONE));
   generator.add(new Rotation4D(GeneratorCreator3D.qw, Quaternion.ONE));
   generator.add(new Rotation4D(Quaternion.ONE,GeneratorCreator3D.qI));
   generator.add(new Rotation4D(Quaternion.ONE,GeneratorCreator3D.qw));     
   return generator;
 }
 
 
 public final static Collection<Rotation4D> generatorIxI60(){
   Collection<Rotation4D> generator = new ArrayList<>();
   generator.add(new Rotation4D(GeneratorCreator3D.qw, GeneratorCreator3D.qw));     
   generator.add(new Rotation4D(GeneratorCreator3D.qI, GeneratorCreator3D.qI));
   return generator;
 }
 
 public final static Collection<Rotation4D> generatorOxO(){
   Collection<Rotation4D> generator = new ArrayList<>();
   generator.add(new Rotation4D(GeneratorCreator3D.qO, Quaternion.ONE));
   generator.add(new Rotation4D(GeneratorCreator3D.qw, Quaternion.ONE));
   generator.add(new Rotation4D(Quaternion.ONE,GeneratorCreator3D.qO));
   generator.add(new Rotation4D(Quaternion.ONE,GeneratorCreator3D.qw));     
   return generator;
 }
 
 public final static Collection<Rotation4D> generatorOxO2(){
   Collection<Rotation4D> generator = new ArrayList<>();
   generator.add(new Rotation4D(Quaternion.I, Quaternion.ONE));
   generator.add(new Rotation4D(GeneratorCreator3D.qw, Quaternion.ONE));
   generator.add(new Rotation4D(Quaternion.ONE,Quaternion.I));
   generator.add(new Rotation4D(Quaternion.ONE,GeneratorCreator3D.qw));
   generator.add(new Rotation4D(GeneratorCreator3D.qO,GeneratorCreator3D.qO));     
   return generator;
 }
 
 public final static Collection<Rotation4D> generatorOxO6(){
   Collection<Rotation4D> generator = new ArrayList<>();
   generator.add(new Rotation4D(Quaternion.I, Quaternion.ONE));
   generator.add(new Rotation4D(Quaternion.J, Quaternion.ONE));
   generator.add(new Rotation4D(Quaternion.ONE,Quaternion.I));
   generator.add(new Rotation4D(Quaternion.ONE,Quaternion.J));
   generator.add(new Rotation4D(GeneratorCreator3D.qw,GeneratorCreator3D.qw));     
   generator.add(new Rotation4D(GeneratorCreator3D.qO,GeneratorCreator3D.qO));     
   return generator;
 }
 
 public final static Collection<Rotation4D> generatorOxO24(){
   Collection<Rotation4D> generator = new ArrayList<>();
   generator.add(new Rotation4D(GeneratorCreator3D.qw, GeneratorCreator3D.qw));          
   generator.add(new Rotation4D(GeneratorCreator3D.qO,GeneratorCreator3D.qO));     
   return generator;
 }
 
 public final static Collection<Rotation4D> generatorTxT(){
   Collection<Rotation4D> generator = new ArrayList<>();
   generator.add(new Rotation4D(Quaternion.I, Quaternion.ONE));
   generator.add(new Rotation4D(qw, Quaternion.ONE));
   generator.add(new Rotation4D(Quaternion.ONE, Quaternion.I));
   generator.add(new Rotation4D(Quaternion.ONE, qw));
   return generator;
 }
 
 public final static Collection<Rotation4D> generatorTxT3(){
   Collection<Rotation4D> generator = new ArrayList<>();
   generator.add(new Rotation4D(Quaternion.I, Quaternion.ONE));
   generator.add(new Rotation4D(Quaternion.ONE, Quaternion.I));
   generator.add(new Rotation4D(Quaternion.J, Quaternion.ONE));
   generator.add(new Rotation4D(Quaternion.ONE, Quaternion.J));
   generator.add(new Rotation4D(qw, qw));
   return generator;
 }
 
 public final static Collection<Rotation4D> generatorTxT12(){
   Collection<Rotation4D> generator = new ArrayList<>();
   generator.add(new Rotation4D(Quaternion.I, Quaternion.I));
   generator.add(new Rotation4D(qw, qw));
   return generator;
 }
 
 /**
  * Add new symmetry groups here
  */
 private static void createSymgroups(){
   System.out.println("TxT Start: " + Calendar.getInstance().getTime());
   createSymgroup("TxT", generatorTxT());
   System.out.println("TxT12 Start: " + Calendar.getInstance().getTime());
   createSymgroup("TxT12", generatorTxT12());
   System.out.println("TxT3 Start: " + Calendar.getInstance().getTime());
   createSymgroup("TxT3", generatorTxT3());
   
   
   System.out.println("OxO Start: " + Calendar.getInstance().getTime());
   createSymgroup("OxO", generatorOxO());
   System.out.println("OxO2 Start: " + Calendar.getInstance().getTime());
   createSymgroup("OxO2", generatorOxO2());
   System.out.println("OxO6 Start: " + Calendar.getInstance().getTime());
   createSymgroup("OxO6", generatorOxO6());   
   System.out.println("OxO24 Start: " + Calendar.getInstance().getTime());
   createSymgroup("OxO24", generatorOxO24());
   
   
   System.out.println("IxI60 Start: " + Calendar.getInstance().getTime());
   createSymgroup("IxI60", generatorIxI60());
   System.out.println("IxI Start: " + Calendar.getInstance().getTime());
   createSymgroup("IxI", generatorIxI());


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
 
 public static Collection<Rotation4D> readSymmetryGroup(final String groupname)
     throws FileNotFoundException, IOException, ClassNotFoundException {
     File f = new File(dir+groupname+".sym");
     ObjectInputStream ois =
         new ObjectInputStream(new FileInputStream(f));
     Object o = ois.readObject();
     // TODO: ugly
     Collection<Rotation4D> group = ((Collection<Rotation4D>) o);
     ois.close();
     return group;
   }
 
 
  static {
    groups = new HashMap<>();
    subgroups = new HashMap<>();
    normalPoints = new HashMap<>();
    
    // load group elems
    try {
      groups.put(IxI,readSymmetryGroup(IxI.schoenflies()));
      groups.put(IxI60,readSymmetryGroup(IxI60.schoenflies()));
      
      groups.put(TxT,readSymmetryGroup(TxT.schoenflies()));
      groups.put(TxT3,readSymmetryGroup(TxT3.schoenflies()));
      groups.put(TxT12,readSymmetryGroup(TxT12.schoenflies()));
      
      groups.put(OxO,readSymmetryGroup(OxO.schoenflies()));
      groups.put(OxO2,readSymmetryGroup(OxO2.schoenflies()));
      groups.put(OxO6,readSymmetryGroup(OxO6.schoenflies()));
      groups.put(OxO24,readSymmetryGroup(OxO24.schoenflies()));
    }
    catch (ClassNotFoundException | IOException e) {
      // TODO Auto-generated catch block
      e.printStackTrace();
    }
    //TODO: IxI OxO6
    // TODO: What about groups with no subgroup?
    subgroups.put(IxI60, Arrays.asList(TxT12));  
    
    subgroups.put(TxT, Arrays.asList(TxT12,TxT3));
    subgroups.put(TxT3, Arrays.asList(TxT12));
    
    subgroups.put(OxO, Arrays.asList(OxO2,OxO24,TxT12,TxT3,TxT)); //Why not OxO6???
    subgroups.put(OxO2, Arrays.asList(TxT12,TxT3,TxT,OxO24)); //Why not OxO6???
    subgroups.put(OxO24, Arrays.asList(TxT12)); //Why not OxO6???
    
    //TODO: normalPoints?!
    normalPoints.put(IxI, new Point4D(0.8, 0.6, 0.3, 0.0));
    normalPoints.put(IxI60, new Point4D(0.8, 0.6, 0.3, 0.0));

    normalPoints.put(TxT, new Point4D(0.8, 0.6, 0.3, 0.0));
    normalPoints.put(TxT3, new Point4D(0.8, 0.6, 0.3, 0.0));
    normalPoints.put(TxT12, new Point4D(0.8, 0.6, 0.3, 0.0));
    
    normalPoints.put(OxO, new Point4D(0.8, 0.6, 0.3, 0.0));
    normalPoints.put(OxO2, new Point4D(0.8, 0.6, 0.3, 0.0));
    normalPoints.put(OxO6, new Point4D(0.8, 0.6, 0.3, 0.0));
    normalPoints.put(OxO24, new Point4D(0.8, 0.6, 0.3, 0.0)); 
  }
  
  private final String coxeter;
  private final String schoenflies;
  private final String orbifold;

  private Symmetry4D(final String schoenflies, final String coxeter,
      final String orbifold) {
    this.schoenflies = schoenflies;
    this.coxeter = coxeter;
    this.orbifold = orbifold;
  }
  
  
  @Override
  public String coxeter() {
    return this.coxeter;
  }

  @Override
  public String schoenflies() {
    return this.schoenflies;
  }

  @Override
  public String orbifold() {
    return this.orbifold;
  }

  @Override
  public Collection<? extends Symmetry<Point4D>> subgroups() {
    return subgroups.get(this);
  }

  @Override
  public int order() {
    return groups.get(this).size();
  }

  @Override
  public Collection<Point4D> images(Point4D p) {
    Collection<Point4D> rotatedPointcollection = new ArrayList<>();
    for(Rotation4D r : groups.get(this)){
      rotatedPointcollection.add(r.rotate(p).asPoint4D());
    }
    return rotatedPointcollection;
  }

  @Override
  public Class<Point4D> getType() {
    return Point4D.class;
  }

  @Override
  public Point4D getNormalPoint() {
    return normalPoints.get(this);
  }
  
  public static Collection<Symmetry4D> getSymmetries() {
    return groups.keySet();
  }


//  public static void main(String[] args){
//    createSymgroups();
//  }

}
