package pointGroups.gui.symchooser;

import java.awt.Dimension;
import java.awt.event.MouseEvent;
import java.util.Collection;

import javax.swing.JPanel;
import javax.swing.JTabbedPane;

import pointGroups.geometry.Point3D;
import pointGroups.geometry.Point4D;
import pointGroups.gui.symchooser.elements.SymmetryEntry;
import pointGroups.util.gui.MouseClickListener;


public class SymmetryChoosePanel
  extends JTabbedPane
{

  final JPanel symmetries3DPanel, symmetries4DPanel;
  final SymmetryInfoProvider prov;
  final SubgroupChoosePanel subgroupChoosePanel;

  public SymmetryChoosePanel(SubgroupChoosePanel subgroupChoosePanel) {
    super();

    Dimension dim = new Dimension(250, 0);
    setMinimumSize(dim);
    setPreferredSize(dim);

    this.subgroupChoosePanel = subgroupChoosePanel;
    prov = new SymmetryInfoProvider();
    symmetries3DPanel = new JPanel();
    symmetries4DPanel = new JPanel();
    fillTabs();

    add("3D", symmetries3DPanel);
    add("4D", symmetries4DPanel);
  }

  private void fillTabs() {
    // Fill 3D-Tab with Top-Level-3D-Symmetries
    Collection<SymmetryInfo<Point3D>> symmetries3D = prov.get(Point3D.class);

    for (final SymmetryInfo<Point3D> symmetryInfo : symmetries3D) {
      final SymmetryEntry s = new SymmetryEntry(symmetryInfo);
      s.addMouseListener(new MouseClickListener() {

        @Override
        public void mouseClicked(MouseEvent e) {
          subgroupChoosePanel.choose(symmetryInfo);
        }
      });
      symmetries3DPanel.add(s);
    }

    // Fill 4D-Tab with Top-Level-3D-Symmetries
    Collection<SymmetryInfo<Point4D>> symmetries4D = prov.get(Point4D.class);

    for (SymmetryInfo<Point4D> symmetryInfo : symmetries4D) {
      symmetries4DPanel.add(new SymmetryEntry(symmetryInfo));
    }
  }
}
