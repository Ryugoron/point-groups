package pointGroups.util.polymake;

import static org.junit.Assert.*;

import java.util.ArrayList;
import java.util.Collection;
import java.util.concurrent.ExecutionException;

import org.junit.Assert;
import org.junit.Ignore;
import org.junit.Test;

import pointGroups.geometry.Point;
import pointGroups.geometry.Point3D;
import pointGroups.geometry.Schlegel;
import pointGroups.util.polymake.SchlegelTransformerTestDrive.GetRunnable;

public class SchlegelTransformerTest {

	Collection<Point> points3D;

	@Ignore
	@Test
	public void test() {
		fail("Not yet implemented");
	}

	public void setUp() {
		points3D = new ArrayList<Point>();
		points3D.add(new Point3D(0, 0, 0));
		points3D.add(new Point3D(1, 0, 0));
		points3D.add(new Point3D(0, 1, 1));
	}

	@Ignore
	@Test
	public void testSetAndGet() {
		SchlegelTransformer st = new SchlegelTransformer(points3D);
		new Thread(new GetRunnable(st)).run();
		st.setResultString("0 0 0 \n1 1 1\n $\n{0 1}");
	}

	public class GetRunnable implements Runnable {

		private SchlegelTransformer st;

		public GetRunnable(SchlegelTransformer st) {
			this.st = st;
		}

		public void run() {
			try {
				st.get();
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (ExecutionException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}

			System.out.println("Got Result!");
		}
	}

	@Test
	public void testTransformResultString2DSchlegel() {
		String result = "0.4321 -0.1234\n1 3\n4 5.5\n$\n{1 0}\n{1 2}\n{0 2}\n";
		SchlegelTransformer st = new SchlegelTransformer(points3D);
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

}
