package oogasalad.database.gamedata;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Properties;
import oogasalad.shared.loader.PropertiesLoader;
import org.bson.Document;

/**
 * Abstract class to handle common game data functionality.
 *
 * @author Philip Lee.
 */
public abstract class AbstractGameData {

  private static final String DATE_FORMAT = "MMMM dd, yyyy";
  private static final String DATABASE_PROPERTIES_PATH = "database/database.properties";

  private final String username;
  private final String levelName;
  private final Date date;
  private final Properties properties;

  /**
   * Constructor for AbstractGameData.
   *
   * @param username  username
   * @param levelName level name
   * @param date      date
   */
  public AbstractGameData(String username, String levelName, Date date) {
    this.username = username;
    this.levelName = levelName;
    this.date = date;
    this.properties = PropertiesLoader.loadProperties(DATABASE_PROPERTIES_PATH);
  }

  /**
   * Formats the date into a human-readable string.
   *
   * @return Formatted date string.
   */
  public String getDate() {
    SimpleDateFormat dateFormat = new SimpleDateFormat(DATE_FORMAT);
    return dateFormat.format(date);
  }

  /**
   * Gets the username.
   *
   * @return username associated with this data.
   */
  public String getUsername() {
    return username;
  }

  /**
   * Gets the level name.
   *
   * @return level name associated with this data.
   */
  public String getLevelName() {
    return levelName;
  }

  /**
   * Gets the database properties.
   *
   * @return database properties.
   */
  public Properties getDatabaseProperties() {
    return properties;
  }

  /**
   * Converts the data to a MongoDB Document.
   *
   * @return Document representing this data.
   */
  public abstract Document toDocument();
}
