package oogasalad.database.dataservice;

import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoDatabase;
import java.util.Date;
import oogasalad.database.gamedata.CommentData;
import oogasalad.database.gamedata.GameSession;
import oogasalad.database.gamedata.LeaderboardData;
import org.bson.Document;

/**
 * This class is responsible for uploading data to the database.
 */
public class DataUploader {

  MongoDatabase database;
  GameSession gameSession;

  /**
   * Constructor for the DataUploader class.
   *
   * @param database    database
   * @param gameSession game session
   */
  public DataUploader(MongoDatabase database, GameSession gameSession) {
    this.database = database;
    this.gameSession = gameSession;
  }

  /**
   * Saves the player leaderboard data to the database.
   *
   * @param startTime start time
   * @param endTime   end time
   */
  public void savePlayerLeaderboardData(long startTime, long endTime) {
    MongoCollection<Document> collection = database.getCollection("data");
    LeaderboardData leaderboardData = gameSession.getLeaderboardData();
    collection.insertOne(leaderboardData.getLeaderboardDocument(startTime, endTime));
    System.out.println("Player data saved successfully.");
  }

  /**
   * Saves the level comment to the database.
   *
   * @param comment comment
   */
  public void saveLevelComment(String comment) {
    MongoCollection<Document> collection = database.getCollection("comment");
    CommentData commentData = gameSession.getLevelCommentData();
    collection.insertOne(commentData.getCommentDocument(comment));
    System.out.println("Level comments saved successfully.");
  }

  /**
   * Adds a reply to a user comment.
   *
   * @param commenterUsername username of the commenter
   * @param playerUsername    username of the player
   * @param reply             reply
   */
  public void addReplyToUserComment(String commenterUsername, String playerUsername, String reply) {
    MongoCollection<Document> collection = database.getCollection("comment");
    Document replyDocument = new Document("username", validateUsername(playerUsername))
        .append("reply", reply)
        .append("date", new Date());
    Document updateOperation = new Document("$push", new Document("replies", replyDocument));
    Document filter = new Document("username", commenterUsername);
    collection.updateOne(filter, updateOperation);
    System.out.println("Reply added successfully");
  }

  private String validateUsername(String username) {
    return username == null ? "Anonymous" : username;
  }

}
