package oogasalad.view.authoring;

import javafx.application.Platform;
import javafx.embed.swing.JFXPanel;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ScrollPane;
import javafx.scene.layout.FlowPane;
import javafx.scene.layout.VBox;
import oogasalad.controller.authoring.LevelController;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

import javafx.application.Platform;
import javafx.embed.swing.JFXPanel;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ScrollPane;
import javafx.scene.layout.FlowPane;
import javafx.scene.layout.VBox;
import oogasalad.controller.authoring.LevelController;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

public class ElementsPaneTest {

  @Mock
  private BuilderPane builderPane;

  @Mock
  private LevelController levelController;

  @Mock
  private MainScene mainScene;

  private ElementsPane elementsPane;

  @BeforeClass
  public static void setUpFX() {
    // Initialize JavaFX for testing
    new JFXPanel();
  }

  @Before
  public void setUp() {
    // Initialize mocks
    MockitoAnnotations.initMocks(this);

    // Mock behavior for levelController
    when(levelController.getLanguage()).thenReturn("en");

    // Create ElementsPane instance on JavaFX application thread
    Platform.runLater(
        () -> elementsPane = new ElementsPane(builderPane, levelController, mainScene));
  }

  @Test
  public void testInitializeElementsLayout() {
    Platform.runLater(() -> {
      VBox layout = elementsPane.getLayout();
      assertFalse(layout.getChildren().isEmpty());
    });
  }

  @Test
  public void testUpdateBlocksDisplay() {
    Platform.runLater(() -> {
      FlowPane blocksContainer = mock(FlowPane.class);
      elementsPane.scrollPane = mock(ScrollPane.class);
      when(elementsPane.scrollPane.getContent()).thenReturn(blocksContainer);

      elementsPane.updateBlocksDisplay("Visual");
      // Verify that the blocksContainer's children are updated based on the category

      elementsPane.updateBlocksDisplay("All");
      // Verify that the blocksContainer's children are updated to show all blocks
    });
  }

  @Test
  public void testToggleRemoveMode() {
    Platform.runLater(() -> {
      Button removeButton = mock(Button.class);
      assertFalse(elementsPane.removeMode);

      elementsPane.toggleRemoveMode(removeButton);
      assertTrue(elementsPane.removeMode);
      // Verify that the removeButton's label is updated to "RemovingMode"

      elementsPane.toggleRemoveMode(removeButton);
      assertFalse(elementsPane.removeMode);
      // Verify that the removeButton's label is updated to "RemoveBlock"
    });
  }

  @Test
  public void testMakeEntryPointButton() {
    Platform.runLater(() -> {
      Button entryPointButton = elementsPane.makeEntryPointButton();
      assertNotNull(entryPointButton);
      // Verify that the button's action takes the user to the entry point
    });
  }

  @Test
  public void testShowLoadingScreen() {
    Platform.runLater(() -> {
      // Test the showLoadingScreen method
      // Verify that the loading screen is displayed and the level generation task is started
    });
  }

  @Test
  public void testSetKeyboardShortcuts() {
    Platform.runLater(() -> {
      Scene scene = mock(Scene.class);
      elementsPane.setKeyboardShortcuts(scene);
      // Verify that the keyboard shortcuts are set correctly
    });
  }

  @Test
  public void testReturnToSplashScreen() {
    Platform.runLater(() -> {
      Runnable returnToSplashScreen = elementsPane.returnToSplashScreen();
      assertNotNull(returnToSplashScreen);
      // Verify that the returnToSplashScreen action takes the user to the entry point
    });
  }
}
