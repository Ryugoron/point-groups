package pointGroups.gui.symchooser;

import java.awt.Dimension;
import java.awt.event.MouseEvent;
import java.util.logging.Logger;

import javax.swing.DefaultListModel;
import javax.swing.JList;

import pointGroups.geometry.Point;
import pointGroups.geometry.Point3D;
import pointGroups.geometry.Point4D;
import pointGroups.geometry.Symmetry;
import pointGroups.gui.event.EventDispatcher;
import pointGroups.gui.event.types.Symmetry3DChooseEvent;
import pointGroups.gui.event.types.Symmetry4DChooseEvent;
import pointGroups.util.gui.MouseAdapter;


public class SubgroupPanel
  extends JList<Symmetry.Subgroup<?>>

{
  private final Logger logger = Logger.getLogger(this.getClass().getName());
  private Symmetry<?, ?> lastChosenSymmetry;

  protected final EventDispatcher dispatcher = EventDispatcher.get();

  public SubgroupPanel() {
    super();
    Dimension dim = new Dimension(250, 0);
    setMinimumSize(dim);
    setPreferredSize(dim);

    addMouseListener(new MouseAdapter() {
      @SuppressWarnings("unchecked")
      @Override
      public void mouseClicked(final MouseEvent e) {
        final SubgroupPanel list = SubgroupPanel.this;
        final int index = list.locationToIndex(e.getPoint());
        list.setSelectedIndex(index);
        Symmetry.Subgroup<?> subgroup = list.getSelectedValue();
        logger.info("SubgroupPanel: Symmetry chosen: " +
            lastChosenSymmetry.getName() + ", Subgroup: " + subgroup.getName());

        Class<?> type = lastChosenSymmetry.getType();
        if (type == Point3D.class) {
          dispatcher.fireEvent(new Symmetry3DChooseEvent(
              (Symmetry<Point3D, ?>) lastChosenSymmetry, subgroup.toString()));
        }
        else if (type == Point4D.class) {
          dispatcher.fireEvent(new Symmetry4DChooseEvent(
              (Symmetry<Point4D, ?>) lastChosenSymmetry, subgroup.toString()));
        }
      }
    });
  }

  private static final long serialVersionUID = 7253276154708286652L;

  public <P extends Point> void choose(final Symmetry<P, ?> symmetry) {
    this.lastChosenSymmetry = symmetry;

    DefaultListModel<Symmetry.Subgroup<?>> neu = new DefaultListModel<>();
    for (Symmetry.Subgroup<?> subgroup : symmetry.getSubgroups()) {
      neu.addElement(subgroup);
    }
    this.setModel(neu);

  }
}
