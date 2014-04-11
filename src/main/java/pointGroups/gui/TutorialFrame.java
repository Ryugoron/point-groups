package pointGroups.gui;

import javax.swing.JFrame;

import pointGroups.gui.event.types.ShowTutorialEvent;
import pointGroups.gui.event.types.ShowTutorialHandler;

public class TutorialFrame 
  extends JFrame
  implements ShowTutorialHandler
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
  }

  @Override
  public void onShowTutorialEvent(ShowTutorialEvent event) {
    // TODO Auto-generated method stub
    
  }

}
