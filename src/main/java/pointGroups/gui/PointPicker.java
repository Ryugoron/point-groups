package pointGroups.gui;

import java.awt.BorderLayout;

import javax.swing.JButton;
import javax.swing.JPanel;

import de.jreality.geometry.Primitives;


public class PointPicker
  extends JPanel
{

  private static final long serialVersionUID = -3642299900579728806L;

  protected final UiViewer uiViewer = new UiViewer(this);

  public PointPicker() {
    super();

    setLayout(new BorderLayout());

    JButton button3 = new JButton("VIEW");
    PointPicker.this.add(button3, BorderLayout.PAGE_END);

    uiViewer.setGeometry(Primitives.cylinder(15));
  }

  public void dispose() {
    uiViewer.dispose();
  }
}
