package pointGroups.gui.symchooser.elements;

import java.awt.Color;

import javax.swing.JList;
import javax.swing.ListModel;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;

import pointGroups.geometry.Point;
import pointGroups.geometry.Symmetry;
import pointGroups.gui.symchooser.SubgroupPanel;


public class SymmetryList<P extends Point>
  extends JList<Symmetry<P>>
{

  private static final long serialVersionUID = -8883294098626183764L;

  public SymmetryList(final ListModel<Symmetry<P>> symmetries3d,
      final SubgroupPanel subgroupPanel) {
    super(symmetries3d);

    this.setCellRenderer(new SymmetryListCellRenderer());

    setSelectionBackground(Color.BLUE);
    setBackground(Color.WHITE);

    addListSelectionListener(new ListSelectionListener() {
      @Override
      public void valueChanged(final ListSelectionEvent e) {
        subgroupPanel.choose(SymmetryList.this.getSelectedValue());
      }
    });
  }

}
