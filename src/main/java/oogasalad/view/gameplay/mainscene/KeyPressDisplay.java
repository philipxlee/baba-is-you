package oogasalad.view.gameplay.mainscene;

import javafx.geometry.Insets;
import javafx.scene.effect.DropShadow;
import javafx.scene.input.KeyCode;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;

/**
 * Class that creates the interactive key press display in the interaction pane of the Gameplay scene
 */
public class KeyPressDisplay {

  public static final Color BASE_COLOR = Color.web("#343342");
  public static final Color HIGHLIGHT_COLOR = Color.web("#FFCA28");
  private static final DropShadow DROP_SHADOW = new DropShadow(5, Color.GRAY);
  private VBox root;
  Rectangle left = createRectangle(false);
  Rectangle right = createRectangle(false);
  Rectangle up = createRectangle(false);
  Rectangle down = createRectangle(false);
  private static final int ROUNDED_CORNER = 10;
  private static final int RECTANGLE_SIZE = 50;

  public KeyPressDisplay() {
    this.root = setupArrowKeys();
  }

  public VBox getRoot() {
    return this.root;
  }

  private VBox setupArrowKeys() {
    HBox topRow = new HBox(createRectangle(true), up, createRectangle(true));
    topRow.setSpacing(5);
    topRow.setPadding(new Insets(5));

    HBox bottomRow = new HBox(left, down, right);
    bottomRow.setSpacing(5);
    bottomRow.setPadding(new Insets(5));

    // Using VBox to stack the HBoxes vertically
    VBox arrowKeysContainer = new VBox(topRow, bottomRow);
    arrowKeysContainer.setSpacing(5);
    arrowKeysContainer.setPadding(new Insets(5));

    return arrowKeysContainer;
  }

  private Rectangle createRectangle(boolean transparent) {
    Rectangle rectangle = new Rectangle(RECTANGLE_SIZE, RECTANGLE_SIZE,
        transparent ? Color.TRANSPARENT : BASE_COLOR);
    rectangle.setArcWidth(ROUNDED_CORNER);
    rectangle.setArcHeight(ROUNDED_CORNER);
    rectangle.setEffect(DROP_SHADOW);
    return rectangle;
  }

  public void handleKeyPress(javafx.scene.input.KeyEvent event) {
    switch (event.getCode()) {
      case UP, DOWN, LEFT, RIGHT -> {
        updateArrowKeyVisual(event.getCode(), HIGHLIGHT_COLOR);
        event.consume();  // Prevent further processing by other controls.
      }
      default -> { /* Do nothing */ }
    }
  }

  public void handleKeyRelease(javafx.scene.input.KeyEvent event) {
    if (event.getCode().isArrowKey()) {
      updateArrowKeyVisual(event.getCode(), BASE_COLOR);
      event.consume();
    }
  }

  protected void updateArrowKeyVisual(KeyCode code, Color color) {
    switch (code) {
      case UP -> up.setFill(color);
      case DOWN -> down.setFill(color);
      case LEFT -> left.setFill(color);
      case RIGHT -> right.setFill(color);
      default -> {
      }
    }
  }

}
