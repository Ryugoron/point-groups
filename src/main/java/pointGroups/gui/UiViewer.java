package pointGroups.gui;

import java.awt.BorderLayout;
import java.awt.Component;
import java.awt.Container;
import java.util.concurrent.ExecutionException;

import javax.swing.SwingWorker;

import de.jreality.jogl.JOGLViewer;
import de.jreality.plugin.JRViewer;
import de.jreality.plugin.basic.Scene;
import de.jreality.plugin.content.ContentTools;
import de.jreality.scene.Appearance;
import de.jreality.scene.Geometry;
import de.jreality.scene.SceneGraphComponent;
import de.jreality.scene.Viewer;
import de.jreality.softviewer.SoftViewer;
import de.jreality.tools.ClickWheelCameraZoomTool;


/**
 * {@link UiViewer} starts jReality as the Backend-Render and will add it to a
 * parent Component. Due to the long startup time of jReality, {@link UiViewer}
 * will load jReality via a SwingWorker Thread, s.t. the Event Dispatch Thread
 * won't be blocked during the Startup-Process to keep Swing responsive.
 * 
 * @author Marcel Ehrhardt
 */
public class UiViewer
{

  protected final SwingWorker<JRViewer, Void> worker =
      new SwingWorker<JRViewer, Void>() {
        protected JRViewer viewer;

        /**
         * compute on the Swing Worker thread
         */
        @Override
        protected JRViewer doInBackground()
          throws Exception {
          return viewer = startupViewer();
        }

        /**
         * add swing elements to the panel, executed on the Event Dispatch
         * Thread
         */
        @Override
        protected void done() {
          if (viewer == null) return;

          Viewer view = viewer.getViewer();

          component.add((Component) view.getViewingComponent(),
              BorderLayout.CENTER);
          component.validate();
          component.repaint();

          onInitialized();
        };

      };

  protected final Container component;
  protected final SceneGraphComponent sceneRoot = new SceneGraphComponent();
  protected final Appearance appearanceRoot = new Appearance();
  protected final ContentTools contentTools = new ContentTools();

  /**
   * @param component Add the jReality Viewer to this Component
   */
  public UiViewer(final Container component) {
    this.component = component;
    worker.execute();

    sceneRoot.setAppearance(appearanceRoot);
  }

  /**
   * Returns the global scene root, which contains the geometry, sub scenes, the
   * appearance and other informations related to the rendering of a scene.
   * 
   * @return the global scene root
   */
  public SceneGraphComponent getSceneRoot() {
    return sceneRoot;
  }

  /**
   * Get the current rendered geometry.
   * 
   * @return
   */
  public Geometry getGeometry() {
    return sceneRoot.getGeometry();
  }

  /**
   * Set the current geometry.
   * 
   * @return
   */
  public void setGeometry(Geometry geometry) {
    sceneRoot.setGeometry(geometry);
  }

  /**
   * Get the asynchronous loaded JRViewer instance from UiViewer. <br>
   * <b>Note</b>: This method will block until the JRViewer was completely
   * loaded.
   * 
   * @throws RuntimeException The {@link InterruptedException} and
   *           {@link ExecutionException} thrown by {@link SwingWorker#get()}
   *           will be re-thrown as {@link RuntimeException}
   * @return the asynchronous loaded JRViewer
   */
  public JRViewer get() {
    try {
      return worker.get();
    }
    catch (Exception e) {
      throw new RuntimeException(e);
    }
  }

  /**
   * Get the {@link Viewer} of jReality. This is the Renderer that will be used
   * by jReality, i.e. the {@link SoftViewer} for software rendering or the
   * {@link JOGLViewer} for hardware rendering (OpenGL).
   * 
   * @return
   */
  public Viewer getViewer() {
    return get().getViewer();
  }

  /**
   * A callback function, that will be called after the complete creation of
   * JRViewer. Override it, if you want to set some properties after the
   * initialization. <b>Note</b>: This callback will be executed on the Event
   * Dispatch Thread, so don't do anything that will take to much time.
   */
  public void onInitialized() {
  }

  /**
   * Dispose of the jReality Viewer. <br> <b>Note</b>: Dispose will block until
   * jReality was constructed.
   */
  public void dispose() {
    JRViewer v = get();
    if (v != null) {
      v.dispose();
    }
  }

  /**
   * Initialize jReality
   * 
   * @return
   */
  protected JRViewer startupViewer() {
    JRViewer v = new JRViewer();

    // v.addBasicUI();
    // v.addVRSupport();
    // v.addContentSupport(ContentType.TerrainAligned);
    // v.registerPlugin(new ContentAppearance());
    contentTools.setRotationEnabled(true);
    contentTools.setDragEnabled(true);
    v.registerPlugin(contentTools);
    v.setContent(sceneRoot);

    {
      ClickWheelCameraZoomTool zoomTool = new ClickWheelCameraZoomTool();
      Scene scene = v.getController().getPlugin(Scene.class);
      scene.getSceneRoot().addTool(zoomTool);
    }

    v.startupLocal();
    return v;
  }
}
