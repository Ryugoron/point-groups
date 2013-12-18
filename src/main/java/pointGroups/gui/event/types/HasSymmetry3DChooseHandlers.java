package pointGroups.gui.event.types;

import pointGroups.gui.event.HasEventHandlers;


public interface HasSymmetry3DChooseHandlers
  extends HasEventHandlers
{
  void addSymmetry3DChooseHandler(Symmetry3DChooseHandler handler);

  void removeSymmetry3DChooseHandler(Symmetry3DChooseHandler handler);
}
