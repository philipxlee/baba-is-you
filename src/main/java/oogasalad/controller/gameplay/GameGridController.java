package oogasalad.controller.gameplay;

import com.google.gson.JsonObject;
import javafx.scene.input.KeyCode;
import oogasalad.model.gameplay.exceptions.InvalidBlockName;
import oogasalad.model.gameplay.grid.Grid;
import oogasalad.model.gameplay.level.JsonGameParser;
import oogasalad.model.gameplay.level.Level;
import oogasalad.model.gameplay.level.LevelMetadata;
import oogasalad.shared.alert.AlertHandler;
import oogasalad.view.gameplay.mainscene.GamePane;

/**
 * Controller that manages state changes from the GamePlay View to the Model, specifically the
 * grid.x
 */
public class GameGridController implements AlertHandler {

  private Grid gameGrid;
  private final GamePane gamePane;
  private final KeyHandlerController keyHandlerController;
  String[][][] empty = {};
  private final Level level;
  private final JsonGameParser jsonGameParser = new JsonGameParser();

  public GameGridController(GamePane gamePane, KeyHandlerController keyHandlerController,
      Level level) {
    this.gamePane = gamePane;
    this.keyHandlerController = keyHandlerController;
    this.level = level;
    createGrid();
    gameGrid.addObserver(gamePane);
  }

  private void createGrid() {
    LevelMetadata metadata = level.getLevelMetadata();
    try {
      this.gameGrid = new Grid(metadata.rows(), metadata.columns(),
          metadata.initialConfiguration());
    } catch (InvalidBlockName e) {
      showError("ERROR", e.getMessage());
    }
  }

  public void sendPlayToModel(KeyCode code) {
    gameGrid.checkForRules(); // Check for rules
    keyHandlerController.executeKey(gameGrid, code); // Handle key press
  }

  public void resetBlocks() {
    gameGrid.resetAllBlocks();
  }

  public void showError(String title, String message) {
    AlertHandler.super.showError(title, message);
  }

  //Access only method
  public Grid getGameGrid() {
    return this.gameGrid;
  }

  public Level parseJson(JsonObject jsonObject) {
    return jsonGameParser.parseLevel(jsonObject);
  }

}
