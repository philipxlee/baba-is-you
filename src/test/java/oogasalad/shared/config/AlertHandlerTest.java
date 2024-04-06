package oogasalad.shared.config;

import javafx.scene.input.KeyCode;
import javafx.stage.Stage;
import oogasalad.app.GamePlayer;
import oogasalad.shared.AlertHandler;
import org.junit.jupiter.api.Test;
import org.testfx.framework.junit5.ApplicationTest;


public class AlertHandlerTest extends ApplicationTest implements AlertHandler {

  @Override
  public void start(Stage stage) throws Exception {
    GamePlayer gamePlayer = new GamePlayer();
    gamePlayer.start(stage);
  }

  @Test
  public void testAlertShowsUp() {
    //GIVEN the AlertHandlerInterface
    interact(() -> {
      //WHEN the different methods are utilized
      press(KeyCode.ENTER).release(KeyCode.ENTER);
      showError("Error", "Error");
      press(KeyCode.ENTER).release(KeyCode.ENTER);
      showInformation("Info", "Info");
      press(KeyCode.ENTER).release(KeyCode.ENTER);
      showWarning("Warning", "Warning");
      press(KeyCode.ENTER).release(KeyCode.ENTER);
      //THEN the correct alerts will pop up
    });
  }

}
