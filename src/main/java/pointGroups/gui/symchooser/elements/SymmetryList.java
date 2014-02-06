package pointGroups.gui.symchooser.elements;

import java.awt.Color;
import java.awt.event.MouseEvent;

import javax.swing.JList;
import javax.swing.ListModel;

import pointGroups.geometry.Point;
import pointGroups.geometry.Point3D;
import pointGroups.geometry.Point4D;
import pointGroups.geometry.Symmetry;
import pointGroups.gui.event.EventDispatcher;
import pointGroups.gui.event.types.Symmetry3DChooseEvent;
import pointGroups.gui.event.types.Symmetry4DChooseEvent;
import pointGroups.gui.symchooser.SubgroupPanel;
import pointGroups.util.gui.MouseAdapter;


public class SymmetryList<P extends Point>
  extends JList<Symmetry<P, ?>>
{

  private static final long serialVersionUID = -8883294098626183764L;

  public SymmetryList(final ListModel<Symmetry<P, ?>> symmetries3d,
      final SubgroupPanel subgroupPanel) {
    super(symmetries3d);

    this.setCellRenderer(new SymmetryListCellRenderer());

    setSelectionBackground(Color.BLUE);
    setBackground(Color.WHITE);

    this.addMouseListener(new MouseAdapter() {
      @Override
      public void mouseClicked(final MouseEvent e) {
        final SymmetryList<P> list = SymmetryList.this;
        final int index = list.locationToIndex(e.getPoint());
        list.setSelectedIndex(index);
        // subgroupPanel.choose(list.getSelectedValue());
        Class<?> type = list.getSelectedValue().getType();
        if (type == Point3D.class) {
          EventDispatcher.get().fireEvent(
              new Symmetry3DChooseEvent(
                  (Symmetry<Point3D, ?>) list.getSelectedValue(), "Full"));
        }
        else if (type == Point4D.class) {
          EventDispatcher.get().fireEvent(
              new Symmetry4DChooseEvent(
                  (Symmetry<Point4D, ?>) list.getSelectedValue(), "Full"));
        }
      }
    });
  }
}
