package pointGroups.util;

import java.util.ArrayList;
import java.util.Collection;


public class Utility
{
  /**
   * Remove all duplicated entries.
   * 
   * @param list
   * @return
   */
  public static <P> Collection<P> uniqueList(
      Collection<P> list) {

    ArrayList<P> uniqueList = new ArrayList<>();
    for (P item : list) {
      if (uniqueList.contains(item)) {
        continue;
      }
      uniqueList.add(item);
    }
    return uniqueList;
  }
}
