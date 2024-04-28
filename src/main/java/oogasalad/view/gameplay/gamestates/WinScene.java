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

/**
 * Scene that displays when the player wins the game.
 */
public class WinScene implements Scene {

  private static final int MILLISECOND_OFFSET = 1000;

  private javafx.scene.Scene scene;
  private VBox root;
  private WidgetFactory factory;
  private final SceneController sceneController;
  private int width;
  private int height;
  private boolean statsSaved = false;
  private final String language;

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
    this.width = width;
    this.height = height;
    this.factory = new WidgetFactory();
    this.root = new VBox(20);
    this.root.setAlignment(Pos.CENTER);
    this.scene = new javafx.scene.Scene(root, width, height);
    this.scene.getStylesheets().add(getClass().getResource(DEFAULT_RESOURCE_FOLDER + STYLESHEET)
        .toExternalForm());
    showWinMessage();
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

  private void addTextComponents() {
    Text header = factory.generateHeader(new WidgetConfiguration("WinHeader", language));
    Text content = factory.generateLine(new WidgetConfiguration("WinContent", language));
    root.getChildren().addAll(header, content);
  }

  private void handleCommentsSection() {
    if (!sceneController.isGuestSession()) {
      addCommentsSection();
    } else {
      addGuestMessage();
    }
  }

  private void addCommentsSection() {
    Text commentsLabel = factory.generateLine(new WidgetConfiguration("CommentMessage", language));
    TextArea commentField = createTextArea();
    Button saveStatsButton = createSaveStatsButton(commentField);
    root.getChildren().addAll(commentsLabel, commentField, saveStatsButton);
  }

  private TextArea createTextArea() {
    TextArea commentField = new TextArea();
    commentField.setPromptText("Enter comments here...");
    commentField.setPrefHeight(100);
    commentField.setMinWidth(300);
    commentField.setMaxWidth(400);
    commentField.setWrapText(true);
    return commentField;
  }

  private Button createSaveStatsButton(TextArea commentField) {
    Button saveStatsButton = factory.makeButton(
        new WidgetConfiguration(200, 40, "SaveStats", "button", language));
    saveStatsButton.setOnAction(event -> saveStatistics(commentField, saveStatsButton));
    return saveStatsButton;
  }

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

  private void addGuestMessage() {
    Text guestMessage = factory.generateLine(new WidgetConfiguration("GuestMessage", language));
    guestMessage.setVisible(true);
    root.getChildren().add(guestMessage);
  }

  private void addButtonComponents() {
    Button playAgainButton = factory.makeButton(
        new WidgetConfiguration(200, 40, "PlayAgain", "button", language));
    playAgainButton.setOnAction(event -> sceneController.initializeViews());
    playAgainButton.setPadding(new Insets(10));
    root.getChildren().add(playAgainButton);
  }
}
