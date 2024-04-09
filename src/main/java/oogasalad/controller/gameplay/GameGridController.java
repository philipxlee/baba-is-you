package oogasalad.controller.gameplay;

import javafx.scene.input.KeyCode;
import oogasalad.model.gameplay.grid.Grid;
import oogasalad.model.gameplay.utils.exceptions.InvalidBlockName;
import oogasalad.shared.alert.AlertHandler;
import oogasalad.view.gameplay.GamePane;

/**
 * Controller that manages state changes from the GamePlay View to the Model, specifically the
 * grid.x
 */
public class GameGridController implements AlertHandler {

  private Grid gameGrid;
  private final GamePane gamePane;
  private final KeyHandlerController keyHandlerController;
  String[][][] empty = {};

  public GameGridController(GamePane gamePane, KeyHandlerController keyHandlerController) {
    this.gamePane = gamePane;
    createGrid();
    gameGrid.addObserver(gamePane);
    this.keyHandlerController = keyHandlerController;
  }

  private void createGrid() {
    try {
      int n = 15;
      this.gameGrid = new Grid(n, n, empty);
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

}
