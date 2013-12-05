package pointGroups.gui;

import java.awt.BorderLayout;
import java.awt.Component;
import java.awt.Dimension;

import javax.swing.JButton;
import javax.swing.JPanel;

import de.jreality.plugin.JRViewer;
import de.jreality.scene.Viewer;


public class PointPicker
  extends JPanel
{

  private static final long serialVersionUID = -3642299900579728806L;

  protected JRViewer pointPickerViewer;
  protected Viewer pointPickerView;

  public PointPicker() {
    super();

    pointPickerViewer = UiViewer.viewerFactory();
    pointPickerView = pointPickerViewer.getViewer();

    setLayout(new BorderLayout());

    setBounds(0, 0, 300, 500);
    setPreferredSize(new Dimension(450, 125));

    add((Component) pointPickerView.getViewingComponent(), BorderLayout.CENTER);

    JButton button3 = new JButton("VIEW");
    add(button3, BorderLayout.PAGE_END);
  }
}
