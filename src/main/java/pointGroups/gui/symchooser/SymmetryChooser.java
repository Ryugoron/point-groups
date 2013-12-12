package pointGroups.gui.symchooser;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.GridLayout;

import javax.swing.JButton;
import javax.swing.JPanel;
import javax.swing.JTabbedPane;

import pointGroups.geometry.Point;
import pointGroups.geometry.Point3D;
import pointGroups.gui.symchooser.elements.SymmetryEntry;


public class SymmetryChooser
  extends JTabbedPane
{

  private static final long serialVersionUID = -4774655588276858307L;

  public SymmetryChooser() {
    super();

    Dimension dim = new Dimension(250, 0);
    setMinimumSize(dim);
    setPreferredSize(dim);

    JButton button1 = new JButton("Generate");
    JPanel panel1 = new JPanel();
    panel1.setLayout(new BorderLayout());

    SymmetryInfoProvider prov2 = new SymmetryInfoProvider();

    JPanel symmetryListPanel = new JPanel();
    symmetryListPanel.setLayout(new GridLayout(prov2.getSymmetryInfo(
        Point3D.class).size(), 1));
    for (SymmetryInfo<? extends Point> info : prov2.getSymmetryInfo(Point3D.class)) {
      SymmetryEntry e = new SymmetryEntry(info);

      symmetryListPanel.add(e);
    }
    panel1.add(symmetryListPanel, BorderLayout.NORTH);
    panel1.add(button1, BorderLayout.PAGE_END);

    JButton button2 = new JButton("Generate2");
    JPanel panel2 = new JPanel();
    panel2.setLayout(new BorderLayout());
    panel2.add(button2, BorderLayout.PAGE_END);

    add("3D Groups", panel1);
    add("4D Groups", panel2);
  }

}
