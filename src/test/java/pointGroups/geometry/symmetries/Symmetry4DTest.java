package pointGroups.geometry.symmetries;

import static org.junit.Assert.*;

import java.util.Collection;
import java.util.Map;

import org.junit.Test;

import pointGroups.geometry.Point4D;
import pointGroups.geometry.Symmetry;
import de.jreality.ui.viewerapp.actions.edit.CreateAppearance;


public class Symmetry4DTest
{

  @Test
  public void testOrder() {
    // Calculate expected group orders like described in Quaternions and
    // Ocontions John H. Conway, Derek A. Smith page 43
    assertEquals(2880, Symmetry4D.IxO.order());
    assertEquals(1440, Symmetry4D.IxT.order());
    assertEquals(576, Symmetry4D.OxT.order());

    assertEquals(7200, Symmetry4D.IxI.order());
    assertEquals(120, Symmetry4D.IxI60.order());
    assertEquals(60, Symmetry4D.IxIPlus60.order());
    assertEquals(120, Symmetry4D.IxIQuer60.order());
    assertEquals(60, Symmetry4D.IxIQuerPlus60.order());

    assertEquals(1152, Symmetry4D.OxO.order());
    assertEquals(576, Symmetry4D.OxO2.order());
    assertEquals(192, Symmetry4D.OxO6.order());
    assertEquals(48, Symmetry4D.OxO24.order());
    assertEquals(24, Symmetry4D.OxOPlus24.order());
    assertEquals(24, Symmetry4D.OxOQuerPlus24.order());

    assertEquals(288, Symmetry4D.TxT.order());
    assertEquals(96, Symmetry4D.TxT3.order());
    assertEquals(96, Symmetry4D.TxTQuer3.order());
    assertEquals(24, Symmetry4D.TxT12.order());
    assertEquals(24, Symmetry4D.TxTQuer12.order());
    assertEquals(12, Symmetry4D.TxTPlus12.order());
    assertEquals(12, Symmetry4D.TxTQuerPlus12.order());

    assertEquals(21, Symmetry4D.getSymmetries().size());

    System.out.println(Symmetry4D.getSymmetries());
  }

  @Test
  public void testOrderOfGroups() {
    Collection<Symmetry4D> groups = Symmetry4D.getSymmetries();
    Symmetry4D[] a = new Symmetry4D[groups.size()];
    a = groups.toArray(a);
    Symmetry4D[] b =
        { Symmetry4D.TxT, Symmetry4D.TxT3, Symmetry4D.TxTQuer3,
            Symmetry4D.TxT12, Symmetry4D.TxTQuer12, Symmetry4D.TxTPlus12,
            Symmetry4D.TxTQuerPlus12, Symmetry4D.OxO, Symmetry4D.OxO2,
            Symmetry4D.OxO6, Symmetry4D.OxO24, Symmetry4D.OxOPlus24,
            Symmetry4D.OxOQuerPlus24, Symmetry4D.IxI, Symmetry4D.IxI60,
            Symmetry4D.IxIQuer60, Symmetry4D.IxIPlus60,
            Symmetry4D.IxIQuerPlus60, Symmetry4D.IxO, Symmetry4D.IxT,
            Symmetry4D.OxT };
    assertArrayEquals(b, a);

    Collection<Symmetry4D> subgroups = Symmetry4D.subgroups.get(Symmetry4D.IxO);
    Symmetry4D[] a1 = new Symmetry4D[subgroups.size()];
    a1 = subgroups.toArray(a1);
    Symmetry4D[] b1 =
        { Symmetry4D.TxT, Symmetry4D.TxT3, Symmetry4D.TxTQuer3,
            Symmetry4D.TxT12, Symmetry4D.TxTQuer12, Symmetry4D.TxTPlus12,
            Symmetry4D.TxTQuerPlus12, Symmetry4D.IxO, Symmetry4D.IxT };
    assertArrayEquals(b1, a1);
  }

  @Test
  public void testSubgroupsOrder() {

    Collection<Symmetry4D> groups = Symmetry4D.getSymmetries();

    int subgroupCounter;
    boolean isSubgroup;
    Collection<Rotation4D> groupMembers;
    Collection<? extends Symmetry<Point4D>> subgroups;

    for (Symmetry4D g : groups) {
      subgroupCounter = 0;
      subgroups = g.subgroups();
      groupMembers = Symmetry4D.groups.get(g);
      for (Symmetry4D f : groups) {
        isSubgroup = true;
        for (Rotation4D rot : Symmetry4D.groups.get(f)) {
          if (!groupMembers.contains(rot)) {
            isSubgroup = false;
            break;
          }
        }
        if (isSubgroup) {
          subgroupCounter++;
          assertTrue(subgroups.contains(f));
        }
      }
      assertEquals(subgroupCounter, g.subgroups().size());
    }
  }

  @Test
  public void testSubgroupsAreSubgroups() {
    boolean isSubgroup;
    Collection<Rotation4D> groupMembers;
    Symmetry4D sub;
    for (Symmetry4D g : Symmetry4D.getSymmetries()) {
      groupMembers = Symmetry4D.groups.get(g);
      for (Symmetry<Point4D> subgroup : g.subgroups()) {
        isSubgroup = true;
        sub = (Symmetry4D) subgroup;
        for (Rotation4D rot : Symmetry4D.groups.get(sub)) {
          if (!groupMembers.contains(rot)) isSubgroup = false;
          break;
        }
        assertTrue(isSubgroup);
      }
    }
  }

}
