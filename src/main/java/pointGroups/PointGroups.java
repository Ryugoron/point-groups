package pointGroups;

import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.io.File;
import java.io.IOException;
import java.util.Properties;

import javax.swing.SwingUtilities;
import javax.swing.UIManager;

import pointGroups.gui.ExternalCalculationEventHub;
import pointGroups.gui.MainFrame;
import pointGroups.gui.StartupErrorFrame;
import pointGroups.util.ExternalCalculationWrapper;
import pointGroups.util.PointGroupsUtility;
import pointGroups.util.polymake.wrapper.PolymakeWrapper;


public class PointGroups
{
  static MainFrame mainframe;
  static ExternalCalculationWrapper polymakeWrapper;

  public static void main(final String[] args) {
    String polyCmd = PointGroupsUtility.getDefaultPolymakePath();

    // Try to load properties
    try {
      Properties prop = PointGroupsUtility.getProperties();
      polyCmd = prop.getProperty("POLYMAKEPATH");
    }
    catch (IOException e) {
      System.err.println("settings.ini not found, using default path to polymake executable " +
          PointGroupsUtility.getDefaultPolymakePath());
    }

    // Check whether Polymake exists on the system
    if (!new File(polyCmd).isFile()) {
      System.err.println("Cannot find polymake executable at " + polyCmd);
      new StartupErrorFrame(polyCmd);
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
          new PolymakeWrapper(polyCmd,
              PointGroupsUtility.getPolymakeDriverPath());
      new ExternalCalculationEventHub(polymakeWrapper);
    }
  }
}
