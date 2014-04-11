/**
 * 
 */
package pointGroups.gui;

import java.awt.Color;
import java.awt.Component;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.logging.Handler;
import java.util.logging.Level;
import java.util.logging.LogRecord;
import java.util.logging.Logger;

import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.table.AbstractTableModel;
import javax.swing.table.TableCellRenderer;
import javax.swing.table.TableModel;

import pointGroups.gui.event.EventDispatcher;
import pointGroups.gui.event.types.ShowLogEvent;
import pointGroups.gui.event.types.ShowLogHandler;


// import javax.swing.JTextArea;

/**
 * @author nadjascharf
 */
public class LogFrame
  extends JFrame
  implements ShowLogHandler
{
  /**
   * 
   */
  private static final long serialVersionUID = 6753966305287019976L;
  // private JTextArea textArea;
  private JScrollPane scrollPane;
  private JTable table;
  TableModel model;
  private final String[] columnNames = { "Time", "Level", "LoggerName",
      "Message" };
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
      /**
       * 
       */
      private static final long serialVersionUID = 7525868394963262424L;

      @Override
      public String getColumnName(int col) {
        return columnNames[col].toString();
      }

      @Override
      public int getRowCount() {
        return data.size();
      }

      @Override
      public int getColumnCount() {
        return columnNames.length;
      }

      @Override
      public Object getValueAt(int row, int col) {
        return data.get(row)[col];
      }
    };
    table = new JTable(model);

    table.getColumnModel().getColumn(0).setPreferredWidth(100);
    table.getColumnModel().getColumn(0).setMaxWidth(200);

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
      Date date = new Date(record.getMillis());
      SimpleDateFormat sdf = new SimpleDateFormat("HH:mm:ss");

      Object[] newLog =
          { sdf.format(date), record.getLevel(), record.getLoggerName(),
              record.getMessage() };
      data.add(newLog);
      // TODO is repaint and revalidate enough?
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
    implements TableCellRenderer
  {

    /**
     * 
     */
    private static final long serialVersionUID = 7165707951780037835L;

    @Override
    public Component getTableCellRendererComponent(JTable table, Object value,
        boolean isSelected, boolean hasFocus, int row, int column) {
      String level = ((Level) value).getName();

      JLabel label = new JLabel();
      if (level.equals("WARNING")) {
        label.setForeground(Color.ORANGE);
      }
      if (level.equals("SEVERE")) {
        label.setForeground(Color.RED);
      }
      label.setText(((Level) value).getName());
      return label;
    }

  }

}
