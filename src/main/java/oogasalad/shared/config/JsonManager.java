package oogasalad.shared.config;

import com.google.gson.Gson;
import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
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
   * @return the JsonObject that represents the JSON file provided.
   */
  public JsonObject loadFromFile() throws IOException {
    Stage stage = new Stage();
    File file = selectFile(stage);
    if (file != null) {
      return loadJsonFromFile(file);
    }
    return null;
  }

  /**
   * Loads JSON data from the specified file using Gson library.
   *
   * @param file The File object representing the JSON file to be loaded.
   * @return The JsonObject containing the parsed JSON data.
   */
  public JsonObject loadJsonFromFile(File file) throws IOException {
    try (FileReader reader = new FileReader(file)) {
      return gson.fromJson(reader, JsonObject.class);
    } catch (JsonSyntaxException e) {
      throw new RuntimeException(e);
    }
  }

  /**
   * Save JSON data to a JSON file.
   *
   * @param jsonObject the JsonObject to save.
   * @param fileName   the template filename of the file.
   */
  public void saveToFile(JsonObject jsonObject, String fileName) throws IOException {
    Stage stage = new Stage();
    File file = selectSaveFile(stage, fileName);
    if (file != null) {
      writeToFile(jsonObject, file);
    }
  }

  /**
   * Writes a JsonObject to a specified File using Gson library.
   *
   * @param jsonObject The JsonObject to write to the file.
   * @param file       The File object representing the file to write to.
   */
  protected void writeToFile(JsonObject jsonObject, File file) throws IOException {
    try (FileWriter writer = new FileWriter(file)) {
      gson.toJson(jsonObject, writer);
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
   * Add a JsonObject to a JsonObject. Also replaces the value if an existing key is given.
   *
   * @param jsonObject the JsonObject to which the JsonObject will be added.
   * @param key        the key of the object.
   * @param object     the JsonObject being added.
   */
  public void addObject(JsonObject jsonObject, String key, JsonObject object) {
    jsonObject.add(key, object);
  }

  /**
   * Adds a JsonArray to a JsonObject under a specified key.
   *
   * @param jsonObject the JsonObject to which the array should be added.
   * @param key        the key under which the array will be added to the object.
   * @param jsonArray  the JsonArray to add.
   */
  public void addArrayToJson(JsonObject jsonObject, String key, JsonArray jsonArray) {
    jsonObject.add(key, jsonArray);
  }

  /**
   * Get the value associated with a key from a JsonObject.
   *
   * @param jsonObject the JsonObject from which to retrieve the value.
   * @param key        the key whose value is to be retrieved.
   * @return a string representation of the value associated with the key provided
   */
  public String getValue(JsonObject jsonObject, String key) {
    JsonElement jsonElement = jsonObject.get(key);
    if (jsonElement != null && !jsonElement.isJsonNull()) {
      return jsonElement.getAsString();
    }
    return null;
  }

  /**
   * Retrieves a nested JsonObject based on the specified key.
   *
   * @param parentJsonObject The parent JsonObject from which to retrieve the nested JsonObject.
   * @param key              The key corresponding to the nested JsonObject.
   * @return The nested JsonObject if it exists, or null if the key does not exist or is not a
   * JsonObject.
   */
  public JsonObject getJsonObject(JsonObject parentJsonObject, String key) {
    if (parentJsonObject != null && parentJsonObject.has(key) && parentJsonObject.get(key)
        .isJsonObject()) {
      return parentJsonObject.getAsJsonObject(key);
    }
    return null;
  }

  /**
   * Retrieves a JsonArray from a JsonObject using the specified key.
   *
   * @param jsonObject the JsonObject from which to retrieve the array.
   * @param key        the key corresponding to the JsonArray.
   * @return the JsonArray if it exists and is a JsonArray, or an empty jsonArray..
   */
  public JsonArray getJsonArray(JsonObject jsonObject, String key) {
    JsonElement element = jsonObject.get(key);
    if (element != null && element.isJsonArray()) {
      return element.getAsJsonArray();
    }
    return new JsonArray();
  }

  /**
   * Opens a file chooser dialog for the user to select a JSON file.
   *
   * @param stage stage that JavaFX uses to display the file chooser.
   * @return the file chosen by the user
   */
  private File selectFile(Stage stage) {
    FileChooser fileChooser = chooseFile();
    return fileChooser.showOpenDialog(stage);
  }

  /**
   * Displays a file chooser dialog for saving a JSON file.
   *
   * @param stage    stage that JavaFX uses to display the file chooser.
   * @param fileName The template filename to use.
   * @return the file chosen to be saved by the user
   */
  private File selectSaveFile(Stage stage, String fileName) {
    FileChooser fileChooser = chooseFile();
    fileChooser.setInitialFileName(fileName);
    return fileChooser.showSaveDialog(stage);
  }

  /**
   * Creates a FileChooser that will open JSON files with a specific extension filter.
   *
   * @return the file chooser that the user will use
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
