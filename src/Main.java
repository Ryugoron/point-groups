import geometry.Point;
import geometry.Point3D;
import geometry.symmetries.OctahedralSymmetry;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.util.Collection;
import java.util.Properties;

import util.polymake.SchlegelTransformer;

public class Main
{

  static Properties prop = new Properties();
  
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
    
//    System.out.println(prop.getProperty("POLYMAKEPATH"));
    String polyCmd = prop.getProperty("POLYMAKEPATH");
    String polyDriver = prop.getProperty("POLYMAKEDRIVER");
    
    try {
      Process p =  Runtime.getRuntime().exec(polyCmd + "--script" + polyDriver);
      
      
      Point3D pt = new Point3D(1, 4, 2.5);
      Collection<Point3D> im = OctahedralSymmetry.get().images(pt, OctahedralSymmetry.Subgroups.Full);
      
      SchlegelTransformer st = new SchlegelTransformer(im);
      String perl = st.toScript().replaceAll("\n", "");
      
      BufferedReader br = new BufferedReader(new InputStreamReader(p.getInputStream()));
      BufferedWriter bw = new BufferedWriter(new OutputStreamWriter(p.getOutputStream()));
      
      bw.write(perl); bw.flush(); bw.close();
      String output;
      while ((output = br.readLine()) != null) {
        System.out.println(output);
      }
      br.close();
    }
    catch (IOException e) {
      e.printStackTrace();
    }
    

  }

}
