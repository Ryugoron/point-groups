package pointGroups.gui;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Component;
import java.awt.Dimension;
import java.awt.Graphics;
import java.util.Calendar;

import javax.swing.BorderFactory;
import javax.swing.Icon;
import javax.swing.JLabel;
import javax.swing.JPanel;

import pointGroups.gui.event.EventDispatcher;
import pointGroups.gui.event.types.ChangeCoordinate3DPointEvent;
import pointGroups.gui.event.types.ChangeCoordinate3DPointHandler;
import pointGroups.gui.event.types.ChangeCoordinate4DPointEvent;
import pointGroups.gui.event.types.ChangeCoordinate4DPointHandler;
import pointGroups.gui.event.types.Schlegel3DComputeEvent;
import pointGroups.gui.event.types.Schlegel3DComputeHandler;
import pointGroups.gui.event.types.Schlegel4DComputeEvent;
import pointGroups.gui.event.types.Schlegel4DComputeHandler;
import pointGroups.gui.event.types.ShowCoordinateEvent;
import pointGroups.gui.event.types.ShowCoordinateHandler;
import pointGroups.gui.event.types.ShowFundamentalDomainEvent;
import pointGroups.gui.event.types.ShowFundamentalDomainHandler;
import pointGroups.gui.event.types.ShowLogEvent;
import pointGroups.gui.event.types.ShowLogHandler;
import pointGroups.gui.event.types.ShowNextEvent;
import pointGroups.gui.event.types.ShowNextHandler;
import pointGroups.gui.event.types.ShowPreviousEvent;
import pointGroups.gui.event.types.ShowPreviousHandler;
import pointGroups.gui.event.types.Symmetry3DChooseEvent;
import pointGroups.gui.event.types.Symmetry3DChooseHandler;
import pointGroups.gui.event.types.Symmetry4DChooseEvent;
import pointGroups.gui.event.types.Symmetry4DChooseHandler;
import pointGroups.gui.event.types.TutorialEvent;
import pointGroups.gui.event.types.TutorialHandler;


