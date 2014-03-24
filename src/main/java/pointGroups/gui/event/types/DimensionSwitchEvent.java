package pointGroups.gui.event.types;

import pointGroups.geometry.Point;
import pointGroups.geometry.Point3D;
import pointGroups.geometry.Point4D;
import pointGroups.gui.event.Event;


public class DimensionSwitchEvent
  extends Event<DimensionSwitchHandler>
{
  private final Class<? extends Point> pointClass;

  /**
   * The type of the corresponding {@link DimensionSwitchHandler}.
   */
  public final static Class<DimensionSwitchHandler> TYPE =
      DimensionSwitchHandler.class;

  /**
   * Creates a new {@link Symmetry3DChooseEvent} with given {@link Symmetry}.
   * 
   * @param symmetry The symmtry chosen
   */
  public DimensionSwitchEvent(final Class<? extends Point> pointClass) {
    this.pointClass = pointClass;
  }

  public Class<? extends Point> getPointType() {
    return this.pointClass;
  }

  public boolean switchedTo3D() {
    return this.pointClass.equals(Point3D.class);
  }

  public boolean switchedTo4D() {
    return this.pointClass.equals(Point4D.class);
  }

  @Override
  public final Class<DimensionSwitchHandler> getType() {
    return TYPE;
  }

  @Override
  protected void dispatch(final DimensionSwitchHandler handler) {
    handler.onDimensionSwitchEvent(this);
  }
}
