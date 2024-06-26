package oogasalad.shared.widgetfactory;

import java.util.List;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.input.KeyCode;
import javafx.scene.layout.FlowPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.shape.Rectangle;
import javafx.scene.text.Text;
import javafx.stage.Stage;

/**
 * Factory class for making general UI widgets.
 */
public class WidgetFactory {

  public static final String STYLESHEET = "style.css";
  public static final String DEFAULT_RESOURCE_PACKAGE = "stylesheets.";
  public static final String DEFAULT_RESOURCE_FOLDER =
      "/" + DEFAULT_RESOURCE_PACKAGE.replace(".", "/");

  /**
   * Creates the generic interaction panel for the Gameplay Interaction pane.
   *
   * @param configuration configuration object specifying CSS + dimensions
   * @return designed Rectangle
   */
  public Rectangle interactionPanel(WidgetConfiguration configuration) {
    Rectangle panel = new Rectangle(0, 0, configuration.getWidth(), configuration.getHeight());
    panel.getStyleClass().add(configuration.getCssMatch());
    return panel;
  }

  /**
   * Generates a Label object with a given css match and potential starting content.
   *
   * @param configuration configuration object specifying language, CSS, etc
   * @param start         potential starting text of the label
   * @return
   */
  public Label makeLabel(WidgetConfiguration configuration, String start) {
    Label hintLabel = new Label();
    hintLabel.setText(start);
    hintLabel.getStyleClass().add(configuration.getCssMatch());
    hintLabel.setWrapText(true);
    hintLabel.maxWidth(configuration.getWidth());
    hintLabel.maxHeight(configuration.getHeight());
    return hintLabel;
  }

  /**
   * Generates a stylized header for the game.
   *
   * @param configuration obj specifying the language and property match label
   * @return stylized Text object
   */
  public Text generateHeader(WidgetConfiguration configuration) {
    Text header = new Text(configuration.getPropertyContents());
    header.getStyleClass().add("header");
    return header;
  }

  /**
   * Generates a stylized (red) header for the game.
   *
   * @param configuration obj specifying the language and property match label
   * @return stylized Text object
   */
  public Text generateRedHeader(WidgetConfiguration configuration) {
    Text header = new Text(configuration.getPropertyContents());
    header.getStyleClass().add("red-header");
    return header;
  }

  /**
   * Generates a stylized paragraph line for the game.
   *
   * @param configuration obj specifying the language and property match label
   * @return stylized Text object
   */
  public Text generateLine(WidgetConfiguration configuration) {
    Text line = new Text(configuration.getPropertyContents());
    line.getStyleClass().add("paragraph");
    return line;
  }

  /**
   * Generates a stylized paragraph line for the game. This method is for dynamic text that changes
   * from database/user input.
   *
   * @param content what the line contains
   * @return stylized Text object
   */
  public Text generateLine(String content) {
    Text line = new Text(content);
    line.getStyleClass().add("paragraph");
    return line;
  }

  /**
   * Generates a stylized subheader for the game.
   *
   * @param configuration obj specifying the language and property match label
   * @return stylized Text object
   */
  public Text generateSubHeader(WidgetConfiguration configuration) {
    Text line = new Text(configuration.getPropertyContents());
    line.getStyleClass().add("sub-header");
    return line;
  }

  /**
   * Generates a stylized caption for the game.
   *
   * @param content what the caption contains (dynamic data).
   * @return stylized Text object
   */
  public Text generateCaption(String content) {
    Text line = new Text(content);
    line.getStyleClass().add("caption");
    return line;
  }

  /**
   * Wraps a list of given JavaFX node in a centered, padded HBox.
   *
   * @param toBeWrapped list of nodes to wrap
   * @param width       preferred width of the HBox
   * @return HBox with the stylized nodes
   */
  public HBox wrapInHBox(List<Node> toBeWrapped, int width) {
    HBox hbox = new HBox(20);
    hbox.getChildren().addAll(toBeWrapped);
    hbox.setPrefWidth(width);
    hbox.setAlignment(Pos.CENTER);
    return hbox;
  }

  /**
   * The single node usage of wrapping in the HBox.
   *
   * @param toBeWrapped Node to wrap in HBox
   * @param width       preferred width of the HBox
   * @param spacing     the spacing on the sides of the node
   * @return stylized HBox with the node
   */
  public HBox wrapInHBox(Node toBeWrapped, int width, int spacing) {
    HBox hbox = new HBox(spacing);
    hbox.getChildren().add(toBeWrapped);
    hbox.setPrefWidth(width);
    hbox.setAlignment(Pos.CENTER);
    return hbox;
  }

  /**
   * Wraps a list of given JavaFX nodes in a centered, padded VBox.
   *
   * @param toBeWrapped list of nodes to wrap
   * @param height      preferred height of the VBox
   * @param spacing     spacing of the VBox
   * @return stylized VBox containing the list of nodes.
   */
  public VBox wrapInVBox(List<Node> toBeWrapped, int height, int spacing) {
    VBox vbox = new VBox(spacing);
    vbox.getChildren().addAll(toBeWrapped);
    vbox.setPrefHeight(height);
    vbox.setAlignment(Pos.CENTER);
    return vbox;
  }

  /**
   * Wraps a given JavaFX node in a centered, padded VBox.
   *
   * @param toBeWrapped node to wrap
   * @param height      preferred height of the VBox
   * @return stylized VBox containing the node.
   */
  public VBox wrapInVBox(Node toBeWrapped, int height) {
    VBox vbox = new VBox(10);
    vbox.getChildren().add(toBeWrapped);
    vbox.setPrefHeight(height);
    vbox.setAlignment(Pos.CENTER);
    return vbox;
  }

