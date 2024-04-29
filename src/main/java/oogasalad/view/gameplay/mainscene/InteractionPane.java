package oogasalad.view.gameplay.mainscene;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Group;
import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ScrollPane;
import javafx.scene.input.KeyCode;
import javafx.scene.layout.FlowPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import javafx.scene.text.Text;
import javafx.stage.Stage;
import oogasalad.controller.gameplay.LevelController;
import oogasalad.controller.gameplay.SceneController;
import oogasalad.model.gameplay.exceptions.JsonParsingException;
import oogasalad.shared.alert.AlertHandler;
import oogasalad.shared.widgetfactory.WidgetConfiguration;
import oogasalad.shared.widgetfactory.WidgetFactory;
import oogasalad.view.gameplay.socialcenter.CommentScene;
import oogasalad.view.gameplay.socialcenter.LeaderboardScene;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

/**
 * A class that encapsulates all the UI functionality for the interaction pane in the Gameplay.
 */
public class InteractionPane implements AlertHandler {

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
  private static final Logger logger = LogManager.getLogger(InteractionPane.class);

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
    header.setId("header");
    return header;
  }

  /**
   * Initialize the reset button (resets Baba to the original position he was at)/
   *
   * @return reset button
   */
  private Button setUpResetButton() {
    Button reset = factory.makeButton(new WidgetConfiguration(100, 40,
        "Reset", "white-button", language));
    reset.setOnAction(event -> {
      scene.resetGame();
    });
    reset.setId("resetButton");
    return reset;
  }

  /**
   * Initialize the load button, which lets the user upload a new game level.
   *
   * @return load button
   */
  private Button setUpLoadButton() {
    Button load = factory.makeButton(new WidgetConfiguration(100, 40,
        "Load", "white-button", language));
    load.setOnAction(event -> {
      try {
        levelController.loadNewLevel(sceneController);
      } catch (IOException e) {
        logger.error("Issue setting up load button: " + e.getMessage());
        throw new RuntimeException(e);
      } catch (JsonParsingException e) {
        showError("Error", e.getMessage());
      }
    });
    load.setId("loadButton");
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

    HBox loadReset = factory.wrapInHBox(new ArrayList<Node>(Arrays.asList(load, reset)), width);
    display.getChildren().addAll(header, arrowKeysBox, fileChooser.getFileChooser(), loadReset,
        leaderboardButton);
    // Setup comments display
    VBox commentButton = setupCommentButton();
    Button backButton = setUpBackButton();
    Button newWindowButton = setUpNewWindowButton();
    HBox backAndWindow = factory.wrapInHBox(new ArrayList<>(Arrays.asList(backButton,
        newWindowButton)), width);

    HBox stats = factory.wrapInHBox(factory.generateCaption(""), width, 20);
    stats.getChildren().addAll(leaderboardButton, commentButton);


    display.setAlignment(Pos.TOP_CENTER);
    display.prefWidth(width);
    header.setAlignment(Pos.CENTER);
    display.getChildren().addAll(stats, backAndWindow);

    root.getChildren().addAll(background, display);
  }

  /**
   * Sets up the hints section, which is oriented in an HBox with the key press display.
   * @param arrowKeysBox key press display object to orient with
   * @return HBox with stylized hints section
   */
  private HBox setUpHintsSection(VBox arrowKeysBox) {
    FlowPane flowpane = factory.createFlowPane(new WidgetConfiguration(250, 100,
        "flowpane", language));
    ScrollPane scrollPane = factory.makeScrollPane(flowpane, 250);
    String hintText = levelController.getHint();
    Label hintLabel = factory.makeLabel(new WidgetConfiguration(250, 100,
        "red-label", language), hintText);
    flowpane.getChildren().add(hintLabel);
    Text hints = factory.generateLine(new WidgetConfiguration("Hints", language));
    VBox vbox = new VBox(hints, scrollPane);
    HBox hbox = factory.wrapInHBox(new ArrayList<>(Arrays.asList(vbox, arrowKeysBox)), width);
    return hbox;
  }

  /**
   * Sets up the leaderboard button for the interaction pane.
   * @return VBox with the button
   */
  private VBox setupLeaderboardButton() {
    Button leaderboardButton = factory.makeButton(new WidgetConfiguration(200, 40,
        "ViewBoard", "black-button", language));
    leaderboardButton.setOnAction(
        event -> sceneController.switchToScene(new LeaderboardScene(factory, sceneController)));
    leaderboardButton.setId("leaderboardButton");
    VBox buttonContainer = new VBox(leaderboardButton);
    buttonContainer.setAlignment(Pos.CENTER);
    buttonContainer.setPadding(new Insets(15, 0, 0, 0));
    return buttonContainer;
  }

  /**
   * Sets up the comment button for the interaction pane.
   * @return VBox with the button
   */
  private VBox setupCommentButton() {
    Button commentButton = factory.makeButton(new WidgetConfiguration(200, 40,
        "ViewComments", "black-button", language));
    commentButton.setOnAction(
        event -> sceneController.switchToScene(new CommentScene(factory, sceneController)));
    commentButton.setId("commentButton");
    VBox buttonContainer = new VBox(commentButton);
    buttonContainer.setAlignment(Pos.CENTER);
    buttonContainer.setPadding(new Insets(15, 0, 0, 0));
    return buttonContainer;
  }

  /**
   * Sets up the new window button for the interaction pane.
   * @return new window button
   */
  private Button setUpNewWindowButton() {
    Button newWindowButton = factory.makeButton(new WidgetConfiguration(150, 40,
        "NewWindow", "white-button", language));
    newWindowButton.setOnAction(event -> {
      //Generate new pop up when the button is clicked
      Stage stage = new Stage();
      LevelController newLevelController = new LevelController(levelController.getLevel());
      SceneController newSceneController = new SceneController(stage, sceneController.getDatabaseController(),
          newLevelController);
      newSceneController.setLanguage(language);
      newSceneController.initializeViews();
    });
    newWindowButton.setId("newWindowButton");
    return newWindowButton;
  }

  /**
   * Sets up the back button for the interaction pane.
   * @return back button
   */
  private Button setUpBackButton() {
    Button backButton = factory.makeButton(new WidgetConfiguration(150, 40,
        "Back", "white-button", language));
    //Back button, on click, returns to starting screen
    backButton.setOnAction(event -> sceneController.initializeViews());
    backButton.setId("backButton");
    return backButton;
  }

  /**
   * Returns the root of this scene.
   * @return Group root
   */
  public Group getPane() {
    return this.root;
  }

  /**
   * Updates the key press display when a key is pressed by the user.
   * @param code KeyCode the user pressed
   */
  public void updateKeyPress(KeyCode code) {
    keyPressDisplay.updateArrowKeyVisual(code, HIGHLIGHT_COLOR);
  }

  /**
   * Updates the key press display when a key is released by the user.
   * @param code KeyCode the user released
   */
  public void updateKeyRelease(KeyCode code) {
    keyPressDisplay.updateArrowKeyVisual(code, BASE_COLOR);
  }
}
