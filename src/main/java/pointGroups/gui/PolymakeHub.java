package pointGroups.gui;

import pointGroups.geometry.Point3D;
import pointGroups.geometry.Point4D;
import pointGroups.geometry.Symmetry;
import pointGroups.gui.event.types.RunEvent;
import pointGroups.gui.event.types.RunHandler;
import pointGroups.gui.event.types.Symmetry3DChooseEvent;
import pointGroups.gui.event.types.Symmetry3DChooseHandler;
import pointGroups.gui.event.types.Symmetry4DChooseEvent;
import pointGroups.gui.event.types.Symmetry4DChooseHandler;
import pointGroups.util.polymake.wrapper.PolymakeWrapper;


public class PolymakeHub
  implements Symmetry3DChooseHandler, Symmetry4DChooseHandler, RunHandler
{
  protected final PolymakeWrapper pmWrapper;
  private Symmetry<Point3D, ?> last3DSymmetry;
  private Symmetry<Point4D, ?> last4DSymmetry;
  private String lastSubgroup;

  public PolymakeHub(final String polyCmd, final String polyDriver) {
    this.pmWrapper = new PolymakeWrapper(polyCmd, polyDriver);
    this.pmWrapper.start();
  }

  @Override
  public void onRunEvent(final RunEvent event) {
    final int dimension = event.getDimension();
    final double[] coords = event.getCoordinates();

    if (dimension == 3) {
      if (last3DSymmetry != null) {
        Point3D p = new Point3D(coords[0], coords[1], coords[2]);
        last3DSymmetry.images(p, lastSubgroup);
      }
    }
    else if (dimension == 4) {
      if (last4DSymmetry != null) {
        Point4D p = new Point4D(coords[0], coords[1], coords[2], coords[3]);
        last4DSymmetry.images(p, lastSubgroup);
      }
    }
  }

  @Override
  public void onSymmetry4DChooseEvent(Symmetry4DChooseEvent event) {
    this.last4DSymmetry = event.getSymmetry4D();
    this.lastSubgroup = event.getSubgroup();
  }

  @Override
  public void onSymmetry3DChooseEvent(Symmetry3DChooseEvent event) {
    this.last3DSymmetry = event.getSymmetry3D();
    this.lastSubgroup = event.getSubgroup();
  }

}
