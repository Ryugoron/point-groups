package pointGroups.gui;

import java.awt.BorderLayout;
import java.awt.Component;
import java.awt.Container;
import java.util.concurrent.Future;

import javax.swing.SwingWorker;

import de.jreality.geometry.Primitives;
import de.jreality.plugin.JRViewer;
import de.jreality.scene.SceneGraphComponent;
import de.jreality.scene.Viewer;


public class UiViewer
{

  protected static JRViewer initJrViewer() {
    SceneGraphComponent world = new SceneGraphComponent();
    world.setGeometry(Primitives.sharedIcosahedron);

    JRViewer jrViewer = JRViewer.createJRViewer(world);
    jrViewer.startupLocal();
    return jrViewer;
  }

  public static Future<JRViewer> viewerFactory(final Container component) {
    SwingWorker<JRViewer, Void> worker = new SwingWorker<JRViewer, Void>() {
      protected JRViewer viewer;

      /**
       * compute on the Swing Worker thread
       */
      @Override
      protected JRViewer doInBackground()
        throws Exception {
        return viewer = initJrViewer();
      }

      /**
       * add swing elements to the panel, executed on the Event Dispatch Thread
       */
      @Override
      protected void done() {
        if (viewer == null) return;

        Viewer view = viewer.getViewer();

        component.add((Component) view.getViewingComponent(),
            BorderLayout.CENTER);
        component.validate();
        component.repaint();
      };

    };
    worker.execute();
    return worker;
  }

  public static void dispose(final Future<JRViewer> viewer) {
    try {
      JRViewer v = viewer.get();
      v.dispose();
    }
    catch (Exception ignore) {
      ignore.printStackTrace();
    }
  }
}
