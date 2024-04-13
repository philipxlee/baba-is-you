package oogasalad.database;

import com.mongodb.ConnectionString;
import com.mongodb.MongoClientSettings;
import com.mongodb.client.MongoClient;
import com.mongodb.client.MongoClients;
import com.mongodb.client.MongoDatabase;
import oogasalad.controller.gameplay.PlayerDataController;

/**
 * Class to configure the MongoDB connection and get a database instance.
 */
public class DatabaseConfig {

  private static MongoClient mongoClient;

  static {
    String connectionString = "mongodb+srv://philiplee:HL1vByyTSqMVT5Bk@babacluster.rj8ylis.mongodb.net/?retryWrites=true&w=majority&appName=BabaCluster";
    MongoClientSettings settings = MongoClientSettings.builder()
        .applyConnectionString(new ConnectionString(connectionString))
        .build();
    mongoClient = MongoClients.create(settings);
  }

  /**
   * Get the database instance.
   *
   * @return The database instance.
   */
  public static MongoDatabase getDatabase() {
    return mongoClient.getDatabase("BabaData");
  }

  /**
   * Close the MongoDB connection.
   */
  public static void closeConnection() {
    if (mongoClient != null) {
      mongoClient.close();
    }
  }

  /**
   * Temporary main method to test the database connection.
   */
  public static void main(String[] args) {
    MongoDatabase db = getDatabase();
    DataManager manager = new DataManager(db);
    PlayerDataController controller = new PlayerDataController(manager);
    controller.startNewPlayer("Bob");
    controller.endPlayerSession("Great level!");
    DatabaseConfig.closeConnection();
  }

}
