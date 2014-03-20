package pointGroups.gui;

import java.awt.BorderLayout;

import javax.swing.JPanel;

import pointGroups.geometry.Edge;
import pointGroups.geometry.Face;
import pointGroups.geometry.Point3D;
import pointGroups.geometry.Schlegel;
import pointGroups.gui.event.EventDispatcher;
import pointGroups.gui.event.types.DimensionSwitchEvent;
import pointGroups.gui.event.types.DimensionSwitchHandler;
import pointGroups.gui.event.types.SchlegelResultEvent;
import pointGroups.gui.event.types.SchlegelResultHandler;
import pointGroups.util.jreality.JRealityUtility;
import de.jreality.scene.Geometry;


public class SchlegelView
  extends JPanel
  implements SchlegelResultHandler, DimensionSwitchHandler
{
  private static final long serialVersionUID = -3642299900579728806L;

  private final EventDispatcher dispatcher = EventDispatcher.get();

  protected final UiViewer uiViewer = new UiViewer(this);
  protected Schlegel lastSchlegel;

  public SchlegelView() {
    super();

    setLayout(new BorderLayout());

    dispatcher.addHandler(SchlegelResultHandler.class, this);
    dispatcher.addHandler(DimensionSwitchHandler.class, this);
  }

  public void dispose() {
    uiViewer.dispose();
  }

  @Override
  public void onSchlegelResultEvent(SchlegelResultEvent event) {
    lastSchlegel = event.getResult();

    Point3D[] points = lastSchlegel.polytope.points;
    Edge[] edges = lastSchlegel.edges;
    Face[] faces = lastSchlegel.polytope.faces;
    Geometry geom = JRealityUtility.generateGraph(points, edges, faces);

    uiViewer.setGeometry(geom);
  }

  @Override
  public void onDimensionSwitchEvent(DimensionSwitchEvent event) {
    if (event.switchedTo3D()) {
      uiViewer.set2DMode();
    }

    if (event.switchedTo4D()) {
      uiViewer.set3DMode();
    }
  }
}
