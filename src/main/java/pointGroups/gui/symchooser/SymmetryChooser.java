package pointGroups.gui.symchooser;

import java.awt.BorderLayout;
import java.awt.Dimension;

import javax.swing.JPanel;
import javax.swing.JSeparator;
import javax.swing.SwingConstants;


public class SymmetryChooser
  extends JPanel
{

  private static final long serialVersionUID = -4774655588276858307L;

  public SymmetryChooser() {
    super();

    setLayout(new BorderLayout());

    final SubgroupPanel subgroupChoose = new SubgroupPanel();
    final SymmetryPanel symChoose = new SymmetryPanel(subgroupChoose);
    final JSeparator separator = new JSeparator(SwingConstants.VERTICAL);

    Dimension d = separator.getPreferredSize();
    d.height = symChoose.getPreferredSize().height;
    separator.setPreferredSize(d);

    this.add(symChoose, BorderLayout.WEST);
    this.add(separator);
    this.add(subgroupChoose, BorderLayout.EAST);
  }
}
