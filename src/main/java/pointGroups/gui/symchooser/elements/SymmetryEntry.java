package pointGroups.gui.symchooser.elements;

import java.awt.BorderLayout;
import java.awt.GridLayout;

import javax.swing.JLabel;
import javax.swing.JPanel;

import pointGroups.gui.symchooser.SubgroupInfo;
import pointGroups.gui.symchooser.SymmetryInfo;


public class SymmetryEntry
  extends JPanel
{
  private static final long serialVersionUID = 9127866137316472526L;
  private JPanel p2 = new JPanel();

  public SymmetryEntry(SymmetryInfo<?> info) {
    super();
    setLayout(new BorderLayout());
    add(new JLabel(info.getTitle()), BorderLayout.NORTH);

    p2.setLayout(new GridLayout(info.getSubgroups().size(), 1));
    for (SubgroupInfo subgroup : info.getSubgroupInfo()) {
      p2.add(new SubgroupEntry(subgroup));
    }
    add(p2, BorderLayout.CENTER);
  }
}
