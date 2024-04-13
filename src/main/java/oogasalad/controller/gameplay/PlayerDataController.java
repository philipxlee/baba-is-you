package oogasalad.controller.gameplay;

import java.util.Date;
import oogasalad.database.DataManager;
import oogasalad.model.gameplay.player.PlayerData;
import java.util.List;

/**
 * Controller class for managing player data, including starting and ending player sessions and
 * saving player data to a database.
 */
public class PlayerDataController {

  private DataManager dataManager;
  private PlayerData playerData;
  private long startTime;


  /**
   * Constructs a new PlayerDataController with the given DataManager.
   *
   * @param dataManager The DataManager to use for saving player data.
   */
  public PlayerDataController(DataManager dataManager) {
    this.dataManager = dataManager;
  }

  /**
   * Initializes a new player session with a given username.
   *
   * @param username the username of the player
   * @return
   */
  public boolean startNewPlayer(String username) {
    if (dataManager.isUsernameAvailable(username)) {
      this.playerData = new PlayerData(username, 0, "", new Date());
      this.startTime = System.currentTimeMillis() / 1000; // make to seconds
      return true;
    } else {
      return false;
    }
  }

  /**
   * Checks if a username is available in the database.
   *
   * @param username The username to check.
   * @return True if the username is available, false otherwise.
   */
  public boolean isUsernameAvailable(String username) {
    return dataManager.isUsernameAvailable(username);
  }

  /**
   * Retrieves top player statistics for leaderboard display.
   *
   * @return a list of PlayerData objects for the top players.
   */
  public List<PlayerData> getTopPlayers() {
    return dataManager.getTopPlayers();
  }

  /**
   * Ends the current player session and captures any final data, such as time spent and comments.
   *
   * @param comments comments provided by the player about the level
   */
  public void endPlayerSession(String comments) {
    if (playerData != null) {
      long endTime = System.currentTimeMillis() / 1000;
      long timeSpent = endTime - startTime;
      playerData.setTimeSpent(timeSpent);
      playerData.setComments(comments);
      dataManager.savePlayerData(playerData); // Persist player data to MongoDB
    } else {
      System.err.println("No player session started.");
    }
  }
}
