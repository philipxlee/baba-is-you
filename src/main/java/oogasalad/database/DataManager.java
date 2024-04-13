package oogasalad.database;

import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoDatabase;
import com.mongodb.client.model.Filters;
import oogasalad.model.gameplay.player.PlayerData;
import org.bson.Document;

public class DataManager {

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
