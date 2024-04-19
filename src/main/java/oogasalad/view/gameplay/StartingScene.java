package oogasalad.view.gameplay;

import static oogasalad.shared.widgetfactory.WidgetFactory.DEFAULT_RESOURCE_FOLDER;
import static oogasalad.shared.widgetfactory.WidgetFactory.STYLESHEET;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.regex.Pattern;
import javafx.geometry.Pos;
import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.HBox;
import javafx.scene.text.Text;
import oogasalad.controller.gameplay.DatabaseController;
import oogasalad.controller.gameplay.SceneController;
import oogasalad.shared.scene.Scene;
import oogasalad.shared.widgetfactory.WidgetConfiguration;
import oogasalad.shared.widgetfactory.WidgetFactory;

/**
 * Splash screen class for beginning the GamePlay.
 */
public class StartingScene implements Scene {

  private final SceneController sceneController;
  private final DatabaseController databaseController;
  private javafx.scene.Scene scene;
  private HBox root;
  private WidgetFactory factory;
  private TextField usernameField;
  private int width;
  private int height;
  public static String language;

  /**
   * Constructor for StartingScene.
   *
   * @param sceneController      The SceneController object.
   * @param databaseController The PlayerDataController object.
   */
  public StartingScene(SceneController sceneController, DatabaseController databaseController,
      String language) {
    this.sceneController = sceneController;
    this.databaseController = databaseController;
    this.language = language;

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
    Text header = factory.generateHeader(new WidgetConfiguration("BabaHeader", language));
    Text content = factory.generateLine(new WidgetConfiguration("BabaRules", language));
    Text enterPrompt = factory.generateLine(new WidgetConfiguration("EnterPrompt", language));
    Label feedbackLabel = factory.generateLabel(new WidgetConfiguration("", language));

    usernameField = factory.createTextField(new WidgetConfiguration(200, 40,
        "UsernamePrompter", "text-field", language));

    List<Node> texts = new ArrayList<>();
    texts.add(header);
    texts.add(content);
    texts.add(enterPrompt);
    texts.add(usernameField);
    texts.add(feedbackLabel);

    List<Node> btns = createButtons(feedbackLabel);

    List<Node> boxes = new ArrayList<>();
    boxes.add(factory.wrapInVBox(texts, height/2, 10));
    boxes.add(factory.wrapInHBox(btns, width));

    root.getChildren().add(factory.wrapInVBox(boxes, height, 10));
  }

  private List<Node> createButtons(Label feedbackLabel) {
    Button start = factory.makeButton(new WidgetConfiguration(200, 40,
        "Enter", "button", language));
    Button guestButton = factory.makeButton(new WidgetConfiguration(200, 40,
        "PlayAsGuest", "button", language));
    usernameField.textProperty().addListener((obs, old, newValue) -> {
      checkUsernameValidity(newValue, feedbackLabel, start);
    });
    ComboBox<String> switchLanguage = factory.makeComboBox(new WidgetConfiguration(200, 40,
        "SwitchLanguage", "combo-box", language), new ArrayList<>(Arrays.asList("English",
        "Spanish")), language);
    //TODO: Change to be a drop down
    switchLanguage.setOnAction(event -> {
      language = switchLanguage.getValue();
      sceneController.switchToScene(new StartingScene(sceneController, databaseController, language));
    });
    startGame(start);
    guestButton.setOnAction(event -> sceneController.beginGame(true));

    //Wrap buttons in list of nodes
    List<Node> btns = new ArrayList<>();
    btns.add(start);
    btns.add(guestButton);
    btns.add(switchLanguage);
    return btns;
  }

  private void startGame(Button start) {
    start.setOnAction(event -> {
      if (!usernameField.getText().trim().isEmpty()) {
        if (databaseController.startNewPlayer(usernameField.getText().trim())) {
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
      factory.replaceLabelContent(feedbackLabel, new WidgetConfiguration("UserNameInvalid", language));
      start.setDisable(true);
    }
  }

  private void checkUsernameAvailability(String newValue, Button start, Label feedbackLabel) {
    if (databaseController.isUsernameAvailable(newValue.trim())) {
      start.setDisable(false);
      factory.replaceLabelContent(feedbackLabel, new WidgetConfiguration("UserNameAvailable", language));
    } else {
      factory.replaceLabelContent(feedbackLabel, new WidgetConfiguration("UserNameTaken", language));
      start.setDisable(true);
    }
  }

}
