package pointGroups.gui;

import java.awt.BorderLayout;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;

import javax.swing.*;


public class MainFrame
  extends JFrame
{

  private static final long serialVersionUID = 1886397100814345247L;

  protected SymmetryChooser symmetryChooser = new SymmetryChooser();
  protected SchlegelView schlegelView = new SchlegelView();
  protected PointPicker pointPicker = new PointPicker();

  public MainFrame() {
    // tabpane.addChangeListener(new ChangeListener() {
    // public void stateChanged(ChangeEvent arg0) {
    // System.out.println(arg0);
    // }
    // });

    // Layout the main window.
    setLayout(new BorderLayout(5, 5));
    add(symmetryChooser, BorderLayout.WEST);

    add(schlegelView, BorderLayout.CENTER);
    add(pointPicker, BorderLayout.EAST);

    setTitle("PolygonGen");
    setSize(1000, 650);
    setLocationRelativeTo(null); // center window

    addWindowListener(new WindowAdapter() {
      public void windowClosing(WindowEvent e) {
        System.exit(0);
      }
    });

    // Position of left upper corner and size of frame when run as
    // application.
    // this.setBounds(new Rectangle(420, 5, 640, 550));
    setVisible(true);
  }

  public static void main(String args[]) {
    try {
      UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
      new MainFrame();
    }
    catch (Exception e) {
      e.printStackTrace();
    }
  }
}
