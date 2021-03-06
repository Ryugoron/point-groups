package pointGroups.gui.logwindow;

import java.util.logging.Logger;

import org.junit.Assert;
import org.junit.Test;

import pointGroups.gui.LogFrame;
import pointGroups.gui.event.EventDispatcher;
import pointGroups.gui.event.types.ShowLogEvent;
import pointGroups.util.LoggerFactory;
import junit.framework.TestCase;


public class LogWindowTest
  extends TestCase
{

  @Test
  public void test() {
    LogFrame logFrame = new LogFrame();
    EventDispatcher dispatcher = EventDispatcher.get();
    Assert.assertFalse(logFrame.isVisible());
    dispatcher.fireEvent(new ShowLogEvent());
    Assert.assertTrue(logFrame.isVisible());
    logFrame.setVisible(false);
    Logger logger1 = LoggerFactory.get(this.getClass());
    Logger logger2 = LoggerFactory.get(this.getClass());
    Logger single = LoggerFactory.getSingle(this.getClass());
    Assert.assertEquals("pointGroups.gui.logwindow.LogWindowTest, ID 1",
        logger1.getName());
    Assert.assertEquals("pointGroups.gui.logwindow.LogWindowTest, ID 2",
        logger2.getName());
    Assert.assertEquals("pointGroups.gui.logwindow.LogWindowTest",
        single.getName());
  }

}
