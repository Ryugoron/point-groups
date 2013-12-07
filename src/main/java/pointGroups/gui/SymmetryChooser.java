package pointGroups.gui;

import java.awt.BorderLayout;
import java.awt.Dimension;

import javax.swing.JButton;
import javax.swing.JPanel;
import javax.swing.JTabbedPane;


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
    panel1.add(button1, BorderLayout.PAGE_END);

    JButton button2 = new JButton("Generate2");
    JPanel panel2 = new JPanel();
    panel2.setLayout(new BorderLayout());
    panel2.add(button2, BorderLayout.PAGE_END);

    add("Panel 1", panel1);
    add("Panel 2", panel2);
  }
}
