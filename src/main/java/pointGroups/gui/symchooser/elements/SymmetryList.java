package pointGroups.gui.symchooser.elements;

import java.awt.event.MouseEvent;

import javax.swing.JList;
import javax.swing.ListModel;

import pointGroups.geometry.Point;
import pointGroups.geometry.Symmetry;
import pointGroups.gui.symchooser.SubgroupPanel;
import pointGroups.util.gui.MouseAdapter;


public class SymmetryList<P extends Point>
  extends JList<Symmetry<P, ?>>
{

  private static final long serialVersionUID = -8883294098626183764L;

  public SymmetryList(final ListModel<Symmetry<P, ?>> symmetries3d,
      final SubgroupPanel subgroupPanel) {
    super(symmetries3d);

    this.addMouseListener(new MouseAdapter() {
      @Override
      public void mouseClicked(final MouseEvent e) {
        final SymmetryList<P> list = SymmetryList.this;
        final int index = list.locationToIndex(e.getPoint());
        list.setSelectedIndex(index);
        subgroupPanel.choose(list.getSelectedValue());
      }
    });
  }

}
