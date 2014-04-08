package pointGroups.geometry.symmetries;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.Map;


/**
 * @author Oliver Find subgroups in a group. Idea: Compare two or more symmetry
 *         groups If a group B is complete in a group A than B is a subgroup of
 *         A Hopefully we can find all subgroups of a symmetry group.
 */
public class SubgroupChecker4D
{
  public static Map<Collection<Symmetry4D>, String> symmetryGroups =
      new HashMap<>();

  public static List<Symmetry4D> findSubgroups(Symmetry4D group,
      Collection<Symmetry4D> possibleSubgroups) {
    List<Symmetry4D> subgroups = new ArrayList<>();
    boolean allIn = true;

    for (Symmetry4D g : possibleSubgroups) {
      allIn = true;
      for (Rotation4D r : Symmetry4D.groups.get(g)) {
        if (!Symmetry4D.groups.get(group).contains(r)) {
          allIn = false;
          break;
        }
      }
      if (allIn) {
        subgroups.add(g);
      }
    }
    return subgroups;
  }

  public static void main(String[] args) {
    Collection<Symmetry4D> symgroups = Symmetry4D.getSymmetries();

    for (Symmetry4D g : symgroups) {
      List<Symmetry4D> subgroups = findSubgroups(g, symgroups);
      System.out.println("Subgroups of " + g.schoenflies() + ":");
      for (Symmetry4D subgroup : subgroups) {
        System.out.print("" + subgroup.schoenflies() + ",");
      }
      System.out.println(" #:" + subgroups.size());
    }

    /**
     * Subgroups of +-[IxO]: +-[IxO],+-[IxT],+ 1/12 [TxT],+- 1/3 [TxT],+- 1/12
     * [TxTQuer],+- 1/12 [TxT],+ 1/12 [TxTQuer],+- 1/3 [TxTQuer],+-
     * [TxT],----------- Subgroups of +-[IxI]: +-[IxI],+ 1/60 [IxI],+-[IxT],+
     * 1/12 [TxT],+- 1/3 [TxT],+- 1/12 [TxTQuer],+- 1/12 [TxT],+ 1/60
     * [IxIQuer],+ 1/12 [TxTQuer],+- 1/60 [IxIQuer],+- 1/3 [TxTQuer],+- [TxT],+-
     * 1/60 [IxI],----------- Subgroups of + 1/60 [IxI]: + 1/60 [IxI],+ 1/12
     * [TxT],----------- Subgroups of +-[IxT]: +-[IxT],+ 1/12 [TxT],+- 1/3
     * [TxT],+- 1/12 [TxTQuer],+- 1/12 [TxT],+ 1/12 [TxTQuer],+- 1/3
     * [TxTQuer],+- [TxT],----------- Subgroups of +-[OxO]: +-[OxO],+ 1/24
     * [OxO],+ 1/12 [TxT],+- 1/3 [TxT],+- 1/12 [TxTQuer],+- 1/12 [TxT],+- 1/24
     * [OxO],+ 1/12 [TxTQuer],+- 1/2 [OxO],+- 1/3 [TxTQuer],+- 1/6 [OxO],+ 1/24
     * [OxOquer],+-[OxT],+- [TxT],----------- Subgroups of + 1/24 [OxO]: + 1/24
     * [OxO],+ 1/12 [TxT],----------- Subgroups of + 1/12 [TxT]: + 1/12
     * [TxT],----------- Subgroups of +- 1/3 [TxT]: + 1/12 [TxT],+- 1/3 [TxT],+-
     * 1/12 [TxT],----------- Subgroups of +- 1/12 [TxTQuer]: +- 1/12
     * [TxTQuer],+ 1/12 [TxTQuer],----------- Subgroups of +- 1/12 [TxT]: + 1/12
     * [TxT],+- 1/12 [TxT],----------- Subgroups of + 1/60 [IxIQuer]: + 1/60
     * [IxIQuer],----------- Subgroups of +- 1/24 [OxO]: + 1/24 [OxO],+ 1/12
     * [TxT],+- 1/12 [TxT],+- 1/24 [OxO],+ 1/24 [OxOquer],----------- Subgroups
     * of + 1/12 [TxTQuer]: + 1/12 [TxTQuer],----------- Subgroups of +- 1/2
     * [OxO]: + 1/24 [OxO],+ 1/12 [TxT],+- 1/3 [TxT],+- 1/12 [TxTQuer],+- 1/12
     * [TxT],+- 1/24 [OxO],+ 1/12 [TxTQuer],+- 1/2 [OxO],+- 1/3 [TxTQuer],+- 1/6
     * [OxO],+ 1/24 [OxOquer],+- [TxT],----------- Subgroups of +- 1/60
     * [IxIQuer]: + 1/60 [IxIQuer],+- 1/60 [IxIQuer],----------- Subgroups of +-
     * 1/3 [TxTQuer]: +- 1/12 [TxTQuer],+ 1/12 [TxTQuer],+- 1/3
     * [TxTQuer],----------- Subgroups of +- 1/6 [OxO]: + 1/24 [OxO],+ 1/12
     * [TxT],+- 1/3 [TxT],+- 1/12 [TxT],+- 1/24 [OxO],+- 1/6 [OxO],+ 1/24
     * [OxOquer],----------- Subgroups of + 1/24 [OxOquer]: + 1/12 [TxT],+ 1/24
     * [OxOquer],----------- Subgroups of +-[OxT]: + 1/12 [TxT],+- 1/3 [TxT],+-
     * 1/12 [TxTQuer],+- 1/12 [TxT],+ 1/12 [TxTQuer],+- 1/3 [TxTQuer],+-[OxT],+-
     * [TxT],----------- Subgroups of +- [TxT]: + 1/12 [TxT],+- 1/3 [TxT],+-
     * 1/12 [TxTQuer],+- 1/12 [TxT],+ 1/12 [TxTQuer],+- 1/3 [TxTQuer],+-
     * [TxT],----------- Subgroups of +- 1/60 [IxI]: + 1/60 [IxI],+ 1/12
     * [TxT],+- 1/12 [TxT],+- 1/60 [IxI],-----------
     **/

  }
}
