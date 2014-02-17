package pointGroups.geometry.symmetries;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashSet;
import java.util.Set;

import pointGroups.geometry.Quaternion;


public abstract class SymmetryGenerated4D
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
  
  
  protected Collection<Rotation4D> generator = new ArrayList<>();
  public Set<Rotation4D> groupElems;
    
  
  public SymmetryGenerated4D(boolean readFile, boolean writeFile, String filename){
    if(readFile){
      try {
        groupElems = readSymmetryGroup(new File(filename));
      }
      catch (ClassNotFoundException | IOException e) {
        // TODO Auto-generated catch block
        e.printStackTrace();
      }      
    }
    else{
      generateSymmetryGroup4D();
      try {
        writeSymmetryGroup(filename);
      }
      catch (IOException e) {
        // TODO Auto-generated catch block
        e.printStackTrace();
      }
    }
  }
  
  private int calculate(Collection<Rotation4D> group,Collection<Rotation4D> arg1, Collection<Rotation4D> arg2, Collection<Rotation4D> dist){
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

  
  
  
  public Collection <Rotation4D> generateSymmetryGroup4D() {
    int newElems = 0;
    if(groupElems != null){
      return groupElems;
    }
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
  private void writeSymmetryGroup(final String filename)
    throws IOException {
    // create a new file with an ObjectOutputStream
    FileOutputStream out = new FileOutputStream(filename);
    ObjectOutputStream oout = new ObjectOutputStream(out);

    oout.writeObject(groupElems);

    oout.close();
  }
  
  private static Set<Rotation4D> readSymmetryGroup(final File filename)
      throws FileNotFoundException, IOException, ClassNotFoundException {
      ObjectInputStream ois =
          new ObjectInputStream(new FileInputStream(filename));
      Object o = ois.readObject();
      // TODO: ugly
      Set<Rotation4D> group = ((Set<Rotation4D>) o);
      ois.close();
      return group;
    }
  
  
}
