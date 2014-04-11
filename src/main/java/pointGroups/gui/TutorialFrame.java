package pointGroups.gui;

import javax.swing.JFrame;

import pointGroups.gui.event.EventDispatcher;
import pointGroups.gui.event.types.TutorialEvent;
import pointGroups.gui.event.types.TutorialHandler;

public class TutorialFrame 
  extends JFrame
  implements TutorialHandler
{
  protected static int defaultWidth = 600;
  protected static int defaultHeight = 400;
  /**
   * 
   */
  private static final long serialVersionUID = 1467143052188689182L;
  
  public TutorialFrame() {
    this(defaultWidth,defaultHeight);
  }
  
  public TutorialFrame(int width, int height) {
    super("Tutorial");
    this.setSize(width, height);
    
    this.setDefaultCloseOperation(HIDE_ON_CLOSE);
    EventDispatcher.get().addHandler(TutorialEvent.TYPE, this);
    this.setVisible(true);
  }

  @Override
  public void onTutorialEvent(TutorialEvent event) {
    this.setVisible(true);
    this.toFront();
  }

}
