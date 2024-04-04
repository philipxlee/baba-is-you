package oogasalad.view.gameplay;

import javafx.application.Application;
import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.effect.DropShadow;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import javafx.stage.Stage;

/**
 * ArrowKeyHighlighter is a simple JavaFX application that highlights arrow keys when pressed.
 * This is used to help users understand which arrow key is being pressed, especially during
 * gameplay and demonstrations.
 */
public class ArrowKeyHighlighter extends Application {

  private static final Color BASE_COLOR = Color.web("#90A4AE");
  private static final Color HIGHLIGHT_COLOR = Color.web("#FFCA28");
  private static final int ROUNDED_CORNER = 10;
  private static final int RECTANGLE_SIZE = 50;
  private static final DropShadow DROP_SHADOW = new DropShadow(5, Color.GRAY);

  Rectangle left = createRectangle();
  Rectangle right = createRectangle();
  Rectangle up = createRectangle();
  Rectangle down = createRectangle();

  public static void main(String[] args) {
    launch(args);
  }

  @Override
  public void start(Stage primaryStage) {
    Rectangle emptyLeft = createRectangle(true);
    Rectangle emptyRight = createRectangle(true);
    HBox firstRow = new HBox(emptyLeft, up, emptyRight);
    firstRow.setSpacing(5);
    firstRow.setPadding(new Insets(5));

    HBox secondRow = new HBox(left, down, right);
    secondRow.setSpacing(5);
    secondRow.setPadding(new Insets(5));

    VBox mainLayout = new VBox(firstRow, secondRow);
    mainLayout.setSpacing(5);
    mainLayout.setPadding(new Insets(10, 10, 10, 10));

    Scene scene = new Scene(mainLayout);
    scene.setOnKeyPressed(this::handleKeyPress);
    scene.setOnKeyReleased(this::handleKeyRelease);

    primaryStage.setScene(scene);
    primaryStage.setTitle("Enhanced Arrow Key Highlighter");
    primaryStage.show();
  }

  private Rectangle createRectangle() {
    return createRectangle(false);
  }

  private Rectangle createRectangle(boolean transparent) {
    Rectangle rectangle = new Rectangle(RECTANGLE_SIZE, RECTANGLE_SIZE,
        transparent ? Color.TRANSPARENT : BASE_COLOR);
    rectangle.setArcWidth(ROUNDED_CORNER);
    rectangle.setArcHeight(ROUNDED_CORNER);
    rectangle.setEffect(DROP_SHADOW);
    return rectangle;
  }

  private void handleKeyPress(javafx.scene.input.KeyEvent event) {
    switch (event.getCode()) {
      case UP -> up.setFill(HIGHLIGHT_COLOR);
      case DOWN -> down.setFill(HIGHLIGHT_COLOR);
      case LEFT -> left.setFill(HIGHLIGHT_COLOR);
      case RIGHT -> right.setFill(HIGHLIGHT_COLOR);
      default -> {
        // Do nothing
      }
    }
  }

  private void handleKeyRelease(javafx.scene.input.KeyEvent event) {
    // Reset all rectangles to base color
    up.setFill(BASE_COLOR);
    down.setFill(BASE_COLOR);
    left.setFill(BASE_COLOR);
    right.setFill(BASE_COLOR);
  }
}
