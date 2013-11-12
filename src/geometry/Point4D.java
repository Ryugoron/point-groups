package geometry;

public class Point4D
  extends Quaternion
{

  public Point4D(double x, double y, double z, double w) {
    super(x, y, z, w);
  }

  public Point4D rotate(UnitQuaternion p, UnitQuaternion q) {
    return p.mult(this).mult(q).asPoint4D();
  }
}
