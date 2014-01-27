package pointGroups.gui;

import java.awt.BorderLayout;
import java.awt.Color;
import java.util.ArrayList;
import java.util.Collection;
import java.util.logging.Logger;

import javax.swing.JPanel;

import pointGroups.geometry.Fundamental;
import pointGroups.geometry.Point3D;
import pointGroups.gui.event.EventDispatcher;
import pointGroups.gui.event.types.ChangeCoordinate3DPointEvent;
import pointGroups.gui.event.types.ChangeCoordinate3DPointHandler;
import pointGroups.gui.event.types.ChangeCoordinate4DPointEvent;
import pointGroups.gui.event.types.ChangeCoordinate4DPointHandler;
import pointGroups.gui.event.types.DimensionSwitchEvent;
import pointGroups.gui.event.types.DimensionSwitchHandler;
import pointGroups.gui.event.types.FundamentalResultEvent;
import pointGroups.gui.event.types.FundamentalResultHandler;
import pointGroups.util.LoggerFactory;
import pointGroups.util.jreality.JRealityUtility;
import pointGroups.util.polymake.FundamentalTransformer;
import de.jreality.geometry.Primitives;
import de.jreality.scene.Appearance;
import de.jreality.scene.Geometry;
import de.jreality.scene.PointSet;
import de.jreality.scene.SceneGraphComponent;
import de.jreality.scene.data.Attribute;
import de.jreality.scene.data.StorageModel;
import de.jreality.shader.DefaultGeometryShader;
import de.jreality.shader.DefaultPointShader;
import de.jreality.shader.ShaderUtility;
import de.jreality.tools.DragEventTool;
import de.jreality.tools.PointDragEvent;
import de.jreality.tools.PointDragListener;


