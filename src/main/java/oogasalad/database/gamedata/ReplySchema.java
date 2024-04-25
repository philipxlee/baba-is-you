package oogasalad.database.gamedata;

import java.util.Properties;
import oogasalad.database.records.ReplyRecord;
import org.bson.Document;

/**
 * Represents a reply within a comment in the game data.
 *
 * @author Philip Lee.
 */
public class ReplySchema extends AbstractGameData {

  private final String replyText;

  /**
   * Constructor for the ReplySchema class.
   *
   * @param replyRecord the record of the reply data
   */
  public ReplySchema(ReplyRecord replyRecord) {
    super(replyRecord.getReplyUsername(), replyRecord.getReplyLevelName(),
        replyRecord.getReplyDate());
    this.replyText = replyRecord.getReplyText();
  }

  /**
   * Converts the reply to a Document.
   *
   * @return The reply as a Document.
   */
  @Override
  public Document toDocument() {
    Properties properties = getDatabaseProperties();
    return new Document(properties.getProperty("field.username"), getUsername())
        .append(properties.getProperty("field.levelName"), getLevelName())
        .append(properties.getProperty("field.date"), getDate())
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
