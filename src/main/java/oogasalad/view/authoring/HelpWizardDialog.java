package oogasalad.view.authoring;

import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.VBox;
import javafx.stage.Modality;
import javafx.stage.Stage;

public class HelpWizardDialog {

    public static void showWizardDialog() {
        Stage dialogStage = new Stage();
        dialogStage.initModality(Modality.APPLICATION_MODAL);
        dialogStage.setTitle("Help Wizard");

        VBox dialogVBox = new VBox(20);
        dialogVBox.setPadding(new Insets(20));

        Label instruction1 = new Label("Welcome to the Game Authoring Help Wizard!");
        Label instruction2 = new Label("Follow the steps below to learn how to use the features:");
        Label step1 = new Label("1. Drag and drop game objects from the toolbar to the canvas.");
        Label step2 = new Label("2. Use the category dropdown to filter game objects.");
        Label step3 = new Label("3. Click the 'Save JSON' button to save your game configuration.");
        Label step4 = new Label("4. Use keyboard shortcuts Ctrl+S to save and Ctrl+L to load levels.");

        Button closeButton = new Button("Close");
        closeButton.setOnAction(event -> dialogStage.close());

        dialogVBox.getChildren().addAll(instruction1, instruction2, step1, step2, step3, step4, closeButton);

        Scene dialogScene = new Scene(dialogVBox, 400, 300);
        dialogStage.setScene(dialogScene);
        dialogStage.show();
    }
}

