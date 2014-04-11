package pointGroups.geometry.symmetries;

import static org.junit.Assert.*;

import org.junit.Test;

import pointGroups.geometry.Point4D;

public class Symmetry4DReflectionTest
{

  @Test
  public void testOrder() {
    Point4D point = Symmetry4DReflection.IxIm2.getNormalPoint();
    assertEquals(14400, Symmetry4DReflection.IxIm2.images(point).size(), 0);
    assertEquals(240, Symmetry4DReflection.IxI60m2.images(point).size(), 0);
    assertEquals(120, Symmetry4DReflection.IxIPlus60m2_3.images(point).size(), 0);
    assertEquals(120, Symmetry4DReflection.IxIPlus60m2_1.images(point).size(), 0);
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

  }

}
