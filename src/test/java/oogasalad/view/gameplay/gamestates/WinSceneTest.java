package oogasalad.view.gameplay.gamestates;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;

import javafx.application.Platform;
import javafx.scene.control.Button;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import oogasalad.controller.gameplay.LevelController;
import oogasalad.controller.gameplay.SceneController;
import oogasalad.model.gameplay.level.Level;
import oogasalad.model.gameplay.level.LevelMetadata;
import oogasalad.view.gameplay.gamestates.WinScene;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.testfx.framework.junit5.ApplicationTest;

public class WinSceneTest extends ApplicationTest {

  private SceneController sceneController;
  private WinScene winScene;
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
    winScene = new WinScene(sceneController);
    winScene.initializeScene(1500, 800);
    stage.setScene(winScene.getScene());
    stage.show();
  }

  @Test
  public void testInitialization() {
    Platform.runLater(() -> {
      assertNotNull(winScene.getScene());
    });
  }

  @Test
  public void testWinMessageContent() {
    Platform.runLater(() -> {
      VBox root = (VBox) winScene.getScene().getRoot();
      assertNotNull(root);

      assertEquals(6, root.getChildren().size());

      Button playAgainButton = (Button) winScene.getScene().lookup("#playAgainButton");
      assertEquals("Play Again", playAgainButton.getText());
    });
  }

  @Test
  public void testPlayAgainButtonAction() {
    VBox root = (VBox) winScene.getScene().getRoot();
    assertNotNull(root);

    Button playAgainButton = (Button) winScene.getScene().lookup("#playAgainButton");
    Platform.runLater(() -> playAgainButton.fire());

    try {
      Thread.sleep(1000);
    } catch (InterruptedException e) {
      e.printStackTrace();
    }

    //Test if the scene switched from win scene back to starting screen
    assertTrue(sceneController.getStage().getScene() != winScene.getScene());
  }

}
