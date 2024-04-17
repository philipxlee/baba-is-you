package oogasalad.shared.widgetfactory;

import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;
import javafx.scene.text.Font;
import oogasalad.shared.alert.AlertHandler;
import oogasalad.view.gameplay.StartingScene;

/**
 * Configuration object class for use within the WidgetFactory
 */
public class WidgetConfiguration implements AlertHandler {

  private Properties styleProperties;
  private int width;
  private int height;
  private String cssMatch;
  private String propertyContents;
  private Font propertyFont;
  private String language = StartingScene.language;

  /**
   * Constructor with a property files match.
   *
   * @param width
   * @param height
   * @param propertyName
   * @param cssMatch
   */
  public WidgetConfiguration(int width, int height, String propertyName, String cssMatch) {
    this.width = width;
    this.height = height;
    this.cssMatch = cssMatch;
    this.styleProperties = loadProperties(language);
    this.propertyContents = styleProperties.getProperty(propertyName);
    this.propertyFont = Font.loadFont(
        getClass().getResourceAsStream(styleProperties.getProperty("Font")), 12);
  }

  public WidgetConfiguration(int width, int height, String cssMatch) {
    this.width = width;
    this.height = height;
    this.cssMatch = cssMatch;
    this.styleProperties = loadProperties(language);
    this.propertyFont = Font.loadFont(
        getClass().getResourceAsStream(styleProperties.getProperty("Font")), 12);
  }

  /**
   * Constructor with just a string property.
   */
  public WidgetConfiguration(String propertyName) {
    this.styleProperties = loadProperties(language);
    this.propertyContents = styleProperties.getProperty(propertyName);
    this.propertyFont = Font.loadFont(
        getClass().getResourceAsStream(styleProperties.getProperty("Font")), 12);
  }

  public int getWidth() {
    return width;
  }

  public int getHeight() {
    return height;
  }

  public String getCssMatch() {
    return cssMatch;
  }

  public String getPropertyContents() {
    return propertyContents;
  }

  public void showError(String title, String message) {
    AlertHandler.super.showError(title, message);
  }

  private Properties loadProperties(String language) {
    Properties properties = new Properties();
    try (InputStream inputStream = getClass().getResourceAsStream(
        "/languages/" + language + ".properties")) {
      properties.load(inputStream);
    } catch (IOException e) {
      showError("ERROR", e.getMessage());
    }
    return properties;
  }

  public Font getFont() {
    return propertyFont;
  }

}