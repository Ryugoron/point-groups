package pointGroups.util.polymake;

import java.util.ArrayList;
import java.util.Collection;
import java.util.concurrent.ExecutionException;

import junit.framework.TestCase;

import org.junit.Assert;
import org.junit.Test;

import pointGroups.geometry.Point;
import pointGroups.geometry.Point3D;
import pointGroups.geometry.Schlegel;


public class SchlegelTransformerTest
  extends TestCase
{

  Collection<Point> points3D;
  SchlegelTransformer st;

  public void setUp() {
    points3D = new ArrayList<Point>();
    points3D.add(new Point3D(0, 0, 0));
    points3D.add(new Point3D(1.1, 0, 0));
    points3D.add(new Point3D(0, -1.23, 1));
    st = new SchlegelTransformer(points3D);
  }

  @Test
  public void testTransformResultString2DSchlegel() {
    String result = "0.4321 -0.1234\n1 3\n4 5.5\n$\n{1 0}\n{1 2}\n{0 2}\n";
    st.setResultString(result);
    Schlegel schlegel = st.transformResultString();
    Assert.assertEquals(schlegel.points[0].i, 0.4321, 0);
    Assert.assertEquals(schlegel.points[0].j, -0.1234, 0);
    Assert.assertEquals(schlegel.points[1].i, 1, 0);
    Assert.assertEquals(schlegel.points[1].j, 3, 0);
    Assert.assertEquals(schlegel.points[2].i, 4, 0);
    Assert.assertEquals(schlegel.points[2].j, 5.5, 0);
    Assert.assertEquals(schlegel.edges[0].left, schlegel.points[1]);
    Assert.assertEquals(schlegel.edges[0].right, schlegel.points[0]);
    Assert.assertEquals(schlegel.edges[1].left, schlegel.points[1]);
    Assert.assertEquals(schlegel.edges[1].right, schlegel.points[2]);
    Assert.assertEquals(schlegel.edges[2].left, schlegel.points[0]);
    Assert.assertEquals(schlegel.edges[2].right, schlegel.points[2]);
  }

  @Test
  public void testTransformResultString3DSchlegel() {
    String result =
        "0.4321 -0.1234 0.0\n1 3 5\n4 5.5 -2\n$\n{1 0}\n{1 2}\n{0 2}\n";
    st.setResultString(result);
    Schlegel schlegel = st.transformResultString();
    Assert.assertEquals(schlegel.points[0].i, 0.4321, 0);
    Assert.assertEquals(schlegel.points[0].j, -0.1234, 0);
    Assert.assertEquals(schlegel.points[0].k, 0, 0);
    Assert.assertEquals(schlegel.points[1].i, 1, 0);
    Assert.assertEquals(schlegel.points[1].j, 3, 0);
    Assert.assertEquals(schlegel.points[1].k, 5, 0);
    Assert.assertEquals(schlegel.points[2].i, 4, 0);
    Assert.assertEquals(schlegel.points[2].j, 5.5, 0);
    Assert.assertEquals(schlegel.points[2].k, -2, 0);
    Assert.assertEquals(schlegel.edges[0].left, schlegel.points[1]);
    Assert.assertEquals(schlegel.edges[0].right, schlegel.points[0]);
    Assert.assertEquals(schlegel.edges[1].left, schlegel.points[1]);
    Assert.assertEquals(schlegel.edges[1].right, schlegel.points[2]);
    Assert.assertEquals(schlegel.edges[2].left, schlegel.points[0]);
    Assert.assertEquals(schlegel.edges[2].right, schlegel.points[2]);
  }

  @Test
  public void testSetAndGetSeq()
    throws InterruptedException, ExecutionException {
    String resultString =
        "0.4321 -0.1234 0.0\n1 3 5\n4 5.5 -2\n$\n{1 0}\n{1 2}\n{0 2}\n";
    st.setResultString(resultString);
    Schlegel schlegel = st.get();
    Assert.assertEquals(schlegel.points[0].i, 0.4321, 0);
    Assert.assertEquals(schlegel.points[0].j, -0.1234, 0);
    Assert.assertEquals(schlegel.points[0].k, 0, 0);
    Assert.assertEquals(schlegel.points[1].i, 1, 0);
    Assert.assertEquals(schlegel.points[1].j, 3, 0);
    Assert.assertEquals(schlegel.points[1].k, 5, 0);
    Assert.assertEquals(schlegel.points[2].i, 4, 0);
    Assert.assertEquals(schlegel.points[2].j, 5.5, 0);
    Assert.assertEquals(schlegel.points[2].k, -2, 0);
    Assert.assertSame(schlegel.points[1], schlegel.edges[0].left);
    Assert.assertSame(schlegel.points[0], schlegel.edges[0].right);
    Assert.assertSame(schlegel.points[1], schlegel.edges[1].left);
    Assert.assertSame(schlegel.points[2], schlegel.edges[1].right);
    Assert.assertSame(schlegel.points[0], schlegel.edges[2].left);
    Assert.assertSame(schlegel.points[2], schlegel.edges[2].right);

    Assert.assertTrue(st.isDone());
  }

  @Test
  public void testSetAndGetConc()
    throws InterruptedException, ExecutionException {
    new Thread(new GetRunnable(st)).start();
    Schlegel schlegel = st.get();
    Assert.assertEquals(0, schlegel.points[0].i, 0);
    Assert.assertEquals(0, schlegel.points[0].j, 0);
    Assert.assertEquals(0, schlegel.points[0].k, 0);
    Assert.assertEquals(1, schlegel.points[1].i, 0);
    Assert.assertEquals(1, schlegel.points[1].j, 0);
    Assert.assertEquals(1, schlegel.points[1].k, 0);
    Assert.assertSame(schlegel.points[0], schlegel.edges[0].left);
    Assert.assertSame(schlegel.points[1], schlegel.edges[0].right);

    Assert.assertTrue(st.isDone());
  }


  public class GetRunnable
    implements Runnable
  {

    private SchlegelTransformer st;

    public GetRunnable(SchlegelTransformer st) {
      this.st = st;
    }

    public void run() {
      try {
        // this is hopefully enough time such that st.get() will be executed
        // first
        synchronized (this) {
          wait(100);
        }
      }
      catch (InterruptedException e) {
        // TODO Auto-generated catch block
        e.printStackTrace();
      }
      st.setResultString("0 0 0\n1 1 1\n$\n{0 1}\n");
    }
  }

  @Test
  public void testToString() {
    String script = st.toScript();
    String desiredScript =
        "use application \"polytope\";my $mat=new Matrix<Rational>([[1,0.0,0.0,0.0],[1,1.1,0.0,0.0],[1,0.0,-1.23,1.0]]);my $p = new Polytope(POINTS=>$mat);my $schlegelverts = $p->SCHLEGEL_DIAGRAM->VERTICES;my $edges = $p->GRAPH->EDGES;my $v = \"$schlegelverts\";my $e = \"$edges\";print $v.$e";
    Assert.assertTrue(script.equals(desiredScript));
  }

}
