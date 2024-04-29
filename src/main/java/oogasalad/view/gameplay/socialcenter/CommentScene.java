package oogasalad.view.gameplay.socialcenter;

import static oogasalad.shared.widgetfactory.WidgetFactory.DEFAULT_RESOURCE_FOLDER;
import static oogasalad.shared.widgetfactory.WidgetFactory.STYLESHEET;

import java.util.Iterator;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.TextArea;
import javafx.scene.layout.FlowPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.text.Text;
import oogasalad.controller.gameplay.DatabaseController;
import oogasalad.controller.gameplay.LevelController;
import oogasalad.controller.gameplay.SceneController;
import oogasalad.database.gamedata.CommentData;
import oogasalad.database.gamedata.ReplySchema;
import oogasalad.shared.scene.Scene;
import oogasalad.shared.widgetfactory.WidgetConfiguration;
import oogasalad.shared.widgetfactory.WidgetFactory;

/**
 * Scene that displays the comments.
 */
public class CommentScene implements Scene {

  private final static String LEVEL_NAME = "Default Level";
  private final WidgetFactory factory;
  private final SceneController sceneController;
  private javafx.scene.Scene scene;
  private VBox root;
  private final LevelController levelController;
  private final String language;
  private int width;
  private int height;

  /**
   * Constructor for LeaderboardScene.
   *
   * @param factory         WidgetFactory
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
    this.height = height;
    this.root = new VBox(20);
    this.root.setAlignment(Pos.CENTER);
    this.scene = new javafx.scene.Scene(root, width, height);
    applyCss(DEFAULT_RESOURCE_FOLDER, STYLESHEET);
    setUpWidgets();
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
   * Set up the layout for the scene with the necessary widgets.
   */
  private void setUpWidgets() {
    Text header = factory.generateHeader(new WidgetConfiguration("Comments", language));

    VBox commentList = new VBox(15);
    commentList.setAlignment(Pos.CENTER);
    commentList.setPadding(new Insets(10));

    Iterator<CommentData> levelCommentsIterator = sceneController.getDatabaseController()
        .getLevelCommentsIterator(levelController.getLevelName());

    populateComments(levelCommentsIterator, commentList);
    commentList.getStyleClass().add("flowpane");

    Button backButton = factory.makeButton(new WidgetConfiguration(200, 50,
        "Back", "button", language));
    backButton.setOnAction(event -> sceneController.switchToPreviousScene());

    root.getChildren().addAll(header, setUpScrollPane(commentList), backButton);
  }

  /**
   * Sets up the scrollpane for the comments.
   *
   * @param commentList list of comments to put in the scrollpane
   * @return stylized scrollpane
   */
  private ScrollPane setUpScrollPane(VBox commentList) {
    FlowPane flowPane = factory.createFlowPane(new WidgetConfiguration(width - 500,
        height - 300, "flowpane", language));
    ScrollPane scrollPane = factory.makeScrollPane(flowPane, width - 500);
    scrollPane.setMaxHeight(height - 300);
    scrollPane.setContent(commentList);

    return scrollPane;
  }

  /**
   * Populate the leaderboard.
   */
  private void populateComments(Iterator<CommentData> levelCommentsIterator, VBox commentList) {

    while (levelCommentsIterator.hasNext()) {
      CommentData comment = levelCommentsIterator.next();
      Label usernameLabel = new Label(comment.getUsername() + " (" + comment.getDate() + "): ");
      usernameLabel.getStyleClass().add("white-label");
      Label commentLabel = new Label(comment.getComment());
      commentLabel.getStyleClass().add("white-label");
      commentLabel.setWrapText(true);

      HBox commentHeader = new HBox(10, usernameLabel, commentLabel);
      commentHeader.setAlignment(Pos.TOP_LEFT);

      VBox commentBox = new VBox(10, commentHeader);

      VBox repliesContainer = new VBox(5);
      repliesContainer.setPadding(new Insets(0, 0, 0, 20));
      updateRepliesUI(comment, repliesContainer);

      commentList.getChildren().addAll(commentBox, repliesContainer);
    }
  }

  /**
   * Update the replies UI whenever there is a new reply.
   *
   * @param comment          data related to commenter
   * @param repliesContainer current container of replies
   */
  private void updateRepliesUI(CommentData comment, VBox repliesContainer) {
    Iterator<ReplySchema> repliesIterator = comment.getRepliesIterator();
    while (repliesIterator.hasNext()) {
      ReplySchema reply = repliesIterator.next();
      Label replyLabel = new Label(
          reply.getUsername() + " replied at " + reply.getDate() + " : " + reply.getReplyText());
      replyLabel.setWrapText(true);
      replyLabel.getStyleClass().add("red-label");
      repliesContainer.getChildren().add(replyLabel);
    }
    Button addReplyButton = factory.makeButton(new WidgetConfiguration(120, 30,
        "AddReply", "black-button", language));
    repliesContainer.getChildren().add(addReplyButton);
    addReplyButton.setOnAction(e -> {
      repliesContainer.getChildren().remove(addReplyButton);
      showReplyInput(comment, repliesContainer, addReplyButton);
    });
  }

  /**
   * Show a textarea for users to reply to other users.
   *
   * @param comment          data related to commenter
   * @param repliesContainer container of current replies
   * @param addReplyButton   reply button
   */
  private void showReplyInput(CommentData comment, VBox repliesContainer, Button addReplyButton) {
    TextArea replyInput = factory.createTextArea(new WidgetConfiguration(600, 100,
        "CommentPrompter", "text-field", language));
    Button submitReply = factory.makeButton(new WidgetConfiguration(120, 30,
        "SubmitReply", "black-button", language));
    submitReply.setOnAction(e -> {
      String replyText = replyInput.getText().trim();
      if (!replyText.isEmpty()) {
        DatabaseController databaseController = sceneController.getDatabaseController();
        databaseController.addReply(comment.getUsername(), databaseController.getUsername(),
            replyText);
        Label newReplyLabel = new Label((databaseController.getUsername() == null ? "Anonymous"
            : databaseController.getUsername()) + ": " + replyText);
        newReplyLabel.getStyleClass().add("red-label");
        repliesContainer.getChildren().add(newReplyLabel);
        replyInput.clear();
        repliesContainer.getChildren()
            .removeAll(replyInput, submitReply);  // Remove input area after submission
        repliesContainer.getChildren().add(addReplyButton);
      }
    });
    repliesContainer.getChildren().addAll(replyInput, submitReply);
  }
}
