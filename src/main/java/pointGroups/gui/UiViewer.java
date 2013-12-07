package pointGroups.gui;

import java.awt.BorderLayout;
import java.awt.Component;
import java.awt.Container;
import java.util.concurrent.Future;

import javax.swing.SwingWorker;

import de.jreality.geometry.Primitives;
import de.jreality.plugin.JRViewer;
import de.jreality.plugin.JRViewer.ContentType;
import de.jreality.plugin.basic.Scene;
import de.jreality.plugin.content.ContentAppearance;
import de.jreality.plugin.content.ContentTools;
import de.jreality.scene.SceneGraphComponent;
import de.jreality.scene.Viewer;
import de.jreality.tools.ClickWheelCameraZoomTool;


public class UiViewer
{
  public static boolean OLD_INITIALIZE_METHOD = true;

  protected static JRViewer initJrViewer() {
    SceneGraphComponent world = new SceneGraphComponent();
    // world.setGeometry(Primitives.sharedIcosahedron);
    world.setGeometry(Primitives.box(2, 2, 2, true));
    // world.setGeometry(Primitives.box(0.5, 0.5, 0.5, true));

    JRViewer v;

    if (OLD_INITIALIZE_METHOD) {
      v = JRViewer.createJRViewer(world);
      v.registerPlugin(new ContentTools());
    }
    else {
      v = new JRViewer();
      v.addBasicUI();
      v.addVRSupport();
      v.addContentSupport(ContentType.TerrainAligned);
      v.registerPlugin(new ContentAppearance());
      v.registerPlugin(new ContentTools());
      v.setContent(world);
    }

    {
      ClickWheelCameraZoomTool zoomTool = new ClickWheelCameraZoomTool();
      Scene scene = v.getController().getPlugin(Scene.class);
      scene.getSceneRoot().addTool(zoomTool);
    }

    v.startupLocal();
    return v;
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
