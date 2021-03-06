package pointGroups.geometry.symmetries;

import static org.junit.Assert.*;

import java.util.Collection;

import org.junit.Test;

import pointGroups.geometry.Point4D;
import pointGroups.geometry.Symmetry;

public class Symmetry4DReflectionTest
{

  @Test
  public void testOrder() {
    Point4D point = Symmetry4DReflection.IxIm2.getNormalPoint();
    assertEquals(14400, Symmetry4DReflection.IxIm2.images(point).size(), 0);
    assertEquals(240, Symmetry4DReflection.IxI60m2.images(point).size(), 0);
    assertEquals(120, Symmetry4DReflection.IxIPlus60m2_3.images(point).size(), 0);
    assertEquals(120, Symmetry4DReflection.IxIPlus60m2_1.images(point).size(), 0);
    assertEquals(120, Symmetry4DReflection.IxIQuerPlus60m2_3.images(point).size(), 0);
    assertEquals(120, Symmetry4DReflection.IxIQuerPlus60m2_1.images(point).size(), 0);
    assertEquals(240, Symmetry4DReflection.IxIQuer60m2.images(point).size(), 0);
    assertEquals(2304, Symmetry4DReflection.OxOm2.images(point).size(), 0);
    assertEquals(1152, Symmetry4DReflection.OxO2m2.images(point).size(), 0);
    assertEquals(1152, Symmetry4DReflection.OxO2m2Quer.images(point).size(), 0);
    assertEquals(384, Symmetry4DReflection.OxO6m2.images(point).size(), 0);
    assertEquals(96, Symmetry4DReflection.OxO24m2.images(point).size(), 0);
    assertEquals(48, Symmetry4DReflection.OxOPlus24m2_3.images(point).size(), 0);
    assertEquals(48, Symmetry4DReflection.OxOPlus24m2_1.images(point).size(), 0);
    assertEquals(48, Symmetry4DReflection.OxOQuerPlus24m2_3.images(point).size(), 0);
    assertEquals(48, Symmetry4DReflection.OxOQuerPlus24m2_1.images(point).size(), 0);
    assertEquals(576, Symmetry4DReflection.TxTm2.images(point).size(), 0);
    assertEquals(192, Symmetry4DReflection.TxT3m2.images(point).size(), 0);
    assertEquals(192, Symmetry4DReflection.TxTQuer3m2.images(point).size(), 0);
    assertEquals(48, Symmetry4DReflection.TxT12m2.images(point).size(), 0);
    assertEquals(48, Symmetry4DReflection.TxTQuer12m2.images(point).size(), 0);
    assertEquals(24, Symmetry4DReflection.TxTPlus12m2_1.images(point).size(), 0);
    assertEquals(24, Symmetry4DReflection.TxTPlus12m2_3.images(point).size(), 0);
    assertEquals(24, Symmetry4DReflection.TxTQuerPlus12m2_1.images(point).size(), 0);
    assertEquals(24, Symmetry4DReflection.TxTQuerPlus12m2_3.images(point).size(), 0);
    
    assertEquals(25, Symmetry4DReflection.getSymmetries().size());
  }
  
  @Test
  public void testSubgroupsAreSubgroups(){
    Symmetry4DReflection s;
    for (Symmetry4DReflection g : Symmetry4DReflection.getSymmetries()) {
      for (Symmetry<Point4D> sym : g.subgroups()) {
        // if sym is a Symmetry4DReflection
        if (sym.getClass().isInstance(g)){
          s = (Symmetry4DReflection) sym;
          assertEquals(g.getExtendingElem(), s.getExtendingElem());
          assertTrue(g.getRotationsym().subgroups().contains(s.getRotationsym()));
        }
        else{
          assertTrue(g.getRotationsym().subgroups().contains(sym));
        }
      }
    }
  }
  
  @Test
  public void testSubgroupsOrder(){
    Collection<? extends Symmetry<Point4D>> subgroups;
    Collection<? extends Symmetry<Point4D>> rotationsymsub;
    Reflection4D extEl;
    int subgroupCounter;
    for (Symmetry4DReflection g : Symmetry4DReflection.getSymmetries()) {
      subgroupCounter = 0;
      subgroups = g.subgroups();
      rotationsymsub = g.getRotationsym().subgroups();
      extEl = g.getExtendingElem();
      for (Symmetry4D sym : Symmetry4D.getSymmetries()) {
        if (rotationsymsub.contains(sym)) {
          assertTrue(subgroups.contains(sym));
          subgroupCounter ++;
        }
      }
      for (Symmetry4DReflection sym : Symmetry4DReflection.getSymmetries()) {
        if (rotationsymsub.contains(sym.getRotationsym()) && extEl.equals(sym.getExtendingElem())) {
          assertTrue(subgroups.contains(sym));
          subgroupCounter ++;
        }
      }
      assertEquals(subgroupCounter, subgroups.size());
    }
  }

}
