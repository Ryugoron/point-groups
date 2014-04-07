package pointGroups.geometry.symmetries;

import static org.junit.Assert.*;

import org.junit.Test;

import de.jreality.ui.viewerapp.actions.edit.CreateAppearance;

public class Symmetry4DTest
{

  @Test
  public void testOrder() {
    //TODO Generate Groups somehow. How is this done in the application?
    assertEquals(2400, Symmetry4D.IxI.order(), 0);
  }

}
