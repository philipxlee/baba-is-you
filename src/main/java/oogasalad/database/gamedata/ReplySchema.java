package oogasalad.database.gamedata;

import java.util.Date;
import org.bson.Document;

/**
 * Represents a reply within a comment in the game data.
 */
public class ReplySchema extends AbstractGameData {
  private String replyText;

  public ReplySchema(String username, String levelName, Date date, String replyText) {
    super(username, levelName, date);
    this.replyText = replyText;
  }

  @Override
  public Document toDocument() {
    return new Document("username", username)
        .append("levelName", levelName)
        .append("date", date)
        .append("reply", replyText);
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
