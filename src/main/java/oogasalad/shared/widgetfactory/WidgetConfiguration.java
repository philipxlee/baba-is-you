package oogasalad.shared.widgetfactory;

import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;
import javafx.scene.text.Font;
import oogasalad.shared.alert.AlertHandler;
import oogasalad.shared.loader.PropertiesLoader;

/**
 * Configuration object class for use within the WidgetFactory
 */
public class WidgetConfiguration implements AlertHandler {

  private final Properties styleProperties;
  private int width;
  private int height;
  private String cssMatch;
  private String propertyContents;
  private final Font propertyFont;
  private final String language;

  /**
   * Constructor with a property files match.
   *
   * @param width        width of the Javafx obj
   * @param height       its height
   * @param propertyName name to fetch from properties
   * @param cssMatch     which css style to apply
   * @param language     language to use in the properties fetching
   */
  public WidgetConfiguration(int width, int height, String propertyName, String cssMatch,
      String language) {
    this.language = language;
    this.width = width;
    this.height = height;
    this.cssMatch = cssMatch;
    this.styleProperties = loadProperties();
    this.propertyContents = styleProperties.getProperty(propertyName);
    this.propertyFont = Font.loadFont(
        getClass().getResourceAsStream(styleProperties.getProperty("Font")), 12);
  }

  /**
   * Constructor without a propertyName parameter. Used for objects containing no text
   *
   * @param width    width of the Javafx obj
   * @param height   its height
   * @param cssMatch which css style to apply
   * @param language language to use in the properties fetching
   */
  public WidgetConfiguration(int width, int height, String cssMatch, String language) {
    this.language = language;
    this.width = width;
    this.height = height;
    this.cssMatch = cssMatch;
    this.styleProperties = loadProperties();
    this.propertyFont = Font.loadFont(
        getClass().getResourceAsStream(styleProperties.getProperty("Font")), 12);
  }

  /**
   * Constructor with just a string property. Used for things like headers/labels.
   *
   * @param propertyName name to fetch from properties
   * @param language     language to use in the properties fetching
   */
  public WidgetConfiguration(String propertyName, String language) {
    this.language = language;
    this.styleProperties = loadProperties();
    this.propertyContents = styleProperties.getProperty(propertyName);
    this.propertyFont = Font.loadFont(
        getClass().getResourceAsStream(styleProperties.getProperty("Font")), 12);
  }

  /**
   * Gets the width of this configuration.
   *
   * @return width
   */
  public int getWidth() {
    return width;
  }

  /**
   * Gets the height of this configuration.
   *
   * @return height
   */
  public int getHeight() {
    return height;
  }

  /**
   * Gets the css match for this configuration
   *
   * @return css match
   */
  public String getCssMatch() {
    return cssMatch;
  }

  /**
   * Gets the property name for this configuration.
   *
   * @return property match
   */
  public String getPropertyContents() {
    return propertyContents;
  }

  /**
   * Shows an error if any of the property matching/css retrieval goes wrong.
   *
   * @param title   the title of the alert dialog.
   * @param message the message to be displayed in the alert dialog.
   */
  public void showError(String title, String message) {
    AlertHandler.super.showError(title, message);
  }

  /**
   * Loads the language properties
   *
   * @return Properties object for this configuration's language
   */
  private Properties loadProperties() {
    return PropertiesLoader.loadProperties("languages/" + language + ".properties");
  }

}
