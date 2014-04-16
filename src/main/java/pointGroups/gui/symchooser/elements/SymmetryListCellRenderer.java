package pointGroups.gui.symchooser.elements;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Component;
import java.awt.Dimension;
import java.awt.GridLayout;

import javax.swing.BorderFactory;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.JPanel;
import javax.swing.ListCellRenderer;
import javax.swing.SwingConstants;
import javax.swing.border.BevelBorder;

import pointGroups.geometry.Symmetry;
import pointGroups.gui.symchooser.SubgroupPanel;


/**
 * This class is a {@link ListCellRenderer} that determines how the entries in
 * the {@link SubgroupPanel} and the {@link SymmetryList} are being rendered.
 * 
 * @author Alex
 */
public class SymmetryListCellRenderer
  extends JPanel
  implements ListCellRenderer<Symmetry<?>>
{
  private static final long serialVersionUID = 5300149105061974822L;

  private final JPanel inner;
  private final JLabel coxeter, schoenflies, orbifold, order;

  public SymmetryListCellRenderer() {
    super(new BorderLayout(0, 0));
    setOpaque(true);
    setBorder(BorderFactory.createBevelBorder(BevelBorder.LOWERED));
    add(inner = new JPanel(), BorderLayout.CENTER);

    inner.setLayout(new GridLayout(5, 1, 5, 5));
    inner.add(coxeter = new JLabel());
    inner.add(schoenflies = new JLabel());
    inner.add(orbifold = new JLabel());
    inner.add(new JLabel());
    inner.add(order = new JLabel());
    coxeter.setHorizontalAlignment(SwingConstants.CENTER);
    schoenflies.setHorizontalAlignment(SwingConstants.CENTER);
    orbifold.setHorizontalAlignment(SwingConstants.CENTER);
    order.setHorizontalAlignment(SwingConstants.CENTER);

    Dimension d = new Dimension(120, 100);
    setPreferredSize(d);
    setSize(d);

  }

  @Override
  public Component getListCellRendererComponent(
      final JList<? extends Symmetry<?>> list, final Symmetry<?> value,
      final int index, final boolean isSelected, final boolean cellHasFocus) {

    if (value.coxeter().length() > 0) coxeter.setText("C: " + value.coxeter());

    if (value.schoenflies().length() > 0)
      schoenflies.setText("S: " + value.schoenflies());

    if (value.orbifold().length() > 0)
      orbifold.setText("O: " + value.orbifold());
    order.setText("Order: " + value.order());

    if (isSelected) {
      setBackground(list.getSelectionBackground());
    }
    else {
      setBackground(list.getBackground());
    }

    return this;
  }

  @Override
  public void setBackground(final Color bg) {
    super.setBackground(bg);

    if (this.inner != null) this.inner.setBackground(bg);

    if (this.coxeter != null) this.coxeter.setBackground(bg);

    if (this.schoenflies != null) this.schoenflies.setBackground(bg);

    if (this.orbifold != null) this.orbifold.setBackground(bg);

    if (this.order != null) this.order.setBackground(bg);
  }
}
