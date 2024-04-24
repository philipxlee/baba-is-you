package oogasalad.view.authoring;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertTrue;

import javafx.application.Platform;
import javafx.embed.swing.JFXPanel;
import javafx.scene.image.ImageView;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.Pane;
import oogasalad.controller.authoring.LevelController;
import oogasalad.model.authoring.level.LevelMetadata;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

public class BuilderPaneTest {

  private BuilderPane builderPane;

  @BeforeEach
  public void setUp() {
    new JFXPanel(); // Initialize JavaFX Toolkit
    LevelMetadata levelMetadata = new LevelMetadata("TestLevel", "test", 10,
        10, "Easy", "BabaIsUs"); // Create a dummy level for testing
    LevelController levelController = new LevelController(
        levelMetadata); // Pass the dummy level to the controller
    builderPane = new BuilderPane(levelController, "English");
  }

  @Test
  public void testGridSetup() {
    // Setting root pane size
    Pane root = builderPane.getRoot();
    root.setPrefSize(400, 300); // Set any preferred size for testing

    builderPane.initializeBuilderScene();

    // Getting the grid pane and checking teh children count
    GridPane gridPane = builderPane.gridPane;
    assertEquals(builderPane.gridWidth * builderPane.gridHeight, gridPane.getChildren().size());
  }

//    @Test
//    public void testDragAndDropHandling() {
//
//    }

  @Test
  public void testCellIndicesCalculation() {
    // testing this by passing (x, y) coordinates and checking the returned cell indices
    double x = 50;
    double y = 50;
    assertNull(builderPane.getCellIndices(x, y));
  }

  @Test
  public void testCellCoordinatesCalculation() {
    // Similar to cell indices calculation, testing by passing cell indices and checking the returned coordinates
    assertNull(builderPane.getCellCoordinates(50, 50));
  }

  @Test
  public void testBlockViewCreation() {
    // Test block view creation logic here

    ImageView blockView = builderPane.createBlockView("BabaTextBlock");
    assertNotNull(blockView);

  }

//    @Test
//    public void testRemoveModeEventHandling() {
//        // Testing remove mode event handling logic
//        ImageView imageView = new ImageView();
//
//        // Adding the ImageView to the gridPane of BuilderPane at a specific cell
//        int columnIndex = 2;
//        int rowIndex = 2;
//        builderPane.gridPane.add(imageView, columnIndex, rowIndex);
//
//        // Enable remove mode
//        builderPane.setRemove(true);
//
//        // Simulate a mouse click event at the specified position (2, 2) within the GridPane
//        MouseButton button = MouseButton.PRIMARY;
//        int clickCount = 1;
//        boolean isStill = true;
//        boolean isShiftDown = false;
//        boolean isControlDown = false;
//        boolean isAltDown = false;
//        boolean isMetaDown = false;
//        boolean isPrimaryButtonDown = true;
//        boolean isMiddleButtonDown = false;
//        boolean isSecondaryButtonDown = false;
//        boolean isSynthesized = false;
//        boolean isPopupTrigger = false;
//
//        // Calculate the coordinates of the center of the cell (2, 2) relative to the GridPane
//        Point2D cellCenter = builderPane.getCellCoordinates(columnIndex, rowIndex);
//        double cellCenterX = cellCenter != null ? cellCenter.getX() : 0; // Use default value if cellCenter is null
//        double cellCenterY = cellCenter != null ? cellCenter.getY() : 0; // Use default value if cellCenter is null
//
//        // Simulate a mouse click event at the calculated coordinates
//        MouseEvent event = new MouseEvent(MouseEvent.MOUSE_CLICKED, cellCenterX, cellCenterY, 0, 0,
//                button, clickCount, isStill, isShiftDown, isControlDown, isAltDown,
//                isMetaDown, isPrimaryButtonDown, isMiddleButtonDown, isSecondaryButtonDown,
//                isSynthesized, isPopupTrigger, null);
//
//        // Fire the event on the gridPane
//        builderPane.gridPane.fireEvent(event);
//
//        // Verify that the ImageView is removed from the gridPane of the BuilderPane
//        assertFalse(builderPane.gridPane.getChildren().contains(imageView));
//    }

  @Test
  public void testRemoveModeSet() {
    // Test setting remove mode logic here
    // Set remove mode to true or false and verify that removeMode flag is correctly updated

    builderPane.setRemove(true);
    assertTrue(builderPane.removeMode); // Assuming remove mode is correctly set to true

    builderPane.setRemove(false);
    assertFalse(builderPane.removeMode);

  }

  @Test
  public void testUpdateGridSize() {
    // Set up initial grid size
    Platform.runLater(() -> {
      // Set up initial grid size
      int initialWidth = builderPane.gridWidth;
      int initialHeight = builderPane.gridHeight;

      // Call updateGridSize with new dimensions
      int newWidth = 15;
      int newHeight = 20;
      builderPane.updateGridSize(newWidth, newHeight);

      // Verify that the grid size is updated
      assertEquals(newWidth, builderPane.gridWidth);
      assertEquals(newHeight, builderPane.gridHeight);
    });
  }
}




