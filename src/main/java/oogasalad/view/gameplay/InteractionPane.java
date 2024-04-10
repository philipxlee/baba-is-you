package oogasalad.view.gameplay;

import javafx.scene.control.ScrollPane;
import java.io.InputStream;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Group;
import javafx.scene.effect.DropShadow;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.FlowPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import javafx.scene.text.Text;
import oogasalad.shared.widgetfactory.WidgetFactory;

/**
 * A class that encapsulates all the UI functionality for the interaction pane in the Gameplay.
 */
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
  private int width;
  private int height;
  private WidgetFactory factory;
  private Image fileIcon;

  /**
   * Sets up all widgets within the interaction pane.
   * @param width the width of the pane
   * @param height height of the pane
   * @param scene the scene this pane belongs to (in its case, the MainScene
   * @param factory an instance of the WidgetFactory used for UI widget creation
   */
  public void initializeInteractionPane(int width, int height, MainScene scene, WidgetFactory
      factory) {
    this.factory = factory;
    this.scene = scene;
    this.root = new Group();
    this.width = width;
    this.height = height;

    InputStream stream = getClass().getResourceAsStream("/images/FileIcon.png");
    fileIcon = new Image(stream);

    root.setOnKeyPressed(this::handleKeyPress);
    root.setOnKeyReleased(this::handleKeyRelease);
    root.setFocusTraversable(true);

    Rectangle background = factory.interactionPanel(width, height);

    // Setup arrow keys layout
    VBox arrowKeys = setupArrowKeys();
    arrowKeys.setAlignment(Pos.CENTER);

    Text title = factory.generateHeader("Baba Is You");
    HBox header = factory.wrapInHBox(title, width);

    // Combine the header and arrow keys into a single display layout
    VBox display = new VBox(10);
    display.getChildren().addAll(header, arrowKeys, setUpFileChooser());
    display.setAlignment(Pos.TOP_CENTER);
    display.prefWidth(width);
    header.setAlignment(Pos.CENTER);

    root.getChildren().addAll(background, display);
  }

  /**
   * Sets up the scrollpane used for selecting files.
   * @return a scrollpane with populated image icons representing JSON level files.
   */
  private VBox setUpFileChooser() {
    FlowPane flowPane = new FlowPane();
    flowPane.setPrefSize(width-50, height/4);
    flowPane.setPadding(new Insets(10));
    flowPane.setHgap(10);
    flowPane.setVgap(10);
    flowPane.setFocusTraversable(false);

    //TODO: change to actual file #
    populateFiles(10, flowPane);
    ScrollPane pane = factory.makeScrollPane(flowPane, width-50);
    Text paneLabel = factory.generateLine("Available Games");
    VBox labelAndChooser = factory.wrapInVBox(paneLabel, height/3);
    labelAndChooser.getChildren().add(pane);
    return labelAndChooser;

  }

  /**
   * Iterates through default game files and populates them within a flowpane. When the file icons
   * representing each game file are clicked, an individual popup dialog window appears with their
   * information.
   * @param numFiles number of default game level JSON files
   * @param flowPane javafx object containing the clickable file icons
   */
  private void populateFiles(int numFiles, FlowPane flowPane) {
    for (int i = 1; i <= numFiles; i++) {
      ImageView imageView = new ImageView(fileIcon);
      imageView.setFitWidth(80);
      imageView.setFitHeight(80);

      Text fileName = factory.generateCaption("File: " + i);
      VBox imageAndLabel = new VBox(10);
      imageAndLabel.getChildren().addAll(imageView, fileName);

      // Make each file icon clickable: TODO: connect to json
      imageView.setOnMouseClicked(new EventHandler<MouseEvent>() {
        @Override
        public void handle(MouseEvent event) {
          Text text = new Text("TEMPORARY: file info goes here");
          VBox vbox = new VBox(text);
          factory.createPopUpWindow(width-100, height/4, vbox, "File Information");
        }
      });
      flowPane.getChildren().add(imageAndLabel);
    }
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
