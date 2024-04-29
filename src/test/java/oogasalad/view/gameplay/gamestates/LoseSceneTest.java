package oogasalad.view.gameplay.gamestates;

import javafx.application.Platform;
import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import oogasalad.controller.gameplay.LevelController;
import oogasalad.controller.gameplay.SceneController;
import oogasalad.model.gameplay.level.Level;
import oogasalad.model.gameplay.level.LevelMetadata;
import oogasalad.view.gameplay.gamestates.LoseScene;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.testfx.framework.junit5.ApplicationTest;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class LoseSceneTest extends ApplicationTest {

  private SceneController sceneController;
  private LoseScene loseScene;
  private Level testLevel;

  @BeforeEach
  void setUp() {
    String[][][] initialConfiguration = {
        {{"EmptyVisualBlock", "RockVisualBlock"}, {"EmptyVisualBlock"}},
        {{"EmptyVisualBlock"}, {"EmptyVisualBlock", "BabaVisualBlock"},
            {"EmptyVisualBlock", "BabaTextBlock"}, {"EmptyVisualBlock"}}
    };
    LevelMetadata metadata = new LevelMetadata("TestLevel", "Easy", 2,
        2, initialConfiguration, "BabaIsUs", "hint");
    testLevel = new Level(metadata);
  }

  @Override
  public void start(Stage stage) {
    setUp();
    LevelController levelController = new LevelController(testLevel);
    sceneController = new SceneController(stage, null,
        levelController);
    sceneController.setLanguage("English");
    loseScene = new LoseScene(sceneController);
    loseScene.initializeScene(1500, 800);
    stage.setScene(loseScene.getScene());
    stage.show();
  }

  @Test
  public void testInitialization() {
    Platform.runLater(() -> {
      assertNotNull(loseScene.getScene());
    });
  }

  @Test
  public void testLoseMessageContent() {
    Platform.runLater(() -> {
      VBox root = (VBox) loseScene.getScene().getRoot();
      assertNotNull(root);

      assertEquals(1, root.getChildren().size());

      Button tryAgainButton = (Button) loseScene.getScene().lookup("#tryAgainButton");
      assertEquals("Try Again", tryAgainButton.getText());
    });
  }

  @Test
  public void testTryAgainButtonAction() {
    VBox root = (VBox) loseScene.getScene().getRoot();
    assertNotNull(root);

    Button tryAgainButton = (Button) loseScene.getScene().lookup("#tryAgainButton");
    Platform.runLater(() -> tryAgainButton.fire());

    try {
      Thread.sleep(1000);
    } catch (InterruptedException e) {
      e.printStackTrace();
    }

    //Test if the scene switched from lose scene back to game
    assertTrue(sceneController.getStage().getScene() != loseScene.getScene());
  }
}
