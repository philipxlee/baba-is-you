package oogasalad.controller.gameplay;

import oogasalad.database.DataManager;
import oogasalad.model.gameplay.player.PlayerData;

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
      this.playerData = new PlayerData(username);
      this.startTime = System.currentTimeMillis();
      System.out.printf("username available: %s\n", username);
      return true;
    } else {
      System.err.printf("username not available: %s\n", username);
      return false;
    }
  }

  public boolean isUsernameAvailable(String username) {
    return dataManager.isUsernameAvailable(username);
  }

  /**
   * Ends the current player session and captures any final data, such as time spent and comments.
   *
   * @param comments comments provided by the player about the level
   */
  public void endPlayerSession(String comments) {
    if (playerData != null) {
      long endTime = System.currentTimeMillis();
      long timeSpent = endTime - startTime;
      playerData.setTimeSpent(timeSpent);
      playerData.setComments(comments);
      dataManager.savePlayerData(playerData); // Persist player data to MongoDB
      System.out.println("Session ended and data saved for " + playerData.getUsername());
    } else {
      System.err.println("No player session started.");
    }
  }
}
