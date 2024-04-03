package oogasalad.shared.config;

import com.google.gson.Gson;
import com.google.gson.JsonObject;
import com.google.gson.JsonSyntaxException;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import javafx.stage.FileChooser;
import javafx.stage.Stage;

/**
 * JsonManager class is responsible for allowing developers to take information from the authoring
 * environment and the game player to either save it as a JSON file or to load in JSON files.
 */
public class JsonManager {

  private static final Gson gson = new Gson();

  /**
   * loadFromFile() is responsible for loading in JSON data from a JSON file that a user selects.
   *
   * @param stage stage that JavaFX uses to display the file chooser.
   */
  public JsonObject loadFromFile(Stage stage) throws IOException {
    File file = selectFile(stage);
    if (file != null) {
      try (FileReader reader = new FileReader(file)) {
        JsonObject tempJson = gson.fromJson(reader, JsonObject.class);
        return tempJson;
      } catch (JsonSyntaxException e) {
        throw new RuntimeException(e);
      }
    }
    return null;
  }

  /**
   * Save JSON data to a JSON file.
   *
   * @param jsonObject the JsonObject to save.
   * @param stage      the JavaFX stage used to display the file chooser dialog.
   */
  public void saveToFile(JsonObject jsonObject, Stage stage) throws IOException {
    File file = saveFile(stage);
    if (file != null) {
      try (FileWriter writer = new FileWriter(file)) {
        gson.toJson(jsonObject, writer);
      }
    }
  }

  /**
   * Add a property to a JsonObject. Also replaces the value if an existing key is given.
   *
   * @param jsonObject the JsonObject to which the property will be added.
   * @param key        the key of the property.
   * @param value      the value of the property.
   */
  public void addProperty(JsonObject jsonObject, String key, String value) {
    jsonObject.addProperty(key, value);
  }

  /**
   * Opens a file chooser dialog for the user to select a JSON file.
   *
   * @param stage stage that JavaFX uses to display the file chooser.
   */
  private File selectFile(Stage stage) {
    FileChooser fileChooser = chooseFile();
    return fileChooser.showOpenDialog(stage);
  }

  /**
   * Displays a file chooser dialog for saving a JSON file.
   *
   * @param stage stage that JavaFX uses to display the file chooser.
   */
  private File saveFile(Stage stage) {
    FileChooser fileChooser = chooseFile();
    return fileChooser.showSaveDialog(stage);
  }

  /**
   * Creates a FileChooser that will open JSON files with a specific extension filter.
   */
  private FileChooser chooseFile() {
    FileChooser fileChooser = new FileChooser();
    fileChooser.setTitle("Open JSON File");
    fileChooser.getExtensionFilters().addAll(
        new FileChooser.ExtensionFilter("JSON Files", "*.json")
    );
    return fileChooser;
  }

}
