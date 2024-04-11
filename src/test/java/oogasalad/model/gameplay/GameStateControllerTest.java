package oogasalad.model.gameplay;

import static org.junit.jupiter.api.Assertions.fail;

import javafx.application.Platform;
import javafx.embed.swing.JFXPanel;
import javafx.stage.Stage;
import oogasalad.controller.gameplay.GameStateController;
import oogasalad.controller.gameplay.SceneController;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

public class GameStateControllerTest {

  private GameStateController gameStateController;
  private SceneController sceneController;
  private Stage stage;

  @BeforeEach
  void setUp() {
    new JFXPanel();
    Platform.runLater(() -> {
      stage = new Stage();
      sceneController = new SceneController(stage);
      gameStateController = new GameStateController(sceneController);
    });
  }

  @Test
  void testDisplayWinScene() throws InterruptedException {
    Platform.runLater(() -> {
      gameStateController.displayGameOver(true);
      stage.getScene().getRoot();
      fail();
    });
    Thread.sleep(1000);
  }

  @Test
  void testDisplayLoseScene() throws InterruptedException {
    Platform.runLater(() -> {
      gameStateController.displayGameOver(false);
      stage.getScene().getRoot();
      fail();
    });
    Thread.sleep(1000);
  }
}
