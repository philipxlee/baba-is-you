package oogasalad.view.gameplay;

import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.layout.VBox;
import javafx.scene.text.Text;
import oogasalad.controller.gameplay.SceneController;
import oogasalad.shared.scene.Scene;
import oogasalad.shared.widgetfactory.WidgetFactory;

public class LeaderboardScene implements Scene {

  private final WidgetFactory factory;
  private final SceneController sceneController;
  private javafx.scene.Scene scene;
  private VBox root;

  public LeaderboardScene(WidgetFactory factory, SceneController sceneController) {
    this.factory = factory;
    this.sceneController = sceneController;
  }

  @Override
  public void initializeScene(int width, int height) {
    this.root = new VBox(10);
    this.root.setAlignment(Pos.CENTER);
    this.scene = new javafx.scene.Scene(root, width, height);
    populateLeaderboard();
  }

  private void populateLeaderboard() {
    Text header = new Text("Leaderboard");
    header.setStyle("-fx-font-size: 24px;");

    Button backButton = factory.makeButton("Back", 200, 40);
    backButton.setOnAction(event -> sceneController.switchToScene(new MainScene(sceneController)));  // Assuming MainScene is the previous scene

    root.getChildren().addAll(header, backButton);
  }

  @Override
  public javafx.scene.Scene getScene() {
    return this.scene;
  }
}
