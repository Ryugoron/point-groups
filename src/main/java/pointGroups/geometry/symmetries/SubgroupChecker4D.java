package pointGroups.geometry.symmetries;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 
 * @author Oliver
 * Find subgroups in a group.
 * Idea:
 *  Compare two or more symmetry groups
 *  If a group B is complete in a group A than B is a subgroup of A
 *  Hopefully we can find all subgroups of a symmetry group. 
 *
 */
public class SubgroupChecker4D
{
  public static Map<Collection<Rotation4D>,String> symmetryGroups = new HashMap<>();
  
  public static List<Collection<Rotation4D>> findSubgroups(Collection<Rotation4D> group,Collection<Collection<Rotation4D>> possibleSubgroups){
    List<Collection<Rotation4D>> subgroups = new ArrayList<>();
    boolean allIn = true;
    for (Collection<Rotation4D> g : possibleSubgroups){
      allIn = true;
      for(Rotation4D r : g){
        if (!group.contains(r)){
          allIn = false;
          break;
        }
      }
      if(allIn){
        subgroups.add(g);
      }
    }
    return subgroups;
  }
  
  
  public static void main(String[] args){
    try {
      symmetryGroups.put(Symmetry4D.readSymmetryGroup(Symmetry4D.TxT), "TxT");
      //symmetryGroups.put(SymmetryGenerated4D.readSymmetryGroup("IxI"), "IxI");
      symmetryGroups.put(Symmetry4D.readSymmetryGroup(Symmetry4D.OxO), "OxO");
      symmetryGroups.put(Symmetry4D.readSymmetryGroup(Symmetry4D.OxO2), "OxO2");

      symmetryGroups.put(Symmetry4D.readSymmetryGroup(Symmetry4D.OxO6), "OxO6");
      symmetryGroups.put(Symmetry4D.readSymmetryGroup(Symmetry4D.TxT12), "TxT12");
      symmetryGroups.put(Symmetry4D.readSymmetryGroup(Symmetry4D.TxT3), "TxT3");
      symmetryGroups.put(Symmetry4D.readSymmetryGroup(Symmetry4D.OxO24), "OxO24");
      symmetryGroups.put(Symmetry4D.readSymmetryGroup(Symmetry4D.IxI60), "IxI60");

    }
    catch (FileNotFoundException e) {
      // TODO Auto-generated catch block
      e.printStackTrace();
    }
    catch (ClassNotFoundException e) {
      // TODO Auto-generated catch block
      e.printStackTrace();
    }
    catch (IOException e) {
      // TODO Auto-generated catch block
      e.printStackTrace();
    }
    
    Collection<Collection<Rotation4D>> groups =  symmetryGroups.keySet();
    for(Collection<Rotation4D> g : groups){
      List<Collection<Rotation4D>> subgroups = findSubgroups(g, groups);
      System.out.println("Subgroups of "+symmetryGroups.get(g)+":");
      for(Collection<Rotation4D> subgroup: subgroups){
        System.out.print(""+symmetryGroups.get(subgroup)+",");
      }
      System.out.println("-----------");
    }
    
  }
}
