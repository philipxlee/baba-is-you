package oogasalad.view.gameplay;

import static oogasalad.shared.widgetfactory.WidgetFactory.DEFAULT_RESOURCE_FOLDER;
import static oogasalad.shared.widgetfactory.WidgetFactory.STYLESHEET;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.Iterator;
import java.util.List;
import javafx.application.Platform;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextArea;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.text.Text;
import javafx.stage.Modality;
import javafx.stage.Stage;
import oogasalad.controller.gameplay.DatabaseController;
import oogasalad.controller.gameplay.LevelController;
import oogasalad.controller.gameplay.SceneController;
import oogasalad.database.gamedata.CommentData;
import oogasalad.database.gamedata.LeaderboardData;
import oogasalad.database.gamedata.ReplySchema;
import oogasalad.shared.scene.Scene;
import oogasalad.shared.widgetfactory.WidgetConfiguration;
import oogasalad.shared.widgetfactory.WidgetFactory;
import oogasalad.view.gameplay.mainscene.MainScene;

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
  private String language;
  private int width;

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
    this.language = sceneController.getLanguage();
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
    Text header = factory.generateHeader(new WidgetConfiguration("Comments", language));

    VBox commentList = new VBox(15);
    commentList.setAlignment(Pos.CENTER);
    commentList.setPadding(new Insets(10));

    Iterator<CommentData> levelCommentsIterator = sceneController.getDatabaseController()
        .getLevelCommentsIterator(levelController.getLevelName());

    while (levelCommentsIterator.hasNext()) {
      CommentData comment = levelCommentsIterator.next();
      Label usernameLabel = new Label(comment.getUsername() + " (" + comment.getDate() + "): ");
      Label commentLabel = new Label(comment.getComment());
      commentLabel.setWrapText(true);

      HBox commentHeader = new HBox(10, usernameLabel, commentLabel);
      commentHeader.setAlignment(Pos.TOP_LEFT);

      VBox commentBox = new VBox(10, commentHeader);

      VBox repliesContainer = new VBox(5);
      repliesContainer.setPadding(new Insets(0, 0, 0, 20));
      updateRepliesUI(comment, repliesContainer);

      commentList.getChildren().addAll(commentBox, repliesContainer);
    }

    Button backButton = factory.makeButton(new WidgetConfiguration(200, 50,
        "Back", "button", language));
    backButton.setOnAction(event -> sceneController.switchToScene(new MainScene(sceneController, levelController)));


    root.getChildren().addAll(header, commentList, backButton);
  }

  private void updateRepliesUI(CommentData comment, VBox repliesContainer) {
    Iterator<ReplySchema> repliesIterator = comment.getRepliesIterator();
    while (repliesIterator.hasNext()) {
      ReplySchema reply = repliesIterator.next();
      Label replyLabel = new Label(reply.getUsername() + " replied: " + reply.getReplyText());
      replyLabel.setWrapText(true);
      repliesContainer.getChildren().add(replyLabel);
    }
    Button addReplyButton = new Button("Add Reply");  // Button to add a reply
    addReplyButton.setOnAction(e -> showReplyInput(comment, repliesContainer));
    repliesContainer.getChildren().add(addReplyButton);
  }

  private void showReplyInput(CommentData comment, VBox repliesContainer) {
    TextArea replyInput = new TextArea();
    Button submitReply = new Button("Submit Reply");
    submitReply.setOnAction(e -> {
      String replyText = replyInput.getText().trim();
      if (!replyText.isEmpty()) {
        DatabaseController databaseController = sceneController.getDatabaseController();
        databaseController.addReply(comment.getUsername(), databaseController.getUsername(), replyText);
        Label newReplyLabel = new Label(databaseController.getUsername() + ": " + replyText);
        repliesContainer.getChildren().add(newReplyLabel);
        replyInput.clear();
        repliesContainer.getChildren().removeAll(replyInput, submitReply);  // Remove input area after submission
      }
    });

    repliesContainer.getChildren().addAll(replyInput, submitReply);
  }


}
