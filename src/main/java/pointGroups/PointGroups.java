package pointGroups;

import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.FileWriter;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.io.Writer;

import javax.swing.SwingUtilities;
import javax.swing.UIManager;

import pointGroups.geometry.Point3D;
import pointGroups.geometry.symmetries.Symmetry3D;
import pointGroups.gui.ExternalCalculationEventHub;
import pointGroups.gui.MainFrame;
import pointGroups.gui.StartupErrorFrame;
import pointGroups.gui.event.EventDispatcher;
import pointGroups.gui.event.types.ChangeCoordinate3DPointEvent;
import pointGroups.gui.event.types.DimensionSwitchEvent;
import pointGroups.gui.event.types.ScaleFundamentalDomainEvent;
import pointGroups.gui.event.types.Schlegel3DComputeEvent;
import pointGroups.gui.event.types.Symmetry3DChooseEvent;
import pointGroups.util.ExternalCalculationWrapper;
import pointGroups.util.PointGroupsUtility;
import pointGroups.util.PointerToString;
import pointGroups.util.polymake.wrapper.PolymakeWrapper;


public class PointGroups
{
  static MainFrame mainframe;
  static ExternalCalculationWrapper polymakeWrapper;

  public static void main(final String[] args)
    throws IOException {
    File polyCmd;
    File driverPath;
    try{
    polyCmd = PointGroupsUtility.getPolymakePath();
    driverPath = PointGroupsUtility.getPolymakeDriverPath();
    }
    catch (FileNotFoundException e) {
      PointerToString ps = new PointerToString();
      new StartupErrorFrame(ps);
      synchronized (ps) {
        try {
          ps.wait();
        }
        catch (InterruptedException e1) {
          // TODO Auto-generated catch block
          e1.printStackTrace();
        }
      }
      System.out.println("Entered Path is:" + ps.s);
      //create two files because while running the first time, the one in target doesnt exist.
      File file = new File("./src/main/resources/settings.ini");
      File file2 = new File("./target/classes/settings.ini");
      try {
        System.out.println(file.createNewFile());
        BufferedWriter output = new BufferedWriter(new FileWriter(file));
        output.write("POLYMAKEPATH = " + ps.s);
        output.close();
        output = new BufferedWriter(new FileWriter(file2));
        output.write("POLYMAKEPATH = " + ps.s);
        output.close();
      } 
      catch (IOException e2) {
        System.err.println("settings.ini not proper initialised");
         e.printStackTrace();
      }
      polyCmd = PointGroupsUtility.getPolymakePath();
      driverPath = null;
    }

    // Check whether Polymake exists on the system
    if (!polyCmd.isFile()) {
      System.err.println("Cannot find polymake executable at " + polyCmd);
      new StartupErrorFrame(polyCmd.toString());
    }
    else {
      // Create Mainframe (copy from MainFrame.java)
      try {
        UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());

        SwingUtilities.invokeLater(new Runnable() {
          @Override
          public void run() {
            mainframe = new MainFrame();

            // Close Polymake on window close
            mainframe.addWindowListener(new WindowAdapter() {
              @Override
              public void windowClosing(final WindowEvent e) {
                polymakeWrapper.stop();
              }
            });
          }
        });
      }
      catch (Exception e) {
        e.printStackTrace();
      }

      // // Only for debugging purposes
      // EventDispatcher.get().addHandler(SchlegelResultHandler.class,
      // new SchlegelResultHandler() {
      //
      // @Override
      // public void onSchlegelResultEvent(final SchlegelResultEvent event) {
      // Logger.getGlobal().info("Got Schlegelresult!");
      // Logger.getGlobal().info(event.getResult().points.toString());
      // }
      // });
      // // End

      // Create Wrapper and HUB
      polymakeWrapper =
          new PolymakeWrapper(polyCmd.toString(), driverPath.toString());
      new ExternalCalculationEventHub(polymakeWrapper);

      // fire events to initialize the whole gui
      {
        Symmetry3DChooseEvent symmetry3dChooseEvent =
            new Symmetry3DChooseEvent(Symmetry3D.T);
        Point3D point = new Point3D(0, 0, 1);

        EventDispatcher.get().fireEvent(new ScaleFundamentalDomainEvent(1));
        EventDispatcher.get().fireEvent(new DimensionSwitchEvent(Point3D.class));
        EventDispatcher.get().fireEvent(symmetry3dChooseEvent);

        EventDispatcher.get().fireEvent(
            new ChangeCoordinate3DPointEvent(point, new Object()));

        EventDispatcher.get().fireEvent(
            new Schlegel3DComputeEvent(symmetry3dChooseEvent, point));
      }
    }
  }
}
