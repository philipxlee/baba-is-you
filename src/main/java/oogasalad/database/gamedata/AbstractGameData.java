package oogasalad.database.gamedata;

import java.text.SimpleDateFormat;
import java.util.Date;
import org.bson.Document;

/**
 * Abstract class to handle common game data functionality.
 *
 * @author Philip Lee.
 */
public abstract class AbstractGameData {

  private static final String DATE_FORMAT = "MMMM dd, yyyy";

  private String username;
  private String levelName;
  private Date date;

  /**
   * Constructor for AbstractGameData.
   *
   * @param username username
   * @param levelName level name
   * @param date date
   */
  public AbstractGameData(String username, String levelName, Date date) {
    this.username = username;
    this.levelName = levelName;
    this.date = date;
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
   * Converts the data to a MongoDB Document.
   *
   * @return Document representing this data.
   */
  public abstract Document toDocument();
}
