package pointGroups.gui;

import java.awt.BorderLayout;
import java.util.logging.Logger;

import javax.swing.JButton;
import javax.swing.JPanel;

import pointGroups.geometry.Fundamental;
import pointGroups.gui.event.EventDispatcher;
import pointGroups.gui.event.types.ChangeCoordinateEvent;
import pointGroups.gui.event.types.ChangeCoordinateHandler;
import pointGroups.gui.event.types.DimensionSwitchEvent;
import pointGroups.gui.event.types.DimensionSwitchHandler;
import pointGroups.gui.event.types.FundamentalResultEvent;
import pointGroups.gui.event.types.FundamentalResultHandler;
import pointGroups.util.jreality.JRealityUtility;
import de.jreality.geometry.Primitives;
import de.jreality.scene.Geometry;


public class PointPicker
  extends JPanel
  implements FundamentalResultHandler, DimensionSwitchHandler,
  ChangeCoordinateHandler
{

  private static final long serialVersionUID = -3642299900579728806L;

  public static int count = 0;
  public final int id = count++;
  // TODO is it a good idea to name the logger this way?
  final protected Logger logger = Logger.getLogger(this.getClass().getName() +
      "(id: " + id + ")");

  protected final UiViewer uiViewer = new UiViewer(this);

  // The current Fundamental Domain
  protected Fundamental fundamental;
  protected boolean isSet;

  protected EventDispatcher dispatcher;

  protected int dim;

  public PointPicker() {
    super();

    setLayout(new BorderLayout());

    JButton button3 = new JButton("VIEW");
    PointPicker.this.add(button3, BorderLayout.PAGE_END);

    this.isSet = false;
    this.dim = 0;
    this.dispatcher = EventDispatcher.get();

    // Register PointPicker on Events
    this.dispatcher.addHandler(FundamentalResultEvent.TYPE, this);
    this.dispatcher.addHandler(DimensionSwitchEvent.TYPE, this);
    this.dispatcher.addHandler(ChangeCoordinateEvent.TYPE, this);

    // Test initial
    uiViewer.setGeometry(JRealityUtility.generateCompleteGraph(new double[][] {
        new double[] { 0, 0 }, new double[] { 1, 0 }, new double[] { 0, 1 } }));
    // uiViewer.setGeometry(Primitives.cylinder(15));
    // uiViewer.setGeometry(Primitives.point(new double[]{0.5,0.5}));
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

  @Override
  public void onChangeCoordinateEvent(ChangeCoordinateEvent event) {
    // Calculate if the point is in the Fundamental Domain and
    // show it, if possible

    // For start test just show the point
    uiViewer.setGeometry(Primitives.point(new double[] { 0.5, 0.5 }));

    return;
  }

  // Method to fire coordinate Changed Event, should be executed by click
  // inside the fundamental domain.
  protected void selectPoint(double[] point) {
    // Maybe the view is translated or smt
    // point = Rotate * point - translation

    // Recalculate to Unitsphere on dim+1 dimensions
    double[] p = this.fundamental.revertPoint(point);
    logger.info("Point Picker got the new point (" + point[0] + "," + point[1] +
        "," + point[2] + (this.dim == 3 ? point[2] : "") + ")");
    logger.info("Point Picker calculated Point (" + point[0] + "," + point[1] +
        "," + point[2] + "," + point[3] + "," +
        (this.dim == 3 ? point[4] : "") + ")");

    // Fire Event, that the coordinate changed
    this.dispatcher.fireEvent(new ChangeCoordinateEvent(p));
  }

  protected void showFundamental() {
    if (!isSet || (this.dim != 2 && this.dim != 3)) {
      logger.info("Point was picked with no useable Fundamental Region.");
      return;
    }

    Geometry g;
    // Calculate the new fundamental
    if (this.fundamental.isKnown()) {
      g = JRealityUtility.generateCompleteGraph(this.fundamental.getVertices());
    }
    else {
      if (this.dim == 2)
      // This should rather be a cylinder with no depth
      g = Primitives.sphere(20);
      else {
        g = Primitives.sphere(20);
      }
    }
    // Reset tools (3D rotation, 2D no Rotation)
    logger.fine("A new Fundamental Region is shown.");
    uiViewer.setGeometry(g);
  }

}
