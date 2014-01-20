package pointGroups.gui;

import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;

import javax.swing.*;

import pointGroups.gui.symchooser.SymmetryChooser;


public class MainFrame
  extends JFrame
{

  private static final long serialVersionUID = 1886397100814345247L;

  protected SymmetryChooser symmetryChooser = new SymmetryChooser();
  protected SchlegelView schlegelView = new SchlegelView();
  protected PointPicker pointPicker = new PointPicker();

  public MainFrame() {
    // Layout the main window.
    setLayout(new GridBagLayout());
    GridBagConstraints c = new GridBagConstraints();

    c.fill = GridBagConstraints.BOTH;
    c.weightx = 0;
    c.weighty = 1;
    c.gridx = 0;
    c.gridy = 0;
    add(symmetryChooser, c);

    c.weightx = 0.5;
    c.gridx = 1;
    add(schlegelView, c);

    c.gridx = 2;
    add(pointPicker, c);

    setTitle("Point groups");
    setSize(1000, 650);
    setLocationRelativeTo(null); // center window

    addWindowListener(new WindowAdapter() {
      public void windowClosing(WindowEvent e) {
        shutdown();
      }
    });

    setVisible(true);
  }

  protected void shutdown() {

    // dispose will block until it gets the resources, but if we block on the
    // current EventDispatchThread it will result in a dead-lock, because the
    // resources will also be created on the EventDispatchThread. So to break
    // this cycle, we will call the dispose functions in a own thread.
    //
    // Note:
    // This edge case should only occur in the startup phase, if we try to close
    // the window before it is completely initialized.
    new Thread(new Runnable() {

      @Override
      public void run() {
        schlegelView.dispose();
        pointPicker.dispose();
        System.exit(0);
      }
    }).start();
  }

  public static void main(String args[]) {
    try {
      UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());

      SwingUtilities.invokeLater(new Runnable() {
        @Override
        public void run() {
          new MainFrame();
        }
      });
    }
    catch (Exception e) {
      e.printStackTrace();
    }
  }
}
