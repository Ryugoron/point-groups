package pointGroups.gui;

import java.awt.BorderLayout;
import java.util.concurrent.Future;

import javax.swing.JButton;
import javax.swing.JPanel;

import de.jreality.plugin.JRViewer;
import de.jreality.scene.Viewer;


public class SchlegelView
  extends JPanel
{

  private static final long serialVersionUID = -3642299900579728806L;

  protected Future<JRViewer> viewerFuture;
  protected JRViewer schlegelViewer;
  protected Viewer schlegelView;

  public SchlegelView() {
    super();

    setLayout(new BorderLayout());

    JButton button3 = new JButton("VIEW");
    add(button3, BorderLayout.PAGE_END);

    viewerFuture = UiViewer.viewerFactory(this);
  }

  public Viewer getSchlegelView() {
    try {
      schlegelViewer = viewerFuture.get();
      schlegelView = schlegelViewer.getViewer();
    }
    catch (Exception e) {
      e.printStackTrace();
    }

    return schlegelView;
  }

  public void dispose() {
    UiViewer.dispose(viewerFuture);
  }
}
