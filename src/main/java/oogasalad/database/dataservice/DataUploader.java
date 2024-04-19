package oogasalad.database.dataservice;

import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoDatabase;
import java.util.Date;
import oogasalad.database.gamedata.CommentData;
import oogasalad.database.gamedata.GameSession;
import oogasalad.database.gamedata.LeaderboardData;
import org.bson.Document;

public class DataUploader {

  MongoDatabase database;
  GameSession gameSession;

  public DataUploader(MongoDatabase database, GameSession gameSession) {
    this.database = database;
    this.gameSession = gameSession;
  }

  public void savePlayerLeaderboardData(long startTime, long endTime) {
    MongoCollection<Document> collection = database.getCollection("data");
    LeaderboardData leaderboardData = gameSession.getLeaderboardData();
    collection.insertOne(leaderboardData.getLeaderboardDocument(startTime, endTime));
    System.out.println("Player data saved successfully.");
  }

  public void saveLevelComment(String comment) {
    MongoCollection<Document> collection = database.getCollection("comment");
    CommentData commentData = gameSession.getLevelCommentData();
    collection.insertOne(commentData.getCommentDocument(comment));
    System.out.println("Level comments saved successfully.");
  }

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
