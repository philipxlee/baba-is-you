package oogasalad.view.gameplay.mainscene;

import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.effect.DropShadow;
import javafx.scene.input.KeyCode;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Region;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;

/**
 * Class that creates the interactive key press display in the interaction pane of the Gameplay
 * scene
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

  /**
   * Creates the light-up key press display corresponding to arrow-key usage.
   */
  public KeyPressDisplay() {
    this.root = setupArrowKeys();
    root.setAlignment(Pos.CENTER_RIGHT);
  }

  /**
   * Returns the root of this widget.
   *
   * @return VBox root
   */
  public VBox getRoot() {
    return this.root;
  }

  /**
   * Sets up the light up arrow key rectangles.
   *
   * @return VBox of the rectangles
   */
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

  /**
   * Creates a stylized rectangle representing an arrow key on the keyboard.
   *
   * @param transparent value of it is transparent (pressed) or not
   * @return Rectangle obj for the arrow key
   */
  private Rectangle createRectangle(boolean transparent) {
    Rectangle rectangle = new Rectangle(RECTANGLE_SIZE, RECTANGLE_SIZE,
        transparent ? Color.TRANSPARENT : BASE_COLOR);
    rectangle.setArcWidth(ROUNDED_CORNER);
    rectangle.setArcHeight(ROUNDED_CORNER);
    rectangle.setEffect(DROP_SHADOW);
    return rectangle;
  }

  /**
   * Creates event handler for the pressed KeyEvent.
   *
   * @param event the KeyEvent (pressed key) that just occurred
   */
  public void handleKeyPress(javafx.scene.input.KeyEvent event) {
    switch (event.getCode()) {
      case UP, DOWN, LEFT, RIGHT -> {
        updateArrowKeyVisual(event.getCode(), HIGHLIGHT_COLOR);
        event.consume();  // Prevent further processing by other controls.
      }
      default -> { /* Do nothing */ }
    }
  }

  /**
   * Creates event handler for the released KeyEvent.
   *
   * @param event the KeyEvent (released key) that just occurred
   */
  public void handleKeyRelease(javafx.scene.input.KeyEvent event) {
    if (event.getCode().isArrowKey()) {
      updateArrowKeyVisual(event.getCode(), BASE_COLOR);
      event.consume();
    }
  }

  /**
   * Updates the visual changes of the rectangles when they are pressed.
   *
   * @param code  the current KeyCode
   * @param color color to fill the rectangle
   */
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
