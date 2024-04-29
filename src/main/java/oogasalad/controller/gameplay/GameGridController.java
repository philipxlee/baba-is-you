package oogasalad.controller.gameplay;

import com.google.gson.JsonObject;
import javafx.scene.input.KeyCode;
import oogasalad.model.gameplay.exceptions.InvalidBlockName;
import oogasalad.model.gameplay.exceptions.JsonParsingException;
import oogasalad.model.gameplay.grid.Grid;
import oogasalad.model.gameplay.level.JsonGameParser;
import oogasalad.model.gameplay.level.Level;
import oogasalad.model.gameplay.level.LevelMetadata;
import oogasalad.shared.alert.AlertHandler;
import oogasalad.view.gameplay.mainscene.GamePane;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

/**
 * Controller that manages state changes from the GamePlay View to the Model, specifically the
 * grid.
 */
public class GameGridController implements AlertHandler {
  private Grid gameGrid;
  private final GamePane gamePane;
  private final KeyHandlerController keyHandlerController;
  private final Level level;
  private final JsonGameParser jsonGameParser = new JsonGameParser();
  private static final Logger logger = LogManager.getLogger(GameGridController.class);

  /**
   * Creates the Game grid controler.
   * @param gamePane GamePane to send data from
   * @param keyHandlerController keyHandlerController to handle key presses
   * @param level current level for the game
   */
  public GameGridController(GamePane gamePane, KeyHandlerController keyHandlerController,
      Level level) {
    this.gamePane = gamePane;
    this.keyHandlerController = keyHandlerController;
    this.level = level;
    createGrid();
    gameGrid.addObserver(gamePane);

  }

  /**
   * Creates the backend grid from the level's metadata.
   */
  private void createGrid() {
    LevelMetadata metadata = level.getLevelMetadata();
    try {
      this.gameGrid = new Grid(metadata.rows(), metadata.columns(),
          metadata.initialConfiguration());
    } catch (InvalidBlockName e) {
      showError("ERROR", e.getMessage());
      logger.error("Failed to create grid: " + e.getMessage());
    }
  }

  /**
   * Sends keycode information to the model.
   * @param code KeyCode pressed in the View
   */
  public void sendPlayToModel(KeyCode code) {
    gameGrid.checkForRules(); // Check for rules
    keyHandlerController.executeKey(gameGrid, code); // Handle key press
  }

  /**
   * Resets all blocks to their original configurations
   */
  public void resetBlocks() {
    gameGrid.resetAllBlocks();
    gameGrid.setCrabAttribute();
  }

  /**
   * Displays an error message.
   * @param title   the title of the alert dialog.
   * @param message the message to be displayed in the alert dialog.
   */
  public void showError(String title, String message) {
    AlertHandler.super.showError(title, message);
  }

  /**
   * Access only: gets the game grid.
   * @return the current Grid object
   */
  public Grid getGameGrid() {
    return this.gameGrid;
  }

  /**
   * Parses an input jsonObject into a Level object.
   */
  public Level parseJson(JsonObject jsonObject) throws JsonParsingException {
    return jsonGameParser.parseLevel(jsonObject);
  }

  /**
   * Sets the game over status for the game pane.
   * @param status new status to set to
   */
  public void setGameOverStatus(boolean status){
    gamePane.setGameOverStatus(status);
  }


}
