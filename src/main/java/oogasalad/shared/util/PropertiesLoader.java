package oogasalad.shared.util;

import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

/**
 * Utility class for loading properties from a file.
 */
public class PropertiesLoader {

  private static final Properties properties = new Properties();
  private static boolean loaded = false;

  /**
   * Loads properties from a specified path within the classpath.
   * This method ensures properties are loaded only once to avoid redundant IO operations.
   *
   * @param propertiesPath the classpath location of the properties file
   */
  public static synchronized Properties loadProperties(String propertiesPath) {
    if (!loaded) {
      try (InputStream input = PropertiesLoader.class.getClassLoader().getResourceAsStream(propertiesPath)) {
        if (input == null) {
          throw new IllegalStateException("Failed to load properties file from: " + propertiesPath);
        }
        properties.load(input);
        loaded = true;
      } catch (IOException ex) {
        throw new RuntimeException("Error loading properties from " + propertiesPath, ex);
      }
    }
    return properties;
  }
}
