package pointGroups.geometry.symmetries;


import java.util.ArrayList;
import java.util.Collection;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import pointGroups.geometry.Quaternion;

/**
 * 
 * @author Oliver
 * 
 * Creates the symmetry groups (3D) with generators.
 *
 */
public class GeneratorCreator3D
{
  /**
   * @return Groupelems of iscosahedral symmetry group
   */
  public static Collection<Quaternion> IcosahedralSymmetryGroup() {
    List<Quaternion> gen = new ArrayList<Quaternion>();
    gen.add(Quaternion.qI);
    gen.add(Quaternion.qw);
    return generateSymmetryGroup3D(gen);
  }

  /**
   * @return Groupelems of octahedral symmetry group
   */
  public static Collection<Quaternion> OctahedralSymmetryGroup() {
    List<Quaternion> gen = new ArrayList<Quaternion>();
    gen.add(Quaternion.qO);
    gen.add(Quaternion.qI);
    return generateSymmetryGroup3D(gen);
  }

  /**
   * @return Groupelems of tetrahedral symmetry group
   */
  public static Collection<Quaternion> TetrahedralSymmetryGroup() {
    List<Quaternion> gen = new ArrayList<Quaternion>();
    gen.add(Quaternion.qT);
    gen.add(Quaternion.qw);
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

}