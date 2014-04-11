package pointGroups.gui;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;

import javax.swing.JPanel;
import javax.swing.JToggleButton;

import pointGroups.geometry.Edge;
import pointGroups.geometry.Face;
import pointGroups.geometry.Point3D;
import pointGroups.geometry.Schlegel;
import pointGroups.gui.event.EventDispatcher;
import pointGroups.gui.event.types.DimensionSwitchEvent;
import pointGroups.gui.event.types.DimensionSwitchHandler;
import pointGroups.gui.event.types.SchlegelResultEvent;
import pointGroups.gui.event.types.SchlegelResultHandler;
import pointGroups.gui.event.types.gui.SchlegelViewModeChangedEvent;
import pointGroups.gui.event.types.gui.SchlegelViewModeChangedHandler;
import pointGroups.util.jreality.JRealityUtility;
import de.jreality.scene.Geometry;


public class SchlegelView
  extends JPanel
  implements SchlegelResultHandler, DimensionSwitchHandler,
  SchlegelViewModeChangedHandler
{

  public static enum SchlegelViewMode {
    VIEW_SCHLEGEL, VIEW_3D
  };

  private static final long serialVersionUID = -3642299900579728806L;

  private final EventDispatcher dispatcher = EventDispatcher.get();

  protected final UiViewer uiViewer;
  protected Schlegel lastSchlegel;

  protected SchlegelViewMode viewMode = SchlegelViewMode.VIEW_SCHLEGEL;

  protected JPanel buttonGroup = new JPanel();
  protected JPanel schlegelRenderer = new JPanel();
  protected JToggleButton viewModeButton = new JToggleButton("View in 3D Mode");

  protected final JPanel viewerPanel = schlegelRenderer;

  public SchlegelView() {
    super();

    setLayout(new BorderLayout());

    initViewButton();

    buttonGroup.setMinimumSize(new Dimension(0, 0));
    schlegelRenderer.setMinimumSize(new Dimension(0, 0));

    add(buttonGroup, BorderLayout.NORTH);
    add(schlegelRenderer, BorderLayout.CENTER);

    uiViewer = new UiViewer(viewerPanel);

    dispatcher.addHandler(SchlegelResultHandler.class, this);
    dispatcher.addHandler(DimensionSwitchHandler.class, this);
    dispatcher.addHandler(SchlegelViewModeChangedHandler.class, this);
  }

  protected void initViewButton() {
    viewModeButton.addItemListener(new ViewModeButtonListener());

    buttonGroup.setLayout(new BorderLayout());
    buttonGroup.add(viewModeButton, BorderLayout.EAST);
  }

  public void dispose() {
    uiViewer.dispose();
  }

  public void setViewMode(SchlegelViewMode viewMode) {
    this.viewMode = viewMode;

    redrawSchlegel();
  }

  public SchlegelViewMode getViewMode() {
    return viewMode;
  }

  public void redrawSchlegel() {
    Point3D[] points = lastSchlegel.points;
    Edge[] edges = lastSchlegel.edges;
    Face[] faces = null;

    // if there is a polytope we can always display the faces
    if (lastSchlegel.polytope != null) {
      faces = lastSchlegel.polytope.faces;
    }

    // only in view 3d mode use the original points
    if (getViewMode() == SchlegelViewMode.VIEW_3D) {
      points = lastSchlegel.polytope.points;
    }

    Geometry geom = JRealityUtility.generateGraph(points, edges, faces);

    uiViewer.setGeometry(geom);
  }

  @Override
  public void onSchlegelResultEvent(SchlegelResultEvent event) {
    lastSchlegel = event.getResult();
    redrawSchlegel();
  }

  @Override
  public void onDimensionSwitchEvent(DimensionSwitchEvent event) {
    if (event.switchedTo3D()) {
      uiViewer.set2DMode();
    }

    if (event.switchedTo4D()) {
      // we can't support the 3d mode in the 4d case
      viewMode = SchlegelViewMode.VIEW_SCHLEGEL;
      uiViewer.set3DMode();
    }
  }

  @Override
  public void onSchlegelViewModeChanged(SchlegelViewModeChangedEvent event) {
    setViewMode(event.getViewMode());
  }


  protected class ViewModeButtonListener
    implements ItemListener
  {

    @Override
    public void itemStateChanged(ItemEvent e) {
      SchlegelViewMode schlegelMode = SchlegelViewMode.VIEW_SCHLEGEL;

      if (e.getStateChange() == ItemEvent.SELECTED) {
        schlegelMode = SchlegelViewMode.VIEW_3D;
      }

      EventDispatcher.get().fireEvent(
          new SchlegelViewModeChangedEvent(schlegelMode));
    }
  }
}
