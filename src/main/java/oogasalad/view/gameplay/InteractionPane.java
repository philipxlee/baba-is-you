package oogasalad.view.gameplay;

import java.io.InputStream;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Group;
import javafx.scene.effect.DropShadow;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.paint.LinearGradient;
import javafx.scene.paint.Stop;
import javafx.scene.shape.Rectangle;

public class InteractionPane {

  private static final Color BASE_COLOR = Color.web("#90A4AE");
  private static final Color HIGHLIGHT_COLOR = Color.web("#FFCA28");
  private static final int ROUNDED_CORNER = 10;
  private static final int RECTANGLE_SIZE = 50;
  private static final DropShadow DROP_SHADOW = new DropShadow(5, Color.GRAY);
  Rectangle left = createRectangle(false);
  Rectangle right = createRectangle(false);
  Rectangle up = createRectangle(false);
  Rectangle down = createRectangle(false);
  private Group root;
  private MainScene scene;

  public void initializeInteractionPane(int width, int height, MainScene scene, WidgetFactory
      factory) {
    this.scene = scene;
    this.root = new Group();
    root.setOnKeyPressed(this::handleKeyPress);
    root.setOnKeyReleased(this::handleKeyRelease);
    root.setFocusTraversable(true);
    Rectangle background = new Rectangle(0, 0, width, height);
    Color start = Color.web("#777DA1");
    Color end = Color.web("#9773FD");
    Stop[] stops = new Stop[]{new Stop(0, start), new Stop(1, end)};
    LinearGradient linearGradient = new LinearGradient(0, 0, 0, 1, true, null, stops);
    background.setFill(linearGradient);
    //Rectangle background = factory.interactionPanel(width, height;

    // Setup arrow keys layout
    VBox arrowKeys = setupArrowKeys();
    arrowKeys.setAlignment(Pos.CENTER);

    // Load and set up the header image
    HBox hbox = new HBox();
    hbox.setAlignment(Pos.CENTER);
    InputStream inputStream = InteractionPane.class.getResourceAsStream(
        "/images/BabaIsYouHeader.png");
    if (inputStream != null) {
      Image image = new Image(inputStream);
      ImageView imageView = new ImageView(image);
      hbox.getChildren().add(imageView);
    }

    // Combine the header and arrow keys into a single display layout
    VBox display = new VBox(10);
    display.getChildren().addAll(hbox, arrowKeys);
    display.setAlignment(Pos.TOP_CENTER);
    root.getChildren().addAll(background, display);
    display.setLayoutX((width - display.getBoundsInParent().getWidth()) / 2);
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
    up.setFill(BASE_COLOR);
    down.setFill(BASE_COLOR);
    left.setFill(BASE_COLOR);
    right.setFill(BASE_COLOR);
  }

  public Group getPane() {
    return this.root;
  }
}
