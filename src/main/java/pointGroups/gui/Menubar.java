package pointGroups.gui;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.InputEvent;
import java.awt.event.KeyEvent;

import javax.swing.JCheckBoxMenuItem;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.KeyStroke;

import pointGroups.gui.event.EventDispatcher;
import pointGroups.gui.event.types.ShowNextEvent;
import pointGroups.gui.event.types.ShowCoordinateEvent;
import pointGroups.gui.event.types.ShowFundamentalDomainEvent;
import pointGroups.gui.event.types.ShowLogEvent;
import pointGroups.gui.event.types.TutorialEvent;
import pointGroups.gui.event.types.ShowPreviousEvent;


public class Menubar
  extends JMenuBar
{
  /**
   * 
   */
  private static final long serialVersionUID = -6870787436220684514L;

  private final EventDispatcher dispatcher;

  public final JMenu historyMenu;
  public final JMenu viewMenu;
  public final JMenu debugMenu;
  public final JMenu helpMenu;

  // Items of editMenu
  public final JMenuItem previousItem;
  public final JMenuItem nextItem;

  // Items of debugMenu
  public final JMenuItem showLogItem;

  // Items of helpMenu
  public final JMenuItem tutorialItem;

  // Items of viewMenu
  public final JCheckBoxMenuItem pointPickerItem;
  public final JCheckBoxMenuItem coordinateItem;

  public Menubar(final EventDispatcher dispatcher) {
    super();
    this.dispatcher = dispatcher;
    historyMenu = new JMenu("History");
    viewMenu = new JMenu("View");
    debugMenu = new JMenu("Debug");
    helpMenu = new JMenu("Help");

    previousItem = new JMenuItem("Previous");
    nextItem = new JMenuItem("Next");
    showLogItem = new JMenuItem("Show Log");
    tutorialItem = new JMenuItem("Tutorial");
    pointPickerItem = new JCheckBoxMenuItem("Show Fundametal Domain");
    pointPickerItem.setState(true);
    coordinateItem = new JCheckBoxMenuItem("Show Coordinates");
    coordinateItem.setState(true);

    previousItem.addActionListener(new ActionListener() {
      public void actionPerformed(ActionEvent event) {
        dispatcher.fireEvent(new ShowPreviousEvent());
      }
    });

    nextItem.addActionListener(new ActionListener() {
      public void actionPerformed(ActionEvent event) {
        dispatcher.fireEvent(new ShowNextEvent());
      }
    });
    
    showLogItem.addActionListener(new ActionListener() {
      public void actionPerformed(ActionEvent event) {
        dispatcher.fireEvent(new ShowLogEvent());
      }
    });
    
    tutorialItem.addActionListener(new ActionListener() {
      public void actionPerformed(ActionEvent event) {
        dispatcher.fireEvent(new TutorialEvent());
      }
    });

    pointPickerItem.addActionListener(new ActionListener() {
      public void actionPerformed(ActionEvent event) {
        dispatcher.fireEvent(new ShowFundamentalDomainEvent(
            pointPickerItem.getState()));
      }
    });
    
    coordinateItem.addActionListener(new ActionListener() {
      public void actionPerformed(ActionEvent event) {
        dispatcher.fireEvent(new ShowCoordinateEvent(
            coordinateItem.getState()));
      }
    });
    
    previousItem.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_P, ActionEvent.CTRL_MASK));
    nextItem.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_N, ActionEvent.CTRL_MASK));
    pointPickerItem.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_F, ActionEvent.CTRL_MASK));
    pointPickerItem.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_C, ActionEvent.CTRL_MASK));
    showLogItem.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_F12, 0));
    tutorialItem.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_F1, 0));

    historyMenu.add(previousItem);
    historyMenu.add(nextItem);

    viewMenu.add(pointPickerItem);
    viewMenu.add(coordinateItem);

    debugMenu.add(showLogItem); 

    helpMenu.add(tutorialItem);

    this.add(historyMenu);
    this.add(viewMenu);
    this.add(debugMenu);
    this.add(helpMenu);
  }

}
