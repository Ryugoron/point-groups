package pointGroups.util.polymake;

import java.util.ArrayList;
import java.util.Collection;

import junit.framework.TestCase;

import org.junit.Test;

import pointGroups.geometry.Fundamental;
import pointGroups.geometry.Point3D;


public class FundamentalTransformerTest
  extends TestCase
{

  public static final String POLYMAKE_RESULT =
      "1 0 0 0\n&\n1 1 0 0\n1 0 0 1\n1 0 1 0\n1 0 0 0\n";

  Collection<Point3D> points;
  FundamentalTransformer fT;
  double[] testCoordinate;

  @Override
  public void setUp() {
    points = new ArrayList<Point3D>();
    points.add(new Point3D(1, 1, 1));
    points.add(new Point3D(1, 1, -1));
    points.add(new Point3D(1, -1, 1));
    points.add(new Point3D(-1, 1, 1));
    points.add(new Point3D(1, -1, -1));
    points.add(new Point3D(-1, 1, -1));
    points.add(new Point3D(-1, -1, 1));
    points.add(new Point3D(-1, -1, -1));
    fT = new FundamentalTransformer(points);

    testCoordinate = new double[] { 0.25, 0.25 };
  }

  @Test
  public void testTransformResult() {
    fT.setResultString(POLYMAKE_RESULT);
    assertResult(fT.transformResultString());
  }

  public void assertResult(Fundamental fund) {
    assertTrue(fund.isKnown());
    double[][] p = fund.getVertices();

    assertEquals(3, p.length);
    assertEquals(1.0, p[0][0]);
    assertEquals(0.0, p[0][1]);
    assertEquals(0.0, p[0][2]);

    assertEquals(0.0, p[1][0]);
    assertEquals(1.0, p[1][1]);
    assertEquals(0.0, p[1][2]);

    assertEquals(0.0, p[2][0]);
    assertEquals(0.0, p[2][1]);
    assertEquals(1.0, p[2][2]);

    assertEquals(3, fund.revertPoint(testCoordinate).length);
  }
}
