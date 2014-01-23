package pointGroups.gui;

import java.awt.BorderLayout;

import javax.swing.JButton;
import javax.swing.JPanel;

import de.jreality.geometry.Primitives;


public class SchlegelView
  extends JPanel
{

  private static final long serialVersionUID = -3642299900579728806L;

  protected final UiViewer uiViewer = new UiViewer(this);

  public SchlegelView() {
    super();

    setLayout(new BorderLayout());

    JButton button3 = new JButton("VIEW");
    add(button3, BorderLayout.PAGE_END);

    // TODO: remove me, just a placeholder geometry
    uiViewer.setGeometry(Primitives.cone(15));
  }

  public void dispose() {
    uiViewer.dispose();
  }
}
