package oogasalad.view.authoring;

import javafx.geometry.Pos;
import javafx.scene.layout.VBox;
import javafx.scene.text.Text;
import oogasalad.shared.Scene;

/**
 * MainScene is the main scene implementation for Authoring Environment.
 */
public class MainScene implements Scene {

  private javafx.scene.Scene scene;

  /**
   * Initialize the Main Scene. Displays level editing platform.
   *
   * @param width:  width of scene.
   * @param height: height of scene.
   */
  @Override
  public void initializeScene(int width, int height) {
    VBox box = new VBox();
    box.setAlignment(Pos.CENTER);
    Text title = new Text("Authoring Environment: View");
    box.getChildren().addAll(title);
    this.scene = new javafx.scene.Scene(box, width, height);
  }

  /**
   * Get the current scene.
   *
   * @return JavaFX scene.
   */
  @Override
  public javafx.scene.Scene getScene() {
    return this.scene;
  }
}
