package pointGroups.gui;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.JCheckBoxMenuItem;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;

import pointGroups.gui.event.EventDispatcher;
import pointGroups.gui.event.types.RedoEvent;
import pointGroups.gui.event.types.ShowCoordinateEvent;
import pointGroups.gui.event.types.ShowFundamentalDomainEvent;
import pointGroups.gui.event.types.ShowLogEvent;
import pointGroups.gui.event.types.TutorialEvent;
import pointGroups.gui.event.types.UndoEvent;


public class Menubar
  extends JMenuBar
{
  /**
   * 
   */
  private static final long serialVersionUID = -6870787436220684514L;

  private final EventDispatcher dispatcher;

  public final JMenu editMenu;
  public final JMenu viewMenu;
  public final JMenu debugMenu;
  public final JMenu helpMenu;

  // Items of editMenu
  public final JMenuItem undoItem;
  public final JMenuItem redoItem;

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
    editMenu = new JMenu("Edit");
    viewMenu = new JMenu("View");
    debugMenu = new JMenu("Debug");
    helpMenu = new JMenu("Help");

    undoItem = new JMenuItem("Undo");
    redoItem = new JMenuItem("Redo");
    showLogItem = new JMenuItem("Show Log");
    tutorialItem = new JMenuItem("Tutorial");
    pointPickerItem = new JCheckBoxMenuItem("Show Fundametal Domain");
    pointPickerItem.setState(true);
    coordinateItem = new JCheckBoxMenuItem("Show Coordinates");
    coordinateItem.setState(true);

    undoItem.addActionListener(new ActionListener() {
      public void actionPerformed(ActionEvent event) {
        dispatcher.fireEvent(new UndoEvent());
      }
    });

    redoItem.addActionListener(new ActionListener() {
      public void actionPerformed(ActionEvent event) {
        dispatcher.fireEvent(new RedoEvent());
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

    editMenu.add(undoItem);
    editMenu.add(redoItem);

    viewMenu.add(pointPickerItem);
    viewMenu.add(coordinateItem);

    debugMenu.add(showLogItem); 

    helpMenu.add(tutorialItem);

    this.add(editMenu);
    this.add(viewMenu);
    this.add(debugMenu);
    this.add(helpMenu);
  }

}
