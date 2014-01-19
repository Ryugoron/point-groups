package pointGroups.gui.symchooser;

import java.awt.Dimension;
import java.util.logging.Logger;

import javax.swing.JPanel;
import javax.swing.JTabbedPane;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;

import pointGroups.geometry.Point;
import pointGroups.geometry.Point3D;
import pointGroups.geometry.Point4D;
import pointGroups.gui.event.EventDispatcher;
import pointGroups.gui.event.types.DimensionSwitchEvent;


public class SymmetryPanel
  extends JTabbedPane
{
  private static final long serialVersionUID = 8455433801260981217L;
  private final Logger logger = Logger.getLogger(this.getClass().getName());
  protected final JPanel symmetries3DPanel, symmetries4DPanel;

  protected final EventDispatcher dispatcher = EventDispatcher.get();
  protected final SubgroupPanel subgroupPanel;

  public SymmetryPanel(final SubgroupPanel subgroupPanel) {
    super();
    this.subgroupPanel = subgroupPanel;

    this.symmetries3DPanel = new JPanel();
    this.symmetries4DPanel = new JPanel();

    Dimension dim = new Dimension(250, 0);
    setMinimumSize(dim);
    setPreferredSize(dim);
    // fillPanelsWithSymmetries();

    add("3D", this.symmetries3DPanel);
    add("4D", this.symmetries4DPanel);

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

          dispatcher.fireEvent(new DimensionSwitchEvent(pointType));
        }
      }
    });
  }

}