  /**
   * Makes a button in the game.
   *
   * @param configuration config object specifying width, height, propertyName, and cssMatch for
   *                      versatility.
   * @return stylized button
   */
  public static Button makeButton(WidgetConfiguration configuration) {
    Button button = new Button(configuration.getPropertyContents());
    button.setPrefWidth(configuration.getWidth());
    button.setPrefHeight(configuration.getHeight());
    button.setPadding(new Insets(10));
    button.getStyleClass().add(configuration.getCssMatch());
    return button;
  }

  /**
   * For dynamic buttons showing player information or other non-property information
   *
   * @param configuration
   * @param label
   * @return
   */
  public Button makeButton(WidgetConfiguration configuration, String label) {
    Button button = new Button(label);
    button.setPrefWidth(configuration.getWidth());
    button.setPrefHeight(configuration.getHeight());
    button.setPadding(new Insets(10));
    button.getStyleClass().add(configuration.getCssMatch());
    return button;
  }

  /**
   * Changes the text on the button
   *
   * @param button              button the change text on
   * @param widgetConfiguration config obj specifying the new properties match to reference
   */
  public void changeButtonLabel(Button button, WidgetConfiguration widgetConfiguration) {
    button.setText(widgetConfiguration.getPropertyContents());
  }

  /**
   * Creates a stylized scrollpane
   *
   * @param flowPane flowPane to orient the scrollpane around
   * @param maxWidth max width the scrollpane should be
   * @return stylized scrollpane
   */
  public ScrollPane makeScrollPane(FlowPane flowPane, int maxWidth) {
    ScrollPane pane = new ScrollPane(flowPane);
    pane.setFitToWidth(true);
    pane.setFitToHeight(true);
    pane.setMaxWidth(maxWidth);
    pane.setPannable(false);
    pane.setFocusTraversable(false);
    pane.getStylesheets().add("scrollpane");
    pane.setOnKeyPressed(event -> {
      if (event.getCode() == KeyCode.DOWN || event.getCode() == KeyCode.UP
          || event.getCode() == KeyCode.LEFT || event.getCode() == KeyCode.RIGHT) {
        event.consume();
      }
    });
    return pane;
  }

  /**
   * Creates a stylized popup window
   *
   * @param configuration config obj specifying css match, width, height, property name, and
   *                      language
   * @param root          root Javafx node to build the scene with
   * @param popup         stage to put the scene on
   */
  public void createPopUpWindow(WidgetConfiguration configuration, Parent root, Stage popup) {
    javafx.scene.Scene scene = new javafx.scene.Scene(root, configuration.getWidth(),
        configuration.getHeight());
    popup.setScene(scene);
    popup.setTitle(configuration.getPropertyContents());
    scene.getStylesheets().add(getClass().getResource(DEFAULT_RESOURCE_FOLDER + STYLESHEET)
        .toExternalForm());
    popup.show();
  }

  /**
   * Creates a stylized flowpane
   *
   * @param configuration config obj specifying width, height, cssmatch, etc
   * @return stylized flowpane object
   */
  public FlowPane createFlowPane(WidgetConfiguration configuration) {
    FlowPane flowPane = new FlowPane();
    flowPane.setPrefSize(configuration.getWidth(), configuration.getHeight());
    flowPane.setPadding(new Insets(10));
    flowPane.setHgap(10);
    flowPane.setVgap(10);
    flowPane.setAlignment(Pos.CENTER);
    flowPane.setFocusTraversable(false);
    flowPane.getStyleClass().add(configuration.getCssMatch());
    return flowPane;
  }

  /**
   * Creates a stylized text field object
   *
   * @param configuration config obj specifying width, height, propertyName, css match, and
   *                      language
   * @return stylized text field
   */
  public TextField createTextField(WidgetConfiguration configuration) {
    TextField field = new TextField();
    field.setPromptText(configuration.getPropertyContents());
    field.setMinWidth(configuration.getWidth());
    field.setMaxWidth(configuration.getWidth() + 100);
    field.setPrefHeight(configuration.getHeight());
    field.getStyleClass().add(configuration.getCssMatch());
    return field;
  }

  /**
   * Creates a stylized text area object
   *
   * @param configuration config obj specifying width, height, propertyName, css match, and
   *                      language
   * @return stylized text area
   */
  public TextArea createTextArea(WidgetConfiguration configuration) {
    TextArea field = new TextArea();
    field.setPromptText(configuration.getPropertyContents());
    field.setMaxWidth(configuration.getWidth());
    field.setMinHeight(configuration.getHeight());
    field.setPrefHeight(configuration.getHeight());
    field.getStyleClass().add(configuration.getCssMatch());
    return field;
  }

  /**
   * Replaces the content on a label.
   *
   * @param label         label to change content on
   * @param configuration config obj specifying new property name
   */
  public void replaceLabelContent(Label label, WidgetConfiguration configuration) {
    label.setText(configuration.getPropertyContents());
  }

  /**
   * Generates a stylized combo box.
   *
   * @param configuration config object specifying width, height, and css match
   * @param options       list of options within the combo box
   * @param defaultOption where the combo box starts on
   * @return stylized combo box
   */
  public ComboBox<String> makeComboBox(WidgetConfiguration configuration, List<String> options,
      String defaultOption) {
    ComboBox<String> categoryComboBox = new ComboBox<>();
    categoryComboBox.getItems().addAll(options);
    categoryComboBox.setValue(defaultOption);
    categoryComboBox.setMinWidth(configuration.getWidth());
    categoryComboBox.setMinHeight(configuration.getHeight());
    categoryComboBox.getStyleClass().add(configuration.getCssMatch());
    return categoryComboBox;
  }
}

