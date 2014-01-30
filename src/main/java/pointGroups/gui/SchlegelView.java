package pointGroups.gui;

import java.awt.BorderLayout;

import javax.swing.JPanel;

import pointGroups.geometry.Edge;
import pointGroups.geometry.Point;
import pointGroups.geometry.Schlegel;
import pointGroups.gui.event.EventDispatcher;
import pointGroups.gui.event.types.SchlegelResultEvent;
import pointGroups.gui.event.types.SchlegelResultHandler;
import pointGroups.util.jreality.JRealityUtility;
import de.jreality.scene.Geometry;


public class SchlegelView
  extends JPanel
  implements SchlegelResultHandler
{
  private static final long serialVersionUID = -3642299900579728806L;

  private final EventDispatcher dispatcher = EventDispatcher.get();

  protected final UiViewer uiViewer = new UiViewer(this);
  protected Schlegel lastSchlegel;

  public SchlegelView() {
    super();

    setLayout(new BorderLayout());

    dispatcher.addHandler(SchlegelResultHandler.class, this);
  }

  public void dispose() {
    uiViewer.dispose();
  }

  @Override
  public void onSchlegelResultEvent(SchlegelResultEvent event) {
    lastSchlegel = event.getResult();

    Point[] points = lastSchlegel.points;
    Edge<Integer, Integer>[] edges = lastSchlegel.edgesViaIndices;
    Geometry geom = JRealityUtility.generateGraph(points, edges);

    uiViewer.setGeometry(geom);
  }
}
