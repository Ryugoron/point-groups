package pointGroups.gui.symchooser;

import java.awt.Color;
import java.util.logging.Logger;

import javax.swing.DefaultListModel;
import javax.swing.JList;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;

import pointGroups.geometry.Point;
import pointGroups.geometry.Point3D;
import pointGroups.geometry.Point4D;
import pointGroups.geometry.Symmetry;
import pointGroups.gui.event.EventDispatcher;
import pointGroups.gui.event.types.Symmetry3DChooseEvent;
import pointGroups.gui.event.types.Symmetry4DChooseEvent;
import pointGroups.gui.symchooser.elements.SymmetryListCellRenderer;
import pointGroups.util.LoggerFactory;


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

    addListSelectionListener(new ListSelectionListener() {

      @Override
      public void valueChanged(final ListSelectionEvent e) {
        final SubgroupPanel list = SubgroupPanel.this;
        Symmetry<?> subgroup = list.getSelectedValue();
        if (subgroup != null) {
          logger.info("SubgroupPanel: Symmetry chosen: " + subgroup.coxeter());

          Class<?> type = lastChosenSymmetry.getType();
          if (type == Point3D.class) {
            dispatcher.fireEvent(new Symmetry3DChooseEvent(
                (Symmetry<Point3D>) subgroup));
          }
          else if (type == Point4D.class) {
            dispatcher.fireEvent(new Symmetry4DChooseEvent(
                (Symmetry<Point4D>) subgroup));
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
