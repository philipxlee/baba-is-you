package oogasalad.database.gamedata;

import java.text.SimpleDateFormat;
import java.util.Date;
import org.bson.Document;

public class ReplySchema {
  private String username;
  private String replyText;
  private Date replyDate;

  public ReplySchema(String username, String replyText, Date replyDate) {
    this.username = username;
    this.replyText = replyText;
    this.replyDate = replyDate;
  }

  public String getUsername() {
    return username;
  }

  public String getReplyText() {
    return replyText;
  }

  public String getFormattedReplyDate() {
    SimpleDateFormat dateFormat = new SimpleDateFormat("MMMM dd, yyyy HH:mm:ss");
    return dateFormat.format(replyDate);
  }

  public Document toDocument() {
    return new Document("username", username)
        .append("reply", replyText)
        .append("date", replyDate);
  }
}
