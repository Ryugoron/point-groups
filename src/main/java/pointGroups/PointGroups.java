package pointGroups;

import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.io.File;
import java.io.IOException;

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
import pointGroups.util.polymake.wrapper.PolymakeWrapper;


public class PointGroups
{
  static MainFrame mainframe;
  static ExternalCalculationWrapper polymakeWrapper;

  public static void main(final String[] args)
    throws IOException {

    File polyCmd = PointGroupsUtility.getPolymakePath();
    File driverPath = PointGroupsUtility.getPolymakeDriverPath();

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
