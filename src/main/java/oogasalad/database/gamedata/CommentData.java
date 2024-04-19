package oogasalad.database.gamedata;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import org.bson.Document;

public class CommentData {

  private final String username;
  private final String levelName;
  private final Date date;
  private final String comment;
  private final ArrayList<String> replies;

  public CommentData(String username, String levelName, Date defaultDate, String comment,
      ArrayList<String> replies) {
    this.username = username;
    this.levelName = levelName;
    this.date = defaultDate;
    this.comment = comment;
    this.replies = replies;
  }

  public Document getCommentDocument(String comment) {
    Document doc = new Document();
    doc.append("username", username);
    doc.append("levelName", levelName);
    doc.append("date", date);
    doc.append("comment", comment);
    doc.append("replies", replies);
    return doc;
  }

  public String getUsername() {
    return username;
  }

  public String getLevelName() {
    return levelName;
  }

  public String getDate() {
    SimpleDateFormat dateFormat = new SimpleDateFormat("MMMM, dd yyyy");
    return dateFormat.format(date);
  }

  public String getComment() {
    return comment;
  }

  public String getReplies() {
    StringBuilder allReplies = new StringBuilder();
    for (String reply : replies) {
      allReplies.append(reply).append("\n");
    }
    return allReplies.toString();
  }

}
