package oogasalad.view.gameplay.mainscene;

import com.google.gson.JsonObject;
import java.io.File;
import java.io.InputStream;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Arrays;
import javafx.geometry.Pos;
import javafx.scene.control.Label;
import javafx.scene.control.ScrollPane;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.FlowPane;
import javafx.scene.layout.VBox;
import javafx.scene.text.Text;
import javafx.stage.Stage;
import oogasalad.controller.gameplay.LevelController;
import oogasalad.controller.gameplay.SceneController;
import oogasalad.shared.config.JsonManager;
import oogasalad.shared.widgetfactory.WidgetConfiguration;
import oogasalad.shared.widgetfactory.WidgetFactory;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

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
  private static final Logger logger = LogManager.getLogger(FileChooserPane.class);

  /**
   * Initializes the file chooser in the Interaction Pane.
   *
   * @param width           width of the file chooser
   * @param height          height of the file chooser
   * @param language        language specified
   * @param levelController LevelController object for this pane
   * @param sceneController SceneController object for this pane
   */
  public FileChooserPane(int width, int height, String language, LevelController levelController,
      SceneController sceneController) {
    this.factory = new WidgetFactory();
    this.width = width;
    this.height = height;
    this.language = language;
    this.jsonManager = new JsonManager();
    this.sceneController = sceneController;
    this.levelController = levelController;

    //Get all the files in the /data directory
    Path gamePath = Paths.get("data");
    File gameDirectory = gamePath.toFile();
    if (gameDirectory.exists() && gameDirectory.isDirectory()) {
      files = gameDirectory.listFiles();
    } else {
      logger.error("File chooser culd not be initialized: directory does not exist or is"
          + " not a directory.");
    }

    InputStream stream = getClass().getResourceAsStream("/images/FileIcon.png");
    fileIcon = new Image(stream);

    fileChooser = setUpFileChooser();
  }

  /**
   * Returns the root of this pane
   *
   * @return VBox root
   */
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
      for (int i = 0; i < files.length; i++) {
        if (!files[i].getName().toLowerCase().endsWith(".json")) {
          continue;
        }
        JsonObject jsonFile = jsonManager.loadJsonFromFile(files[i]);
        String fileName = jsonManager.getValue(jsonFile, "levelName");
        ImageView imageView = new ImageView(fileIcon);
        imageView.setFitWidth(80);
        imageView.setFitHeight(80);

        VBox imageAndLabel = createIconAndName(fileName, imageView);

        setImageClickEvent(imageView, jsonFile);
        flowPane.getChildren().add(imageAndLabel);
      }
    } catch (Exception e) {
      logger.info("Couldn't populate file chooser: " + e.getMessage());
    }
  }

  /**
   * Sets the click event for each ImageView object in the file chooser.
   *
   * @param imageView the current ImageView object
   * @param jsonFile  the Json file it corresponds to
   */
  private void setImageClickEvent(ImageView imageView, JsonObject jsonFile) {
    imageView.setOnMouseClicked(event -> {
      JsonObject gridObject = jsonManager.getJsonObject(jsonFile, "grid");
      JsonObject metadata = jsonManager.getJsonObject(gridObject, "metadata");
      JsonObject dimensions = jsonManager.getJsonObject(jsonFile, "gridSize");

      //Pull out metadata from the file
      Text rows = factory.generateCaption("Rows: " + jsonManager.getValue(
          dimensions, "rows"));
      Text cols = factory.generateCaption("Cols: " + jsonManager.getValue(
          dimensions, "columns"));
      Text difficulty;

      if (metadata != null) {
        difficulty = factory.generateCaption("Difficulty: " + jsonManager.getValue(
            metadata, "difficulty"));
      } else {
        difficulty = new Text();
      }
      VBox vbox = factory.wrapInVBox(new ArrayList<>(Arrays.asList(rows, cols, difficulty)),
          width - 100, 10);

      factory.createPopUpWindow(new WidgetConfiguration(width - 100,
              height / 4, "FileInformation", "root", language), vbox,
          new Stage());
    });
  }

  /**
   * Wraps the file Icon image with the filename text in a VBox.
   *
   * @param fileName  name of the file
   * @param imageView file icon associated
   * @return stylized VBox
   */
  private VBox createIconAndName(String fileName, ImageView imageView) {
    Label name = new Label(fileName);
    name.getStyleClass().add("caption-label");
    name.setWrapText(true);
    name.setPrefWidth(100);
    VBox imageAndLabel = new VBox(10);
    imageAndLabel.getChildren().addAll(imageView, name);
    return imageAndLabel;
  }


}
