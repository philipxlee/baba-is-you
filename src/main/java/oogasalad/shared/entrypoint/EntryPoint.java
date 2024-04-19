package oogasalad.shared.entrypoint;

import static oogasalad.shared.widgetfactory.WidgetFactory.DEFAULT_RESOURCE_FOLDER;
import static oogasalad.shared.widgetfactory.WidgetFactory.STYLESHEET;

import java.awt.Color;
import java.awt.Paint;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.Arrays;
import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.image.Image;
import javafx.scene.layout.Background;
import javafx.scene.layout.BackgroundImage;
import javafx.scene.layout.BackgroundPosition;
import javafx.scene.layout.BackgroundRepeat;
import javafx.scene.layout.BackgroundSize;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Region;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.scene.shape.Rectangle;
import oogasalad.controller.entrypoint.EntryPointController;
import oogasalad.shared.scene.Scene;
import oogasalad.shared.widgetfactory.WidgetConfiguration;
import oogasalad.shared.widgetfactory.WidgetFactory;

/**
 * Scene representing the entry point to go to the authoring env or the gameplay.
 */
public class EntryPoint implements Scene {

  private WidgetFactory factory;
  private int width;
  private int height;
  private StackPane root;
  private javafx.scene.Scene scene;
  private EntryPointController entryController;
  private String language;

  public EntryPoint(EntryPointController entryController) {
    this.entryController = entryController;
  }
  @Override
  public void initializeScene(int width, int height) {
    this.width = width;
    this.height = height;
    this.language = entryController.getLanguage();
    this.factory = new WidgetFactory();
    this.root = new StackPane();

    populateSplashArt();
    setUpButtons();
    this.scene = new javafx.scene.Scene(root, width, height);
    getScene().getStylesheets().add(getClass().getResource(DEFAULT_RESOURCE_FOLDER + STYLESHEET)
        .toExternalForm());
  }

  private void setUpButtons() {
    Button gamePlay = factory.makeButton(new WidgetConfiguration(200, 50,
        "GameEntrance", "white-button", language));
    gamePlay.setOnAction(event -> {
      entryController.switchToGamePlay();
    });
    Button authoring = factory.makeButton(new WidgetConfiguration(200, 50,
        "AuthEntrance", "white-button", language));
    authoring.setOnAction(event -> {
      entryController.switchToAuthoringEnvironment();
    });

    HBox buttons = factory.wrapInHBox(new ArrayList<>(Arrays.asList(gamePlay, authoring)),
        width/2);
    buttons.setAlignment(Pos.BASELINE_CENTER);
    setUpLanguageChooser(buttons);
  }

  private void setUpLanguageChooser(HBox buttons) {
    ComboBox<String> switchLanguage = factory.makeComboBox(new WidgetConfiguration(200, 40,
        "SwitchLanguage", "combo-box", language), new ArrayList<>(Arrays.asList("English",
        "Spanish")), language);
    //TODO: Change to be a drop down
    switchLanguage.setOnAction(event -> {
      language = switchLanguage.getValue();
      entryController.setLanguage(language);
      entryController.switchToScene(new EntryPoint(entryController));
    });
    VBox buttonsAndCombo = factory.wrapInVBox(new ArrayList<>(Arrays.asList(buttons, switchLanguage)),
        height/4, 15);
    root.getChildren().add(buttonsAndCombo);
  }

  private void populateSplashArt() {
    InputStream stream = getClass().getResourceAsStream("/images/EntryPointBg.png");
    Image image = new Image(stream);

    BackgroundImage background = new BackgroundImage(
        image,
        BackgroundRepeat.NO_REPEAT,
        BackgroundRepeat.NO_REPEAT,
        BackgroundPosition.DEFAULT,
        new BackgroundSize(BackgroundSize.AUTO, BackgroundSize.AUTO, false,
            false, true, false)
    );

    Background backgroundImage = new Background(background);
    root.setBackground(backgroundImage);
  }

  @Override
  public javafx.scene.Scene getScene() {
    return this.scene;
  }
}
