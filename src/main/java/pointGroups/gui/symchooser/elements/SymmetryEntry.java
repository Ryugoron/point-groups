package pointGroups.gui.symchooser.elements;

import javax.swing.JButton;

import pointGroups.geometry.Point;
import pointGroups.gui.symchooser.SymmetryInfo;


public class SymmetryEntry
  extends JButton
{
  private static final long serialVersionUID = 9127866137316472526L;

  // private final JPanel p2 = new JPanel();

  // private final List<SubgroupEntry> sEntry;

  public <P extends Point> SymmetryEntry(SymmetryInfo<P> info) {
    super(info.getTitle());

    // p2.setLayout(new GridLayout(info.getSubgroups().size(), 1));
    // sEntry = new LinkedList<SubgroupEntry>();

    // for (SubgroupInfo<P> subgroup : info.getSubgroupInfo()) {
    // final SubgroupEntry s = new SubgroupEntry(subgroup);
    // sEntry.add(s);
    // p2.add(s);
    // }
    // add(p2, BorderLayout.CENTER);
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
