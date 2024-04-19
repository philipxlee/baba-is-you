package oogasalad.database.gamedata;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Properties;
import java.util.stream.Collectors;
import oogasalad.shared.util.PropertiesLoader;
import org.bson.Document;

/**
 * Stores and manages comment data for a game session, including replies.
 */
public class CommentData extends AbstractGameData {

  private static final String DATABASE_PROPERTIES_PATH = "database/database.properties";

  private final Properties properties;
  private final String comment;
  private final List<ReplySchema> replies;

  /**
   * Constructor for the CommentData class.
   *
   * @param username  the username of the person who made the comment
   * @param levelName the level at which the comment was made
   * @param date      the date the comment was made
   * @param comment   the text of the comment
   * @param replies   a list of replies to the comment
   */
  public CommentData(String username, String levelName, Date date, String comment, List<ReplySchema> replies) {
    super(username, levelName, date);
    this.comment = comment;
    this.replies = new ArrayList<>(replies);
    this.properties = PropertiesLoader.loadProperties(DATABASE_PROPERTIES_PATH);
  }

  @Override
  public Document toDocument() {
    List<Document> replyDocs = replies.stream()
        .map(ReplySchema::toDocument)
        .collect(Collectors.toList());

    return new Document(properties.getProperty("field.username"), username)
        .append(properties.getProperty("field.levelName"), levelName)
        .append(properties.getProperty("field.date"), date)
        .append(properties.getProperty("field.comment"), comment)
        .append(properties.getProperty("field.replies"), replyDocs);
  }

  /**
   * Gets the text of the comment.
   *
   * @return the comment text
   */
  public String getComment() {
    return comment;
  }

  /**
   * Gets the list of replies to this comment.
   *
   * @return a list of ReplySchema objects representing replies to this comment
   */
  public List<ReplySchema> getReplies() {
    return new ArrayList<>(replies);  // Return a copy to preserve encapsulation
  }

}
