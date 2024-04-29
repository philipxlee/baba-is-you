package oogasalad.view.gameplay.mainscene;

import javafx.application.Platform;
import javafx.scene.input.KeyCode;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;
import oogasalad.controller.gameplay.LevelController;
import oogasalad.controller.gameplay.SceneController;
import oogasalad.model.gameplay.level.Level;
import oogasalad.model.gameplay.level.LevelMetadata;
import oogasalad.view.gameplay.mainscene.GamePane;
import oogasalad.view.gameplay.mainscene.MainScene;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.testfx.framework.junit5.ApplicationTest;
import static org.junit.jupiter.api.Assertions.assertNotNull;

public class GamePaneTest extends ApplicationTest {

  private GamePane gamePane;
  private Level testLevel;

  @BeforeEach
  void setUp() {
    String[][][] initialConfiguration = {
        {{"EmptyVisualBlock", "RockVisualBlock"}, {"EmptyVisualBlock"}},
        {{"EmptyVisualBlock"}, {"EmptyVisualBlock", "BabaVisualBlock"},
            {"EmptyVisualBlock", "BabaTextBlock"}, {"EmptyVisualBlock"}}
    };
    LevelMetadata metadata = new LevelMetadata("TestLevel", "Easy", 2,
        2, initialConfiguration,"BabaIsUs","hint");
    testLevel = new Level(metadata);
  }

  @Override
  public void start(Stage stage) {
    setUp();
    LevelController levelController = new LevelController(testLevel);
    SceneController sceneController = new SceneController(stage, null,
        levelController);
    sceneController.setLanguage("English");
    MainScene scene = new MainScene(sceneController, levelController);
    gamePane = new GamePane();
    gamePane.initializeGameGrid(800, 600, scene, sceneController, testLevel, levelController);
    stage.setScene(new javafx.scene.Scene(gamePane.setUpScreen(), 800, 600));
    stage.show();
  }

  @Test
  public void testPaneIsNotNull() {
    assertNotNull(gamePane.getGrid());
  }

  @Test
  public void testTimerIsVisible() {
    Platform.runLater(() -> {
      Pane timerPane = (Pane) gamePane.setUpScreen();
      assertNotNull(timerPane.lookup("#timeText"));
    });
  }

  @Test
  public void testPauseButtonIsVisible() {
    Platform.runLater(() -> {
      Pane buttonPane = (Pane) gamePane.setUpScreen();
      assertNotNull(buttonPane.lookup("#pauseButton"));
    });
  }

  @Test
  public void testSaveButtonIsVisible() {
    Platform.runLater(() -> {
      Pane gameScreen = (Pane) gamePane.setUpScreen();
      assertNotNull(gameScreen.lookup("#saveButton"));
    });
  }

  @Test
  public void testArrowKeyPress() {
    press(KeyCode.RIGHT);
  }

  @Test
  public void testLetterKeyPress() {
    press(KeyCode.X);
  }
}

