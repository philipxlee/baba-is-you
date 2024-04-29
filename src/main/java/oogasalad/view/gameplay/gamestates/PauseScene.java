package oogasalad.view.gameplay.gamestates;

import static oogasalad.shared.widgetfactory.WidgetFactory.DEFAULT_RESOURCE_FOLDER;
import static oogasalad.shared.widgetfactory.WidgetFactory.STYLESHEET;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import javafx.animation.Timeline;
import javafx.geometry.Pos;
import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.text.Text;
import oogasalad.controller.gameplay.SceneController;
import oogasalad.shared.scene.Scene;
import oogasalad.shared.widgetfactory.WidgetConfiguration;
import oogasalad.shared.widgetfactory.WidgetFactory;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class PauseScene implements Scene {
  private javafx.scene.Scene scene;
  private VBox root;
  private WidgetFactory factory;
  private final SceneController sceneController;
  private int width;
  private int height;
  private final String language;
  private long milliseconds;
  private Timeline timeline;
  private static final Logger logger = LogManager.getLogger(PauseScene.class);

  public PauseScene(SceneController sceneController, long milliseconds, Timeline timeline) {
    this.sceneController = sceneController;
    this.language = sceneController.getLanguage();
    this.milliseconds = milliseconds;
    this.timeline = timeline;
    logger.info("Entered pause scene.");
  }

  @Override
  public void initializeScene(int width, int height) {
    this.width = width;
    this.height = height;
    this.factory = new WidgetFactory();
    this.root = new VBox(20);
    this.root.setAlignment(Pos.CENTER);
    this.scene = new javafx.scene.Scene(root, width, height);
    getScene().getStylesheets().add(getClass().getResource(DEFAULT_RESOURCE_FOLDER + STYLESHEET)
        .toExternalForm());
    showPauseScreen();
  }

  private void showPauseScreen() {
    Text header = factory.generateHeader(new WidgetConfiguration("PauseHeader", language));
    header.setId("header");
    Text content = factory.generateLine(new WidgetConfiguration("PauseContent", language));
    content.setId("content");

    List<Node> texts = new ArrayList<>();
    texts.add(header);

    Text time = factory.generateLine(formatTime());
    HBox timeAndContent = factory.wrapInHBox(new ArrayList<>(Arrays.asList(content, time)), width/4);

    texts.add(timeAndContent);

    Button start = factory.makeButton(new WidgetConfiguration(200, 40,
        "Back", "button", language));
    start.setId("backButton");
    texts.add(start);
    start.setOnAction(event -> {
      sceneController.switchToPreviousScene();
      timeline.play();
    });

    VBox textContainer = factory.wrapInVBox(texts, height, 10);
    root.getChildren().add(textContainer);
  }

  private String formatTime() {
    long min = milliseconds / (60 * 1000);
    long sec = (milliseconds / 1000) % 60;
    long mill = milliseconds % 1000;
    return String.format("%02d:%02d:%03d", min, sec, mill);
  }

  @Override
  public javafx.scene.Scene getScene() {
    return this.scene;
  }
}
