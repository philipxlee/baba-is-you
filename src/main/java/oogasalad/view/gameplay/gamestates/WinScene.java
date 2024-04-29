package oogasalad.view.gameplay.gamestates;

import static oogasalad.shared.widgetfactory.WidgetFactory.DEFAULT_RESOURCE_FOLDER;
import static oogasalad.shared.widgetfactory.WidgetFactory.STYLESHEET;

import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.control.TextArea;
import javafx.scene.layout.VBox;
import javafx.scene.text.Text;
import oogasalad.controller.gameplay.SceneController;
import oogasalad.shared.scene.Scene;
import oogasalad.shared.widgetfactory.WidgetConfiguration;
import oogasalad.shared.widgetfactory.WidgetFactory;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

/**
 * Scene that displays when the player wins the game.
 *
 * @author Philip Lee, Yasha Doddabele.
 */
public class WinScene implements Scene {

  private static final int MILLISECOND_OFFSET = 1_000;

  private javafx.scene.Scene scene;
  private VBox root;
  private WidgetFactory factory;
  private final SceneController sceneController;
  private boolean statsSaved = false;
  private final String language;
  private static final Logger logger = LogManager.getLogger(WinScene.class);

  /**
   * Constructor for the WinScene class.
   *
   * @param sceneController the controller that manages the scenes.
   */
  public WinScene(SceneController sceneController) {
    this.sceneController = sceneController;
    this.language = sceneController.getLanguage();
  }

  /**
   * Initialize the scene.
   *
   * @param width  width of scene
   * @param height height of scene
   */
  @Override
  public void initializeScene(int width, int height) {
    this.factory = new WidgetFactory();
    this.root = new VBox(20);
    this.root.setAlignment(Pos.CENTER);
    this.scene = new javafx.scene.Scene(root, width, height);
    applyCss(DEFAULT_RESOURCE_FOLDER, STYLESHEET);
    showWinMessage();
    logger.info("Entered win scene.");
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

  /**
   * Show the win message and other UI components based on the user's session status.
   */
  private void showWinMessage() {
    addTextComponents();
    handleCommentsSection();
    addButtonComponents();
  }

  /**
   * Adds all text components to the scene
   */
  private void addTextComponents() {
    Text header = factory.generateHeader(new WidgetConfiguration("WinHeader", language));
    Text content = factory.generateLine(new WidgetConfiguration("WinContent", language));
    root.getChildren().addAll(header, content);
  }

  /**
   * Adds event handlers to the comment section for cases when user is a guest or not
   */
  private void handleCommentsSection() {
    if (!sceneController.isGuestSession()) {
      addCommentsSection();
    } else {
      addGuestMessage();
    }
  }

  /**
   * Generates the comment section for this scene
   */
  private void addCommentsSection() {
    Text commentsLabel = factory.generateLine(new WidgetConfiguration("CommentMessage", language));
    TextArea commentField = factory.createTextArea(new WidgetConfiguration(400, 100,
        "CommentPrompter", "text-field", language));
    Button saveStatsButton = createSaveStatsButton(commentField);
    root.getChildren().addAll(commentsLabel, commentField, saveStatsButton);
  }

  /**
   * Generates the save stats button.
   *
   * @param commentField Comment field for the user to put their stats/comments
   * @return save stats button
   */
  private Button createSaveStatsButton(TextArea commentField) {
    Button saveStatsButton = factory.makeButton(
        new WidgetConfiguration(200, 40, "SaveStats", "button", language));
    saveStatsButton.setOnAction(event -> saveStatistics(commentField, saveStatsButton));
    return saveStatsButton;
  }

  /**
   * Saves the stats to the database.
   *
   * @param commentField    Comment field for the user to put their stats/comments
   * @param saveStatsButton save stats button
   */
  private void saveStatistics(TextArea commentField, Button saveStatsButton) {
    if (!statsSaved) {
      long endTime = System.currentTimeMillis() / MILLISECOND_OFFSET;
      String comment = commentField.getText();
      sceneController.getDatabaseController().savePlayerData(endTime);
      sceneController.getDatabaseController().saveLevelCommentData(comment);
      saveStatsButton.setText("Stats Saved!");
      saveStatsButton.setDisable(true);
      statsSaved = true;
    }
  }

  /**
   * Handles guest messaging.
   */
  private void addGuestMessage() {
    Text guestMessage = factory.generateLine(new WidgetConfiguration("GuestMessage", language));
    guestMessage.setVisible(true);
    root.getChildren().add(guestMessage);
  }

  /**
   * Creates the play again button, which takes you back to the starting scene.
   */
  private void addButtonComponents() {
    Button playAgainButton = factory.makeButton(
        new WidgetConfiguration(200, 40, "PlayAgain", "button", language));
    playAgainButton.setOnAction(event -> sceneController.initializeViews());
    playAgainButton.setPadding(new Insets(10));
    playAgainButton.setId("playAgainButton");
    root.getChildren().add(playAgainButton);
  }
}
