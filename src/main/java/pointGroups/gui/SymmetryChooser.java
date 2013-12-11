package pointGroups.gui;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.GridLayout;
import java.util.Collection;
import java.util.HashSet;
import java.util.Set;

import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTabbedPane;

import pointGroups.geometry.Point3D;
import pointGroups.geometry.Symmetry;
import pointGroups.geometry.symmetries.IcosahedralSymmetry;
import pointGroups.geometry.symmetries.OctahedralSymmetry;
import pointGroups.geometry.symmetries.TetrahedralSymmetry;


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

    SymmetryProvider prov = new SymmetryProvider();
    JPanel symmetryListPanel = new JPanel();
    symmetryListPanel.setLayout(new GridLayout(prov.get3DSymmetries().size(), 1));
    for (Symmetry<Point3D, ?> sym : prov.get3DSymmetries()) {
      JPanel p = new JPanel();
      p.setLayout(new BorderLayout());
      p.add(new JLabel(sym.getClass().getName()), BorderLayout.NORTH);

      JPanel p2 = new JPanel();
      p2.setLayout(new GridLayout(sym.getSubgroups().size(), 1));
      for (Symmetry.Subgroup<?> subgroup : sym.getSubgroups()) {
        p2.add(new JButton(subgroup.getName() + " (Order: " + subgroup.order() +
            ")"));
      }
      p.add(p2, BorderLayout.CENTER);
      symmetryListPanel.add(p);

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


  class SymmetryProvider
  {
    final private Set<Symmetry<Point3D, ?>> symmetries3D;

    public SymmetryProvider() {
      this.symmetries3D = new HashSet<Symmetry<Point3D, ?>>();
      this.symmetries3D.add(TetrahedralSymmetry.get());
      this.symmetries3D.add(OctahedralSymmetry.get());
      this.symmetries3D.add(IcosahedralSymmetry.get());
    }

    public Collection<Symmetry<Point3D, ?>> get3DSymmetries() {
      return this.symmetries3D;
    }
  }

  // TODO: Create class for indirection, with names, icons, bla and use this
  // instead of symmetry classes itself in provider.
}
