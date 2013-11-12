package geometry;

public class Point3D
  extends Quaternion
{

  public Point3D(double x, double y, double z) {
    super(0, x, y, z);
  }

  public Point3D rotate(UnitQuaternion q) {
    return q.mult(this).mult(q.inverse()).asPoint3D();
  }
}
