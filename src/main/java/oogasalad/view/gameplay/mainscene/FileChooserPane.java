package oogasalad.view.gameplay.mainscene;

import com.google.gson.JsonObject;
import java.io.File;
import java.io.InputStream;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Arrays;
import javafx.scene.control.ScrollPane;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.FlowPane;
import javafx.scene.layout.VBox;
import javafx.scene.text.Text;
import oogasalad.controller.gameplay.LevelController;
import oogasalad.controller.gameplay.SceneController;
import oogasalad.shared.config.JsonManager;
import oogasalad.shared.widgetfactory.WidgetConfiguration;
import oogasalad.shared.widgetfactory.WidgetFactory;

/**
 * UI class to handle the file choosing functionality within the Interaction Pane.
 */

public class FileChooserPane {

  private final WidgetFactory factory;
  private final int width;
  private final int height;
  private final Image fileIcon;
  private FlowPane flowPane;
  private File[] files;
  private final String language;
  private final VBox fileChooser;
  private final JsonManager jsonManager;
  private final LevelController levelController;
  private final SceneController sceneController;


  public FileChooserPane(int width, int height, String language, LevelController levelController,
      SceneController sceneController) {
    this.factory = new WidgetFactory();
    this.width = width;
    this.height = height;
    this.language = language;
    this.jsonManager = new JsonManager();
    this.sceneController = sceneController;
    this.levelController = levelController;

    Path gamePath = Paths.get("data");
    File gameDirectory = gamePath.toFile();
    if (gameDirectory.exists() && gameDirectory.isDirectory()) {
      files = gameDirectory.listFiles();
    } else {
      //TODO: alert
      System.out.println("Directory does not exist or is not a directory.");
    }

    InputStream stream = getClass().getResourceAsStream("/images/FileIcon.png");
    fileIcon = new Image(stream);

    fileChooser = setUpFileChooser();
  }

  public VBox getFileChooser() {
    return fileChooser;
  }

  /**
   * Sets up the scrollpane used for selecting files.
   *
   * @return a scrollpane with populated image icons representing JSON level files.
   */
  private VBox setUpFileChooser() {
    this.flowPane = factory.createFlowPane(new WidgetConfiguration(width - 50,
        height / 4, "flowpane", language));

    populateFiles(flowPane);
    ScrollPane pane = factory.makeScrollPane(flowPane, width - 50);
    Text paneLabel = factory.generateLine(new WidgetConfiguration("Games", language));
    VBox labelAndChooser = factory.wrapInVBox(paneLabel, height / 3);
    labelAndChooser.getChildren().add(pane);
    return labelAndChooser;
  }

  /**
   * Iterates through default game files and populates them within a flowpane. When the file icons
   * representing each game file are clicked, an individual popup dialog window appears with their
   * information.
   *
   * @param flowPane javafx object containing the clickable file icons
   */
  private void populateFiles(FlowPane flowPane) {
    try {
      for (int i = 1; i <= files.length; i++) {
        JsonObject jsonFile = jsonManager.loadJsonFromFile(files[i]);
        String fileName = jsonManager.getValue(jsonFile, "levelName");
        ImageView imageView = new ImageView(fileIcon);
        imageView.setFitWidth(80);
        imageView.setFitHeight(80);

        VBox imageAndLabel = createIconAndName(fileName, jsonFile, imageView);

        imageView.setOnMouseClicked(event -> {
          JsonObject dimensions = jsonManager.getJsonObject(jsonFile, "gridSize");
          Text rows = factory.generateCaption("Rows: " + jsonManager.getValue(
              dimensions, "rows"));
          Text cols = factory.generateCaption("Cols: " + jsonManager.getValue(
              dimensions, "columns"));

          VBox vbox = factory.wrapInVBox(new ArrayList<>(Arrays.asList(rows, cols)),
              width - 100, 10);

          factory.createPopUpWindow(new WidgetConfiguration(width - 100,
              height / 4, "FileInformation", "root", language), vbox);
        });
        flowPane.getChildren().add(imageAndLabel);
      }
    } catch (Exception e) {
//      System.out.println(e.getMessage());
    }
  }

  private VBox createIconAndName(String fileName, JsonObject jsonFile, ImageView imageView) {
    Text name = factory.generateCaption(fileName);
//    Button load = factory.makeButton(new WidgetConfiguration(150, 40,
//        "Load", "white-button", language));
//    load.setOnAction(event -> {
//      try {
//        levelController.loadNewLevel(sceneController);
//      } catch (IOException e) {
//        throw new RuntimeException(e);
//      }
//    });
    VBox imageAndLabel = new VBox(10);
    imageAndLabel.getChildren().addAll(imageView, name);
    return imageAndLabel;
  }


}
