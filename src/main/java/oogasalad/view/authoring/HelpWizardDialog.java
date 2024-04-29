package oogasalad.view.authoring;

import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.VBox;
import javafx.stage.Modality;
import javafx.stage.Stage;
import java.util.ResourceBundle;

public class HelpWizardDialog {

  /**
   * Displays a modal dialog with step-by-step instructions on how to use the game authoring
   * environment. The dialog provides a sequence of steps outlining basic operations for game
   * creation, such as dragging and dropping game objects, using the category filter, and
   * saving/loading game configurations. It is designed to guide new users through the basic
   * functionalities of the game authoring tool.
   * <p>
   * Steps include: 1. Dragging and dropping game objects from the toolbar to the canvas. 2. Using
   * the category dropdown to filter game objects. 3. Saving game configurations using the 'Save
   * JSON' button. 4. Utilizing keyboard shortcuts for saving (Ctrl+S) and loading (Ctrl+L) levels.
   * <p>
   * A close button is provided to exit the help dialog.
   */

  private static final ResourceBundle RESOURCES = ResourceBundle.getBundle(
      "auth_view/HelpWizardText");

  public static void showWizardDialog() {
    Stage dialogStage = new Stage();
    dialogStage.initModality(Modality.APPLICATION_MODAL);
    dialogStage.setTitle(RESOURCES.getString("title"));

    VBox dialogVBox = new VBox(20);
    dialogVBox.setPadding(new Insets(20));

    Label instruction1 = new Label(RESOURCES.getString("welcomeMessage"));
    Label instruction2 = new Label(RESOURCES.getString("instructionMessage"));
    Label step1 = new Label(RESOURCES.getString("step1"));
    Label step2 = new Label(RESOURCES.getString("step2"));
    Label step3 = new Label(RESOURCES.getString("step3"));
    Label step4 = new Label(RESOURCES.getString("step4"));

    Button closeButton = new Button(RESOURCES.getString("closeButtonText"));
    closeButton.setOnAction(event -> dialogStage.close());

    dialogVBox.getChildren()
        .addAll(instruction1, instruction2, step1, step2, step3, step4, closeButton);

    Scene dialogScene = new Scene(dialogVBox, 400, 300);
    dialogStage.setScene(dialogScene);
    dialogStage.show();
  }
}