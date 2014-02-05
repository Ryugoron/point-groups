package pointGroups.gui.symchooser.elements;

import java.awt.Component;

import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.ListCellRenderer;

import pointGroups.geometry.Symmetry;


public class SubgroupListCellRenderer
  extends JLabel
  implements ListCellRenderer<Symmetry.Subgroup<?>>
{
  private static final long serialVersionUID = 5300149105061974822L;

  public SubgroupListCellRenderer() {

    setOpaque(true);
    // Dimension d = new Dimension(120, 150);
    // setPreferredSize(d);
    // setSize(d);

  }

  @Override
  public Component getListCellRendererComponent(
      JList<? extends Symmetry.Subgroup<?>> list, Symmetry.Subgroup<?> value,
      int index, boolean isSelected, boolean cellHasFocus) {

    setText(value.getName());

    if (isSelected) {
      setBackground(list.getSelectionBackground());
      // setForeground(list.getSelectionForeground());
    }
    else {
      setBackground(list.getBackground());
      // setForeground(list.getForeground());
    }

    return this;
  }
}
