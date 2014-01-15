/**
 * 
 */
package pointGroups.gui.menubar;

import java.awt.AWTException;
import java.awt.Robot;
import java.awt.event.KeyEvent;

import javax.swing.JFrame;

import org.junit.Assert;

import junit.framework.TestCase;

import org.junit.Test;

import pointGroups.gui.Menubar;
import pointGroups.gui.event.Event;
import pointGroups.gui.event.EventDispatcher;
import pointGroups.gui.event.types.ShowNextEvent;
import pointGroups.gui.event.types.ShowCoordinateEvent;
import pointGroups.gui.event.types.ShowFundamentalDomainEvent;
import pointGroups.gui.event.types.ShowLogEvent;
import pointGroups.gui.event.types.TutorialEvent;
import pointGroups.gui.event.types.ShowPreviousEvent;


/**
 * @author nadjascharf
 */
public class MenubarTest
  extends TestCase
{

  private class TestEventDispatcher
    extends EventDispatcher
  {
    public Event<?> lastEvent;

    public void fireEvent(final Event<?> event) {
      lastEvent = event;
    }
  }

  @Test
  public void test() throws AWTException {
    JFrame frame = new JFrame();
    final TestEventDispatcher dispatcher = new TestEventDispatcher();
    Menubar menubar = new Menubar(dispatcher);
    frame.setJMenuBar(menubar);
//     frame.setTitle("JMenu mit ActionListener");
//     frame.setSize(400, 300);
//     frame.setLocation(100, 100);
//     frame.setVisible(true);

    menubar.previousItem.doClick();
    Assert.assertTrue(dispatcher.lastEvent instanceof ShowPreviousEvent);
    menubar.nextItem.doClick();
    Assert.assertTrue(dispatcher.lastEvent instanceof ShowNextEvent);
    menubar.showLogItem.doClick();
    Assert.assertTrue(dispatcher.lastEvent instanceof ShowLogEvent);
    menubar.tutorialItem.doClick();
    Assert.assertTrue(dispatcher.lastEvent instanceof TutorialEvent);

    menubar.pointPickerItem.doClick();
    Assert.assertTrue(dispatcher.lastEvent instanceof ShowFundamentalDomainEvent);
    ShowFundamentalDomainEvent lastEvent =
        (ShowFundamentalDomainEvent) dispatcher.lastEvent;
    Assert.assertFalse(lastEvent.getVisible());
    menubar.pointPickerItem.doClick();
    Assert.assertTrue(dispatcher.lastEvent instanceof ShowFundamentalDomainEvent);
    lastEvent = (ShowFundamentalDomainEvent) dispatcher.lastEvent;
    Assert.assertTrue(lastEvent.getVisible());

    menubar.coordinateItem.doClick();
    Assert.assertTrue(dispatcher.lastEvent instanceof ShowCoordinateEvent);
    ShowCoordinateEvent lastEvent2 = (ShowCoordinateEvent) dispatcher.lastEvent;
    Assert.assertFalse(lastEvent2.getVisible());
    menubar.coordinateItem.doClick();
    Assert.assertTrue(dispatcher.lastEvent instanceof ShowCoordinateEvent);
    lastEvent2 = (ShowCoordinateEvent) dispatcher.lastEvent;
    Assert.assertTrue(lastEvent.getVisible());
    //TODO test accelerator, only tested once via real user input
//    Robot robot = new Robot();
//    robot.keyPress(KeyEvent.VK_CONTROL);
//    robot.keyPress(KeyEvent.VK_P);
//    robot.delay(100);
//    robot.keyRelease(KeyEvent.VK_P);
//    robot.keyRelease(KeyEvent.VK_CONTROL);
//    Assert.assertTrue(dispatcher.lastEvent instanceof ShowPreviousEvent);
  }

}
