/**
 * 
 */
package pointGroups.util;

import java.util.HashMap;
import java.util.logging.Logger;


/**
 * @author Nadja
 */
public class LoggerFactory
{
  private static HashMap<Class<?>, Integer> counters = new HashMap<>();

  /**
   * Returns a Logger with unique name an globalLogger as parent.
   * 
   * @param clazz
   * @return {@link java.util.logging.Logger} with name clazz.getName() + an
   *         unique ID
   */
  public static Logger get(Class<?> clazz) {
    Logger logger;
    if (counters.containsKey(clazz)) {
      int counter = counters.get(clazz);
      logger =
          Logger.getLogger(clazz.getName() + ", ID " + String.valueOf(counter));
      counters.put(clazz, counter + 1);
    }
    else {
      logger = Logger.getLogger(clazz.getName() + ", ID 1");
      counters.put(clazz, 2);
    }
    logger.setParent(Logger.getGlobal());
    return logger;
  }

  /**
   * @param clazz
   * @return {@link java.util.logging.Logger} with name clazz.getName()
   */
  public static Logger getSingle(Class<?> clazz) {
    Logger logger = Logger.getLogger(clazz.getName());
    logger.setParent(Logger.getGlobal());
    return logger;
  }
}
