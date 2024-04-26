package oogasalad.shared.entrypoint;

import java.io.InputStream;
import java.util.ArrayList;
import java.util.Arrays;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.image.Image;
import javafx.scene.layout.Background;
import javafx.scene.layout.BackgroundImage;
import javafx.scene.layout.BackgroundPosition;
import javafx.scene.layout.BackgroundRepeat;
import javafx.scene.layout.BackgroundSize;
import javafx.scene.layout.HBox;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.scene.text.Text;
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
  private StackPane pane;
  private javafx.scene.Scene scene;
  private final EntryPointController entryController;
  private String language;

  public static final String STYLESHEET = "entrypoint.css";
  public static final String DEFAULT_RESOURCE_PACKAGE = "stylesheets.";
  public static final String DEFAULT_RESOURCE_FOLDER =
      "/" + DEFAULT_RESOURCE_PACKAGE.replace(".", "/");

  /**
   * Constructor: initializes with controller
   *
   * @param entryController Controller to handle switching between apps
   */
  public EntryPoint(EntryPointController entryController) {
    this.entryController = entryController;
  }

  /**
   * Initializes the scene.
   *
   * @param width  width of scene
   * @param height height of scenes
   */
  @Override
  public void initializeScene(int width, int height) {
    this.width = width;
    this.height = height;
    this.language = entryController.getLanguage();
    this.factory = new WidgetFactory();
    this.pane = new StackPane();

    setUpText();
    populateSplashArt();
    this.scene = new javafx.scene.Scene(pane, width, height);
    getScene().getStylesheets().add(getClass().getResource(DEFAULT_RESOURCE_FOLDER + STYLESHEET)
        .toExternalForm());
  }

  /**
   * Sets up the "Baba Is You" header
   */
  private void setUpText() {
    Text baba = factory.generateRedHeader(new WidgetConfiguration("Baba", language));
    Text is = factory.generateHeader(new WidgetConfiguration("Is", language));
    Text you = factory.generateRedHeader(new WidgetConfiguration("You", language));
    HBox header = factory.wrapInHBox(new ArrayList<Node>(Arrays.asList(baba, is, you)), width / 2);
    VBox vbox = factory.wrapInVBox(header, height / 3);
    setUpButtons(vbox);
  }

  /**
   * Sets up the buttons to enter each of the 2 apps.
   */
  private void setUpButtons(VBox vbox) {
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

    HBox buttons = factory.wrapInHBox(new ArrayList<>(Arrays.asList(authoring, gamePlay)),
        width / 2);
    buttons.setAlignment(Pos.BASELINE_CENTER);
    vbox.getChildren().add(buttons);
    setUpLanguageChooser(vbox);
  }

  /**
   * Sets up the combo box to select the language for the apps.
   *
   * @param vbox header + buttons
   */
  private void setUpLanguageChooser(VBox vbox) {
    ComboBox<String> switchLanguage = factory.makeComboBox(new WidgetConfiguration(200, 40,
        "SwitchLanguage", "combo-box", language), new ArrayList<>(Arrays.asList("English",
        "Spanish")), language);
    //TODO: Change to be a drop down
    switchLanguage.setOnAction(event -> {
      language = switchLanguage.getValue();
      entryController.setLanguage(language);
      entryController.switchToScene(new EntryPoint(entryController));
    });

    vbox.getChildren().add(switchLanguage);
    vbox.setAlignment(Pos.BOTTOM_CENTER);
    vbox.setSpacing(20);
    vbox.setPadding(new Insets(0, 0, 100, 0));
    pane.getChildren().add(vbox);
  }

  /**
   * Sets the background to be the Baba Is Us splash art.
   */
  private void populateSplashArt() {
    InputStream stream = getClass().getResourceAsStream("/images/EntryPointBg.png");
    Image image = new Image(stream);

    BackgroundImage backgroundImage = new BackgroundImage(
        image,
        BackgroundRepeat.NO_REPEAT,
        BackgroundRepeat.NO_REPEAT,
        BackgroundPosition.DEFAULT,
        new BackgroundSize(BackgroundSize.AUTO, BackgroundSize.AUTO, false,
            false, true, false)
    );

    pane.setBackground(new Background(backgroundImage));
  }

  /**
   * Returns the actual JavaFx scene associated w/ this scene.
   *
   * @return the javafx scene object
   */
  @Override
  public javafx.scene.Scene getScene() {
    return this.scene;
  }
}
