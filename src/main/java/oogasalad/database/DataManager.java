package oogasalad.database;

import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoDatabase;
import oogasalad.model.gameplay.player.PlayerData;
import org.bson.Document;

public class DataManager {

  private MongoDatabase database;

  /**
   * Constructs a new DataManager object with the given database.
   *
   * @param database The MongoDB database to use.
   */
  public DataManager(MongoDatabase database) {
    this.database = database;
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
