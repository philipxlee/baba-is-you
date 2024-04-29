package oogasalad.view.gameplay;

import javafx.scene.input.KeyCode;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import oogasalad.controller.gameplay.LevelController;
import oogasalad.controller.gameplay.SceneController;
import oogasalad.model.gameplay.level.Level;
import oogasalad.model.gameplay.level.LevelMetadata;
import oogasalad.shared.widgetfactory.WidgetFactory;
import oogasalad.view.gameplay.mainscene.InteractionPane;
import oogasalad.view.gameplay.mainscene.MainScene;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.testfx.framework.junit5.ApplicationTest;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

public class InteractionPaneTest extends ApplicationTest {

  private InteractionPane interactionPane;
  private Level testLevel;

  @BeforeEach
  void setUp() {
    String[][][] initialConfiguration = {
        {{"EmptyVisualBlock", "RockVisualBlock"}, {"EmptyVisualBlock"}},
        {{"EmptyVisualBlock"}, {"EmptyVisualBlock", "BabaVisualBlock"}}
    };
    LevelMetadata metadata = new LevelMetadata("TestLevel", "Easy", 2,
        2, initialConfiguration, "hint");
    testLevel = new Level(metadata);
  }

  @Override
  public void start(Stage stage) {
    setUp();
    LevelController levelController = new LevelController(testLevel);
    SceneController sceneController = new SceneController(stage, null, levelController);
    sceneController.setLanguage("English");
    MainScene scene = new MainScene(sceneController, levelController);
    WidgetFactory factory = new WidgetFactory();
    interactionPane = new InteractionPane();
    interactionPane.initializeInteractionPane(800, 600, scene, factory, sceneController, levelController);
    stage.setScene(new javafx.scene.Scene(interactionPane.getPane(), 800, 600));
    stage.show();
  }

  @Test
  public void testResetButton() {
    clickOn("#resetButton");
  }

  @Test
  public void testLoadButton() {
    clickOn("#loadButton");
  }

  @Test
  public void testKeyPressAndRelease() {
    KeyCode keyCode = KeyCode.UP;
    push(keyCode);
    sleep(1000); // Sleep for a second to see if the key is visually highlighted
    interactionPane.updateKeyPress(keyCode);
    sleep(1000); // Sleep for a second to see if the key is visually released
    interactionPane.updateKeyRelease(keyCode);
    // Assert that your key was visually highlighted and then released
  }

  @Test
  public void testLeaderboardButton() {
    clickOn("#leaderboardButton");
  }

  @Test
  public void testCommentButton() {
    clickOn("#commentButton");
  }

  @Test
  public void testBackButton() {
    clickOn("#backButton");
  }

  @Test
  public void testNewWindowButton() {
    clickOn("#newWindowButton");
  }

  @Test
  public void testRootIsNotNull() {
    assertNotNull(interactionPane.getPane());
  }

  @Test
  public void testHeaderIsVisible() {
    VBox header = lookup("#header").query();
    assertNotNull(header);
    assertEquals(2, header.getChildren().size());
  }
}

