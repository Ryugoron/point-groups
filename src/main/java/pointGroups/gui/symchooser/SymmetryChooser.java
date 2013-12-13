package pointGroups.gui.symchooser;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.GridLayout;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;

import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTabbedPane;
import javax.swing.JTextField;

import pointGroups.geometry.Point;
import pointGroups.geometry.Point3D;
import pointGroups.gui.symchooser.elements.SubgroupEntry;
import pointGroups.gui.symchooser.elements.SymmetryEntry;


public class SymmetryChooser
  extends JTabbedPane
{

  private static final long serialVersionUID = -4774655588276858307L;
  private final JTextField x, y, z;

  public SymmetryChooser() {
    super();

    Dimension dim = new Dimension(250, 0);
    setMinimumSize(dim);
    setPreferredSize(dim);

    JButton button1 = new JButton("Generate");
    JPanel panel1 = new JPanel();
    panel1.setLayout(new BorderLayout());

    SymmetryInfoProvider prov2 = new SymmetryInfoProvider();
    // List symmetries
    JPanel symmetryListPanel = new JPanel();
    symmetryListPanel.setLayout(new GridLayout(prov2.getSymmetryInfo(
        Point3D.class).size(), 1));
    for (SymmetryInfo<? extends Point> info : prov2.getSymmetryInfo(Point3D.class)) {
      SymmetryEntry e = new SymmetryEntry(info);
      e.addMouseListener(this);
      symmetryListPanel.add(e);
    }
    panel1.add(symmetryListPanel, BorderLayout.NORTH);
    // Input

    JPanel inputPanel = new JPanel();
    inputPanel.setLayout(new BorderLayout());

    JLabel inputlbl = new JLabel("Input coordinates");

    JPanel coords = new JPanel();
    coords.setLayout(new GridLayout(1, 6));
    JLabel xlbl = new JLabel("X:");
    x = new JTextField();
    JLabel ylbl = new JLabel("Y:");
    y = new JTextField();
    JLabel zlbl = new JLabel("Z:");
    z = new JTextField();

    coords.add(xlbl);
    coords.add(x);
    coords.add(ylbl);
    coords.add(y);
    coords.add(zlbl);
    coords.add(z);

    inputPanel.add(inputlbl, BorderLayout.CENTER);
    inputPanel.add(coords, BorderLayout.SOUTH);
    panel1.add(inputPanel, BorderLayout.CENTER);
    panel1.add(button1, BorderLayout.PAGE_END);

    JButton button2 = new JButton("Generate2");
    JPanel panel2 = new JPanel();
    panel2.setLayout(new BorderLayout());
    panel2.add(button2, BorderLayout.PAGE_END);

    add("3D Groups", panel1);
    add("4D Groups", panel2);
  }
  
  
  class InputListener implements MouseListener  {
    private SubgroupInfo s;
    
    public void setSubgroupEntry(SubgroupInfo s) {
      this.s = s;
    }
    @Override
    public void mouseClicked(MouseEvent e) {
      final double x = Double.parseDouble(SymmetryChooser.this.x.getText());
      final double y = Double.parseDouble(SymmetryChooser.this.y.getText());
      final double z = Double.parseDouble(SymmetryChooser.this.z.getText());
      
      s.getSymmetryInfo().get().imagesByName(new Point3D(x, y, z), s.get().toString());
      
    }

    @Override
    public void mousePressed(MouseEvent e) {
      // TODO Auto-generated method stub
      
    }

    @Override
    public void mouseReleased(MouseEvent e) {
      // TODO Auto-generated method stub
      
    }

    @Override
    public void mouseEntered(MouseEvent e) {
      // TODO Auto-generated method stub
      
    }

    @Override
    public void mouseExited(MouseEvent e) {
      // TODO Auto-generated method stub
      
    }
  }
  }

}
