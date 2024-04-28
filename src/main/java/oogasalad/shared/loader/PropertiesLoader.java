package oogasalad.shared.loader;

import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

/**
 * Utility class for loading properties from a file.
 *
 * @author Philip Lee.
 */
public class PropertiesLoader {

  /**
   * Loads properties from a specified path within the classpath. This method ensures properties are
   * loaded only once to avoid redundant IO operations.
   *
   * @param propertiesPath the classpath location of the properties file
   */
  public static Properties loadProperties(String propertiesPath) {
    Properties properties = new Properties();
    try (InputStream input = PropertiesLoader.class
        .getClassLoader()
        .getResourceAsStream(propertiesPath)) {
      if (input == null) {
        throw new IllegalStateException(propertiesPath);
      }
      properties.load(input);
    } catch (IOException ex) {
      throw new RuntimeException(propertiesPath, ex);
    }
    return properties;
  }
}
