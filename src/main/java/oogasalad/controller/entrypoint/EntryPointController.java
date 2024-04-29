package oogasalad.controller.entrypoint;

import javafx.stage.Stage;
import oogasalad.app.AuthoringEnvironment;
import oogasalad.app.GamePlayer;
import oogasalad.controller.gameplay.GameGridController;
import oogasalad.shared.alert.AlertHandler;
import oogasalad.shared.entrypoint.EntryPoint;
import oogasalad.shared.scene.Scene;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

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
  private static final Logger logger = LogManager.getLogger(EntryPointController.class);

  /**
   * Initializes the controller with the stage.
   * @param stage JavaFx stage to set the scene
   */
  public EntryPointController(Stage stage) {
    this.stage = stage;
    this.language = "English";
  }

  /**
   * Initialize the entrypoint scene.
   */
  public void initializeViews() {
    switchToScene(new EntryPoint(this));
  }

  /**
   * Switch to an input scene.
   * @param scene new scene to go to
   */
  public void switchToScene(Scene scene) {
    scene.initializeScene(WIDTH, HEIGHT);
    stage.setScene(scene.getScene());
  }

  /**
   * Switch to gameplay environment scene.
   */
  public void switchToGamePlay() {
    gamePlayer = new GamePlayer();
    gamePlayer.setLanguage(language);
    try {
      gamePlayer.start(stage);
    } catch (Exception e) {
      showError("ERROR", e.getMessage());
      logger.error("Issues switching to gameplay: " + e.getMessage());
    }
  }

  /**
   * Switch to authoring environment scene.
   */
  public void switchToAuthoringEnvironment() {
    this.authoringEnvironment = new AuthoringEnvironment();
    authoringEnvironment.setLanguage(language);
    authoringEnvironment.start(stage);
  }

  /**
   * Show an error on the GUI.
   * @param title   the title of the alert dialog.
   * @param message the message to be displayed in the alert dialog.
   */
  public void showError(String title, String message) {
    AlertHandler.super.showError(title, message);
  }

  /**
   * Set the language for the entire application.
   * @param newLanguage new language to switch to
   */
  public void setLanguage(String newLanguage) {
    this.language = newLanguage;
    switchToScene(new EntryPoint(this));

  }

  /**
   * Get the language for the entire application.
   * @return the current language
   */
  public String getLanguage() {
    return this.language;
  }

}
