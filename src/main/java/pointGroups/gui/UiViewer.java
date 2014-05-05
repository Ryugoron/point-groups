package pointGroups.gui;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Component;
import java.awt.Container;
import java.util.Arrays;
import java.util.concurrent.ExecutionException;

import javax.swing.SwingWorker;

import pointGroups.geometry.Point;
import pointGroups.geometry.Schlegel;
import pointGroups.geometry.Symmetry;
import pointGroups.gui.SchlegelView.SchlegelViewMode;
import pointGroups.gui.event.EventDispatcher;
import pointGroups.gui.event.types.DimensionSwitchEvent;
import pointGroups.gui.event.types.DimensionSwitchHandler;
import pointGroups.gui.event.types.SchlegelResultEvent;
import pointGroups.gui.event.types.SchlegelResultHandler;
import pointGroups.gui.event.types.gui.SchlegelViewModeChangedEvent;
import pointGroups.gui.event.types.gui.SchlegelViewModeChangedHandler;
import de.jreality.jogl.JOGLViewer;
import de.jreality.math.Pn;
import de.jreality.plugin.JRViewer;
import de.jreality.plugin.JRViewerUtility;
import de.jreality.plugin.basic.Content;
import de.jreality.scene.Appearance;
import de.jreality.scene.Camera;
import de.jreality.scene.Geometry;
import de.jreality.scene.SceneGraphComponent;
import de.jreality.scene.Transformation;
import de.jreality.scene.Viewer;
import de.jreality.scene.tool.AbstractTool;
import de.jreality.scene.tool.InputSlot;
import de.jreality.scene.tool.Tool;
import de.jreality.scene.tool.ToolContext;
import de.jreality.shader.DefaultGeometryShader;
import de.jreality.shader.DefaultLineShader;
import de.jreality.shader.DefaultPointShader;
import de.jreality.shader.DefaultPolygonShader;
import de.jreality.shader.RenderingHintsShader;
import de.jreality.shader.ShaderUtility;
import de.jreality.softviewer.SoftViewer;
import de.jreality.tools.AnimatorTool;
import de.jreality.tools.ClickWheelCameraZoomTool;
import de.jreality.tools.DraggingTool;
import de.jreality.tools.RotateTool;
import de.jreality.util.EncompassFactory;
import de.jtem.jrworkspace.plugin.Controller;
import de.jtem.jrworkspace.plugin.Plugin;


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

          view.getSceneRoot().setAppearance(appearanceRoot);

          component.setLayout(new BorderLayout());
          component.add((Component) view.getViewingComponent(),
              BorderLayout.CENTER);

          component.validate();
          component.repaint();

          onInitialized();
        };

      };

  public final UiState uiState = new UiState();
  protected double lineThicknessPercentage = 1.;

  protected final Container component;
  protected final SceneGraphComponent sceneRoot = new SceneGraphComponent();
  protected final Appearance appearanceRoot = setupAppearance(new Appearance());
  protected final UiViewerToolsPlugin toolsPlugin = new UiViewerToolsPlugin();

  /**
   * @param component Add the jReality Viewer to this Component
   */
  public UiViewer(final Container component) {
    this.component = component;
    worker.execute();

    // sceneRoot.setAppearance(appearanceRoot);
  }

  /**
   * Force the dimension according to the dimension of the state.
   */
  public void forceDimensionMode(boolean is3DMode) {
    if (!is3DMode) {
      set2DMode();
    }

    if (is3DMode) {
      set3DMode();
    }
  }

  /**
   * Lazily switch the dimension of UiViewer only if the dimension in the state
   * really changed.
   */
  public void setDimensionMode(boolean is3DMode) {
    // currently the ui is in 3D mode, but it should be in 2D mode
    if (is3DMode() && !is3DMode) {
      set2DMode();
    }

    // currently the ui is in 2D mode, but it should be in 3D mode
    if (is2DMode() && is3DMode) {
      set3DMode();
    }
  }

  private void set2DMode() {
    toolsPlugin.set2DMode();
  }

  private void set3DMode() {
    toolsPlugin.set3DMode();
  }

  private boolean is2DMode() {
    return toolsPlugin.is2DMode();
  }

  private boolean is3DMode() {
    return toolsPlugin.is3DMode();
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

    v.registerPlugin(toolsPlugin);
    v.setContent(sceneRoot);
    v.startupLocal();

    return v;
  }

  public void setLineThicknessPercentage(int percentage) {
    lineThicknessPercentage = percentage / 100.;
    setupAppearance(appearanceRoot);
  }

  private Appearance setupAppearance(Appearance ap) {
    DefaultGeometryShader dgs;
    DefaultPolygonShader dps;
    DefaultLineShader dls;
    DefaultPointShader dpts;
    RenderingHintsShader rhs;

    // RootAppearance rootAp = ShaderUtility.createRootAppearance(ap);
    // rootAp.setBackgroundColor(Color.blue);

    // ap.setAttribute(CommonAttributes.POLYGON_SHADER + "." +
    // CommonAttributes.DIFFUSE_COLOR, Color.yellow);
    //
    // ap.setAttribute(CommonAttributes.POINT_SHADER + "." +
    // CommonAttributes.DIFFUSE_COLOR, Color.red);
    //
    // ap.setAttribute(CommonAttributes.VERTEX_SHADER + "." +
    // CommonAttributes.DIFFUSE_COLOR, Color.red);
    //
    // ap.setAttribute(CommonAttributes.POINT_RADIUS, 15d);
    //
    // ap.setAttribute(POLYGON_SHADER, TwoSidePolygonShader.class);
    // ap.setAttribute(POLYGON_SHADER + ".front." + DIFFUSE_COLOR, new Color(0,
    // 204, 204));
    // ap.setAttribute(POLYGON_SHADER + ".back." + DIFFUSE_COLOR, new Color(204,
    // 204, 0));
    // ap.setAttribute(LINE_SHADER + "." + DIFFUSE_COLOR, WHITE);
    // ap.setAttribute(POINT_SHADER + "." + DIFFUSE_COLOR, RED);
    // ap.setAttribute(POINT_SHADER + "." + POINT_RADIUS, 2 *
    // POINT_RADIUS_DEFAULT);

    dgs = ShaderUtility.createDefaultGeometryShader(ap, true);
    dgs.setShowFaces(true);
    dgs.setShowLines(true);
    dgs.setShowPoints(true);
    dps = (DefaultPolygonShader) dgs.createPolygonShader("default");
    dps.setDiffuseColor(Color.cyan);

    dls = (DefaultLineShader) dgs.createLineShader("default");
    dls.setDiffuseColor(Color.yellow);
    dls.setLineWidth(1.0);
    dls.setTubeRadius(lineThicknessPercentage * 0.01);
    dls.setTubeDraw(true);
    dls.setLineStipple(true);
    dls.setLineLighting(true);

    dpts = (DefaultPointShader) dgs.createPointShader("default");
    dpts.setDiffuseColor(Color.red);
    dpts.setSpheresDraw(true);
    dpts.setPointSize(lineThicknessPercentage * 0.011);
    dpts.setPointRadius(lineThicknessPercentage * 0.011);

    rhs = ShaderUtility.createDefaultRenderingHintsShader(ap, true);
    rhs.setTransparencyEnabled(true);
    rhs.setOpaqueTubesAndSpheres(true);

    dps.setTransparency(.5);

    return ap;
  }


  protected class UiViewerToolsPlugin
    extends Plugin
  {
    protected Content content;
    protected transient SceneGraphComponent rootScene;

    protected boolean is3DMode = false;

    protected LeftClickDraggingTool leftClickDraggingTool;
    protected RightClickDraggingTool rightClickDraggingTool;
    protected ViewerRotateTool rotateTool;
    protected ViewerZoomTool zoomTool;
    protected ViewerEncompassTool encompassTool;
    protected ViewerResetTool resetTool;

    public UiViewerToolsPlugin() {
      resetTool = new ViewerResetTool();

      leftClickDraggingTool = new LeftClickDraggingTool();
      leftClickDraggingTool.setMoveChildren(false);

      rightClickDraggingTool = new RightClickDraggingTool();
      rightClickDraggingTool.setMoveChildren(false);

      zoomTool = new ViewerZoomTool();

      rotateTool = new ViewerRotateTool();
      rotateTool.setFixOrigin(false);
      rotateTool.setMoveChildren(false);
      rotateTool.setUpdateCenter(false);
      rotateTool.setAnimTimeMin(250.0);
      rotateTool.setAnimTimeMax(750.0);

      encompassTool = new ViewerEncompassTool();
    }

    @Override
    public void install(Controller c)
      throws Exception {
      super.install(c);

      content = JRViewerUtility.getContentPlugin(c);

      // set the currently selected dimension, which could have be set during
      // the rather long loading time of jReality
      forceDimensionMode(is3DMode());
    }

    @Override
    public void uninstall(Controller c)
      throws Exception {
      setRotateEnabled(false);
      setDragEnabled(false);
      setZoomEnabled(false);
      setEmcompassEnabled(false);
      setToolEnabled(resetTool, false);

      super.uninstall(c);
    }

    public void set3DMode() {
      is3DMode = true;

      setRotateEnabled(true);
      setDragEnabled(true);
      setZoomEnabled(true);
      setEmcompassEnabled(true);
      setToolEnabled(resetTool, true);

      resetCamera();
    }

    public void set2DMode() {
      is3DMode = false;

      setRotateEnabled(false);
      setDragEnabled(true);
      setZoomEnabled(true);
      setEmcompassEnabled(true);
      setToolEnabled(resetTool, true);

      resetCamera();
    }

    public boolean is3DMode() {
      return is3DMode;
    }

    public boolean is2DMode() {
      return !is3DMode;
    }

    public void resetCamera() {
      resetTool.reset();
    }

    public void setEmcompassEnabled(boolean enable) {
      setToolEnabled(encompassTool, enable);
    }

    public void setRotateEnabled(boolean enable) {
      setToolEnabled(rotateTool, is3DMode && enable);
    }

    public void setDragEnabled(boolean enable) {
      setToolEnabled(leftClickDraggingTool, !is3DMode && enable);
      setToolEnabled(rightClickDraggingTool, is3DMode && enable);
    }

    public void setZoomEnabled(boolean enable) {
      setToolEnabled(zoomTool, enable);
    }

    protected boolean setToolEnabled(Tool tool, boolean enable) {
      if (content != null) {
        if (enable) return content.addContentTool(tool);
        else content.removeContentTool(tool);
        return false;
      }
      return enable;
    }


    protected class ViewerResetTool
      extends AbstractTool
    {
      final InputSlot resetSlot = InputSlot.getDevice("DrawPickActivation");

      public ToolContext lastToolContext;
      public Double fieldOfView = null;

      public ViewerResetTool() {
        addCurrentSlot(resetSlot);
      }

      @Override
      public void perform(ToolContext tc) {
        setLastToolContext(tc);
        System.out.println("ResetTool: reset");
        reset();
      }

      public void setFieldOfView(ToolContext tc) {
        if (fieldOfView != null) return;

        Camera cam = (Camera) tc.getViewer().getCameraPath().getLastElement();
        fieldOfView = cam.getFieldOfView();
        System.out.println("Set Field of View: " + fieldOfView);
      }

      public void setLastToolContext(ToolContext tc) {
        lastToolContext = tc;
      }

      public void reset() {
        stopAnimation();
        resetTransformation();
        resetZoom();

        if (lastToolContext != null) {
          lastToolContext.getViewer().renderAsync();
        }
      }

      public boolean resetZoom() {
        if (lastToolContext == null || fieldOfView == null) return false;
        ToolContext tc = lastToolContext;

        Camera cam = (Camera) tc.getViewer().getCameraPath().getLastElement();
        cam.setFieldOfView(fieldOfView);

        fieldOfView = null;

        return true;
      }

      public boolean resetTransformation() {
        if (rootScene == null) return false;
        rootScene.setTransformation(new Transformation());
        return true;
      }

      public boolean stopAnimation() {
        if (lastToolContext == null || rootScene == null) return false;

        // stop the rotation animation
        AnimatorTool.getInstance(lastToolContext).deschedule(rootScene);
        return true;
      }
    }


    protected class ViewerZoomTool
      extends ClickWheelCameraZoomTool
    {
      @Override
      public void activate(ToolContext tc) {
        resetTool.setFieldOfView(tc);

        super.activate(tc);

        resetTool.setLastToolContext(tc);
      }
    }


    protected class ViewerEncompassTool
      extends AbstractTool
    {

      double margin = 1.75; // value greater than one creates a margin around
                            // the encompassed object
      boolean automaticClippingPlanes = true;

      final InputSlot encompassSlot =
          InputSlot.getDevice("EncompassActivation");
      final InputSlot SHIFT = InputSlot.getDevice("Secondary");
      final InputSlot CTRL = InputSlot.getDevice("Meta");

      EncompassFactory encompassFactory = new EncompassFactory();

      public ViewerEncompassTool() {
        addCurrentSlot(encompassSlot);
      }

      @Override
      public void perform(ToolContext tc) {
        // HACK: otherwise collision with viewerapp key bindings
        if (tc.getAxisState(SHIFT).isPressed() ||
            tc.getAxisState(CTRL).isPressed()) return;

        if (tc.getAxisState(encompassSlot).isPressed()) {
          System.out.println("encompass performed");

          resetTool.setLastToolContext(tc);
          resetCamera();

          // TODO get the metric from the effective appearance of avatar path
          encompassFactory.setAvatarPath(tc.getAvatarPath());
          encompassFactory.setCameraPath(tc.getViewer().getCameraPath());
          encompassFactory.setScenePath(tc.getRootToLocal());
          encompassFactory.setMargin(margin);
          encompassFactory.setMetric(Pn.EUCLIDEAN); // TODO: other metrics?
          encompassFactory.setClippingPlanes(automaticClippingPlanes);
          encompassFactory.update();
        }
      }

      public void setMargin(double p) {
        margin = p;
      }

      public double getMargin() {
        return margin;
      }

      public boolean isSetClippingPlanes() {
        return automaticClippingPlanes;
      }

      public void setAutomaticClippingPlanes(boolean setClippingPlanes) {
        this.automaticClippingPlanes = setClippingPlanes;
      }
    }


    protected class ViewerRotateTool
      extends RotateTool
    {

      @Override
      public void activate(ToolContext tc) {
        super.activate(tc);
        rootScene = comp;
        resetTool.setLastToolContext(tc);
      }
    }


    protected class RightClickDraggingTool
      extends DraggingTool
    {
      public RightClickDraggingTool() {
        super();
        activationSlots = Arrays.asList(InputSlot.RIGHT_BUTTON);
      }

      @Override
      public void activate(ToolContext tc) {
        super.activate(tc);
        rootScene = comp;
        resetTool.setLastToolContext(tc);
      }
    }


    protected class LeftClickDraggingTool
      extends DraggingTool
    {
      public LeftClickDraggingTool() {
        super();
        activationSlots = Arrays.asList(InputSlot.LEFT_BUTTON);
      }

      @Override
      public void activate(ToolContext tc) {
        super.activate(tc);
        rootScene = comp;
        resetTool.setLastToolContext(tc);
      }
    }
  }


  public class UiState
    implements DimensionSwitchHandler, SchlegelResultHandler,
    SchlegelViewModeChangedHandler
  {
    protected final EventDispatcher dispatcher = EventDispatcher.get();

    protected Schlegel lastSchlegel;
    protected Point lastPickedPoint;
    protected Symmetry<? extends Point> lastPickedSymmetry;
    protected SchlegelViewMode lastSchlegelViewMode =
        SchlegelViewMode.VIEW_SCHLEGEL;

    protected boolean is4D = false;

    public UiState() {
      dispatcher.addHandler(DimensionSwitchHandler.class, this);
      dispatcher.addHandler(SchlegelResultHandler.class, this);
      dispatcher.addHandler(SchlegelViewModeChangedHandler.class, this);
    }

    public boolean is3DMode() {
      return !is4D;
    }

    public boolean is4DMode() {
      return is4D;
    }

    public boolean isPointPicker2DMode() {
      return is3DMode();
    }

    public boolean isPointPicker3DMode() {
      return is4DMode();
    }

    public boolean isSchlegel2DMode() {
      return is3DMode() &&
          getSchlegelViewMode() == SchlegelViewMode.VIEW_SCHLEGEL;
    }

    public boolean isSchlegel3DMode() {
      return is4DMode() || getSchlegelViewMode() == SchlegelViewMode.VIEW_3D;
    }

    public SchlegelViewMode getSchlegelViewMode() {
      return lastSchlegelViewMode;
    }

    public Schlegel getLastSchlegel() {
      return lastSchlegel;
    }

    public Point getLastPickedPoint() {
      return lastPickedPoint;
    }

    public Symmetry<? extends Point> getLastPickedSymmetry() {
      return lastPickedSymmetry;
    }

    @Override
    public void onDimensionSwitchEvent(DimensionSwitchEvent event) {
      if (event.switchedTo3D()) {
        is4D = false;
      }

      if (event.switchedTo4D()) {
        is4D = true;
      }
    }

    @Override
    public void onSchlegelResultEvent(SchlegelResultEvent event) {
      lastSchlegel = event.getResult();
      lastPickedPoint = event.getPickedPoint();
      lastPickedSymmetry = event.getPickedSymmetry();
    }

    @Override
    public void onSchlegelViewModeChanged(SchlegelViewModeChangedEvent event) {
      lastSchlegelViewMode = event.getViewMode();
    }
  }
}
