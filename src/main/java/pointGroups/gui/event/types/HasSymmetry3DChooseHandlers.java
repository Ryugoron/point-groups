package pointGroups.gui.event.types;

public interface HasSymmetry3DChooseHandlers
  extends HasHandlers
{
  void addSymmetry3DChooseHandler(Symmetry3DChooseHandler handler);

  void removeSymmetry3DChooseHandler(Symmetry3DChooseHandler handler);
}
