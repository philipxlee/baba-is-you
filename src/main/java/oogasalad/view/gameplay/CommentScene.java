package oogasalad.view.gameplay;

import static oogasalad.shared.widgetfactory.WidgetFactory.DEFAULT_RESOURCE_FOLDER;
import static oogasalad.shared.widgetfactory.WidgetFactory.STYLESHEET;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.List;
import javafx.application.Platform;
import javafx.geometry.Pos;
import javafx.scene.Node;
import javafx.scene.control.Button;
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
    VBox commentList = new VBox(5);
    commentList.setAlignment(Pos.CENTER);
    List<CommentData> levelComments = sceneController.getDatabaseController().getLevelComments();

    for (CommentData comment : levelComments) {
      WidgetConfiguration configuration = new WidgetConfiguration(300, 50,
          "row-cell", language);
      Button username = factory.makeButton(configuration, comment.getUsername());
      Button timeSpent = factory.makeButton(configuration, comment.getComment());
      Button date = factory.makeButton(configuration, comment.getDate());
      Button replies = factory.makeButton(configuration, comment.getReplies());
      Button replyButton = factory.makeButton(configuration, "Reply");

      username.setDisable(true);
      timeSpent.setDisable(true);
      date.setDisable(true);
      replies.setDisable(true);

      replyButton.setOnAction(event -> addReply(comment.getUsername()));
      List<Node> row = new ArrayList<>(Arrays.asList(username, timeSpent, date, replies, replyButton));
      HBox rowBtns = factory.wrapInHBox(row, width - 400);
      commentList.getChildren().add(rowBtns);
    }

    Button backButton = factory.makeButton(new WidgetConfiguration(200, 50,
        "Back", "button", language));
    backButton.setOnAction(event -> sceneController.switchToScene(new MainScene(sceneController, levelController)));

    root.getChildren().addAll(header, commentList, backButton);
  }

  private void addReply(String targetUsername) {
    // Create a new dialog where users can type their reply
    TextArea replyArea = new TextArea();
    replyArea.setPromptText("Type your reply here...");

    Stage dialogStage = new Stage();
    dialogStage.initModality(Modality.APPLICATION_MODAL);
    dialogStage.setTitle("Reply to Comment");

    Button submitButton = new Button("Submit");
    submitButton.setOnAction(e -> {
      DatabaseController databaseController = sceneController.getDatabaseController();
      String playerUsername = databaseController.getUsername();
      databaseController.addReply(targetUsername, playerUsername, replyArea.getText());
      dialogStage.close();
    });

    VBox dialogVBox = new VBox(10, replyArea, submitButton);
    dialogVBox.setAlignment(Pos.CENTER);

    javafx.scene.Scene dialogScene = new javafx.scene.Scene(dialogVBox, 300, 200);
    dialogStage.setScene(dialogScene);
    dialogStage.show();
  }

}
