package oogasalad.database.dataservice;

import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoDatabase;
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
    System.out.println("Attempting to save player data");
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

}
