package oogasalad.database.dataservice;

import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoDatabase;
import com.mongodb.client.model.Filters;
import com.mongodb.client.model.Sorts;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;
import oogasalad.database.gamedata.LeaderboardData;
import org.bson.Document;

public class DataFetcher {

  private final MongoDatabase database;

  public DataFetcher(MongoDatabase database) {
    this.database = database;
  }

  public boolean isUsernameAvailable(String username) {
    MongoCollection<Document> collection = database.getCollection("data");
    long count = collection.countDocuments(Filters.eq("username", username));
    return count == 0;
  }

  public List<LeaderboardData> getTopPlayers() {
    MongoCollection<Document> collection = database.getCollection("data");
    return StreamSupport.stream(collection.find().sort(Sorts.descending("timeSpent")).limit(10).spliterator(), false)
        .map(document -> new LeaderboardData())
        .collect(Collectors.toList());
  //   .map(document -> new LeaderboardData(document.getString("username"), document.getString("levelName"), document.getDate("date"), document.getInteger("timeSpent")))
  }
}
