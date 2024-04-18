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
import javafx.scene.control.TextField;
import javafx.scene.input.KeyCode;
import javafx.scene.layout.FlowPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import javafx.scene.text.Font;
import javafx.scene.text.Text;
import javafx.stage.Stage;

/**
 * Factory class for making general UI widgets.
 */
public class WidgetFactory {
  public static final String STYLESHEET = "gameplay.css";
  public static final String DEFAULT_RESOURCE_PACKAGE = "stylesheets.";
  public static final String DEFAULT_RESOURCE_FOLDER =
      "/" + DEFAULT_RESOURCE_PACKAGE.replace(".", "/");

  public WidgetFactory() {
  }

  public Rectangle interactionPanel(WidgetConfiguration configuration) {
    Rectangle panel = new Rectangle(0, 0, configuration.getWidth(), configuration.getHeight());
    panel.getStyleClass().add(configuration.getCssMatch());
    return panel;
  }

  public Text generateHeader(WidgetConfiguration configuration) {
    Text header = new Text(configuration.getPropertyContents());
    header.getStyleClass().add("header");
    return header;
  }

  public Text generateLine(WidgetConfiguration configuration) {
    Text line = new Text(configuration.getPropertyContents());
    line.getStyleClass().add("paragraph");
    return line;
  }

  public Text generateSubHeader(String content) {
    Text line = new Text(content);
    line.getStyleClass().add("sub-header");
    return line;
  }

  public Text generateCaption(WidgetConfiguration configuration) {
    Text line = new Text(configuration.getPropertyContents());
    line.getStyleClass().add("caption");
    return line;
  }

  public Text generateCaption(String content) {
    Text line = new Text(content);
    line.getStyleClass().add("caption");
    return line;
  }

  public HBox wrapInHBox(List<Node> toBeWrapped, int width) {
    HBox hbox = new HBox(20);
    hbox.getChildren().addAll(toBeWrapped);
    hbox.setPrefWidth(width);
    hbox.setAlignment(Pos.CENTER);
    return hbox;
  }

  /**
   * The single node usage.
   *
   * @param toBeWrapped
   * @param width
   * @return
   */
  public HBox wrapInHBox(Node toBeWrapped, int width) {
    HBox hbox = new HBox(20);
    hbox.getChildren().add(toBeWrapped);
    hbox.setPrefWidth(width);
    hbox.setAlignment(Pos.CENTER);
    return hbox;
  }

  public VBox wrapInVBox(List<Node> toBeWrapped, int height) {
    VBox vbox = new VBox(10);
    vbox.getChildren().addAll(toBeWrapped);
    vbox.setPrefHeight(height);
    vbox.setAlignment(Pos.CENTER);
    return vbox;
  }

  public VBox wrapInVBox(Node toBeWrapped, int height) {
    VBox vbox = new VBox(10);
    vbox.getChildren().add(toBeWrapped);
    vbox.setPrefHeight(height);
    vbox.setAlignment(Pos.CENTER);
    return vbox;
  }

  public Button makeButton(WidgetConfiguration configuration) {
    Button button = new Button(configuration.getPropertyContents());
    button.setPrefWidth(configuration.getWidth());
    button.setPrefHeight(configuration.getHeight());
    button.setPadding(new Insets(10));
    button.getStyleClass().add(configuration.getCssMatch());
    return button;
  }

  /**
   * For dynamic buttons showing player information or other non-property information
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

  public void createPopUpWindow(WidgetConfiguration configuration, Parent root) {
    Stage popup = new Stage();
    javafx.scene.Scene scene = new javafx.scene.Scene(root, configuration.getWidth(),
        configuration.getHeight());
    popup.setScene(scene);
    popup.setTitle(configuration.getPropertyContents());
    scene.getStylesheets().add(getClass().getResource(DEFAULT_RESOURCE_FOLDER + STYLESHEET)
        .toExternalForm());
    popup.show();
  }

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

  public TextField createTextField(WidgetConfiguration configuration) {
    TextField field = new TextField();
    field.setPromptText(configuration.getPropertyContents());
    field.setMinWidth(configuration.getWidth());
    field.setMaxWidth(configuration.getWidth()+100);
    field.setPrefHeight(configuration.getHeight());
    field.getStyleClass().add("text-field");
    return field;
  }

  public void replaceLabelContent(Label old, WidgetConfiguration configuration) {
    old.setText(configuration.getPropertyContents());
  }

  public void replaceTextContent(Text old, WidgetConfiguration configuration) {
    old.setText(configuration.getPropertyContents());
  }

  public Label generateLabel(WidgetConfiguration configuration) {
    Label label = new Label();
    label.setText(configuration.getPropertyContents());
    label.getStyleClass().add("label");
    return label;
  }

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

