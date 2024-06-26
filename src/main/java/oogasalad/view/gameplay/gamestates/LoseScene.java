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
import oogasalad.shared.widgetfactory.WidgetConfiguration;
import oogasalad.shared.widgetfactory.WidgetFactory;
import oogasalad.view.gameplay.StartingScene;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

/**
 * Scene that displays when the player loses the game.
 *
 * @author Philip Lee, Yasha Doddabele.
 */
public class LoseScene implements Scene {

  private javafx.scene.Scene scene;
  private VBox root;
  private WidgetFactory factory;
  private final SceneController sceneController;
  private int width;
  private int height;
  private final String language;
  private static final Logger logger = LogManager.getLogger(LoseScene.class);

  /**
   * Constructor for the LoseScene class.
   *
   * @param sceneController the controller that manages the scenes.
   */
  public LoseScene(SceneController sceneController) {
    this.sceneController = sceneController;
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
    this.factory = new WidgetFactory();
    this.root = new VBox(20);
    this.root.setAlignment(Pos.CENTER);
    this.scene = new javafx.scene.Scene(root, width, height);
    applyCss(DEFAULT_RESOURCE_FOLDER, STYLESHEET);
    showLoseMessage();
    logger.info("Entered lose scene.");
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
   * Show the losing message.
   */
  private void showLoseMessage() {
    Text header = factory.generateHeader(new WidgetConfiguration("LostHeader", language));
    Text content = factory.generateLine(new WidgetConfiguration("LostContent", language));

    List<Node> texts = new ArrayList<>();
    texts.add(header);
    texts.add(content);

    //Create the start over button, which takes you to the starting scene
    Button start = factory.makeButton(new WidgetConfiguration(200, 40,
        "TryAgain", "button", language));
    start.setId("tryAgainButton");
    texts.add(start);
    start.setOnAction(event -> {
      sceneController.beginGame(sceneController.isGuestSession());
    });

    VBox textContainer = factory.wrapInVBox(texts, height, 10);
    root.getChildren().add(textContainer);
  }
}
