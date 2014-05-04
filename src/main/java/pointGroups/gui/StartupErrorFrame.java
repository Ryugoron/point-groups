package pointGroups.gui;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.ComponentListener;
import java.io.File;

import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.SwingConstants;

import pointGroups.util.PointerToString;


public class StartupErrorFrame
  extends JFrame implements ActionListener
{
  private static final long serialVersionUID = 4969101456999078788L;
  
  JTextField text;
  PointerToString ps;

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
  
  public StartupErrorFrame(PointerToString ps) {
    this.setLayout(new BorderLayout());
    this.ps = ps;
    this.setTitle("Error - Polymake not found");

    JPanel mainPanel = new JPanel();
    mainPanel.setLayout(
        new BoxLayout(
          mainPanel,BoxLayout.PAGE_AXIS));

    mainPanel.add(new JLabel("Please enter Polymakepath: ",
        SwingConstants.CENTER));
    mainPanel.add(
        Box.createRigidArea(new Dimension(0,5)));
    text = new JTextField();
    mainPanel.add(text);
    
    mainPanel.add(
        Box.createRigidArea(new Dimension(0,5)));
    
    JButton button = new JButton("OK");
    button.addActionListener(this);
    mainPanel.add(button);
    
    this.getContentPane().add(mainPanel);

    setSize(600, 400);
    setLocationRelativeTo(null); // center window
    setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    setVisible(true);
  }
  

  @Override
  public void actionPerformed(ActionEvent e) {
    synchronized (ps) {
      ps.s = text.getText();
      ps.notifyAll();
    }
    this.setVisible(false);

    
  }

}
