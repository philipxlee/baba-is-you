package oogasalad.view.gameplay.gamestates;

import static oogasalad.shared.widgetfactory.WidgetFactory.DEFAULT_RESOURCE_FOLDER;
import static oogasalad.shared.widgetfactory.WidgetFactory.STYLESHEET;

import java.util.ArrayList;
import java.util.List;
import javafx.geometry.Pos;
import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.layout.VBox;
import javafx.scene.text.Text;
import oogasalad.controller.gameplay.SceneController;
import oogasalad.shared.scene.Scene;
import oogasalad.shared.widgetfactory.WidgetFactory;

/**
 * Scene that displays when the player wins the game.
 */
public class WinScene implements Scene {

  private javafx.scene.Scene scene;
  private VBox root;
  private WidgetFactory factory;
  private final SceneController sceneController;
  private int width;
  private int height;

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

    //Generate win stats here, eventually

    List<Node> texts = new ArrayList<>();
    texts.add(header);
    texts.add(content);

    Button start = factory.makeButton("Play Again", 200, 40);
    texts.add(start);
    start.setOnAction(event -> {
      sceneController.beginGame();
    });

    VBox textContainer = factory.wrapInVBox(texts, height);
    root.getChildren().add(textContainer);
  }
}
