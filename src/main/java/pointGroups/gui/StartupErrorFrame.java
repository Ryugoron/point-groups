package pointGroups.gui;

import java.awt.BorderLayout;

import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.SwingConstants;


public class StartupErrorFrame
  extends JFrame
{
  private static final long serialVersionUID = 4969101456999078788L;

  public StartupErrorFrame(final String polyCmd) {
    this.setTitle("Error - Polymake not found");

    this.setLayout(new BorderLayout());

    this.add(new JLabel("Cannot find polymake executable at " + polyCmd,
        SwingConstants.CENTER));

    setSize(600, 400);
    setLocationRelativeTo(null); // center window
    setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    setVisible(true);
  }

}
