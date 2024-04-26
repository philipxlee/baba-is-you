package oogasalad.view.gameplay.mainscene;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Group;
import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.effect.DropShadow;
import javafx.scene.input.KeyCode;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import javafx.scene.text.Text;
import oogasalad.controller.gameplay.LevelController;
import oogasalad.controller.gameplay.SceneController;
import oogasalad.shared.widgetfactory.WidgetConfiguration;
import oogasalad.shared.widgetfactory.WidgetFactory;
import oogasalad.view.gameplay.socialcenter.CommentScene;
import oogasalad.view.gameplay.socialcenter.LeaderboardScene;

/**
 * A class that encapsulates all the UI functionality for the interaction pane in the Gameplay.
 */
public class InteractionPane {

  public static final Color BASE_COLOR = Color.web("#343342");
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
  private SceneController sceneController;
  private LevelController levelController;
  private String language;

  /**
   * Sets up all widgets within the interaction pane.
   *
   * @param width   the width of the pane
   * @param height  height of the pane
   * @param scene   the scene this pane belongs to (in its case, the MainScene
   * @param factory an instance of the WidgetFactory used for UI widget creation
   */
  public void initializeInteractionPane(int width, int height, MainScene scene, WidgetFactory
      factory, SceneController sceneController, LevelController levelController) {
    this.factory = factory;
    this.sceneController = sceneController;
    this.scene = scene;
    this.root = new Group();
    this.width = width;
    this.height = height;
    this.levelController = levelController;
    this.language = sceneController.getLanguage();

    root.setOnKeyPressed(this::handleKeyPress);
    root.setOnKeyReleased(this::handleKeyRelease);
    root.setFocusTraversable(true);

    Rectangle background = factory.interactionPanel(new WidgetConfiguration(width, height,
        "interaction-background", language));

    // Setup arrow keys layout
    VBox arrowKeys = setupArrowKeys();
    HBox arrowKeysBox = factory.wrapInHBox(arrowKeys, width, 10);
    arrowKeysBox.setAlignment(Pos.CENTER);

    //Set up header + subtitle
    VBox header = setUpHeader();

    //Initializes reset and load buttons
    Button reset = setUpResetButton();
    Button load = setUpLoadButton();

    //Orient all elements in the space
    orientPaneDisplay(load, reset, header, arrowKeysBox, background);
  }

  /**
   * Set up the "Baba Is You" header + the "Game Player" subtitle.
   *
   * @return VBox with both texts
   */
  private VBox setUpHeader() {
    Text title = factory.generateHeader(new WidgetConfiguration("BIU", language));
    Text subtitle = factory.generateSubHeader(new WidgetConfiguration(
        "GamePlay", language));
    VBox header = factory.wrapInVBox(new ArrayList<>(Arrays.asList(title, subtitle)),
        height / 6, 5);
    return header;
  }

  /**
   * Initialize the reset button (resets Baba to the original position he was at)/
   *
   * @return reset button
   */
  private Button setUpResetButton() {
    Button reset = factory.makeButton(new WidgetConfiguration(150, 40,
        "Reset", "white-button", language));
    reset.setOnAction(event -> {
      scene.resetGame();
    });
    return reset;
  }

  /**
   * Initialize the load button, which lets the user upload a new game level.
   *
   * @return load button
   */
  private Button setUpLoadButton() {
    Button load = factory.makeButton(new WidgetConfiguration(150, 40,
        "Load", "white-button", language));
    load.setOnAction(event -> {
      try {
        levelController.loadNewLevel(sceneController);
      } catch (IOException e) {
        throw new RuntimeException(e);
      }
    });
    return load;
  }

  /**
   * Initializes/arranges all buttons and elements to be centered in the Interaction Pane.
   *
   * @param load         load button
   * @param reset        reset button
   * @param header       text with header+subtitle
   * @param arrowKeysBox HBox with light-up rectangles showing key presses
   * @param background   Rectangle background hosting all widgets
   */
  private void orientPaneDisplay(Button load, Button reset, VBox header, HBox arrowKeysBox,
      Rectangle background) {
    VBox leaderboardButton = setupLeaderboardButton();
    //Set up the file chooser
    VBox display = new VBox(15);
    FileChooserPane fileChooser = new FileChooserPane(width, height, language, levelController,
        sceneController);

    HBox loadAndReset = factory.wrapInHBox(new ArrayList<Node>(Arrays.asList(load, reset)), width);
    display.getChildren().addAll(header, arrowKeysBox, fileChooser.getFileChooser(), loadAndReset,
        leaderboardButton);
    // Setup comments display
    VBox commentButton = setupCommentButton();

    HBox stats = factory.wrapInHBox(factory.generateCaption(""), width, 20);
    stats.getChildren().addAll(leaderboardButton, commentButton);

    // Combine the header and arrow keys into a single display layout

    // Back button
    Button backButton = factory.makeButton(new WidgetConfiguration(150, 40,
        "Back", "white-button", language));

    backButton.setOnAction(event -> sceneController.initializeViews());

    display.setAlignment(Pos.TOP_CENTER);
    display.prefWidth(width);
    header.setAlignment(Pos.CENTER);
    display.getChildren().addAll(stats, backButton);

    root.getChildren().addAll(background, display);
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
        "ViewBoard", "black-button", language));
    leaderboardButton.setOnAction(
        event -> sceneController.switchToScene(new LeaderboardScene(factory, sceneController)));
    VBox buttonContainer = new VBox(leaderboardButton);
    buttonContainer.setAlignment(Pos.CENTER);
    buttonContainer.setPadding(new Insets(15, 0, 0, 0));
    return buttonContainer;
  }

  private VBox setupCommentButton() {
    Button commentButton = factory.makeButton(new WidgetConfiguration(200, 40,
        "ViewComments", "black-button", language));
    commentButton.setOnAction(
        event -> sceneController.switchToScene(new CommentScene(factory, sceneController)));
    VBox buttonContainer = new VBox(commentButton);
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
