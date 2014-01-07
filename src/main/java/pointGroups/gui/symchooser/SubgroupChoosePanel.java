package pointGroups.gui.symchooser;

import java.awt.Dimension;
import java.util.Collection;

import javax.swing.DefaultListModel;
import javax.swing.JList;

import pointGroups.geometry.Point;
import pointGroups.gui.symchooser.elements.SubgroupEntry;


public class SubgroupChoosePanel
  extends JList<SubgroupEntry>
{

  public SubgroupChoosePanel() {
    super(new DefaultListModel<SubgroupEntry>());

    Dimension dim = new Dimension(250, 0);
    setMinimumSize(dim);
    setPreferredSize(dim);
  }

  public <P extends Point> void choose(SymmetryInfo<P> symInfo) {
    Collection<SubgroupInfo<P>> c = symInfo.getSubgroupInfo();

    DefaultListModel<SubgroupEntry> neu = new DefaultListModel<>();

    for (SubgroupInfo<P> subgroupInfo : c) {
      neu.addElement(new SubgroupEntry(subgroupInfo));
    }

    this.setModel(neu);

  }
}
