package pointGroups.util.polymake;

import java.util.ArrayList;
import java.util.List;

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
      "1 0 0 3\n1 0 3 0\n1 3 0 0\n-----\n{0 1}\n{0 2}\n{1 2}\n-----\n0 0 1 0\n0 0 0 1\n0 1 0 0";

  public static final String POLYMAKE_SCRIPT =
      "my $hyper = new Matrix<Rational>([[0.0, 0.0, 1.0, 0.0],[0.0, 0.0, 0.0, 1.0],[0.0, 1.0, 0.0, 0.0]]);my $aff = new Matrix<Rational>([[-3.0 , 1.0 , 1.0 , 1.0]]);my $poly = new Polytope(INEQUALITIES=>$hyper, EQUATIONS=>$aff);print $poly->VERTICES;print \"-----\\n\";print $poly->GRAPH->EDGES;print \"-----\\n\";print $poly->FACETS;";

  public static final double EPSILON = 0.00001;
  
  public static final double[][] FUND_VERTICES = new double[][]{ new double[] {Math.sqrt(3), -Math.sqrt(3/2), 3 / Math.sqrt(2)}, new double[] {Math.sqrt(3), Math.sqrt(6), 0}, new double[] {Math.sqrt(3), -Math.sqrt(3/2), -3 / Math.sqrt(2)} };

  List<Point3D> points;
  Point3D center;

  FundamentalTransformer fT;
  double[] testCoordinate;
  double[] outside;
  double[] inside;

  Fundamental fund;

  @Override
  public void setUp() {
    center = new Point3D(1.0, 1.0, 1.0);
    points = new ArrayList<Point3D>();
    points.add(new Point3D(1.0, 0.0, 1.0));
    points.add(new Point3D(1.0, 1.0, 0.0));
    points.add(new Point3D(0.0, 1.0, 1.0));

    fT = new FundamentalTransformer(center, points);
    fT.setResultString(POLYMAKE_RESULT);
  }

  @Test
  public void testNormalize() {
    // Vectoren konkret angeben
    double[][] base = fT.baseTransformation();
    for (double[] v : base) {
      System.out.println("Normal Base Vector : " + PointUtil.showPoint(v));
    }
    assert (true);
  }

  @Test
  public void testScript() {
    assertEquals(POLYMAKE_SCRIPT, fT.toScript());
  }

  @Test
  public void testNormalVector() {
    fT.transformResultString();
    double[] normalInFun =
        PointUtil.applyMatrix(fT.n2f, center.getComponents());

    // The normalvector should be (1,0,0)
    for (int i = 1; i < normalInFun.length; i++) {
      assert (normalInFun[i] < EPSILON);
    }
  }
  
  @Test
  public void testVertices() {
    Fundamental f = fT.transformResultString();
    double[][] vert = f.getVertices();
    for(int i = 0; i < vert.length; i++){
      for(int j = 0; j < vert[i].length; j++) {
        assert(Math.abs(vert[i][j] - FUND_VERTICES[i][j]) < EPSILON);
      }
    }
  }

}
