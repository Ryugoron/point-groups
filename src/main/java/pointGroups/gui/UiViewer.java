package pointGroups.gui;

import de.jreality.geometry.Primitives;
import de.jreality.plugin.JRViewer;
import de.jreality.scene.SceneGraphComponent;


public class UiViewer
{

  public static JRViewer viewerFactory() {
    SceneGraphComponent world = new SceneGraphComponent();
    world.setGeometry(Primitives.sharedIcosahedron);
    // View.setTitle("The Icosahedron");
    // View.setIcon(getIcon("color_swatch.png"));

    JRViewer jrViewer = JRViewer.createJRViewer(world);
    jrViewer.startupLocal();
    return jrViewer;
  }
}
