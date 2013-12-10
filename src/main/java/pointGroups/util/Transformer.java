package pointGroups.util;

import java.util.concurrent.Future;


/**
 * An instance holds everything polymake needs for computation.
 * 
 * @author Nadja, Simon
 */
public interface Transformer<E> extends Future<E>
{
  /**
   * Generates a script.
   * @return A script as String.
   */
  public String toScript();

}
