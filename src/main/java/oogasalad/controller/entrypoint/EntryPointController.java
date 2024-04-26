package oogasalad.controller.entrypoint;

import javafx.stage.Stage;
import oogasalad.app.AuthoringEnvironment;
import oogasalad.app.GamePlayer;
import oogasalad.shared.alert.AlertHandler;
import oogasalad.shared.entrypoint.EntryPoint;
import oogasalad.shared.scene.Scene;

/**
 * Controller to facilitate entrance to the authoring env app or the gameplay app.
 */
public class EntryPointController implements AlertHandler {

  private static final int WIDTH = 1500;
  private static final int HEIGHT = 800;
  private String language;
  private GamePlayer gamePlayer;
  private AuthoringEnvironment authoringEnvironment;
  private final Stage stage;

  public EntryPointController(Stage stage) {
    this.stage = stage;
    this.language = "English";
  }

  public void initializeViews() {
    switchToScene(new EntryPoint(this));
  }

  public void switchToScene(Scene scene) {
    scene.initializeScene(WIDTH, HEIGHT);
    stage.setScene(scene.getScene());
  }

  public void switchToGamePlay() {
    gamePlayer = new GamePlayer();
    gamePlayer.setLanguage(language);
    try {
      gamePlayer.start(stage);
    } catch (Exception e) {
      showError("ERROR", e.getMessage());
    }
  }

  public void switchToAuthoringEnvironment() {
    this.authoringEnvironment = new AuthoringEnvironment();
    authoringEnvironment.setLanguage(language);
    authoringEnvironment.start(stage);
  }

  public void showError(String title, String message) {
    AlertHandler.super.showError(title, message);
  }

  public void setLanguage(String newLanguage) {
    this.language = newLanguage;
    switchToScene(new EntryPoint(this));

  }

  public String getLanguage() {
    return this.language;
  }

}
