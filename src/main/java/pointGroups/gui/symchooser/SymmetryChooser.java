package pointGroups.gui.symchooser;

import java.awt.Color;
import java.awt.GridLayout;

import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.ScrollPaneConstants;


public class SymmetryChooser
  extends JPanel
{

  private static final long serialVersionUID = -4774655588276858307L;
  protected static final Color SelectionBackground = new Color(208,225,248);

  public SymmetryChooser() {
    super();
    setLayout(new GridLayout(1, 2));

    final SubgroupPanel subgroupChoose = new SubgroupPanel();
    final SymmetryPanel symChoose = new SymmetryPanel(subgroupChoose);

    this.add(symChoose);
    this.add(new JScrollPane(subgroupChoose,
        ScrollPaneConstants.VERTICAL_SCROLLBAR_AS_NEEDED,
        ScrollPaneConstants.HORIZONTAL_SCROLLBAR_NEVER));
  }
}
