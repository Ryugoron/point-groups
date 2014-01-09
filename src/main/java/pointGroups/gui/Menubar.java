package pointGroups.gui;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;

import javax.swing.JCheckBoxMenuItem;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;

import pointGroups.gui.event.EventDispatcher;
import pointGroups.gui.event.types.RedoEvent;
import pointGroups.gui.event.types.ShowFundamentalDomainEvent;
import pointGroups.gui.event.types.UndoEvent;


public class Menubar
  extends JMenuBar
{
  private final EventDispatcher dispatcher;

  private JMenu editMenu;
  private JMenu viewMenu;
  private JMenu debugMenu;
  private JMenu helpMenu;

  // Items of editMenu
  private JMenuItem undoItem;
  private JMenuItem redoItem;

  // Items of debugMenu
  private JMenuItem showLogItem;

  // Items of helpMenu
  private JMenuItem tutorialItem;

  // Items of viewMenu
  private JCheckBoxMenuItem pointPickerItem;
  private JCheckBoxMenuItem coordinateItem;

  public Menubar(final EventDispatcher dispatcher) {
    super();
    this.dispatcher = dispatcher;
    editMenu = new JMenu("Edit");
    viewMenu = new JMenu("View");
    debugMenu = new JMenu("Debug");
    helpMenu = new JMenu("Help");

    undoItem = new JMenuItem("Undo");
    redoItem = new JMenuItem("Redo");
    // TODO: create Events show Log, Tutorial
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

    pointPickerItem.addActionListener(new ActionListener() {
      public void actionPerformed(ActionEvent event) {
        dispatcher.fireEvent(new ShowFundamentalDomainEvent(
            pointPickerItem.getState()));
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
