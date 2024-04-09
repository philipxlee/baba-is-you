package oogasalad.view.gameplay.gamestates;

import javafx.geometry.Pos;
import javafx.scene.control.Label;
import javafx.scene.layout.VBox;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import oogasalad.shared.scene.Scene;

/**
 * Scene that displays when the player wins the game.
 */
public class WinScene implements Scene {

  private javafx.scene.Scene scene;
  private VBox root;

  /**
   * Initialize the scene.
   *
   * @param width  width of scene
   * @param height height of scenes
   */
  @Override
  public void initializeScene(int width, int height) {
    this.root = new VBox();
    this.root.setAlignment(Pos.CENTER);
    this.scene = new javafx.scene.Scene(root, width, height);
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
    root.setStyle("-fx-background-color: #191A20;");
    Label winLabel = new Label("YOU WIN!");
    winLabel.setFont(Font.font("Arial", FontWeight.BOLD, 72));
    winLabel.setTextFill(javafx.scene.paint.Color.GREEN);
    root.getChildren().add(winLabel);
  }
}
