package pointGroups;

import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.util.logging.Logger;

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
import pointGroups.util.LoggerFactory;
import pointGroups.util.PointGroupsUtility;
import pointGroups.util.PointerToString;
import pointGroups.util.polymake.wrapper.PolymakeWrapper;


public class PointGroups
{
  static MainFrame mainframe;
  static ExternalCalculationWrapper polymakeWrapper;
  static final Logger logger = LoggerFactory.get(PointGroups.class);

  public static void main(final String[] args)
    throws IOException {
    File polyCmd;
    File driverPath = null;
    try {
      driverPath = PointGroupsUtility.getPolymakeDriverPath();
    }
    catch (FileNotFoundException e) {
      final String err =
          "Standard set-up of project was changed!\n"
              + "As a result, the polymake driver file 'pmDriver.pl' was not found. \n"
              + "Please restore pmDriver.pl at its original path. Program is terminating.";

      logger.severe(err);
      System.err.println(err);
      System.exit(1);
    }

    try {
      polyCmd = PointGroupsUtility.getPolymakePath();
    }
    catch (FileNotFoundException | NullPointerException e) {
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
      logger.info("Entered Path is:" + ps.s);

      File file = new File("./settings.ini");
      try {
        System.out.println(file.createNewFile());
        BufferedWriter output = new BufferedWriter(new FileWriter(file));
        output.write("POLYMAKEPATH = " + ps.s);
        output.close();
      }
      catch (IOException e2) {
        logger.severe("settings.ini not proper initialised");
        logger.fine(e.getStackTrace().toString());
      }
      polyCmd = PointGroupsUtility.getPolymakePath();
    }

    // No info where to find polymake
    if (polyCmd == null) {
      logger.severe("Creation of settings.ini failed, polymake cannot be found. Exiting");
      System.exit(1);
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
