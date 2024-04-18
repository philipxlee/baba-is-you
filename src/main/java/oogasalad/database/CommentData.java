package oogasalad.database;

import java.text.SimpleDateFormat;
import java.util.Date;

public class CommentData {
  private String username;
  private String levelComments;
  private Date date;
  private String levelNames;
  private String reply;

  public CommentData(String username, String levelComments, Date date, String levelNames, String reply) {
    this.username = username;
    this.levelComments = levelComments;
    this.date = date;
    this.levelNames = levelNames;
    this.reply = reply;
  }

  public String getUsername() {
    return username;
  }

  public String getLevelComments() {
    return levelComments;
  }

  public String getDate() {
    SimpleDateFormat dateFormat = new SimpleDateFormat("MMMM, dd yyyy");
    return dateFormat.format(date);
  }

  public String getLevelNames() {
    return levelNames;
  }

  public String getReply() {
    return reply;
  }

}
