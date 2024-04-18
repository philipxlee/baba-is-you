package oogasalad.database;

import com.mongodb.client.FindIterable;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoDatabase;
import com.mongodb.client.model.Filters;
import com.mongodb.client.model.Sorts;
import com.mongodb.client.model.UpdateOptions;
import com.mongodb.client.model.Updates;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import org.bson.Document;

public class DataManager {

  private static final Integer LEADERBOARD_SIZE = 10;
  private static final String TIME_SPENT = "timeSpent";
  private final MongoDatabase database;
  /**
   * Constructs a new DataManager object with the given database.
   *
   * @param database The MongoDB database to use.
   */
  public DataManager(MongoDatabase database) {
    this.database = database;
  }

  /**
   * Checks if a username is available in the database.
   *
   * @param username The username to check.
   * @return True if the username is available, false otherwise.
   */
  public boolean isUsernameAvailable(String username) {
    MongoCollection<Document> collection = database.getCollection("data");
    long count = collection.countDocuments(Filters.eq("username", username));
    return count == 0; // Return true if no documents found with that username
  }

  /**
   * Retrieves the top 10 players based on their time spent.
   *
   * @return a list of PlayerData objects representing the top players.
   */
  public List<LeaderboardData> getTopPlayers() {
    MongoCollection<Document> collection = database.getCollection("data");
    FindIterable<Document> result = collection.find()
        .sort(Sorts.ascending(TIME_SPENT))
        .limit(LEADERBOARD_SIZE);
    List<LeaderboardData> topPlayers = new ArrayList<>();
    for (Document doc : result) {
      String username = doc.getString("username");
      String levelName = doc.getString("levelName");
      Date date = doc.getDate("date");
      long timeSpent = doc.getLong(TIME_SPENT);
      topPlayers.add(new LeaderboardData(username, date, levelName, timeSpent));
    }
    return topPlayers;
  }

  /**
   * Retrieves comments for a specific level.
   *
   * @param levelName The name of the level to retrieve comments for.
   * @return a list of CommentData objects representing the comments for the level.
   */
  public List<CommentData> getCommentsByLevel(String levelName) {
    MongoCollection<Document> collection = database.getCollection("comments");
    FindIterable<Document> result = collection.find(Filters.eq("levelName", levelName));
    List<CommentData> comments = new ArrayList<>();
    for (Document doc : result) {
      List<Document> commentDocs = doc.getList("comments", Document.class);
      for (Document commentDoc : commentDocs) {
        String username = commentDoc.getString("username");
        String levelComments = commentDoc.getString("comments");
        String levelNames = commentDoc.getString("levelName");
        String reply = commentDoc.getString("reply");
        Date date = commentDoc.getDate("date");
        comments.add(new CommentData(username, levelComments, date, levelNames, reply));
      }
    }
    return comments;
  }


  /**
   * Saves player data to MongoDB.
   *
   * @param playerData The player data to save.
   */
  public void savePlayerData(PlayerData playerData) {
    MongoCollection<Document> collection = database.getCollection("data");
    collection.insertOne(playerData.getDocument());
    System.out.println("Player data saved successfully.");
  }


}
