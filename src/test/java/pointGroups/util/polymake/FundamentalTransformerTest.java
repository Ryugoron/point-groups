package pointGroups.util.polymake;

import java.util.ArrayList;
import java.util.Collection;

import junit.framework.TestCase;

import org.junit.Test;

import pointGroups.geometry.Fundamental;
import pointGroups.geometry.KnownFundamental;
import pointGroups.geometry.Point3D;
import pointGroups.util.point.PointUtil;


public class FundamentalTransformerTest
  extends TestCase
{

  public static final String POLYMAKE_RESULT =
      "1 0 0 0\n&\n3 3 3 3\n3 3 3 -3\n3 3 -3 3\n3 -3 3 3\n";

  public static final String POLYMAKE_SCRIPT =
      "my $points = new Matrix<Rational>([[1.0, 1.0, 1.0, 1.0],[1.0, 1.0, 1.0, -1.0],[1.0, 1.0, -1.0, 1.0],[1.0, -1.0, 1.0, 1.0],[1.0, 1.0, -1.0, -1.0],[1.0, -1.0, 1.0, -1.0],[1.0, -1.0, -1.0, 1.0],[1.0, -1.0, -1.0, -1.0]]);my $v = new VoronoiDiagram(SITES=>$points);print $v->VORONOI_VERTICES->[0];print \"\\n\";my $adj = $v->DUAL_GRAPH->ADJACENCY->adjacent_nodes(0);my $s0 = $points->[0];my $s1 = $points->[$adj->[0]];my $s2 = $points->[$adj->[1]];my $s3 = $points->[$adj->[2]];print \"&\\n\";print $s0;print \"\\n\";print $s1; print \"\\n\";print $s2; print \"\\n\";print $s3; print \"\\n\";";

  Collection<Point3D> points;
  FundamentalTransformer fT;
  double[] testCoordinate;
  double[] outside;
  double[] inside;

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
    fT = null;//new FundamentalTransformer(points);

    testCoordinate = new double[] { 0.25, 0.25 };
    inside = new double[] { 0.2, 0.2 };
    outside = new double[] { 1.5, 1.2 };
  }

  @Test
  public void testTransformResult() {
    fT.setResultString(POLYMAKE_RESULT);
    assertResult(fT.transformResultString());
  }

  @Test
  public void testScript() {
    assertEquals(POLYMAKE_SCRIPT, fT.toScript());
  }

  @Test
  public void testAffine() {
    fT.setResultString(POLYMAKE_RESULT);

    assertAffine(fT.transformResultString());
  }

  @Test
  public void testInFundamental() {
    fT.setResultString(POLYMAKE_RESULT);
    Fundamental fund = fT.transformResultString();

    assertTrue(fund.isKnown());
    // assertTrue(fund.inFundamental(inside));
    assertFalse(fund.inFundamental(outside));
  }

  public void assertAffine(Fundamental fund) {
    assertTrue(fund.isKnown());
    KnownFundamental f1 = (KnownFundamental) fund;

    double[] affineT = new double[] { 3, 1, 1 };

    assertEquals(affineT[0], f1.affine[0]);
    assertEquals(affineT[1], f1.affine[1]);
    assertEquals(affineT[2], f1.affine[2]);

    affineT = PointUtil.normalize(affineT);
    double[] p = fund.revertPoint(new double[] { 0.0, 0.0 });
    assertEquals(affineT[0], p[0]);
    assertEquals(affineT[1], p[1]);
    assertEquals(affineT[2], p[2]);
  }

  public void assertResult(Fundamental fund) {
    assertTrue(fund.isKnown());
    double[][] p = fund.getVertices();

    assertEquals(3, p.length);
    assertEquals(0.0, p[0][0]);
    assertEquals(0.0, p[0][1]);

    assertEquals(1.0, p[1][0]);
    assertEquals(0.0, p[1][1]);

    assertEquals(0.0, p[2][0]);
    assertEquals(1.0, p[2][1]);

    assertEquals(3, fund.revertPoint(testCoordinate).length);
  }
}
