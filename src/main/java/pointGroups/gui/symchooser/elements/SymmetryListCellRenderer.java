package pointGroups.gui.symchooser.elements;

import java.awt.BorderLayout;
import java.awt.Component;
import java.awt.Dimension;

import javax.swing.BorderFactory;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.JPanel;
import javax.swing.ListCellRenderer;
import javax.swing.SwingConstants;
import javax.swing.border.BevelBorder;

import pointGroups.geometry.Symmetry;


public class SymmetryListCellRenderer
  extends JPanel
  implements ListCellRenderer<Symmetry<?>>
{
  private static final long serialVersionUID = 5300149105061974822L;

  private final JLabel text; // icon, text;

  public SymmetryListCellRenderer() {
    super(new BorderLayout(0, 0));
    setOpaque(true);
    setBorder(BorderFactory.createBevelBorder(BevelBorder.LOWERED));
    // add(icon = new JLabel(), BorderLayout.CENTER);
    add(text = new JLabel(), BorderLayout.CENTER);
    text.setHorizontalAlignment(SwingConstants.CENTER);
    // icon.setHorizontalAlignment(SwingConstants.CENTER);
    Dimension d = new Dimension(120, 40);
    setPreferredSize(d);
    setSize(d);

  }

  @Override
  public Component getListCellRendererComponent(
      final JList<? extends Symmetry<?>> list, final Symmetry<?> value,
      final int index, final boolean isSelected, final boolean cellHasFocus) {

    // try {
    // String image = value.getName() + " Icon.png";
    // URL imagePath = PointGroupsUtility.getImage(image);
    // icon.setIcon(new ImageIcon(imagePath));
    // }
    // catch (IOException e) {
    // // this shouldn't happen, since the resource folder
    // // should exist
    // e.printStackTrace();
    // }

    text.setText(value.coxeter() + " " + value.schoenflies() + " " +
        value.orbifold());

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
