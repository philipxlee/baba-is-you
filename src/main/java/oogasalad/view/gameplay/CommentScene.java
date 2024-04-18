package oogasalad.view.gameplay;

import static oogasalad.shared.widgetfactory.WidgetFactory.DEFAULT_RESOURCE_FOLDER;
import static oogasalad.shared.widgetfactory.WidgetFactory.STYLESHEET;

import java.util.Arrays;
import java.util.Date;
import java.util.List;
import javafx.application.Platform;
import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.control.TextArea;
import javafx.scene.layout.VBox;
import javafx.scene.text.Text;
import javafx.stage.Modality;
import javafx.stage.Stage;
import oogasalad.controller.gameplay.LevelController;
import oogasalad.controller.gameplay.SceneController;
import oogasalad.database.CommentData;
import oogasalad.shared.scene.Scene;
import oogasalad.shared.widgetfactory.WidgetConfiguration;
import oogasalad.shared.widgetfactory.WidgetFactory;

/**
 * Scene that displays the leaderboard.
 */
public class CommentScene implements Scene {

  private final static String LEVEL_NAME = "Default Level";
  private final WidgetFactory factory;
  private final SceneController sceneController;
  private javafx.scene.Scene scene;
  private VBox root;
  private LevelController levelController;

  /**
   * Constructor for LeaderboardScene.
   *
   * @param factory WidgetFactory
   * @param sceneController SceneController
   */
  public CommentScene(WidgetFactory factory, SceneController sceneController) {
    this.factory = factory;
    this.sceneController = sceneController;
    this.levelController = sceneController.getLevelController();
  }

  /**
   * Initialize the scene.
   *
   * @param width  width of scene
   * @param height height of scenes
   */
  @Override
  public void initializeScene(int width, int height) {
    this.root = new VBox(10);
    this.root.setAlignment(Pos.CENTER);
    this.scene = new javafx.scene.Scene(root, width, height);
    getScene().getStylesheets().add(getClass().getResource(DEFAULT_RESOURCE_FOLDER + STYLESHEET)
        .toExternalForm());
    populateComments();
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
   * Populate the leaderboard.
   */
  private void populateComments() {
    Text header = factory.generateHeader(new WidgetConfiguration("Comments for " + LEVEL_NAME));
    root.getChildren().add(header); // Ensure the header is added to the root

//    List<CommentData> comments = sceneController.getDataManager().getCommentsByLevel(LEVEL_NAME); // Retrieve comments for a specific level

    // Hardcoded comments data
    List<CommentData> comments = Arrays.asList(
        new CommentData("user1", "Great level, really enjoyed it!", new Date(), LEVEL_NAME, "Thanks!"),
        new CommentData("user2", "Found a bug on the third challenge.", new Date(), LEVEL_NAME, "Reported!"),
        new CommentData("user3", "Any tips for beating the boss?", new Date(), LEVEL_NAME, "Use the special attack.")
    );

    VBox commentsList = new VBox(5);
    commentsList.setAlignment(Pos.CENTER);

    // Iterate over each comment and create a UI element to display it
    for (CommentData comment : comments) {
      String commentDetails = String.format("%s (%s): %s - %s",
          comment.getUsername(), comment.getDate(), comment.getLevelComments(), comment.getReply());
      Text commentText = new Text(commentDetails);
      commentText.setWrappingWidth(300);  // Set wrapping width to ensure the text fits within the scene
      commentsList.getChildren().add(commentText);

      Button replyButton = factory.makeButton(new WidgetConfiguration(100, 20, "Reply", "button"));
      replyButton.setOnAction(event -> openReplyDialog(comment));
      commentsList.getChildren().add(replyButton);
    }

    Button backButton = factory.makeButton(new WidgetConfiguration(200, 50, "Back", "button"));
    backButton.setOnAction(event -> sceneController.switchToScene(new MainScene(sceneController, levelController)));

    root.getChildren().addAll(header, commentsList, backButton);
  }

  private void openReplyDialog(CommentData comment) {
    Platform.runLater(() -> {
      Stage dialogStage = new Stage();
      dialogStage.setTitle("Reply to Comment");
      dialogStage.initModality(Modality.WINDOW_MODAL);
      dialogStage.initOwner(scene.getWindow());

      // Create text area for input
      TextArea replyArea = new TextArea();
      replyArea.setPromptText("Type your reply here...");

      // Submit button to save the reply
      Button submitReply = factory.makeButton(new WidgetConfiguration(100, 20, "Submit", "button"));
      submitReply.setOnAction(e -> {
        saveReply(comment, replyArea.getText());
        dialogStage.close(); // Close the dialog after submission
      });

      VBox layout = new VBox(10, replyArea, submitReply);
      layout.setAlignment(Pos.CENTER);

      javafx.scene.Scene dialogScene = new javafx.scene.Scene(layout, 300, 200); // Creating a scene for the dialog
      dialogStage.setScene(dialogScene);
      dialogStage.showAndWait();
    });
  }

  private void saveReply(CommentData comment, String reply) {
    // Save the reply to the database
  }


}
