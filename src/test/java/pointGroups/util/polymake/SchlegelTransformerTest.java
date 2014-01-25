package pointGroups.util.polymake;

import java.util.ArrayList;
import java.util.Collection;
import java.util.concurrent.ExecutionException;

import junit.framework.TestCase;

import org.junit.Test;

import pointGroups.geometry.Point;
import pointGroups.geometry.Point3D;
import pointGroups.geometry.Schlegel;


public class SchlegelTransformerTest
  extends TestCase
{
  public static final String POLYMAKE_2D_RESULT =
      "0.4321 -0.1234\n1 3\n4 5.5\n$\n{1 0}\n{1 2}\n{0 2}\n";

  public static final String POLYMAKE_3D_RESULT =
      "0.4321 -0.1234 0.0\n1 3 5\n4 5.5 -2\n$\n{1 0}\n{1 2}\n{0 2}\n";

  Collection<Point> points3D;
  SchlegelTransformer st;

  @Override
  public void setUp() {
    points3D = new ArrayList<Point>();
    points3D.add(new Point3D(0, 0, 0));
    points3D.add(new Point3D(1.1, 0, 0));
    points3D.add(new Point3D(0, -1.23, 1));
    st = new SchlegelTransformer(points3D);
  }

  @Test
  public void testTransformResultString2DSchlegel() {
    st.setResultString(POLYMAKE_2D_RESULT);
    Schlegel schlegel = st.transformResultString();

    assert2DResults(schlegel);
  }

  @Test
  public void testTransformResultString3DSchlegel() {
    st.setResultString(POLYMAKE_3D_RESULT);
    Schlegel schlegel = st.transformResultString();

    assert3DResults(schlegel);
  }

  @Test
  public void testSetAndGetSeq()
    throws InterruptedException, ExecutionException {
    st.setResultString(POLYMAKE_3D_RESULT);
    Schlegel schlegel = st.get();

    assert3DResults(schlegel);
    assertTrue(st.isDone());
  }

  @Test
  public void testSetAndGetConc()
    throws InterruptedException, ExecutionException {
    new Thread(new GetRunnable(st)).start();
    Schlegel schlegel = st.get();

    assert3DResults(schlegel);
    assertTrue(st.isDone());
  }


  public class GetRunnable
    implements Runnable
  {

    private final SchlegelTransformer st;

    public GetRunnable(SchlegelTransformer st) {
      this.st = st;
    }

    @Override
    public void run() {
      try {
        // this is hopefully enough time such that st.get() will be executed
        // first
        synchronized (this) {
          wait(100);
        }
      }
      catch (InterruptedException e) {
        e.printStackTrace();
      }
      st.setResultString(POLYMAKE_3D_RESULT);
    }
  }

  @Test
  public void testToScript() {
    String script = st.toScript();
    String desiredScript =
        "use application \"polytope\";my $mat=new Matrix<Rational>([[1,0.0,0.0,0.0],[1,1.1,0.0,0.0],[1,0.0,-1.23,1.0]]);my $p = new Polytope(POINTS=>$mat);my $schlegelverts = $p->SCHLEGEL_DIAGRAM->VERTICES;my $edges = $p->GRAPH->EDGES;my $v = \"$schlegelverts\";my $e = \"$edges\";print $v.\"\\$\\n\".$e";

    assertEquals(desiredScript, script);
  }

  public void assert2DResults(Schlegel schlegel) {
    assertEquals(schlegel.points[0].i, 0.4321, 0);
    assertEquals(schlegel.points[0].j, -0.1234, 0);
    assertEquals(schlegel.points[1].i, 1, 0);
    assertEquals(schlegel.points[1].j, 3, 0);
    assertEquals(schlegel.points[2].i, 4, 0);
    assertEquals(schlegel.points[2].j, 5.5, 0);
    assertEquals(schlegel.edgesViaPoints[0].left, schlegel.points[1]);
    assertEquals(schlegel.edgesViaPoints[0].right, schlegel.points[0]);
    assertEquals(schlegel.edgesViaPoints[1].left, schlegel.points[1]);
    assertEquals(schlegel.edgesViaPoints[1].right, schlegel.points[2]);
    assertEquals(schlegel.edgesViaPoints[2].left, schlegel.points[0]);
    assertEquals(schlegel.edgesViaPoints[2].right, schlegel.points[2]);
    assertEquals(schlegel.edgesViaIndices[0].left, 1, 0);
    assertEquals(schlegel.edgesViaIndices[0].right, 0, 0);
    assertEquals(schlegel.edgesViaIndices[1].left, 1, 0);
    assertEquals(schlegel.edgesViaIndices[1].right, 2, 0);
    assertEquals(schlegel.edgesViaIndices[2].left, 0, 0);
    assertEquals(schlegel.edgesViaIndices[2].right, 2, 0);
  }

  public void assert3DResults(Schlegel schlegel) {
    assert2DResults(schlegel);

    assertEquals(schlegel.points[0].k, 0, 0);
    assertEquals(schlegel.points[1].k, 5, 0);
    assertEquals(schlegel.points[2].k, -2, 0);
  }
}
