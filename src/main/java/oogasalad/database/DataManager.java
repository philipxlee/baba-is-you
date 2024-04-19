package oogasalad.database;

import com.mongodb.client.FindIterable;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoDatabase;
import com.mongodb.client.model.Filters;
import com.mongodb.client.model.Sorts;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;
import oogasalad.database.gamedata.LeaderboardData;
import org.bson.Document;

public class DataManager {

  private static final Integer LEADERBOARD_SIZE = 10;
  private final MongoDatabase database;

  /**
   * Constructs a new DataManager object with the given database.
   *
   * @param database The MongoDB database to use.
   */
  public DataManager(MongoDatabase database) {
    this.database = database;
  }



}
