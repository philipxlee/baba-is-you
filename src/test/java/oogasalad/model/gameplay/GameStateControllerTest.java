package oogasalad.model.gameplay;

import javafx.application.Platform;
import javafx.embed.swing.JFXPanel;
import javafx.stage.Stage;
import oogasalad.controller.gameplay.GameStateController;
import oogasalad.controller.gameplay.SceneController;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.fail;

public class GameStateControllerTest {

  private GameStateController gameStateController;
  private SceneController sceneController;
  private Stage stage;

  @BeforeEach
  void setUp() {
    stage = new Stage();
    sceneController = new SceneController(stage);
    gameStateController = new GameStateController(sceneController);
  }

}
