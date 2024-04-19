package oogasalad.database.gamedata;

import java.text.SimpleDateFormat;
import java.util.Date;
import org.bson.Document;

/**
 * This class is responsible for storing the reply schema.
 */
public class ReplySchema {

  private final String username;
  private final String replyText;
  private final Date replyDate;

  /**
   * Constructor for the ReplySchema class.
   *
   * @param username  username
   * @param replyText replyText
   * @param replyDate replyDate
   */
  public ReplySchema(String username, String replyText, Date replyDate) {
    this.username = username;
    this.replyText = replyText;
    this.replyDate = replyDate;
  }

  /**
   * Gets the reply date.
   *
   * @return reply date
   */
  public String getFormattedReplyDate() {
    SimpleDateFormat dateFormat = new SimpleDateFormat("MMMM dd, yyyy HH:mm:ss");
    return dateFormat.format(replyDate);
  }

  /**
   * Converts the reply schema to a document.
   *
   * @return document
   */
  public Document toDocument() {
    return new Document("username", username)
        .append("reply", replyText)
        .append("date", replyDate);
  }

  /**
   * Gets the username.
   *
   * @return username
   */
  public String getUsername() {
    return username;
  }

  /**
   * Gets the reply text.
   *
   * @return reply text
   */
  public String getReplyText() {
    return replyText;
  }

}
