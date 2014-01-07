package pointGroups.gui.symchooser;

import java.awt.BorderLayout;
import java.awt.Dimension;

import javax.swing.JPanel;
import javax.swing.JSeparator;
import javax.swing.SwingConstants;


public class SymmetryChooser
  extends JPanel
{

  public SymmetryChooser() {
    super();
    // Dimension dim = new Dimension(250, 0);
    // setMinimumSize(dim);
    // setPreferredSize(dim);
    setLayout(new BorderLayout());

    final SubgroupChoosePanel subgroupChoose = new SubgroupChoosePanel();
    final SymmetryChoosePanel symChoose =
        new SymmetryChoosePanel(subgroupChoose);
    final JSeparator separator = new JSeparator(SwingConstants.VERTICAL);

    Dimension d = separator.getPreferredSize();
    d.height = symChoose.getPreferredSize().height;
    separator.setPreferredSize(d);

    this.add(symChoose, BorderLayout.WEST);
    this.add(separator);
    this.add(subgroupChoose, BorderLayout.EAST);
  }
}
