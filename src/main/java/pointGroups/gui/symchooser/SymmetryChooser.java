package pointGroups.gui.symchooser;

import java.awt.Color;
import java.awt.GridLayout;

import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JSplitPane;
import javax.swing.ScrollPaneConstants;


public class SymmetryChooser
  extends JPanel
{

  private static final long serialVersionUID = -4774655588276858307L;
  public static final Color SelectionBackground = new Color(208, 225, 248);

  public SymmetryChooser() {
    super();
    setLayout(new GridLayout(1, 2));

    final SubgroupPanel subgroupChoose = new SubgroupPanel();
    final SymmetryPanel symChoose = new SymmetryPanel(subgroupChoose);

    final JScrollPane x =
        new JScrollPane(subgroupChoose,
            ScrollPaneConstants.VERTICAL_SCROLLBAR_AS_NEEDED,
            ScrollPaneConstants.HORIZONTAL_SCROLLBAR_NEVER);

    final JSplitPane split =
        new JSplitPane(JSplitPane.HORIZONTAL_SPLIT, symChoose, x);
    this.add(split);
    split.setSize(250, 0);
    split.setOneTouchExpandable(true);
    split.setDividerLocation(0.7d);
  }
}
