package oogasalad.view.gameplay;

import static oogasalad.shared.widgetfactory.WidgetFactory.DEFAULT_RESOURCE_FOLDER;
import static oogasalad.shared.widgetfactory.WidgetFactory.STYLESHEET;

import java.util.ArrayList;
import java.util.List;
import javafx.geometry.Pos;
import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.text.Text;
import oogasalad.controller.gameplay.SceneController;
import oogasalad.shared.scene.Scene;
import oogasalad.shared.widgetfactory.WidgetFactory;

/**
 * Splash screen class for beginning the GamePlay.
 */
public class StartingScene implements Scene {

  private final SceneController sceneController;
  private javafx.scene.Scene scene;
  private HBox root;
  private WidgetFactory factory;
  private int width;
  private int height;

  private final static String rules =
      "Baba Is You is a puzzle game where players manipulate the game's\n "
          + "rules to solve puzzles and progress. Players control Baba, a character, and aim to reach specific\n"
          + " goals like touching flags or objects. The game world consists of blocks with words defining\n"
          + " rules like 'Baba Is You' or 'Flag Is Win.' By moving blocks, players change rules to create\n"
          + " win conditions or alter the game's logic. For example, by arranging 'Flag Is You' near a \n"
          + "flag, Baba becomes the flag and wins. Players must think logically, as changing rules can\n"
          + " have unintended consequences. Through experimentation, players solve increasingly complex puzzles.";

  public StartingScene(SceneController sceneController) {
    this.sceneController = sceneController;
  }

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

  private void generateContent() {
    Text header = factory.generateHeader("Welcome to Baba Is You!");
    Text content = factory.generateLine(rules);

    List<Node> texts = new ArrayList<>();
    texts.add(header);
    texts.add(content);

    Button start = factory.makeButton("Click Enter or Here To Begin", 300, 40);
    texts.add(start);
    start.setDefaultButton(true); // Allows enter key to trigger button
    setStartButtonAction(start);

    VBox textContainer = factory.wrapInVBox(texts, height);
    root.getChildren().add(textContainer);
  }

  private void setStartButtonAction(Button start) {
    start.setOnAction(event -> {
      sceneController.beginGame();
    });
  }

  @Override
  public javafx.scene.Scene getScene() {
    return this.scene;
  }
}
