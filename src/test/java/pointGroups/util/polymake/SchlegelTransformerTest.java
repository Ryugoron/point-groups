package pointGroups.util.polymake;

import java.io.IOException;
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
      "1.613228378 2.147844766\n-0.1878060471 0.9978447659\n-1.988840472 -0.1521552341\n$\n{0 1}\n{0 2}\n{1 2}\n";

  public static final String POLYMAKE_3D_RESULT =
      "1.613228378 2.147844766 0.0\n-0.1878060471 0.9978447659 5\n-1.988840472 -0.1521552341 -2\n$\n{0 1}\n{0 2}\n{1 2}\n";

  Collection<Point> points3D;
  SchlegelTransformer st;

  @Override
  public void setUp() {
    points3D = new ArrayList<Point>();
    points3D.add(new Point3D(1.1, 2.2, 3.3));
    points3D.add(new Point3D(-1.0, 2.0, -3.0));
    points3D.add(new Point3D(0.0, -1.23, 1.0));
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
        "use application \"polytope\";my $mat=new Matrix<Rational>([[1,1.1,2.2,3.3],[1,-1.0,2.0,-3.0],[1,0.0,-1.23,1.0]]);my $p = new Polytope(POINTS=>$mat);my $schlegelverts = $p->SCHLEGEL_DIAGRAM->VERTICES;my $edges = $p->GRAPH->EDGES;my $v = \"$schlegelverts\";my $e = \"$edges\";print $v.\"\\$\\n\".$e";

    assertEquals(desiredScript, script);
  }

  @Test
  public void testExecutionOnPolymake()
    throws IOException, InterruptedException, ExecutionException {

    Schlegel schlegel = PolymakeTestExecutor.execute(st);
    assert2DResults(schlegel);
  }

  public void assert2DResults(Schlegel schlegel) {
    assertEquals(1.613228378, schlegel.points[0].i, 0);
    assertEquals(2.147844766, schlegel.points[0].j, 0);
    assertEquals(-0.1878060471, schlegel.points[1].i, 0);
    assertEquals(0.9978447659, schlegel.points[1].j, 0);
    assertEquals(-1.988840472, schlegel.points[2].i, 0);
    assertEquals(-0.1521552341, schlegel.points[2].j, 0);

    assertEquals(schlegel.points[0], schlegel.edgesViaPoints[0].left);
    assertEquals(schlegel.points[1], schlegel.edgesViaPoints[0].right);
    assertEquals(schlegel.points[0], schlegel.edgesViaPoints[1].left);
    assertEquals(schlegel.points[2], schlegel.edgesViaPoints[1].right);
    assertEquals(schlegel.points[1], schlegel.edgesViaPoints[2].left);
    assertEquals(schlegel.points[2], schlegel.edgesViaPoints[2].right);

    assertEquals((Integer) 0, schlegel.edgesViaIndices[0].left);
    assertEquals((Integer) 1, schlegel.edgesViaIndices[0].right);
    assertEquals((Integer) 0, schlegel.edgesViaIndices[1].left);
    assertEquals((Integer) 2, schlegel.edgesViaIndices[1].right);
    assertEquals((Integer) 1, schlegel.edgesViaIndices[2].left);
    assertEquals((Integer) 2, schlegel.edgesViaIndices[2].right);
  }

  public void assert3DResults(Schlegel schlegel) {
    assert2DResults(schlegel);

    assertEquals(0, schlegel.points[0].k, 0);
    assertEquals(5, schlegel.points[1].k, 0);
    assertEquals(-2, schlegel.points[2].k, 0);
  }
}
