package oogasalad.database;

import com.mongodb.client.FindIterable;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoDatabase;
import com.mongodb.client.model.Filters;
import com.mongodb.client.model.Sorts;
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
  public List<PlayerData> getTopPlayers() {
    MongoCollection<Document> collection = database.getCollection("data");
    FindIterable<Document> result = collection.find()
        .sort(Sorts.ascending(TIME_SPENT))
        .limit(LEADERBOARD_SIZE);
    List<PlayerData> topPlayers = new ArrayList<>();
    for (Document doc : result) {
      // Default values assigned if fields are null
      String username = doc.getString("username");
      String comments = doc.getString("comments") != null ? doc.getString("comments") : "No comments";
      Date date = doc.getDate("date") != null ? doc.getDate("date") : new Date();  // Use current date as default
      long timeSpent = doc.getLong("timeSpent");
      topPlayers.add(new PlayerData(username, timeSpent, comments, date));
    }
    return topPlayers;
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
