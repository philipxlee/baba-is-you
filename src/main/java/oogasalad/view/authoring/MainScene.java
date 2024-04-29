package oogasalad.view.authoring;

import static oogasalad.shared.widgetfactory.WidgetFactory.DEFAULT_RESOURCE_FOLDER;
import static oogasalad.shared.widgetfactory.WidgetFactory.STYLESHEET;

import javafx.scene.control.SplitPane;
import oogasalad.controller.authoring.LevelController;
import oogasalad.controller.authoring.SceneController;
import oogasalad.shared.scene.Scene;

public class MainScene implements Scene {

  private String language;
  private final LevelController levelController;
  private javafx.scene.Scene scene;
  private SceneController sceneController;

  public MainScene(LevelController levelController, SceneController sceneController) {
    this.sceneController = sceneController;
    this.levelController = levelController;
  }

  @Override
  public void initializeScene(int width, int height) {
    SplitPane root = new SplitPane();

    // Initialize builder scene with 60% of width
    BuilderPane builderPane = new BuilderPane(levelController, levelController.getLanguage());

    // Initialize elements scene with 40% of width
    ElementsPane elementsPane = new ElementsPane(builderPane, levelController, this);

    // Set up left and right sides of SplitPane
    root.getItems().addAll(elementsPane.getLayout(), builderPane.getRoot());
    root.setDividerPositions(0.4);

    this.scene = new javafx.scene.Scene(root, width, height);

    applyCss(DEFAULT_RESOURCE_FOLDER, STYLESHEET);
  }

  public javafx.scene.Scene getScene() {
    return this.scene;
  }

  public void goToEntryPoint() {
    this.sceneController.goToEntryPoint();
  }
}
