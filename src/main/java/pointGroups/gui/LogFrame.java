/**
 * 
 */
package pointGroups.gui;

import java.awt.Color;
import java.awt.Component;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Handler;
import java.util.logging.Level;
import java.util.logging.LogRecord;
import java.util.logging.Logger;

import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
// import javax.swing.JTextArea;
import javax.swing.table.AbstractTableModel;
import javax.swing.table.TableCellRenderer;
import javax.swing.table.TableModel;

import pointGroups.gui.event.EventDispatcher;
import pointGroups.gui.event.types.ShowLogEvent;
import pointGroups.gui.event.types.ShowLogHandler;


/**
 * @author nadjascharf
 */
public class LogFrame
  extends JFrame
  implements ShowLogHandler
{
  // private JTextArea textArea;
  private JScrollPane scrollPane;
  private JTable table;
  TableModel model;
  private String[] columnNames = { "LoggerName", "Level", "Message" };
  private List<Object[]> data;

  public LogFrame() {
    this(1000, 600);
  }

  public LogFrame(int width, int height) {
    super("Log");
    this.setSize(width, height);
    data = new ArrayList<>();

    // create TableModel to customize Table (not editable and List instead of an
    // array for data)
    model = new AbstractTableModel() {
      public String getColumnName(int col) {
        return columnNames[col].toString();
      }

      public int getRowCount() {
        return data.size();
      }

      public int getColumnCount() {
        return columnNames.length;
      }

      public Object getValueAt(int row, int col) {
        return data.get(row)[col];
      }
    };
    table = new JTable(model);
    // second column holds log level -> only short strings
    table.getColumnModel().getColumn(1).setMaxWidth(100);
    table.getColumnModel().getColumn(1).setCellRenderer(new LevelRenderer());
    scrollPane = new JScrollPane(table);
    // textArea = new JTextArea();
    // textArea.setEditable(false);
    // scrollPane = new JScrollPane(textArea);

    this.add(scrollPane);

    // don't quit program, only hide log window. can be shown again via
    // ShowLogEvent fired by the Menubar
    this.setDefaultCloseOperation(HIDE_ON_CLOSE);
    EventDispatcher.get().addHandler(ShowLogEvent.TYPE, this);

    // get all logs via the global logger
    Logger.getGlobal().addHandler(new LogHandler(data, table, scrollPane));
    // Logger.getGlobal().addHandler(new LogHandler(textArea));
  }

  @Override
  public void onShowLogEvent(ShowLogEvent event) {
    this.setVisible(true);
    this.toFront();
  }


  /**
   * Handler should be registered at {@link Logger#getGlobal()}. Published
   * records will be inserted into the Log Window.
   * 
   * @author nadjascharf
   */
  private class LogHandler
    extends Handler
  {

    // JTextArea area;
    List<Object[]> data;
    // boolean asTable;
    JTable table;
    JScrollPane pane;

    // LogHandler(JTextArea area) {
    // this.area = area;
    // asTable = false;
    // }
    //
    LogHandler(List<Object[]> data, JTable table, JScrollPane pane) {
      this.data = data;
      // asTable = true;
      this.table = table;
      this.pane = pane;
    }

    @Override
    public void publish(LogRecord record) {
      // if(!asTable){
      // area.append(record.getLoggerName() + ": <" + record.getLevel() + "> " +
      // record.getMessage() + "\n");
      // }
      // else{
      Object[] newLog =
          { record.getLoggerName(), record.getLevel(), record.getMessage() };
      data.add(newLog);
      // updated->repaint to show new log
      table.repaint();
      // is it necessary to be scrollable?
      pane.revalidate();
      // }

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


  public class LevelRenderer
    extends JLabel
    implements TableCellRenderer
  {

    @Override
    public Component getTableCellRendererComponent(JTable table, Object value,
        boolean isSelected, boolean hasFocus, int row, int column) {
      String level = ((Level) value).getName();
      if (level.equals("WARNING")){
        this.setForeground(Color.ORANGE);
      }
      if (level.equals("SEVERE")){
        this.setForeground(Color.RED);
      }
      this.setText(((Level) value).getName());
      return this;
    }

  }

}
