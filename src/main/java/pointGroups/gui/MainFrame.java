package pointGroups.gui;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Component;
import java.awt.Dimension;

import javax.swing.BorderFactory;
import javax.swing.JFrame;
import javax.swing.JMenuBar;
import javax.swing.JPanel;
import javax.swing.JSplitPane;
import javax.swing.SwingUtilities;
import javax.swing.UIManager;

import pointGroups.gui.event.EventDispatcher;
import pointGroups.gui.symchooser.SymmetryChooser;


public class MainFrame
  extends JFrame
{

  private static final long serialVersionUID = 1886397100814345247L;

  protected JMenuBar menuBar;
  protected JPanel schlegelView = new SchlegelView();
  protected JPanel pointPicker = new PointPicker();
  protected JPanel symmetryChooser;
  protected JPanel coordinates;
  protected JPanel statusBar;

  protected EventDispatcher dispatcher = EventDispatcher.get();

  public MainFrame() {
    // setting up main split pane

    Component leftPanel = setUpLeftPanel();
    Component rightPanel = setUpRightPanel();

    // ensures to drag the divider all the way to both sides
    Dimension minimumSize = new Dimension(0, 0);
    leftPanel.setMinimumSize(minimumSize);
    rightPanel.setMinimumSize(minimumSize);

    JSplitPane mainSplitPane =
        new JSplitPane(JSplitPane.HORIZONTAL_SPLIT, leftPanel, rightPanel);
    mainSplitPane.setResizeWeight(0);
    mainSplitPane.setDividerLocation(320);
    mainSplitPane.setOneTouchExpandable(true);
    mainSplitPane.setBorder(BorderFactory.createEmptyBorder());

    menuBar = new Menubar(dispatcher);
    statusBar = new StatusBar(dispatcher);

    add(menuBar, BorderLayout.NORTH);
    add(mainSplitPane, BorderLayout.CENTER);
    add(statusBar, BorderLayout.SOUTH);

    setTitle("Point groups");
    setSize(1000, 800);
    setLocationRelativeTo(null); // center window
    setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    setVisible(true);
  }

  private JPanel setUpRightPanel() {
    schlegelView.setBackground(Color.CYAN);
    return schlegelView;
  }

  private Component setUpLeftPanel() {

    symmetryChooser = new SymmetryChooser();
    pointPicker.setBackground(Color.YELLOW);
    coordinates = new CoordinateView(3, dispatcher);

    JSplitPane leftTopComponent =
        new JSplitPane(JSplitPane.VERTICAL_SPLIT, symmetryChooser, pointPicker);
    JSplitPane leftComponent =
        new JSplitPane(JSplitPane.VERTICAL_SPLIT, leftTopComponent, coordinates);
    leftComponent.setBorder(BorderFactory.createEmptyBorder());
    leftTopComponent.setBorder(BorderFactory.createEmptyBorder());
    leftComponent.setOneTouchExpandable(true);
    leftTopComponent.setOneTouchExpandable(true);

    leftComponent.setDividerLocation(600);
    leftTopComponent.setDividerLocation(500);

    return leftComponent;
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
