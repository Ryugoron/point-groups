package pointGroups.gui;

import javax.swing.JSlider;

public class ScalingSlider
  extends JSlider
{
  private static final long serialVersionUID = 8450158610275245377L;
  
  protected final static int MIN_VALUE = 1;
  protected final static int MAX_VALUE = 10;
  protected final static int INIT_VALUE = 1;
  
  public ScalingSlider() {
    super(JSlider.HORIZONTAL, MIN_VALUE, MAX_VALUE, INIT_VALUE);
    
    this.setMajorTickSpacing(9);
    this.setMinorTickSpacing(1);
    this.setPaintTicks(true);
    this.setPaintLabels(true);
    this.setSnapToTicks(true);
  }

}
