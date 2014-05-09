package pointGroups.gui.symchooser;

import java.util.logging.Logger;

import javax.swing.DefaultListModel;
import javax.swing.JList;
import javax.swing.JScrollPane;
import javax.swing.JTabbedPane;
import javax.swing.ScrollPaneConstants;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;

import pointGroups.geometry.Point;
import pointGroups.geometry.Point3D;
import pointGroups.geometry.Point4D;
import pointGroups.geometry.Symmetry;
import pointGroups.geometry.symmetries.Symmetry3D;
import pointGroups.geometry.symmetries.Symmetry4D;
import pointGroups.geometry.symmetries.Symmetry4DReflection;
import pointGroups.gui.event.EventDispatcher;
import pointGroups.gui.event.types.DimensionSwitchEvent;
import pointGroups.gui.event.types.DimensionSwitchHandler;
import pointGroups.gui.symchooser.elements.SymmetryList;
import pointGroups.util.LoggerFactory;


public class SymmetryPanel
  extends JTabbedPane
  implements DimensionSwitchHandler
{
  private static final long serialVersionUID = 8455433801260981217L;
  private final Logger logger = LoggerFactory.get(this.getClass());

  protected final JList<Symmetry<Point3D>> symmetries3DPanel;
  protected final JList<Symmetry<Point4D>> symmetries4DPanel;
  protected final JScrollPane symmetries3DScrollPane;
  protected final JScrollPane symmetries4DScrollPane;
  protected final DefaultListModel<Symmetry<Point3D>> symmetries3D;
  protected final DefaultListModel<Symmetry<Point4D>> symmetries4D;

  protected final EventDispatcher dispatcher = EventDispatcher.get();

  public SymmetryPanel(final SubgroupPanel subgroupPanel) {
    super();

    this.symmetries3D = new DefaultListModel<>();
    this.symmetries4D = new DefaultListModel<>();
    this.symmetries3DPanel =
        new SymmetryList<Point3D>(this.symmetries3D, subgroupPanel);
    this.symmetries4DPanel =
        new SymmetryList<Point4D>(this.symmetries4D, subgroupPanel);
    this.symmetries3DPanel.setSelectionBackground(SymmetryChooser.SelectionBackground);
    this.symmetries4DPanel.setSelectionBackground(SymmetryChooser.SelectionBackground);

    this.fillPanelsWithSymmetries();

    symmetries3DScrollPane =
        new JScrollPane(symmetries3DPanel,
            ScrollPaneConstants.VERTICAL_SCROLLBAR_AS_NEEDED,
            ScrollPaneConstants.HORIZONTAL_SCROLLBAR_NEVER);

    symmetries4DScrollPane =
        new JScrollPane(symmetries4DPanel,
            ScrollPaneConstants.VERTICAL_SCROLLBAR_AS_NEEDED,
            ScrollPaneConstants.HORIZONTAL_SCROLLBAR_NEVER);

    add("3D", symmetries3DScrollPane);
    add("4D", symmetries4DScrollPane);

    addChangeListener(new ChangeListener() {
      @Override
      public void stateChanged(final ChangeEvent e) {
        JTabbedPane t = SymmetryPanel.this;
        if (e.getSource() == t) {
          logger.info("SymmetryChoosePanel: Tab switched");

          Class<? extends Point> pointType;
          switch (t.getSelectedIndex()) {
          case 0:
            pointType = Point3D.class;
            break;
          case 1:
            pointType = Point4D.class;
            break;

          default:
            pointType = null;
            logger.warning("SymmetryChoosePanel: Unknown Tab index selected");
            break;
          }
          subgroupPanel.choose(null);
          dispatcher.fireEvent(new DimensionSwitchEvent(pointType));
        }
      }
    });

    EventDispatcher.get().addHandler(DimensionSwitchHandler.class, this);
  }

  private void fillPanelsWithSymmetries() {
    // 3D Symmetries
    for (Symmetry3D sym : Symmetry3D.getSymmetries()) {
      this.symmetries3D.addElement(sym);
    }
    // 4D Symmetries for rotations
    for (Symmetry4D sym : Symmetry4D.getSymmetries()) {
      this.symmetries4D.addElement(sym);
    }
    // 4D Symmetries for reflections
    for (Symmetry4DReflection sym : Symmetry4DReflection.getSymmetries()) {
      this.symmetries4D.addElement(sym);
    }
  }

  @Override
  public void onDimensionSwitchEvent(DimensionSwitchEvent event) {
    this.symmetries3DPanel.clearSelection();
    this.symmetries4DPanel.clearSelection();

    java.awt.Point topleft = new java.awt.Point(0, 0);
    symmetries3DScrollPane.getViewport().setViewPosition(topleft);
    symmetries4DScrollPane.getViewport().setViewPosition(topleft);
  }
}
