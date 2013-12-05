package pointGroups.gui;

import java.awt.BorderLayout;
import java.awt.Component;

import javax.swing.JPanel;

import de.jreality.plugin.JRViewer;
import de.jreality.scene.Viewer;


public class SchlegelView
  extends JPanel
{

  private static final long serialVersionUID = -3642299900579728806L;

  protected JRViewer schlegelViewer;
  protected Viewer schlegelView;

  public SchlegelView() {
    super();

    schlegelViewer = UiViewer.viewerFactory();
    schlegelView = schlegelViewer.getViewer();

    setLayout(new BorderLayout());

    add((Component) schlegelView.getViewingComponent(), BorderLayout.CENTER);
  }
}
