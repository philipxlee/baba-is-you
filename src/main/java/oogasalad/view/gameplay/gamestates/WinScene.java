package oogasalad.view.gameplay.gamestates;

import static oogasalad.shared.widgetfactory.WidgetFactory.DEFAULT_RESOURCE_FOLDER;
import static oogasalad.shared.widgetfactory.WidgetFactory.STYLESHEET;

import java.util.ArrayList;
import java.util.List;
import javafx.geometry.Pos;
import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.control.TextArea;
import javafx.scene.layout.VBox;
import javafx.scene.text.Text;
import oogasalad.controller.gameplay.SceneController;
import oogasalad.shared.scene.Scene;
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

  public WinScene(SceneController sceneController) {
    this.sceneController = sceneController;
  }

  /**
   * Initialize the scene.
   *
   * @param width  width of scene
   * @param height height of scenes
   */
  @Override
  public void initializeScene(int width, int height) {
    this.width = width;
    this.height = height;
    this.factory = new WidgetFactory();
    this.root = new VBox();
    this.root.setAlignment(Pos.CENTER);
    this.scene = new javafx.scene.Scene(root, width, height);
    getScene().getStylesheets().add(getClass().getResource(DEFAULT_RESOURCE_FOLDER + STYLESHEET)
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
   * Show the win message.
   */
  private void showWinMessage() {
    Text header = factory.generateHeader("You Won!");
    Text content = factory.generateLine("You're great at this game! :)");

    // Add a text area for the player to enter comments about their gameplay
    Text commentsLabel = factory.generateLine("Enter any comments about your gameplay or the level:");
    TextArea commentField = new TextArea();
    commentField.setPromptText("Enter comments here...");
    commentField.setPrefHeight(100);
    commentField.setMinWidth(300);
    commentField.setMaxWidth(400);
    commentField.setWrapText(true);
    Button saveStatsButton = factory.makeButton("Save Stats", 200, 40);

    saveStatsButton.setOnAction(event -> {
      if (!statsSaved) {
        String comments = commentField.getText();
        long endTime = System.currentTimeMillis() / MILLISECOND_OFFSET;
        sceneController.getPlayerDataController().endPlayerSession(comments, endTime);
        saveStatsButton.setText("Stats Saved!");
        saveStatsButton.setDisable(true);
        statsSaved = true; // Ensure stats can only be saved once
      }
    });

    //Generate win stats here, eventually

    Button start = factory.makeButton("Play Again", 200, 40);
    List<Node> texts = new ArrayList<>();
    texts.add(header);
    texts.add(content);
    texts.add(commentsLabel);
    texts.add(commentField);
    texts.add(saveStatsButton);
    texts.add(start);
    start.setOnAction(event -> {
      sceneController.beginGame();
    });

    VBox textContainer = factory.wrapInVBox(texts, height);
    root.getChildren().add(textContainer);
  }
}
