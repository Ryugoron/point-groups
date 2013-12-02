package pointGroups;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.net.Socket;
import java.util.Collection;
import java.util.Properties;

import pointGroups.geometry.Point3D;
import pointGroups.geometry.symmetries.OctahedralSymmetry;
import pointGroups.util.polymake.SchlegelTransformer;


public class PointGroups
{
  private static final Properties prop = new Properties();
  private static Socket s;
  private static Process p;

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

    try {
      // Start Polymake
      p = Runtime.getRuntime().exec(polyCmd + " --script=" + polyDriver);
      Thread.sleep(1000);
      
      
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
      SchlegelTransformer st = new SchlegelTransformer(im);
      String perl = st.toScript().replaceAll("\n", "");

      // Wait for start-up of polymake
      Thread.sleep(5000);

      s = new Socket("localhost", 57177);
      BufferedWriter bw =
          new BufferedWriter(new OutputStreamWriter(s.getOutputStream()));
      BufferedReader br =
          new BufferedReader(new InputStreamReader(s.getInputStream()));

      bw.write(perl + "\n");
      bw.flush();

      String output;
      while ((output = br.readLine()) != null) {
        if (output.equals("__END__")) break;
        System.out.println(output);
      }
      bw.write("__END__" + "\n");
      bw.flush();
      
      s.close();
      p.destroy();
    }
    catch (IOException e) {
      e.printStackTrace();
    }
    catch (InterruptedException e) {
      e.printStackTrace();
    }
    finally {
      p.destroy();
      try {
        s.close();
      }
      catch (IOException e) {
        e.printStackTrace();
      }
    }

  }

}
