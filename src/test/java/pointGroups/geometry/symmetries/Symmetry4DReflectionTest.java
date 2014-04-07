package pointGroups.geometry.symmetries;

import static org.junit.Assert.*;

import org.junit.Test;

import pointGroups.geometry.Point4D;

public class Symmetry4DReflectionTest
{

  @Test
  public void testOrder() {
    //TODO Generate Groups somehow.
    //TODO Is this point always outside of any rotation or reflection axis? Is the normal point a better idea?
    Point4D point = new Point4D(0.5, 0.25, 0.125, 0.125);
    assertEquals(4800, Symmetry4DReflection.IxIm2.images(point).size(), 0);
  }

}
