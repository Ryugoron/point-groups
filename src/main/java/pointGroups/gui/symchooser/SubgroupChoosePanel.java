package pointGroups.gui.symchooser;

import java.awt.Dimension;
import java.awt.event.MouseEvent;
import java.util.Collection;

import javax.swing.DefaultListModel;
import javax.swing.JList;

import pointGroups.geometry.Point;
import pointGroups.geometry.Point3D;
import pointGroups.geometry.Point4D;
import pointGroups.geometry.Symmetry;
import pointGroups.gui.event.EventDispatcher;
import pointGroups.gui.event.types.Symmetry3DChooseEvent;
import pointGroups.gui.symchooser.elements.SubgroupEntry;
import pointGroups.util.gui.MouseClickListener;


public class SubgroupChoosePanel
  extends JList<SubgroupEntry>
{
  protected final EventDispatcher dispatcher = EventDispatcher.get();

  public SubgroupChoosePanel() {
    super(new DefaultListModel<SubgroupEntry>());

    Dimension dim = new Dimension(250, 0);
    setMinimumSize(dim);
    setPreferredSize(dim);

    addMouseListener(new MouseClickListener() {
      @Override
      @SuppressWarnings("unchecked")
      public void mouseClicked(final MouseEvent e) {
        final JList<SubgroupEntry> list = SubgroupChoosePanel.this;
        final int index = list.locationToIndex(e.getPoint());
        list.setSelectedIndex(index);
        System.out.println("Symmetry selected: " +
            list.getSelectedValue().getInfo().getName());
        Class<?> type =
            list.getSelectedValue().getInfo().getSymmetryInfo().getType();
        if (type == Point3D.class) {
          dispatcher.fireEvent(new Symmetry3DChooseEvent(
              (Symmetry<Point3D, ?>) list.getSelectedValue().getInfo().getSymmetryInfo().get(),
              list.getSelectedValue().getInfo().getName()));
        }
        else if (type == Point4D.class) {

        }

        // event
      }
    });
  }

  public <P extends Point> void choose(SymmetryInfo<P> symInfo) {
    Collection<SubgroupInfo<P>> c = symInfo.getSubgroupInfo();

    DefaultListModel<SubgroupEntry> neu = new DefaultListModel<>();

    for (SubgroupInfo<P> subgroupInfo : c) {
      neu.addElement(new SubgroupEntry(subgroupInfo));
    }

    this.setModel(neu);

  }
}
