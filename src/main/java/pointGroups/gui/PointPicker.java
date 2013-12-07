package pointGroups.gui;

import java.awt.BorderLayout;
import java.util.concurrent.Future;

import javax.swing.JButton;
import javax.swing.JPanel;

import de.jreality.plugin.JRViewer;
import de.jreality.scene.Viewer;


public class PointPicker
  extends JPanel
{

  private static final long serialVersionUID = -3642299900579728806L;

  protected Future<JRViewer> viewerFuture;
  protected JRViewer pointPickerViewer;
  protected Viewer pointPickerView;

  public PointPicker() {
    super();

    setLayout(new BorderLayout());

    JButton button3 = new JButton("VIEW");
    PointPicker.this.add(button3, BorderLayout.PAGE_END);

    viewerFuture = UiViewer.viewerFactory(this);
  }

  public void dispose() {
    UiViewer.dispose(viewerFuture);
  }
}
