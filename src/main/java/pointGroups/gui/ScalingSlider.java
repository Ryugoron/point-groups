package pointGroups.gui;

import java.util.logging.Logger;

import javax.swing.JSlider;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;

import pointGroups.gui.event.EventDispatcher;
import pointGroups.gui.event.types.ScaleFundamentalDomainEvent;
import pointGroups.util.LoggerFactory;

public class ScalingSlider
  extends JSlider
{
  private static final long serialVersionUID = 8450158610275245377L;
  
  protected final static int MIN_VALUE = 1;
  protected final static int MAX_VALUE = 10;
  protected final static int INIT_VALUE = 1;
  
  protected EventDispatcher dispatcher = EventDispatcher.get();
  private final Logger logger = LoggerFactory.get(this.getClass());
  
  public ScalingSlider() {
    super(JSlider.HORIZONTAL, MIN_VALUE, MAX_VALUE, INIT_VALUE);
    
    this.setMajorTickSpacing(3);
    this.setMinorTickSpacing(1);
    this.setPaintTicks(true);
    this.setPaintLabels(true);
    this.setSnapToTicks(true);
    
    this.addChangeListener(new SliderListener());
  }
  
  class SliderListener implements ChangeListener {
    public void stateChanged(ChangeEvent e) {
      JSlider source = (JSlider) e.getSource();
      if (!source.getValueIsAdjusting()) {
        int value = source.getValue();
        dispatcher.fireEvent(new ScaleFundamentalDomainEvent(value));
        logger.info("ScalingSlider: Value set to " + value);
        }    
    }
  }

}
