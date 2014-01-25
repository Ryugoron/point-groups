package pointGroups.gui;

import java.awt.BorderLayout;
import java.util.logging.Logger;
import java.awt.Color;

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
import de.jreality.scene.Appearance;
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
  implements FundamentalResultHandler, DimensionSwitchHandler, ChangeCoordinateHandler
{

  private static final long serialVersionUID = -3642299900579728806L;

  public static int count = 0;
  public final int id = count ++;
  //TODO is it a good idea to name the logger this way?
  final protected Logger logger = Logger.getLogger(this.getClass().getName() + "(id: " + id + ")");

  protected final UiViewer uiViewer = new UiViewer(this) {
    public final SceneGraphComponent point = new SceneGraphComponent();
    public final Appearance pointAppearance = new Appearance();
    public final SceneGraphComponent fundamental = new SceneGraphComponent();

    @Override
    public void onInitialized() {
      SceneGraphComponent root = getSceneRoot();

      fundamental.setGeometry(Primitives.cylinder(15));
      point.setGeometry(Primitives.point(new double[] { 0, 0, 0 }, "Take me :)"));

      setPointAppearance(pointAppearance);
      point.setAppearance(pointAppearance);

      root.addChild(fundamental);
      root.addChild(point);

      DragEventTool dragTool = new DragEventTool();
      dragTool.addPointDragListener(new PointDragListener() {

        @Override
        public void pointDragStart(PointDragEvent e) {
          System.out.println("drag start of vertex no " + e.getIndex());
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

          for (double[] point : points) {
            System.out.printf("[%2f, %2f, %2f]\n", point[0], point[1], point[2]);
          }

          // TODO:
          // because the point scene has only one vertex, pointSet should only
          // contain one point; and therefore only the statements below should
          // be sufficient for point picking.
          double[] pickedPoint = e.getPosition();
          System.out.printf("Point picked: [%2f, %2f, %2f]\n", pickedPoint[0],
              pickedPoint[1], pickedPoint[2]);

          System.out.println("dragged");
          // TODO: either fire the point-pick event here or
        }

        @Override
        public void pointDragEnd(PointDragEvent e) {
          // the point-pick event here
          System.out.println("drag end of vertex no " + e.getIndex());
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

  public PointPicker() {
    super();

    setLayout(new BorderLayout());

    this.isSet = false;
    this.dim = 0;
    this.dispatcher = EventDispatcher.get();

    // Register PointPicker on Events
    this.dispatcher.addHandler(FundamentalResultEvent.TYPE, this);
    this.dispatcher.addHandler(DimensionSwitchEvent.TYPE, this);
    this.dispatcher.addHandler(ChangeCoordinateEvent.TYPE, this);
  }

  public void dispose() {
    uiViewer.dispose();
  }

@Override
public void onSchlegelResultEvent(FundamentalResultEvent event) {
	this.isSet = true;
	this.fundamental = event.getResult();
	//Maybe check for dimension
	this.showFundamental();
}


// Maybe i need this.
@Override
public void onDimensionSwitchEvent(DimensionSwitchEvent event) {
	if(event.switchedTo3D()){
		logger.fine("Point Picker switched to 2D Mode.");
		this.dim = 2;
	} else if (event.switchedTo4D()){
		logger.fine("Point Picker switched to 3D Mode.");
		this.dim = 3;
	} else {
		this.dim = 0;
		this.isSet = false;
	}
}

@Override
public void onChangeCoordinateEvent(ChangeCoordinateEvent event) {
	// Calculate if the point is in the Fundamental Domain and
	// show it, if possible

	// For start test just show the point
	uiViewer.setGeometry(Primitives.point(new double[]{0.5,0.5}));

	return;
}

// Method to fire coordinate Changed Event, should be executed by click inside the fundamental domain.
protected void selectPoint(double[] point){
	//Maybe the view is translated or smt
	// point = Rotate * point - translation

	// Recalculate to Unitsphere on dim+1 dimensions
	double[] p = this.fundamental.revertPoint(point);
	logger.info("Point Picker got the new point ("+point[0]+","+point[1]+","+point[2]+(this.dim == 3 ? point[2] : "")+")");
	logger.info("Point Picker calculated Point ("+point[0]+","+point[1]+","+point[2]+","+point[3]+","+(this.dim == 3 ? point[4] : "")+")");

	// Fire Event, that the coordinate changed
	this.dispatcher.fireEvent(new ChangeCoordinateEvent(p));
}

protected void showFundamental(){
	if (!isSet || (this.dim != 2 && this.dim != 3)) {
		logger.info("Point was picked with no useable Fundamental Region.");
		return;
	}

	Geometry g;
	// Calculate the new fundamental
	if (this.fundamental.isKnown()){
		g = JRealityUtility.generateCompleteGraph(this.fundamental.getVertices());
	} else {
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
