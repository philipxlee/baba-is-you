package oogasalad.database;

import com.mongodb.ConnectionString;
import com.mongodb.MongoClientSettings;
import com.mongodb.MongoException;
import com.mongodb.client.MongoClient;
import com.mongodb.client.MongoClients;
import com.mongodb.client.MongoDatabase;
import org.bson.Document;

public class DatabaseConnection {

  private static MongoClient mongoClient;

  // Initialize the MongoClient statically, so it's created only once
  static {
    String connectionString = "mongodb+srv://philiplee:HL1vByyTSqMVT5Bk@babacluster.rj8ylis.mongodb.net/?retryWrites=true&w=majority&appName=BabaCluster";
    MongoClientSettings settings = MongoClientSettings.builder()
        .applyConnectionString(new ConnectionString(connectionString))
        .build();
    mongoClient = MongoClients.create(settings);
  }

  public static MongoDatabase getDatabase() {
    return mongoClient.getDatabase("BabaData");
  }

  public static void sendTestData(MongoDatabase database) {
    Document testDoc = new Document("name", "Test User2")
        .append("type", "test2")
        .append("timestamp", System.currentTimeMillis());
    try {
      database.getCollection("data").insertOne(testDoc);
      System.out.println("Test document inserted successfully into the collection 'data'.");
    } catch (MongoException e) {
      System.err.println("Error inserting document into MongoDB: " + e.getMessage());
      e.printStackTrace();
    }
  }

  public static void main(String[] args) {
    try {
      MongoDatabase database = getDatabase();
      // Sending a ping to confirm a successful connection
      database.runCommand(new Document("ping", 1));
      System.out.println("Pinged your deployment. You successfully connected to MongoDB!");
      sendTestData(database);
    } catch (MongoException e) {
      e.printStackTrace();
    }
  }
}
