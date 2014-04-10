package pointGroups.geometry.symmetries;

import static org.junit.Assert.*;

import org.junit.Test;

import de.jreality.ui.viewerapp.actions.edit.CreateAppearance;

public class Symmetry4DTest
{

  @Test
  public void testOrder() {
    //Calculate expected group orders like described in Quaternions and Ocontions John H. Conway, Derek A. Smith page 43
    //assertEquals(2880, Symmetry4D.IxO.order());
    //assertEquals(1440, Symmetry4D.IxT.order());
    //assertEquals(576, Symmetry4D.OxT.order());
    
//    assertEquals(7200, Symmetry4D.IxI.order());
    assertEquals(120, Symmetry4D.IxI60.order());
    assertEquals(60, Symmetry4D.IxIPlus60.order());
    assertEquals(120, Symmetry4D.IxIQuer60.order());
    assertEquals(60, Symmetry4D.IxIQuerPlus60.order());
    
//    assertEquals(1152, Symmetry4D.OxO.order());
//    assertEquals(576, Symmetry4D.OxO2.order());
//    assertEquals(192, Symmetry4D.OxO6.order());
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
  }

}
