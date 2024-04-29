package oogasalad.view.gameplay.mainscene;

import javafx.stage.Stage;
import oogasalad.controller.gameplay.LevelController;
import oogasalad.controller.gameplay.SceneController;
import oogasalad.model.gameplay.level.Level;
import oogasalad.model.gameplay.level.LevelMetadata;
import oogasalad.view.gameplay.mainscene.MainScene;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.testfx.framework.junit5.ApplicationTest;

import static org.junit.jupiter.api.Assertions.*;

public class MainSceneTest extends ApplicationTest {

  private MainScene mainScene;
  private SceneController sceneController;
  private LevelController levelController;
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
    SceneController sceneController = new SceneController(stage, null,
        levelController);
    sceneController.setLanguage("English");
    mainScene = new MainScene(sceneController, levelController);
    mainScene.initializeScene(1500, 800);
  }

  @Test
  public void testInitialization() {
    assertNotNull(mainScene.getScene());
    assertNotNull(mainScene.getInteractionPane());
  }
}

