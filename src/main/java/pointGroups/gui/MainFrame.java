package pointGroups.gui;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Component;
import java.awt.Dimension;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.BorderFactory;
import javax.swing.JFrame;
import javax.swing.JMenuBar;
import javax.swing.JPanel;
import javax.swing.JSplitPane;
import javax.swing.SwingUtilities;
import javax.swing.UIManager;

import pointGroups.gui.event.EventDispatcher;
import pointGroups.gui.event.EventHandler;
import pointGroups.gui.event.types.ShowCoordinateEvent;
import pointGroups.gui.event.types.ShowCoordinateHandler;
import pointGroups.gui.event.types.ShowFundamentalDomainEvent;
import pointGroups.gui.event.types.ShowFundamentalDomainHandler;
import pointGroups.gui.symchooser.SymmetryChooser;


public class MainFrame
  extends JFrame
{

  private static final long serialVersionUID = 1886397100814345247L;

  protected JMenuBar menuBar;
  protected JPanel schlegelView;
  protected JPanel pointPicker;
  protected JPanel symmetryChooser;
  protected JPanel coordinates;
  protected JPanel statusBar;
  protected JFrame log;
  
  protected JSplitPane mainSplitPane;
  protected JSplitPane leftTopComponent;
  protected JSplitPane leftComponent;
  
  protected EventDispatcher dispatcher = EventDispatcher.get();
  protected MenuBarEventHandler menuBarEventHandler = new MenuBarEventHandler();

  public MainFrame() {
    
    setTitle("Point groups");
    setSize(1000, 800);
    setLocationRelativeTo(null); // center window
    setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    
    log = new LogFrame();
    
    // setting up main split pane
    schlegelView = new SchlegelView();
    Component leftPanel = setUpLeftPanel();   
    
    // ensures to drag the divider all the way to both sides
    Dimension minimumSize = new Dimension(0, 0);
    leftPanel.setMinimumSize(minimumSize);
    schlegelView.setMinimumSize(minimumSize);
    
    mainSplitPane =
        new JSplitPane(JSplitPane.HORIZONTAL_SPLIT, leftPanel, schlegelView);
    mainSplitPane.setResizeWeight(0);
    mainSplitPane.setDividerLocation(320);
    mainSplitPane.setOneTouchExpandable(true);
    mainSplitPane.setBorder(BorderFactory.createEmptyBorder());

    menuBar = new Menubar(dispatcher);
    statusBar = new StatusBar(dispatcher);

    add(menuBar, BorderLayout.NORTH);
    add(mainSplitPane, BorderLayout.CENTER);
    add(statusBar, BorderLayout.SOUTH);
    
    setVisible(true);
  }

  private Component setUpLeftPanel() {

    symmetryChooser = new SymmetryChooser();
    pointPicker = new PointPicker(false);
    coordinates = new CoordinateView(3, 4, dispatcher);
    
    leftTopComponent = new JSplitPane(JSplitPane.VERTICAL_SPLIT, symmetryChooser, pointPicker);
    leftComponent = new JSplitPane(JSplitPane.VERTICAL_SPLIT, leftTopComponent, coordinates);

    leftComponent.setBorder(BorderFactory.createEmptyBorder());
    leftTopComponent.setBorder(BorderFactory.createEmptyBorder());
    leftComponent.setOneTouchExpandable(true);
    leftTopComponent.setOneTouchExpandable(true);

    leftComponent.setDividerLocation(600);
    leftTopComponent.setDividerLocation(500);
    
    // add EventHandler for View menubar item
    dispatcher.addHandler(ShowFundamentalDomainEvent.TYPE, menuBarEventHandler);
    dispatcher.addHandler(ShowCoordinateEvent.TYPE, menuBarEventHandler);

    return leftComponent;
  }
  
  
  /**
   * Method to hide CoordinateView and PointPickerView only!
   * @param component JSplitPane containing CoordinateView or PointPicker
   */
  private void hideView(JSplitPane component) {
    component.setLastDividerLocation(component.getDividerLocation());
    component.setDividerLocation(1.0);
  }
  
  /**
   * Method to show CoordinateView and PointPickerView only!
   * @param component JSplitPane containing CoordinateView or PointPicker
   */
  private void showView(JSplitPane component) {
    component.setDividerLocation(component.getLastDividerLocation());
  }
  
  private class MenuBarEventHandler implements ShowCoordinateHandler, ShowFundamentalDomainHandler {

    @Override
    public void onShowCoordinateEvent(ShowCoordinateEvent event) {
      if (event.getVisible())
        showView(leftComponent);
      else
        hideView(leftComponent);
    }

    @Override
    public void onShowFundamentalDomainEvent(ShowFundamentalDomainEvent event) {
      if (event.getVisible())
        showView(leftTopComponent);
      else
        hideView(leftTopComponent);
      
    }
    
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
