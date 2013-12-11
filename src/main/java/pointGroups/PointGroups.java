package pointGroups;

import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.Collection;
import java.util.Properties;

import pointGroups.geometry.Point3D;
import pointGroups.geometry.Schlegel;
import pointGroups.geometry.symmetries.OctahedralSymmetry;
import pointGroups.util.polymake.SchlegelTransformer;
import pointGroups.util.polymake.wrapper.PolymakeWrapper;


public class PointGroups
{
  private static final Properties prop = new Properties();

  public static void main(String[] args) {

    /*
     * Read configuration
     */
    try {
      prop.load(new FileInputStream("settings.ini"));
    }
    catch (FileNotFoundException e) {
      System.out.println("Configuration file not found");
      e.printStackTrace();
    }
    catch (IOException e) {
      e.printStackTrace();
    }

    final String polyCmd = prop.getProperty("POLYMAKEPATH");
    final String polyDriver = prop.getProperty("POLYMAKEDRIVER");

    PolymakeWrapper pmWrapper = new PolymakeWrapper(polyCmd, polyDriver);
    pmWrapper.start();

    try {
      // Provisional input
      System.out.println("Please input a 3D-Point in Format x,y,z:");
      BufferedReader stdin =
          new BufferedReader(new InputStreamReader(System.in));
      String point = stdin.readLine();
      String[] coords = point.split(",");

      Point3D pt =
          new Point3D(Double.valueOf(coords[0]), Double.valueOf(coords[1]),
              Double.valueOf(coords[2]));

      // Calculate Poins
      Collection<Point3D> im =
          OctahedralSymmetry.get().images(pt, OctahedralSymmetry.Subgroups.Full);

      // Apply Schlegeltransformation script
      SchlegelTransformer<Schlegel> st = new SchlegelTransformer<Schlegel>(im);
      pmWrapper.sendRequest(st);
      // Since Transformer is not yet a future
      Thread.sleep(2000);
      System.out.println("Result is:");
      System.out.println("------------");
//      System.out.println(st.get().toString());
      System.out.println("------------");
      pmWrapper.stop();
      Thread.sleep(2000);


    }
    catch (IOException e) {
      e.printStackTrace();
    }
    catch (InterruptedException e) {
      e.printStackTrace();
    }

  }

}
