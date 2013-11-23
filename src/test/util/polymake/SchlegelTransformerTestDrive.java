package test.util.polymake;

import java.util.ArrayList;
import java.util.Collection;

import geometry.Point;
import geometry.Point3D;
import geometry.Point4D;

import util.polymake.SchlegelTransformer;

public class SchlegelTransformerTestDrive
{

  /**
   * @param args
   */
  public static void main(String[] args) {
    // TODO Auto-generated method stub
    toScriptTest();
  }
  
  public static void toScriptTest() {
    
    // 3D test    
    Collection<Point> points3D = new ArrayList<Point>();
    points3D.add(new Point3D(0,0,0));
    points3D.add(new Point3D(1,0,0));
    points3D.add(new Point3D(0,1,1));
    
    SchlegelTransformer st3D = new SchlegelTransformer(points3D);
    System.out.println(st3D.toScript());
    
    // 4D test    
    Collection<Point> points4D = new ArrayList<Point>();
    points3D.add(new Point4D(1,0,0,0));
    points3D.add(new Point4D(1,1,0,0));
    points3D.add(new Point4D(0,0,1,1));
    
    SchlegelTransformer st4D = new SchlegelTransformer(points4D);
    System.out.println(st4D.toScript());
  }

}
