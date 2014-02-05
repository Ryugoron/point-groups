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
import pointGroups.geometry.symmetries.IcosahedralSymmetry;
import pointGroups.geometry.symmetries.OctahedralSymmetry;
import pointGroups.geometry.symmetries.TetrahedralSymmetry;
import pointGroups.gui.event.EventDispatcher;
import pointGroups.gui.event.types.DimensionSwitchEvent;
import pointGroups.gui.symchooser.elements.SymmetryList;
import pointGroups.util.LoggerFactory;


public class SymmetryPanel
  extends JTabbedPane
{
  private static final long serialVersionUID = 8455433801260981217L;
  private final Logger logger = LoggerFactory.get(this.getClass());

  protected final JList<Symmetry<Point3D, ?>> symmetries3DPanel;
  protected final JList<Symmetry<Point4D, ?>> symmetries4DPanel;
  protected final DefaultListModel<Symmetry<Point3D, ?>> symmetries3D;
  protected final DefaultListModel<Symmetry<Point4D, ?>> symmetries4D;

  protected final EventDispatcher dispatcher = EventDispatcher.get();

  public SymmetryPanel(final SubgroupPanel subgroupPanel) {
    super();

    this.symmetries3D = new DefaultListModel<>();
    this.symmetries4D = new DefaultListModel<>();
    this.symmetries3DPanel =
        new SymmetryList<Point3D>(this.symmetries3D, subgroupPanel);
    this.symmetries4DPanel =
        new SymmetryList<Point4D>(this.symmetries4D, subgroupPanel);

    this.fillPanelsWithSymmetries();

    add("3D", new JScrollPane(symmetries3DPanel,
        ScrollPaneConstants.VERTICAL_SCROLLBAR_AS_NEEDED,
        ScrollPaneConstants.HORIZONTAL_SCROLLBAR_NEVER));
    add("4D", new JScrollPane(symmetries4DPanel,
        ScrollPaneConstants.VERTICAL_SCROLLBAR_AS_NEEDED,
        ScrollPaneConstants.HORIZONTAL_SCROLLBAR_NEVER));

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
  }

  private void fillPanelsWithSymmetries() {
    // 3D Symmetries
    this.symmetries3D.addElement(TetrahedralSymmetry.get());
    this.symmetries3D.addElement(OctahedralSymmetry.get());
    this.symmetries3D.addElement(IcosahedralSymmetry.get());
    // 4D Symmetries
  }
}