public class PointPicker
  extends JPanel
  implements FundamentalResultHandler, DimensionSwitchHandler,
  ChangeCoordinate3DPointHandler, ChangeCoordinate4DPointHandler
{

  private static final long serialVersionUID = -3642299900579728806L;

  private final boolean responsive = false;

  final protected Logger logger = LoggerFactory.getSingle(PointPicker.class);

  protected final UiViewer uiViewer = new UiViewer(this) {
    public final SceneGraphComponent point = new SceneGraphComponent();
    public final Appearance pointAppearance = new Appearance();
    public final SceneGraphComponent fundamental = new SceneGraphComponent();

    public void testShow() {
      Collection<Point3D> points = new ArrayList<Point3D>();
      points.add(new Point3D(1, 1, 1));
      points.add(new Point3D(1, 1, -1));
      points.add(new Point3D(1, -1, 1));
      points.add(new Point3D(-1, 1, 1));
      points.add(new Point3D(1, -1, -1));
      points.add(new Point3D(-1, 1, -1));
      points.add(new Point3D(-1, -1, 1));
      points.add(new Point3D(-1, -1, -1));
      FundamentalTransformer fT = new FundamentalTransformer(points);
      fT.setResultString("1 0 0 0\n&\n1 1 0 0\n1 0 0 1\n1 0 1 0\n1 0 0 0\n");
      dispatcher.fireEvent(new FundamentalResultEvent(
          fT.transformResultString()));
    }

    @Override
    public void onInitialized() {
      SceneGraphComponent root = getSceneRoot();

      // Test for Fundamental
      testShow();

      // fundamental.setGeometry(Primitives.cylinder(15));
      point.setGeometry(Primitives.point(new double[] { 0, 0, 0 }, "Take me :)"));

      setPointAppearance(pointAppearance);
      point.setAppearance(pointAppearance);

      root.addChild(fundamental);
      root.addChild(point);

      DragEventTool dragTool = new DragEventTool();
      dragTool.addPointDragListener(new PointDragListener() {

        @Override
        public void pointDragStart(PointDragEvent e) {
          logger.finest("drag start of vertex no " + e.getIndex());
        }

        @Override
        public void pointDragged(PointDragEvent e) {
          PointSet pointSet = e.getPointSet();

          double[][] points = new double[pointSet.getNumPoints()][];

          pointSet.getVertexAttributes(Attribute.COORDINATES).toDoubleArrayArray(
              points);
          points[e.getIndex()] = e.getPosition();
          pointSet.setVertexAttributes(Attribute.COORDINATES,
              StorageModel.DOUBLE_ARRAY.array(3).createReadOnly(points));

          /*
           * for (double[] point : points) {
           * System.out.printf("[%2f, %2f, %2f]\n", point[0], point[1],
           * point[2]); }
           */
          // TODO:
          // because the point scene has only one vertex, pointSet should only
          // contain one point; and therefore only the statements below should
          // be sufficient for point picking.
          double[] pickedPoint = e.getPosition();
          if (responsive) selectPoint(pickedPoint);
        }

        @Override
        public void pointDragEnd(PointDragEvent e) {
          logger.finest("drag end of vertex no " + e.getIndex());
          if (!responsive) selectPoint(e.getPosition());
        }
      });

      point.addTool(dragTool);

    }

    private void setPointAppearance(Appearance ap) {
      // make points a little bigger, than the default.
      DefaultGeometryShader dgs;
      DefaultPointShader dpts;

      dgs = ShaderUtility.createDefaultGeometryShader(ap, true);
      dgs.setShowPoints(true);

      dpts = (DefaultPointShader) dgs.createPointShader("default");
      dpts.setDiffuseColor(Color.MAGENTA);
      dpts.setSpheresDraw(true);
      dpts.setPointSize(0.1);
      dpts.setPointRadius(0.1);
    }
  };

  // The current Fundamental Domain
  protected Fundamental fundamental;
  protected boolean isSet;

  protected EventDispatcher dispatcher;

  protected int dim;

  /**
   * Creates the Point Picker, to choose a point graphically.
   * 
   * @param responsive - true => ChangeCoordinateEvent on Drag, False => Change
   *          Coordinate Event on Release
   */
  public PointPicker(boolean responsive) {
    super();

    setLayout(new BorderLayout());

    /*
     * JButton button3 = new JButton("VIEW"); PointPicker.this.add(button3,
     * BorderLayout.PAGE_END);
     */

    this.isSet = false;
    this.dim = 2;
    this.dispatcher = EventDispatcher.get();

    // Register PointPicker on Events
    this.dispatcher.addHandler(FundamentalResultEvent.TYPE, this);
    this.dispatcher.addHandler(DimensionSwitchEvent.TYPE, this);
    this.dispatcher.addHandler(ChangeCoordinate3DPointEvent.TYPE, this);
    this.dispatcher.addHandler(ChangeCoordinate4DPointEvent.TYPE, this);

  }

  public void dispose() {
    uiViewer.dispose();
  }

  @Override
  public void onSchlegelResultEvent(FundamentalResultEvent event) {
    this.isSet = true;
    this.fundamental = event.getResult();
    // Maybe check for dimension
    this.showFundamental();
  }

  // Maybe i need this.
  @Override
  public void onDimensionSwitchEvent(DimensionSwitchEvent event) {
    if (event.switchedTo3D()) {
      logger.fine("Point Picker switched to 2D Mode.");
      this.dim = 2;
    }
    else if (event.switchedTo4D()) {
      logger.fine("Point Picker switched to 3D Mode.");
      this.dim = 3;
    }
    else {
      this.dim = 0;
      this.isSet = false;
    }
  }

  // Method to fire coordinate Changed Event, should be executed by click inside
  // the fundamental domain.
  protected void selectPoint(double[] point) {
    if (!isSet || (this.dim != 2 && this.dim != 3)) {
      logger.info("Point was picked with no useable Fundamental Region.");
      return;
    }
    logger.info("Selected Point (" + point[0] + "," + point[1] +
        (this.dim == 3 ? "," + point[2] : "") + ")");
    if (!isSet) return;
    // Maybe the view is translated or smt
    // point = Rotate * point - translation

    // Recalculate to Unitsphere on dim+1 dimensions
    // Only take the first dim components
    double[] selComp = new double[this.dim];
    for (int i = 0; i < this.dim; i++)
      selComp[i] = point[i];
    double[] resP = this.fundamental.revertPoint(selComp);

    logger.info("Point Picker calculated Point (" + resP[0] + "," + resP[1] +
        "," + resP[2] + (this.dim == 3 ? "," + resP[3] : "") + ")");

    // Fire Event, that the coordinate changed
    if (dim == 2) {
      this.dispatcher.fireEvent(new ChangeCoordinate3DPointEvent(
          JRealityUtility.asPoint3D(resP), this));
    }
    else if (dim == 3) {
      this.dispatcher.fireEvent(new ChangeCoordinate4DPointEvent(
          JRealityUtility.asPoint4D(resP), this));

    }
  }

  protected void showFundamental() {
    logger.info("Showing new Fundamental Domain.");
    Geometry g;
    // Calculate the new fundamental
    if (this.fundamental.isKnown()) {
      g = JRealityUtility.generateCompleteGraph(this.fundamental.getVertices());
    }
    else {
      if (this.dim == 2) g = Primitives.sphere(20);
      else g = Primitives.sphere(20);
    }
    // Reset tools (3D rotation, 2D no Rotation)
    logger.fine("A new Fundamental Region is shown.");
    uiViewer.setGeometry(g);
  }

  @Override
  public void
      onChangeCoordinate4DPointEvent(ChangeCoordinate4DPointEvent event) {
    // Calculate if the point is in the Fundamental Domain and
    // show it, if possible

    // For start test just show the point
    // uiViewer.setGeometry(Primitives.point(new double[] { 0.5, 0.5 }));

    return;

  }

  @Override
  public void
      onChangeCoordinate3DPointEvent(ChangeCoordinate3DPointEvent event) {
    // Calculate if the point is in the Fundamental Domain and
    // show it, if possible

    // For start test just show the point
    // uiViewer.setGeometry(Primitives.point(new double[] { 0.5, 0.5 }));

    return;
  }
}
