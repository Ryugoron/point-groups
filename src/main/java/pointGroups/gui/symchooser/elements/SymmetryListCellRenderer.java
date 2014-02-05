package pointGroups.gui.symchooser.elements;

import java.awt.BorderLayout;
import java.awt.Component;
import java.awt.Dimension;
import java.io.IOException;
import java.net.URL;

import javax.swing.BorderFactory;
import javax.swing.ImageIcon;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.JPanel;
import javax.swing.ListCellRenderer;
import javax.swing.SwingConstants;
import javax.swing.border.BevelBorder;

import pointGroups.geometry.Symmetry;
import pointGroups.util.PointGroupsUtility;


public class SymmetryListCellRenderer
  extends JPanel
  implements ListCellRenderer<Symmetry<?, ?>>
{
  private static final long serialVersionUID = 5300149105061974822L;

  private final JLabel icon, text;

  public SymmetryListCellRenderer() {
    super(new BorderLayout(0, 0));
    setOpaque(true);
    setBorder(BorderFactory.createBevelBorder(BevelBorder.LOWERED));
    add(icon = new JLabel(), BorderLayout.CENTER);
    add(text = new JLabel(), BorderLayout.SOUTH);
    text.setHorizontalAlignment(SwingConstants.CENTER);
    icon.setHorizontalAlignment(SwingConstants.CENTER);
    Dimension d = new Dimension(120, 150);
    setPreferredSize(d);
    setSize(d);

  }

  @Override
  public Component getListCellRendererComponent(
      final JList<? extends Symmetry<?, ?>> list, final Symmetry<?, ?> value,
      final int index, final boolean isSelected, final boolean cellHasFocus) {

    try {
      String image = value.getName() + " Icon.png";
      URL imagePath = PointGroupsUtility.getImage(image);
      icon.setIcon(new ImageIcon(imagePath));
    }
    catch (IOException e) {
      // this shouldn't happen, since the resource folder
      // should exist
      e.printStackTrace();
    }

    text.setText(value.getName());

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
