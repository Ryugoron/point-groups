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


public class Frame
  extends JFrame
{
  protected JMenuBar menuBar = new JMenuBar();
  protected JPanel schlegelView = new JPanel();
  protected JPanel pointPicker = new JPanel();
  protected JPanel symmetryChooser = new JPanel();
  protected JPanel coordinates = new JPanel();
  protected JPanel statusBar = new JPanel();

  /**
   * 
   */
  private static final long serialVersionUID = 1364440148259443158L;

  public Frame() {

    // setting up main split pane
    
    Component leftPanel = setUpLeftPanel();
    Component rightPanel = setUpRightPanel();    
    
    // ensures to drag the divider all the way to both sides
    Dimension minimumSize = new Dimension(0,0);
    leftPanel.setMinimumSize(minimumSize);
    rightPanel.setMinimumSize(minimumSize);
    
    JSplitPane mainSplitPane =
        new JSplitPane(JSplitPane.HORIZONTAL_SPLIT, leftPanel, rightPanel);
    mainSplitPane.setResizeWeight(0);
    mainSplitPane.setDividerLocation(250);
    mainSplitPane.setOneTouchExpandable(true);
    mainSplitPane.setBorder(BorderFactory.createEmptyBorder());
    
    
    add(menuBar);
    add(mainSplitPane, BorderLayout.CENTER);
    add(statusBar, BorderLayout.SOUTH);
    setTitle("Point groups");
    setSize(1000, 650);
    setLocationRelativeTo(null); // center window
    setVisible(true);
  }

  private JPanel setUpRightPanel() {
    schlegelView.setBackground(Color.CYAN);
    return schlegelView;
  }

  private Component setUpLeftPanel() {
    
    symmetryChooser.setBackground(Color.RED);
    pointPicker.setBackground(Color.YELLOW);
    coordinates.setBackground(Color.GREEN);
    
    JSplitPane leftTopComponent = new JSplitPane(JSplitPane.VERTICAL_SPLIT, symmetryChooser, pointPicker);
    JSplitPane leftComponent = new JSplitPane(JSplitPane.VERTICAL_SPLIT, leftTopComponent, coordinates);
    leftComponent.setBorder(BorderFactory.createEmptyBorder());
    leftTopComponent.setBorder(BorderFactory.createEmptyBorder());
    leftTopComponent.setOneTouchExpandable(true);
    leftComponent.setOneTouchExpandable(true);
    
    leftComponent.setDividerLocation(550);
    leftTopComponent.setDividerLocation(300);
    
    
    return leftComponent;
  }

  /**
   * @param args
   */
  public static void main(String[] args) {
    // TODO Auto-generated method stub
    try {
      UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());

      SwingUtilities.invokeLater(new Runnable() {
        @Override
        public void run() {
          new Frame();
        }
      });
    }
    catch (Exception e) {
      e.printStackTrace();
    }
  }
  

}
