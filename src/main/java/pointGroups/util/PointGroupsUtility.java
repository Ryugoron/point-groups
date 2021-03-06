package pointGroups.util;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.net.MalformedURLException;
import java.net.URI;
import java.net.URISyntaxException;
import java.net.URL;
import java.nio.file.Files;
import java.nio.file.StandardCopyOption;
import java.util.Properties;

import pointGroups.PointGroups;


public class PointGroupsUtility
{

  /**
   * Get the location of the resource relative to the binaries of point groups.
   * 
   * @param file
   * @return the {@link URI} of the resource
   * @throws FileNotFoundException
   */
  public static URI getResource(final String file)
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
   * Gets the location of the resource relative to the {@link PointGroups} class
   * as a stream. This is necessary if a resource is called from within a
   * executable jar, where {@link #getResource(String)} would fail.
   * 
   * @param file
   * @return An {@link InputStream} to the requested resource
   * @throws FileNotFoundException
   */
  public static InputStream getResourceAsStream(final String file)
    throws FileNotFoundException {
    try {
      ClassLoader classLoader = PointGroups.class.getClassLoader();
      return classLoader.getResourceAsStream(file);
    }
    catch (NullPointerException e) {
      throw new FileNotFoundException("File " + file +
          " couldn't be found. Error-Message: " + e.getMessage());
    }
  }

  /**
   * Get the standard {@link Properties} of the point group project. We assume a
   * {@linkplain settings.ini} beside the base directory the project is started
   * from.
   * 
   * @return
   * @throws IOException
   */
  public static Properties getProperties()
    throws IOException {

    Properties prop = new Properties();

    InputStream is =
        new FileInputStream(System.getProperty("user.home") +
            "/.pointgroups/settings.ini");
    prop.load(is);

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
  public static InputStream getSymmetry(final String symmetry)
    throws IOException {
    return getResourceAsStream("symmetries/" + symmetry);
    // URI file = getResource("symmetries/" + symmetry);
    // return new File(file);
  }

  /**
   * Get the location of the image relative to the `images` folder in the
   * resources.
   * 
   * @param image
   * @return
   * @throws IOException
   */
  public static URL getImage(final String image)
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
    File file = new File("pmDriver.temp.pl");
    file.deleteOnExit();
    Files.copy(getResourceAsStream("perl/pmDriver.pl"), file.toPath(),
        StandardCopyOption.REPLACE_EXISTING);
    file.deleteOnExit();
    return file;
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
