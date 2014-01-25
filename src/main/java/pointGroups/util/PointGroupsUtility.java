package pointGroups.util;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.Properties;

import pointGroups.PointGroups;


public class PointGroupsUtility
{

  /**
   * Get the standard {@link Properties} of the point group project. We assume a
   * {@linkplain settings.ini} in the root directory of the compiled classes to
   * fetch from.
   * 
   * @return
   * @throws IOException
   */
  public static Properties getProperties()
    throws IOException {

    Properties prop = new Properties();

    try {
      URI file =
          PointGroups.class.getClassLoader().getResource("settings.ini").toURI();
      prop.load(new FileInputStream(new File(file)));
    }
    catch (URISyntaxException e) {
      throw new FileNotFoundException("File " + e.getMessage() +
          " couldn't be found.");
    }

    return prop;
  }

  /**
   * @deprecated This needs to be changed to a maven resource project path.
   * @return
   */
  @Deprecated
  public static String getPolymakeDriverPath() {
    return "src/main/perl/pmDriver.pl";
  }

  /**
   * @deprecated This needs to be changed to a maven resource project path.
   * @return
   */
  @Deprecated
  public static String getDefaultPolymakePath() {
    return "/usr/bin/polymake";
  }
}
