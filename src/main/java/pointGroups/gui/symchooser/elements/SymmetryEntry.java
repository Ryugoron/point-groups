package pointGroups.gui.symchooser.elements;

import java.awt.BorderLayout;
import java.awt.GridLayout;
import java.util.LinkedList;
import java.util.List;

import javax.swing.JLabel;
import javax.swing.JPanel;

import pointGroups.geometry.Point;
import pointGroups.gui.symchooser.SubgroupInfo;
import pointGroups.gui.symchooser.SymmetryInfo;


public class SymmetryEntry
  extends JPanel
{
  private static final long serialVersionUID = 9127866137316472526L;
  private final JPanel p2 = new JPanel();
  private final List<SubgroupEntry> sEntry;

  public <P extends Point> SymmetryEntry(SymmetryInfo<P> info) {
    super();
    setLayout(new BorderLayout());
    add(new JLabel(info.getTitle()), BorderLayout.NORTH);

    p2.setLayout(new GridLayout(info.getSubgroups().size(), 1));
    sEntry = new LinkedList<SubgroupEntry>();

    for (SubgroupInfo<P> subgroup : info.getSubgroupInfo()) {
      final SubgroupEntry s = new SubgroupEntry(subgroup);
      sEntry.add(s);
      p2.add(s);
    }
    add(p2, BorderLayout.CENTER);
  }

  // @Override
  // public void addMouseListener(MouseListener l) {
  // if (l instanceof InputListener<?>) {
  // InputListener<?> l2 = (InputListener<?>) l;
  // for (SubgroupEntry s : sEntry) {
  // l2.setSubgroupEntry(s.getInfo());
  // l2.getClass().get
  // s.addMouseListener(l2);
  // }
  // }
  // else super.addMouseListener(l);
  // }
}
