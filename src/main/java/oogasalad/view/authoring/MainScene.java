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

        // Initialize builder scene with 60% of width
        this.builderScene = new BuilderScene();

        // Initialize elements scene with 40% of width
        this.elementsScene = new ElementsScene();

        // Set up left and right sides of SplitPane
        root.getItems().addAll(builderScene.getRoot(), elementsScene.getLayout());
        root.setDividerPositions(0.4);

        this.scene = new javafx.scene.Scene(root, width, height);
        scene.setFill(Color.WHITE);
    }

    public javafx.scene.Scene getScene() {
        return this.scene;
    }
}




