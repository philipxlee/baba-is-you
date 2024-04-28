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
import org.checkerframework.checker.units.qual.A;
import org.checkerframework.checker.units.qual.K;

/**
 * A class that encapsulates all the UI functionality for the interaction pane in the Gameplay.
 */
public class InteractionPane {

  public static final Color BASE_COLOR = Color.web("#343342");
  public static final Color HIGHLIGHT_COLOR = Color.web("#FFCA28");
  private Group root;
  private MainScene scene;
  private int width;
  private int height;
  private WidgetFactory factory;
  private SceneController sceneController;
  private LevelController levelController;
  private String language;
  private KeyPressDisplay keyPressDisplay;

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

    // Setup arrow keys layout
    keyPressDisplay = new KeyPressDisplay();
    VBox arrowKeys = keyPressDisplay.getRoot();

    root.setOnKeyPressed(keyPressDisplay::handleKeyPress);
    root.setOnKeyReleased(keyPressDisplay::handleKeyRelease);
    root.setFocusTraversable(true);

    Rectangle background = factory.interactionPanel(new WidgetConfiguration(width, height,
        "interaction-background", language));

    HBox hintsAndKeys = setUpHintsSection(arrowKeys);
    //Set up header + subtitle
    VBox header = setUpHeader();
    //Initializes reset and load buttons
    Button reset = setUpResetButton();
    Button load = setUpLoadButton();
    //Orient all elements in the space
    orientPaneDisplay(load, reset, header, hintsAndKeys, background);
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
        height / 6, 0);
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
    VBox display = new VBox(10);
    FileChooserPane fileChooser = new FileChooserPane(width, height, language, levelController,
        sceneController);

    HBox loadAndReset = factory.wrapInHBox(new ArrayList<Node>(Arrays.asList(load, reset)), width);
    display.getChildren().addAll(header, arrowKeysBox, fileChooser.getFileChooser(), loadAndReset,
        leaderboardButton);
    // Setup comments display
    VBox commentButton = setupCommentButton();

    HBox stats = factory.wrapInHBox(factory.generateCaption(""), width, 20);
    stats.getChildren().addAll(leaderboardButton, commentButton);

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

  private HBox setUpHintsSection(VBox arrowKeysBox) {
    String hintText = levelController.getHint();
    VBox hints = factory.hintsPanel(new WidgetConfiguration(250, 100, "Hints",
        "hints-panel", language), hintText);
    HBox hbox = factory.wrapInHBox(new ArrayList<>(Arrays.asList(hints, arrowKeysBox)), width);
    return hbox;
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
    keyPressDisplay.updateArrowKeyVisual(code, HIGHLIGHT_COLOR);
  }

  public void updateKeyRelease(KeyCode code) {
    keyPressDisplay.updateArrowKeyVisual(code, BASE_COLOR);
  }

  //For testing
//  public Rectangle getUpRectangle() {
//    return up;
//  }
}
