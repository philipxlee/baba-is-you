package oogasalad.database.gamedata;

import java.util.Date;
import java.util.Properties;
import oogasalad.shared.util.PropertiesLoader;
import org.bson.Document;

/**
 * Represents a reply within a comment in the game data.
 */
public class ReplySchema extends AbstractGameData {

  private static final String DATABASE_PROPERTIES_PATH = "database/database.properties";
  private final Properties properties;
  private final String replyText;

  /**
   * Constructor for the ReplySchema class.
   *
   * @param username  the username of the person who made the reply
   * @param levelName the level at which the reply was made
   * @param date      the date the reply was made
   * @param replyText the text of the reply
   */
  public ReplySchema(String username, String levelName, Date date, String replyText) {
    super(username, levelName, date);
    this.replyText = replyText;
    this.properties = PropertiesLoader.loadProperties(DATABASE_PROPERTIES_PATH);
  }

  /**
   * Converts the reply to a Document.
   *
   * @return The reply as a Document.
   */
  @Override
  public Document toDocument() {
    return new Document(properties.getProperty("field.username"), username)
        .append(properties.getProperty("field.levelName"), levelName)
        .append(properties.getProperty("field.date"), date)
        .append(properties.getProperty("field.reply"), replyText);
  }

  /**
   * Gets the reply text.
   *
   * @return Reply text of the comment.
   */
  public String getReplyText() {
    return replyText;
  }
}
