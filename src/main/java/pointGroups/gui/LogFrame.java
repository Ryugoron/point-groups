/**
 * 
 */
package pointGroups.gui;

import java.util.logging.Handler;
import java.util.logging.LogRecord;
import java.util.logging.Logger;

import javax.swing.JFrame;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;

import pointGroups.gui.event.EventDispatcher;
import pointGroups.gui.event.types.ShowLogEvent;
import pointGroups.gui.event.types.ShowLogHandler;

/**
 * @author nadjascharf
 *
 */
public class LogFrame
  extends JFrame implements ShowLogHandler
{
  private JTextArea textArea;
  private JScrollPane scrollPane;
  
  public LogFrame(){
    super("Log");
    this.setSize(400, 400);
    textArea = new JTextArea();
    textArea.setEditable(false);
    scrollPane = new JScrollPane(textArea);
    this.add(scrollPane);
    this.setDefaultCloseOperation(HIDE_ON_CLOSE);
    this.setAlwaysOnTop(true);
    EventDispatcher.get().addHandler(ShowLogEvent.TYPE, this);
    Logger.getGlobal().addHandler(new LogHandler(textArea));
  }
  
  public LogFrame(int width, int height){
    super("Log");
    this.setSize(width, height);
    textArea = new JTextArea();
    textArea.setEditable(false);
    scrollPane = new JScrollPane(textArea);
    this.add(scrollPane);
    this.setDefaultCloseOperation(HIDE_ON_CLOSE);
    this.setAlwaysOnTop(true);
    EventDispatcher.get().addHandler(ShowLogEvent.TYPE, this);
    Logger.getGlobal().addHandler(new LogHandler(textArea));
  }

  @Override
  public void onShowLogEvent(ShowLogEvent event) {
    this.setVisible(true);
  }
  
  private class LogHandler extends Handler{
    
    JTextArea area;
    
    LogHandler(JTextArea area){
      this.area = area;
    }

    @Override
    public void publish(LogRecord record) {
      area.append(record.getLoggerName() + ": " + record.getLevel() + ": " + record.getMessage() +"\n");
      
    }

    @Override
    public void flush() {
      // TODO Auto-generated method stub
      
    }

    @Override
    public void close()
      throws SecurityException {
      // TODO Auto-generated method stub
      
    }
    
  }
  
}


