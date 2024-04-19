package oogasalad.database.dataservice;

import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoDatabase;
import com.mongodb.client.model.Filters;
import com.mongodb.client.model.Sorts;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;
import oogasalad.database.gamedata.GameSession;
import oogasalad.database.gamedata.LeaderboardData;
import org.bson.Document;

public class DataFetcher {

  private final MongoDatabase database;

  public DataFetcher(MongoDatabase database, GameSession gameSession) {
    this.database = database;
  }

  public boolean isUsernameAvailable(String username) {
    MongoCollection<Document> collection = database.getCollection("data");
    long count = collection.countDocuments(Filters.eq("username", username));
    return count == 0;
  }

  public List<LeaderboardData> getTopPlayers(String currentLevelName) {
    MongoCollection<Document> collection = database.getCollection("data");
    return StreamSupport.stream(collection.find(Filters.eq("levelName", currentLevelName))
            .sort(Sorts.ascending("timeSpent"))
            .limit(10)
            .spliterator(), false)
        .map(document -> new LeaderboardData(
            document.getString("username"),
            document.getString("levelName"),
            document.getDate("date"),
            document.getLong("timeSpent")))
        .collect(Collectors.toList());
  }
}
