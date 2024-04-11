package oogasalad.view.gameplay;

import static org.junit.Assert.assertEquals;
import javafx.scene.input.KeyCode;
import javafx.scene.shape.Rectangle;
import javafx.stage.Stage;
import oogasalad.util.DukeApplicationTest;
import oogasalad.controller.gameplay.SceneController;
import oogasalad.shared.widgetfactory.WidgetFactory;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertNotNull;

/**
 * Testing class for the gameplay's interaction pane in the View.
 */
public class InteractionPaneTest extends DukeApplicationTest {

  private InteractionPane interactionPane;
  private WidgetFactory factory;
  private MainScene scene;
  private Stage stage;
  private SceneController sceneController;

  @Override
  public void start(Stage stage) throws Exception {
    this.factory = new WidgetFactory();
    this.stage = stage;
    this.sceneController = new SceneController(stage);
    this.interactionPane = new InteractionPane();
    interactionPane.initializeInteractionPane(800, 600,
        new MainScene(sceneController), factory);

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

  @Test
  public void testHandleKeyPress() {
    press(KeyCode.UP);
    interactionPane.handleKeyPress(new javafx.scene.input.KeyEvent(
        javafx.scene.input.KeyEvent.KEY_PRESSED, "", "", KeyCode.UP,
        false, false, false, false));

    Rectangle upRectangle = interactionPane.getUpRectangle();
    assertEquals(InteractionPane.HIGHLIGHT_COLOR, upRectangle.getFill());
  }

  @Test
  public void testHandleKeyRelease() {
    // Simulate pressing and releasing the DOWN arrow key
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
//    // Verify that the expected method calls were made
//    verify(factory, times(1)).generateCaption(anyString());
//    verify(factory, times(1)).createPopUpWindow(anyInt(), anyInt(), any(), anyString());
//  }
}

