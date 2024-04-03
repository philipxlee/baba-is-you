package oogasalad.view.authoring;

import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;
import javafx.scene.layout.Priority;
import javafx.scene.paint.Color;
import oogasalad.shared.scene.Scene;

import javafx.scene.control.SplitPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import oogasalad.shared.scene.Scene;

public class MainScene implements Scene {
    private javafx.scene.Scene scene;
    private SplitPane root;
    private BuilderScene builderScene;
    private ElementsScene elementsScene;

    @Override
    public void initializeScene(int width, int height) {
        root = new SplitPane();
        root.setDividerPositions(0.4); // Set divider position to 60%

        // Initialize builder scene with 60% of width
        this.builderScene = new BuilderScene();
        int builderWidth = (int) (width * 0.4);
        builderScene.initializeBuilderScene(builderWidth, height, this);

        // Initialize elements scene with 40% of width
        this.elementsScene = new ElementsScene();
        int elementsWidth = (int) (width * 0.6);
        elementsScene.initializeElementsScene(elementsWidth, height, this);

        // Set up left and right sides of SplitPane
        VBox elementsBox = new VBox();
        elementsBox.getChildren().addAll(elementsScene.getScrollPane());
        VBox.setVgrow(elementsScene.getScrollPane(), javafx.scene.layout.Priority.ALWAYS);

        VBox builderBox = new VBox();
        builderBox.getChildren().addAll(builderScene.getRoot());
        VBox.setVgrow(builderScene.getRoot(), javafx.scene.layout.Priority.ALWAYS);

        root.getItems().addAll(builderBox, elementsBox); // Switched the order here

        this.scene = new javafx.scene.Scene(root, width, height);
        scene.setFill(Color.WHITE);
    }

    public javafx.scene.Scene getScene() {
        return this.scene;
    }
}




