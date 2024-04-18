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
import oogasalad.shared.widgetfactory.WidgetConfiguration;
import oogasalad.shared.widgetfactory.WidgetFactory;
import oogasalad.view.gameplay.MainScene;

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
    Text header = factory.generateHeader(new WidgetConfiguration("WinHeader"));
    Text content = factory.generateLine(new WidgetConfiguration("WinContent"));

    // Create UI components that are conditional
    Text commentsLabel = factory.generateLine(new WidgetConfiguration("CommentMessage"));
    TextArea commentField = new TextArea();
    commentField.setPromptText("Enter comments here...");
    commentField.setPrefHeight(100);
    commentField.setMinWidth(300);
    commentField.setMaxWidth(400);
    commentField.setWrapText(true);

    Button saveStatsButton = factory.makeButton(new WidgetConfiguration(
        200, 40, "SaveStats", "button"));
    Text guestMessage = factory.generateLine(new WidgetConfiguration("GuestMessage"));
    guestMessage.setVisible(false); // Default to not visible

    List<Node> texts = new ArrayList<>();
    texts.add(header);
    texts.add(content);

    // Only add comment elements if not a guest
    if (!sceneController.isGuestSession()) {
      saveStatsButton.setOnAction(event -> {
        if (!statsSaved) {
          String comments = commentField.getText();
          long endTime = System.currentTimeMillis() / MILLISECOND_OFFSET;
          sceneController.getDatabaseController().endPlayerSession(comments, endTime);
          saveStatsButton.setText("Stats Saved!");
          saveStatsButton.setDisable(true);
          statsSaved = true; // Ensure stats can only be saved once
        }
      });

      texts.add(commentsLabel);
      texts.add(commentField);
    } else {
      saveStatsButton.setDisable(true); // Disable save button for guests
      guestMessage.setVisible(true); // Show guest message for guests
    }

    texts.add(saveStatsButton);


    Button playAgainButton = factory.makeButton(new WidgetConfiguration(200, 40,
        "PlayAgain","button"));
    playAgainButton.setOnAction(event -> {
      sceneController.initializeViews();
    });
    texts.add(playAgainButton);
    texts.add(guestMessage);

    VBox textContainer = factory.wrapInVBox(texts, height);
    root.getChildren().add(textContainer);
  }



}
