package oogasalad.view.gameplay;

import static oogasalad.shared.widgetfactory.WidgetFactory.DEFAULT_RESOURCE_FOLDER;
import static oogasalad.shared.widgetfactory.WidgetFactory.STYLESHEET;

import java.util.ArrayList;
import java.util.List;
import java.util.regex.Pattern;
import javafx.geometry.Pos;
import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.text.Text;
import oogasalad.controller.gameplay.PlayerDataController;
import oogasalad.controller.gameplay.SceneController;
import oogasalad.shared.scene.Scene;
import oogasalad.shared.widgetfactory.WidgetFactory;

/**
 * Splash screen class for beginning the GamePlay.
 */
public class StartingScene implements Scene {

  private final SceneController sceneController;
  private final PlayerDataController playerDataController;
  private javafx.scene.Scene scene;
  private HBox root;
  private WidgetFactory factory;
  private TextField usernameField;
  private int width;
  private int height;

  private final static String rules =
      "Baba Is You is a puzzle game where players manipulate the game's\n "
          + "rules to solve puzzles and progress. Players control Baba, a character, and aim to reach specific\n"
          + " goals like touching flags or objects. The game world consists of blocks with words defining\n"
          + " rules like 'Baba Is You' or 'Flag Is Win.' By moving blocks, players change rules to create\n"
          + " win conditions or alter the game's logic. For example, by arranging 'Flag Is You' near a \n"
          + "flag, Baba becomes the flag and wins. Players must think logically, as changing rules can\n"
          + " have unintended consequences. Through experimentation, players solve increasingly complex puzzles.";

  /**
   * Constructor for StartingScene.
   *
   * @param sceneController      The SceneController object.
   * @param playerDataController The PlayerDataController object.
   */
  public StartingScene(SceneController sceneController, PlayerDataController playerDataController) {
    this.sceneController = sceneController;
    this.playerDataController = playerDataController;
  }

  /**
   * Initialize the scene.
   *
   * @param width  width of scene
   * @param height height of scenes
   */
  @Override
  public void initializeScene(int width, int height) {
    this.factory = new WidgetFactory();
    this.root = new HBox();
    this.width = width;
    this.height = height;
    this.root.setAlignment(Pos.TOP_CENTER);

    generateContent();
    this.scene = new javafx.scene.Scene(root, width, height);
    getScene().getStylesheets().add(getClass().getResource(DEFAULT_RESOURCE_FOLDER + STYLESHEET)
        .toExternalForm());
  }

  /**
   * Get the scene object.
   *
   * @return Java FX Scene object that represents the scene
   */
  @Override
  public javafx.scene.Scene getScene() {
    return this.scene;
  }

  private void generateContent() {
    Text header = factory.generateHeader("Welcome to Baba Is You!");
    Text content = factory.generateLine(rules);
    Text enterPrompt = factory.generateLine("Enter your username:");
    Label feedbackLabel = new Label();
    feedbackLabel.setStyle("-fx-text-fill: red;");

    createUsernamePromptField();
    Button start = factory.makeButton("Click Enter To Begin", 300, 40);
    Button guestButton = factory.makeButton("Play as Guest", 300, 40);
    usernameField.textProperty().addListener((obs, old, newValue) -> {
      checkUsernameValidity(newValue, feedbackLabel, start);
    });
    startGame(start);

    guestButton.setOnAction(event -> sceneController.beginGame(true));

    List<Node> texts = new ArrayList<>();
    texts.add(header);
    texts.add(content);
    texts.add(enterPrompt);
    texts.add(usernameField);
    texts.add(feedbackLabel);
    texts.add(start);
    texts.add(guestButton);

    VBox textContainer = factory.wrapInVBox(texts, height);
    root.getChildren().add(textContainer);
  }

  private void createUsernamePromptField() {
    usernameField = new TextField();
    usernameField.setPromptText("Enter username here...");
    usernameField.setMinWidth(200);
    usernameField.setMaxWidth(300);
  }

  private void startGame(Button start) {
    start.setOnAction(event -> {
      if (!usernameField.getText().trim().isEmpty()) {
        if (playerDataController.startNewPlayer(usernameField.getText().trim())) {
          sceneController.beginGame(false);
        }
      }
    });
  }

  private void checkUsernameValidity(String newValue, Label feedbackLabel, Button start) {
    if (Pattern.matches("^[\\w]+[\\w\\d_]*$", newValue)) {
      feedbackLabel.setText("");
      checkUsernameAvailability(newValue, start, feedbackLabel);
    } else {
      feedbackLabel.setText("Invalid username. Use only letters, digits, and underscores.");
      start.setDisable(true);
    }
  }

  private void checkUsernameAvailability(String newValue, Button start, Label feedbackLabel) {
    if (playerDataController.isUsernameAvailable(newValue.trim())) {
      start.setDisable(false);
      feedbackLabel.setText("Username is available.");
    } else {
      feedbackLabel.setText("Username already taken, please choose another.");
      start.setDisable(true);
    }
  }

}