public class StatusBar
  extends JPanel
  implements ChangeCoordinate4DPointHandler, ChangeCoordinate3DPointHandler,
  Schlegel3DComputeHandler, Schlegel4DComputeHandler, ShowCoordinateHandler,
  ShowFundamentalDomainHandler, ShowLogHandler, ShowNextHandler,
  ShowPreviousHandler, Symmetry3DChooseHandler, Symmetry4DChooseHandler,
  TutorialHandler
{
  /**
   * 
   */
  private static final long serialVersionUID = 5642754641792078341L;
  private final JLabel statusLabel;
  protected final JPanel resizeHandle;

  public StatusBar(final EventDispatcher dispatcher) {
    // GUI layout
    setLayout(new BorderLayout(2, 1));
    setPreferredSize(new Dimension(10, 23));
    setBorder(BorderFactory.createLoweredBevelBorder());

    statusLabel = new JLabel(" ");
    statusLabel.setForeground(Color.black);

    resizeHandle = new JPanel(new BorderLayout());
    resizeHandle.add(new JLabel(new ResizeHandleIcon()), BorderLayout.SOUTH);
    resizeHandle.setOpaque(false);

    add(statusLabel, BorderLayout.CENTER);
    add(resizeHandle, BorderLayout.EAST);

    // register events
    dispatcher.addHandler(ChangeCoordinate3DPointEvent.TYPE, this);
    dispatcher.addHandler(ChangeCoordinate4DPointEvent.TYPE, this);
    dispatcher.addHandler(Schlegel3DComputeEvent.TYPE, this);
    dispatcher.addHandler(Schlegel4DComputeEvent.TYPE, this);
    dispatcher.addHandler(ShowCoordinateEvent.TYPE, this);
    dispatcher.addHandler(ShowFundamentalDomainEvent.TYPE, this);
    dispatcher.addHandler(ShowLogEvent.TYPE, this);
    dispatcher.addHandler(ShowNextEvent.TYPE, this);
    dispatcher.addHandler(ShowPreviousEvent.TYPE, this);
    dispatcher.addHandler(Symmetry3DChooseEvent.TYPE, this);
    dispatcher.addHandler(TutorialEvent.TYPE, this);
  }

  public void setStatus(final String status) {
    Calendar now = Calendar.getInstance();
    int hour = now.get(Calendar.HOUR_OF_DAY);
    int minute = now.get(Calendar.MINUTE);
    if (minute < 10) {
      statusLabel.setText("[" + hour + ":0" + minute + "] " + status);

    }
    else {
      statusLabel.setText("[" + hour + ":" + minute + "] " + status);
    }
  }

  public void removeStatusmessage() {
    setStatus(" ");
  }

  @Override
  public void onTutorialEvent(final TutorialEvent event) {
    setStatus("Show Tutorial");
  }

  @Override
  public void onSymmetry3DChooseEvent(final Symmetry3DChooseEvent event) {
    setStatus("Symmetry chosen: " + event.getSymmetry3D().schoenflies() +
        " / " + event.getSymmetry3D().coxeter() + " / " +
        event.getSymmetry3D().orbifold());

  }

  @Override
  public void onPreviousEvent(final ShowPreviousEvent event) {
    setStatus("Show previous point");

  }

  @Override
  public void onNextEvent(final ShowNextEvent event) {
    setStatus("Show next point");

  }

  @Override
  public void onShowLogEvent(final ShowLogEvent event) {
    setStatus("Show Logfile");
  }

  @Override
  public void onShowFundamentalDomainEvent(
      final ShowFundamentalDomainEvent event) {
    if (event.getVisible()) {
      setStatus("Show fundamental domain");
    }
    else {
      setStatus("Fundamental domain is hidden");
    }
  }

  @Override
  public void onShowCoordinateEvent(final ShowCoordinateEvent event) {
    if (event.getVisible()) {
      setStatus("Show coordinatesystem");
    }
    else {
      setStatus("Coordinatesystem is hidden");
    }
  }

  @Override
  public void onSchlegel4DComputeEvent(final Schlegel4DComputeEvent event) {
    showComputeInfo(event.getPickedPoint().getComponents(),
        event.getSymmetry4D().schoenflies() + " / " +
            event.getSymmetry4D().coxeter() + " / " +
            event.getSymmetry4D().orbifold());

  }

  @Override
  public void onSchlegel3DComputeEvent(final Schlegel3DComputeEvent event) {
    showComputeInfo(event.getPickedPoint().getComponents(),
        event.getSymmetry3D().schoenflies() + " / " +
            event.getSymmetry3D().coxeter() + " / " +
            event.getSymmetry3D().orbifold());
  }

  private void showComputeInfo(final double[] coords, final String symgroup) {
    StringBuilder s = new StringBuilder(300);
    s.append("Run with point (");
    for (double d : coords) {
      s.append(d + ", ");
    }
    s.delete(s.length() - 2, s.length() - 1);
    s.append(") Symmetry group: ");
    s.append(symgroup);

    setStatus(s.toString());
  }

  @Override
  public void onChangeCoordinate3DPointEvent(
      final ChangeCoordinate3DPointEvent event) {
    setStatus("Coordinate chosen");
  }

  @Override
  public void onChangeCoordinate4DPointEvent(
      final ChangeCoordinate4DPointEvent event) {
    setStatus("Coordinate chosen");

  }

  @Override
  public void onSymmetry4DChooseEvent(final Symmetry4DChooseEvent event) {
    setStatus("Symmetry chosen: " + event.getSymmetry4D().schoenflies() +
        " / " + event.getSymmetry4D().coxeter() + " / " +
        event.getSymmetry4D().orbifold());

  }


  private static class ResizeHandleIcon
    implements Icon
  {
    private static final Color WHITE_LINE_COLOR = new Color(255, 255, 255);
    private static final Color GRAY_LINE_COLOR = new Color(172, 168, 153);
    private static final int WIDTH = 13;
    private static final int HEIGHT = 13;

    @Override
    public int getIconHeight() {
      return WIDTH;
    }

    @Override
    public int getIconWidth() {
      return HEIGHT;
    }

    @Override
    public void paintIcon(final Component c, final Graphics g, final int x,
        final int y) {

      g.setColor(WHITE_LINE_COLOR);
      g.drawLine(0, 12, 12, 0);
      g.drawLine(5, 12, 12, 5);
      g.drawLine(10, 12, 12, 10);

      g.setColor(GRAY_LINE_COLOR);
      g.drawLine(1, 12, 12, 1);
      g.drawLine(2, 12, 12, 2);
      g.drawLine(3, 12, 12, 3);

      g.drawLine(6, 12, 12, 6);
      g.drawLine(7, 12, 12, 7);
      g.drawLine(8, 12, 12, 8);

      g.drawLine(11, 12, 12, 11);
      g.drawLine(12, 12, 12, 12);

    }
  }
}
