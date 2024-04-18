package oogasalad.view.gameplay;

import static org.junit.Assert.assertEquals;
import javafx.scene.input.KeyCode;
import javafx.scene.shape.Rectangle;
import javafx.stage.Stage;
import oogasalad.controller.gameplay.LevelController;
import oogasalad.model.gameplay.level.Level;
import oogasalad.model.gameplay.level.LevelMetadata;
import oogasalad.util.DukeApplicationTest;
import oogasalad.controller.gameplay.SceneController;
import oogasalad.shared.widgetfactory.WidgetFactory;
import oogasalad.view.gameplay.mainscene.InteractionPane;
import oogasalad.view.gameplay.mainscene.MainScene;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertNotNull;

/**
 * Testing class for the gameplay's interaction pane in the View.
 */
public class InteractionPaneTest extends DukeApplicationTest {

  private InteractionPane interactionPane;
  private WidgetFactory factory;
  private SceneController sceneController;
  private final String[][][] initialConfiguration = {
      {{"EmptyVisualBlock", "RockVisualBlock"}, {"EmptyVisualBlock"}},
      {{"EmptyVisualBlock"}, {"EmptyVisualBlock", "BabaVisualBlock"}}
  };
  private final LevelMetadata metadata = new LevelMetadata("TestLevel", "Easy", "3",
      2, 2, initialConfiguration);
  private final Level level = new Level(metadata);
  private final LevelController levelController = new LevelController(level);

  @Override
  public void start(Stage stage) throws Exception {
    this.factory = new WidgetFactory();
//    this.sceneController = new SceneController(stage, new DatabaseManager);
    this.interactionPane = new InteractionPane();
    interactionPane.initializeInteractionPane(800, 600,
        new MainScene(sceneController, levelController), factory, sceneController, levelController);

    javafx.scene.Scene scene = new javafx.scene.Scene(interactionPane.getPane(), 800, 600);
    stage.setScene(scene);
    stage.show();
  }

  /**
   * Tests that the pane is correctly initialized
   */
  @Test
  public void testPaneInitialization() {
    assertNotNull(interactionPane.getPane());
    assertEquals(2, interactionPane.getPane().getChildren().size());
  }

  /**
   * Simulates an up arrow key press and ensures the up arrow key is highlighted
   */
  @Test
  public void testHandleKeyPress() {
    press(KeyCode.UP);
    interactionPane.handleKeyPress(new javafx.scene.input.KeyEvent(
        javafx.scene.input.KeyEvent.KEY_PRESSED, "", "", KeyCode.UP,
        false, false, false, false));

    Rectangle upRectangle = interactionPane.getUpRectangle();
    assertEquals(InteractionPane.HIGHLIGHT_COLOR, upRectangle.getFill());
  }

  /**
   * Simulates an up arrow key press and release ensures the up arrow key isn't highlighted
   * anymore
   */
  @Test
  public void testHandleKeyRelease() {
    interactionPane.handleKeyPress(new javafx.scene.input.KeyEvent(
        javafx.scene.input.KeyEvent.KEY_PRESSED, "", "", KeyCode.UP, false, false, false, false));
    interactionPane.handleKeyRelease(new javafx.scene.input.KeyEvent(
        javafx.scene.input.KeyEvent.KEY_RELEASED, "", "", KeyCode.UP, false, false, false, false));

    Rectangle downRectangle = interactionPane.getUpRectangle();
    assertEquals(InteractionPane.BASE_COLOR, downRectangle.getFill());
  }

//  @Test
//  public void testFileIconClick() {
//    javafx.scene.image.ImageView imageView = mock(javafx.scene.image.ImageView.class);
//    when(factory.generateCaption(anyString())).thenReturn(new javafx.scene.text.Text());
//    when(factory.createPopUpWindow(anyInt(), anyInt(), new VBox(), anyString())).thenReturn(null);
//
//    interactionPane.populateFiles(1, interactionPane.setUpFlowPane(800, 600));
//    interactionPane.getPane().lookup("#fileIcon").fireEvent(new MouseEvent(MouseEvent.MOUSE_CLICKED, 0, 0, 0, 0, MouseEvent.MOUSE_CLICKED, 1, true, true, true, true, true, true, true, true, true, true, null));
//
//    verify(factory, times(1)).generateCaption(anyString());
//    verify(factory, times(1)).createPopUpWindow(anyInt(), anyInt(), any(), anyString());
//  }
}

