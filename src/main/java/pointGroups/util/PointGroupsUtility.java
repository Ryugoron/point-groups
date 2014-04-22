package pointGroups.util;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URI;
import java.net.URISyntaxException;
import java.net.URL;
import java.util.Properties;

import pointGroups.PointGroups;


public class PointGroupsUtility
{

  /**
   * Get the location of the resource relative to the binaries of point groups.
   * 
   * @param file
   * @returned.addAll(this.edges);
   * @throws FileNotFoundException
   */
  public static URI getResource(String file)
    throws FileNotFoundException {

    try {
      ClassLoader classLoader = PointGroups.class.getClassLoader();
      URL url = classLoader.getResource(file);
      return url.toURI();
    }
    catch (NullPointerException | URISyntaxException e) {
      throw new FileNotFoundException("File " + file +
          " couldn't be found. Error-Message: " + e.getMessage());
    }
  }

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

    URI file = getResource("settings.ini");
    prop.load(new FileInputStream(new File(file)));

    return prop;
  }

  /**
   * Get the location of a symmetry relative to the `symmetries` folder in the
   * resources.
   * 
   * @param symmetry
   * @return
   * @throws IOException
   */
  public static File getSymmetry(String symmetry)
    throws IOException {
    URI file = getResource("symmetries/" + symmetry);
    return new File(file);
  }

  /**
   * Get the location of the image relative to the `images` folder in the
   * resources.
   * 
   * @param image
   * @return
   * @throws IOException
   */
  public static URL getImage(String image)
    throws IOException {
    URI file = getResource("images/" + image);
    try {
      return file.toURL();
    }
    catch (MalformedURLException e) {
      throw new FileNotFoundException("File " + e.getMessage() +
          " couldn't be found.");
    }
  }

  /**
   * Get the path of the polymake driver from the resources.
   * 
   * @return
   * @throws IOException
   */
  public static File getPolymakeDriverPath()
    throws IOException {
    URI file = getResource("perl/pmDriver.pl");
    return new File(file);
  }

  /**
   * Get the path of polymake from the `settings.ini`.
   * 
   * @return
   * @throws IOException
   */
  public static File getPolymakePath()
    throws IOException {
    Properties prop = getProperties();
    String file = prop.getProperty("POLYMAKEPATH");
    return new File(file);
  }
}
