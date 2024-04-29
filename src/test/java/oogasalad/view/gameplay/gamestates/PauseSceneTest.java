package oogasalad.view.gameplay.gamestates;

import javafx.animation.Timeline;
import javafx.application.Platform;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.text.Text;
import javafx.stage.Stage;
import oogasalad.controller.gameplay.LevelController;
import oogasalad.controller.gameplay.SceneController;
import oogasalad.model.gameplay.level.Level;
import oogasalad.model.gameplay.level.LevelMetadata;
import oogasalad.view.gameplay.gamestates.PauseScene;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.testfx.framework.junit5.ApplicationTest;

import static org.junit.jupiter.api.Assertions.*;

public class PauseSceneTest extends ApplicationTest {

  private SceneController sceneController;
  private PauseScene pauseScene;
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
    pauseScene = new PauseScene(sceneController, 10000, new Timeline());
    pauseScene.initializeScene(1500, 800);
    stage.setScene(pauseScene.getScene());
    stage.show();
  }

  @Test
  public void testInitialization() {
    assertNotNull(pauseScene.getScene());
  }

  @Test
  public void testPauseScreenContent() {
    VBox root = (VBox) pauseScene.getScene().getRoot();
    assertNotNull(root);

    Text header = (Text) root.lookup("#header");
    Text content = (Text) root.lookup("#content");
    Button backButton = (Button) root.lookup("#backButton");
    assertEquals("Back", backButton.getText());
    assertEquals("Game Paused", header.getText());
    assertTrue(content.getText().contains("Current Time:"));
  }

  @Test
  public void testBackButtonAction() {
    VBox root = (VBox) pauseScene.getScene().getRoot();
    assertNotNull(root);

    Button backButton = (Button) root.lookup("#backButton");
    assertNotNull(backButton);

    Platform.runLater(() -> backButton.fire());

    try {
      Thread.sleep(1000);
    } catch (InterruptedException e) {
      e.printStackTrace();
    }

    //Test if the scene switched from pause scene back to game screen
    assertTrue(sceneController.getStage().getScene() != pauseScene.getScene());
  }
}

