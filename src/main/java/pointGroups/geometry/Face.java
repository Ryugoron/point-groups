package pointGroups.geometry;

import java.util.ArrayList;
import java.util.List;


public class Face
{
  public final List<Integer> indices;

  public Face() {
    this(new ArrayList<Integer>());
  }

  public Face(List<Integer> indices) {
    this.indices = indices;
  }

  public boolean addIndex(int index) {
    return indices.add(index);
  }
}
