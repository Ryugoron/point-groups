package pointGroups.util.polymake;

import java.util.ArrayList;
import java.util.List;

import junit.framework.TestCase;

import org.junit.Test;

import pointGroups.geometry.Edge;
import pointGroups.geometry.Fundamental;
import pointGroups.geometry.KnownFundamental;
import pointGroups.geometry.Point3D;
import pointGroups.util.point.PointUtil;


public class FundamentalTransformerTest
  extends TestCase
{

  public static final String POLYMAKE_RESULT =
      "1\n1 0 0 3\n1 0 3 0\n1 3 0 0\n-----\n{0 1}\n{0 2}\n{1 2}\n-----\n0 0 1 0\n0 0 0 1\n0 1 0 0";

  public static final String POLYMAKE_SCRIPT =
      "my $hyper = new Matrix<Rational>([[0.0, 0.0, 1.0, 0.0],[0.0, 0.0, 0.0, 1.0],[0.0, 1.0, 0.0, 0.0]]);my $aff = new Matrix<Rational>([[-3.0 , 1.0 , 1.0 , 1.0]]);my $poly = new Polytope(INEQUALITIES=>$hyper, EQUATIONS=>$aff);print $poly->BOUNDED;print \"\\n\";print $poly->VERTICES;print \"-----\\n\";print $poly->GRAPH->EDGES;print \"-----\\n\";print $poly->FACETS;";

  public static final double EPSILON = 0.00001;
  
  public static final double[][] FUND_VERTICES = new double[][]{ new double[] {-Math.sqrt(3d/2d), 3 / Math.sqrt(2)}, new double[] {Math.sqrt(6), 0}, new double[] {-Math.sqrt(3d/2d), -3 / Math.sqrt(2)} };

  public static List<Edge> edges;
  
  List<Point3D> points;
  Point3D center;

  FundamentalTransformer fT;
  
  Fundamental fund;
  
  double[] testCoordinate;
  double[] outside;
  double[] inside;

  

  @Override
  public void setUp() {
    center = new Point3D(1.0, 1.0, 1.0);
    points = new ArrayList<Point3D>();
    points.add(new Point3D(1.0, 0.0, 1.0));
    points.add(new Point3D(1.0, 1.0, 0.0));
    points.add(new Point3D(0.0, 1.0, 1.0));
    
    edges = new ArrayList<Edge>();
    edges.add(new Edge(0, 1));
    edges.add(new Edge(0, 2));
    edges.add(new Edge(1, 2));

    fT = new FundamentalTransformer(center, points);
    fT.setResultString(POLYMAKE_RESULT);
    fund = fT.transformResultString();
  }


  @Test
  public void testScript() {
    assertEquals(POLYMAKE_SCRIPT, fT.toScript());
  }

  @Test
  public void testNormalVector() {
    double[] normalInFun =
        PointUtil.applyMatrix(fT.n2f, center.getComponents());

    // The normalvector should be (1,0,0)
    for (int i = 1; i < normalInFun.length; i++) {
      assertTrue (normalInFun[i] < EPSILON);
    }
  }
  
  @Test
  public void testVertices() {
    double[][] vert = fund.getVertices();
    for(int i = 0; i < vert.length; i++){
      for(int j = 0; j < vert[i].length; j++) {
        assertTrue(Math.abs(vert[i][j] - FUND_VERTICES[i][j]) < EPSILON);
      }
    }
  }
  
  @Test
  public void testRevertPoint() {
    double[] testPoint = new double[] {0.5, 0.5}; 
    double[] revertedPoint = new double[] {0.23643130220223896, 0.7527403740608412, 0.6143957752114663};
    
    double[] realRevert = fund.revertPoint(testPoint);
    
    for(int j = 0; j < realRevert.length; j++) {
      assertTrue(Math.abs(realRevert[j] - revertedPoint[j]) < EPSILON);
    }
  }
  
  @Test
  public void testInside() {
    double[] outside = new double[] {2.0, 2.0};
    double[] inside = new double[] {0.5, 0.5};
    
    assertTrue(fund.inFundamental(inside));
    assertFalse(fund.inFundamental(outside));
  }
  
  @Test
  public void testEdges() {
    for (Edge ed : fund.getEdges()) {
      edges.contains(ed);
    }
  }
  
  @Test
  public void testRevertBoundaryPoints(){
    for(double[] p : fT.clalcPoints){
      System.out.println("Check");
      double[] p1 = PointUtil.applyMatrix(fT.n2f, p);
      p1 = PointUtil.rmFst(p1);
      p1 = ((KnownFundamental) fund).affinePolytope(p1);
      for(int j = 0; j < p.length; j++) {
        assertTrue(Math.abs(p[j] - p1[j]) < EPSILON);
      }
    }
  }
  
  
  @Test
  public void testInsideBoundaryPoints(){
    
  }
}
