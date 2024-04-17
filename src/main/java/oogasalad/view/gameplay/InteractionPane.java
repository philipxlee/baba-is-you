package oogasalad.view.gameplay;

import java.io.InputStream;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Group;
import javafx.scene.control.Button;
import javafx.scene.control.ScrollPane;
import javafx.scene.effect.DropShadow;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.KeyCode;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.FlowPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import javafx.scene.text.Text;
import oogasalad.controller.gameplay.SceneController;
import oogasalad.shared.widgetfactory.WidgetConfiguration;
import oogasalad.shared.widgetfactory.WidgetFactory;

/**
 * A class that encapsulates all the UI functionality for the interaction pane in the Gameplay.
 */
public class InteractionPane {

  public static final Color BASE_COLOR = Color.web("#90A4AE");
  public static final Color HIGHLIGHT_COLOR = Color.web("#FFCA28");
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
  private SceneController sceneController;

  /**
   * Sets up all widgets within the interaction pane.
   *
   * @param width   the width of the pane
   * @param height  height of the pane
   * @param scene   the scene this pane belongs to (in its case, the MainScene
   * @param factory an instance of the WidgetFactory used for UI widget creation
   */
  public void initializeInteractionPane(int width, int height, MainScene scene, WidgetFactory
      factory, SceneController sceneController) {
    this.factory = factory;
    this.sceneController = sceneController;
    this.scene = scene;
    this.root = new Group();
    this.width = width;
    this.height = height;

    InputStream stream = getClass().getResourceAsStream("/images/FileIcon.png");
    fileIcon = new Image(stream);

    root.setOnKeyPressed(this::handleKeyPress);
    root.setOnKeyReleased(this::handleKeyRelease);
    root.setFocusTraversable(true);

    Rectangle background = factory.interactionPanel(new WidgetConfiguration(width, height,
        "interaction-background"));

    // Setup arrow keys layout
    VBox arrowKeys = setupArrowKeys();
    HBox arrowKeysBox = factory.wrapInHBox(arrowKeys, width);
    arrowKeysBox.setAlignment(Pos.CENTER);

    Text title = factory.generateHeader(new WidgetConfiguration("BIU"));
    HBox header = factory.wrapInHBox(title, width);

    Button reset = factory.makeButton(new WidgetConfiguration(150, 40,
        "Reset", "white-button"));
    reset.setOnAction(event -> {
      scene.resetGame();
    });

    // Setup leaderboard display
    VBox leaderboardButton = setupLeaderboardButton();

    // Combine the header and arrow keys into a single display layout
    VBox display = new VBox(20);
    display.getChildren().addAll(header, arrowKeysBox, setUpFileChooser(), reset, leaderboardButton);
    display.setAlignment(Pos.TOP_CENTER);
    display.prefWidth(width);
    header.setAlignment(Pos.CENTER);

    root.getChildren().addAll(background, display);
  }

  /**
   * Sets up the scrollpane used for selecting files.
   *
   * @return a scrollpane with populated image icons representing JSON level files.
   */
  private VBox setUpFileChooser() {
    FlowPane flowPane = factory.createFlowPane(new WidgetConfiguration(width - 50,
        height / 4, "flowpane"));

    //TODO: change to actual file #
    populateFiles(10, flowPane);
    ScrollPane pane = factory.makeScrollPane(flowPane, width - 50);
    Text paneLabel = factory.generateLine(new WidgetConfiguration("Games"));
    VBox labelAndChooser = factory.wrapInVBox(paneLabel, height / 3);
    labelAndChooser.getChildren().add(pane);
    return labelAndChooser;
  }

  /**
   * Iterates through default game files and populates them within a flowpane. When the file icons
   * representing each game file are clicked, an individual popup dialog window appears with their
   * information.
   *
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
          factory.createPopUpWindow(new WidgetConfiguration(width - 100,
              height / 4, "FileInformation"), vbox);
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

  private void updateArrowKeyVisual(KeyCode code, Color color) {
    switch (code) {
      case UP -> up.setFill(color);
      case DOWN -> down.setFill(color);
      case LEFT -> left.setFill(color);
      case RIGHT -> right.setFill(color);
      default -> {
      }
    }
  }

  private VBox setupLeaderboardButton() {
    Button leaderboardButton = factory.makeButton(new WidgetConfiguration(200, 40,
        "ViewBoard", "white-button"));
    leaderboardButton.setOnAction(event -> sceneController.switchToScene(new LeaderboardScene(factory, sceneController)));
    VBox buttonContainer = new VBox(leaderboardButton);
    buttonContainer.setAlignment(Pos.CENTER);
    buttonContainer.setPadding(new Insets(15, 0, 0, 0));
    return buttonContainer;
  }

  public Group getPane() {
    return this.root;
  }

  public void updateKeyPress(KeyCode code) {
    updateArrowKeyVisual(code, HIGHLIGHT_COLOR);
  }

  public void updateKeyRelease(KeyCode code) {
    updateArrowKeyVisual(code, BASE_COLOR);
  }

  //For testing
  public Rectangle getUpRectangle() {
    return up;
  }
}
