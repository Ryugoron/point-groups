package pointGroups.gui;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;

import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JSlider;
import javax.swing.JToggleButton;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;

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

  protected String VIEW_TOGGLE_MODE_SCHLEGEL_TXT = "View as Polyhedron";
  protected String VIEW_TOGGLE_MODE_3D_TXT = "View as Schlegel";

  protected JPanel buttonGroup = new JPanel();
  protected JPanel schlegelRenderer = new JPanel();
  protected JToggleButton viewModeButton = new JToggleButton(
      VIEW_TOGGLE_MODE_SCHLEGEL_TXT);

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
    dispatcher.addHandler(SchlegelViewModeChangedHandler.class, this);
    dispatcher.addHandler(DimensionSwitchHandler.class, this);
  }

  protected JSlider thicknessSlider;

  protected void initViewButton() {
    viewModeButton.addItemListener(new ViewModeButtonListener());

    buttonGroup.setLayout(new BorderLayout());
    buttonGroup.add(viewModeButton, BorderLayout.EAST);

    thicknessSlider = new JSlider(1, 100, 100);
    thicknessSlider.setPaintLabels(false);
    thicknessSlider.setPaintTicks(false);
    thicknessSlider.setPaintTrack(true);

    thicknessSlider.addChangeListener(new ChangeListener() {

      @Override
      public void stateChanged(ChangeEvent e) {
        JSlider self = (JSlider) e.getSource();
        uiViewer.setLineThicknessPercentage(self.getValue());
      }
    });

    JPanel thicknessGroup = new JPanel();
    thicknessGroup.setLayout(new FlowLayout(FlowLayout.LEFT));
    thicknessGroup.add(new JLabel("Line thickness: "));
    thicknessGroup.add(thicknessSlider);

    buttonGroup.add(thicknessGroup);
  }

  public void dispose() {
    uiViewer.dispose();
  }

  public void redrawSchlegel() {
    Schlegel lastSchlegel = uiViewer.uiState.getLastSchlegel();

    Point3D[] points = lastSchlegel.points;
    Edge[] edges = lastSchlegel.edges;
    Face[] faces = null;

    // if there is a polytope we can always display the faces
    if (lastSchlegel.polytope != null) {
      faces = lastSchlegel.polytope.faces;
    }

    // only in view 3d mode use the original points
    if (uiViewer.uiState.getSchlegelViewMode() == SchlegelViewMode.VIEW_3D) {
      points = lastSchlegel.polytope.points;
    }

    Geometry geom = JRealityUtility.generateGraph(points, edges, faces);

    uiViewer.setGeometry(geom);
    uiViewer.setDimensionMode(uiViewer.uiState.isSchlegel3DMode());
  }

  @Override
  public void onSchlegelResultEvent(SchlegelResultEvent event) {
    redrawSchlegel();
  }

  @Override
  public void onSchlegelViewModeChanged(SchlegelViewModeChangedEvent event) {
    redrawSchlegel();
  }

  @Override
  public void onDimensionSwitchEvent(DimensionSwitchEvent event) {
    // show SchlegelViewModeButton only in 3D Mode
    viewModeButton.setVisible(event.switchedTo3D());
  }


  protected class ViewModeButtonListener
    implements ItemListener
  {

    @Override
    public void itemStateChanged(ItemEvent e) {
      SchlegelViewMode schlegelMode = SchlegelViewMode.VIEW_SCHLEGEL;
      String text = VIEW_TOGGLE_MODE_SCHLEGEL_TXT;

      if (e.getStateChange() == ItemEvent.SELECTED) {
        schlegelMode = SchlegelViewMode.VIEW_3D;
        text = VIEW_TOGGLE_MODE_3D_TXT;
      }

      ((JToggleButton) e.getSource()).setText(text);

      EventDispatcher.get().fireEvent(
          new SchlegelViewModeChangedEvent(schlegelMode));
    }
  }
}
