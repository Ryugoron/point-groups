package pointGroups.gui.symchooser;

import java.awt.Color;
import java.awt.event.MouseEvent;
import java.util.logging.Logger;

import javax.swing.DefaultListModel;
import javax.swing.JList;

import pointGroups.geometry.Symmetry;
import pointGroups.geometry.Point;
import pointGroups.geometry.Point3D;
import pointGroups.geometry.Point4D;
import pointGroups.gui.event.EventDispatcher;
import pointGroups.gui.event.types.Symmetry3DChooseEvent;
import pointGroups.gui.event.types.Symmetry4DChooseEvent;
import pointGroups.gui.symchooser.elements.SymmetryListCellRenderer;
import pointGroups.util.LoggerFactory;
import pointGroups.util.gui.MouseAdapter;


public class SubgroupPanel
  extends JList<Symmetry<?>>

{
  private final Logger logger = LoggerFactory.get(this.getClass());
  private Symmetry<?> lastChosenSymmetry;

  protected final EventDispatcher dispatcher = EventDispatcher.get();

  public SubgroupPanel() {
    super();
    setCellRenderer(new SymmetryListCellRenderer());

    setSelectionBackground(SymmetryChooser.SelectionBackground);
    setBackground(Color.WHITE);

    addMouseListener(new MouseAdapter() {
      @SuppressWarnings("unchecked")
      @Override
      public void mouseClicked(final MouseEvent e) {
        final SubgroupPanel list = SubgroupPanel.this;
        final int index = list.locationToIndex(e.getPoint());
        list.setSelectedIndex(index);
        Symmetry<?> subgroup = list.getSelectedValue();
        if (subgroup != null) {
          logger.info("SubgroupPanel: Symmetry chosen: " +
              lastChosenSymmetry.coxeter());

          Class<?> type = lastChosenSymmetry.getType();
          if (type == Point3D.class) {
            dispatcher.fireEvent(new Symmetry3DChooseEvent(
                (Symmetry<Point3D>) lastChosenSymmetry));
          }
          else if (type == Point4D.class) {
            dispatcher.fireEvent(new Symmetry4DChooseEvent(
                (Symmetry<Point4D>) lastChosenSymmetry));
          }
        }
      }
    });
  }

  private static final long serialVersionUID = 7253276154708286652L;

  /**
   * The previously chosen "top level" symmetry <code>symmetry</code> is used
   * for enumerating all of its subgroups in the underlying
   * {@link SubgroupPanel}. If <code>symmetry</code> is <code>null</code>, the
   * panels gets emptied.
   * 
   * @param symmetry the symmetry
   */
  public <P extends Point> void choose(final Symmetry<P> symmetry) {
    this.lastChosenSymmetry = symmetry;

    DefaultListModel<Symmetry<?>> neu = new DefaultListModel<>();
    if (symmetry != null) {
      for (Symmetry<?> subgroup : symmetry.subgroups()) {
        neu.addElement(subgroup);
      }
    }
    this.setModel(neu);

  }
}
