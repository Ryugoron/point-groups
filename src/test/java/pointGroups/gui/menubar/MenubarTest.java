/**
 * 
 */
package pointGroups.gui.menubar;

import static org.junit.Assert.*;

import javax.swing.JFrame;

import junit.framework.TestCase;

import org.junit.Before;
import org.junit.Test;

import pointGroups.gui.Menubar;
import pointGroups.gui.event.EventDispatcher;


/**
 * @author nadjascharf
 */
public class MenubarTest
  extends TestCase
{

  /**
   * @throws java.lang.Exception
   */
  public void setUp()
    throws Exception {
  }

  @Test
  public void test() {
    JFrame frame = new JFrame();
    final EventDispatcher dispatcher = new EventDispatcher();
    frame.setTitle("JMenu mit ActionListener");
    frame.setSize(400, 300);
    frame.setJMenuBar(new Menubar(dispatcher));
    frame.setLocation(100, 100);
    frame.setVisible(true);
    frame.setLocation(100, 100);
  }

}
