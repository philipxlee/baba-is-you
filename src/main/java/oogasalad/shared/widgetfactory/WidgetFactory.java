package oogasalad.shared.widgetfactory;

import java.util.List;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.control.Button;
import javafx.scene.control.ScrollPane;
import javafx.scene.input.KeyCode;
import javafx.scene.layout.FlowPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.shape.Rectangle;
import javafx.scene.text.Font;
import javafx.scene.text.Text;
import javafx.stage.Stage;

/**
 * Factory class for making general UI widgets.
 */
public class WidgetFactory {

  private oogasalad.shared.scene.Scene scene;
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
    HBox hbox = new HBox();
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
    HBox hbox = new HBox(10);
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

  public ScrollPane makeScrollPane(FlowPane flowPane, int maxWidth) {
    ScrollPane pane = new ScrollPane(flowPane);
    pane.setFitToWidth(true);
    pane.setFitToHeight(true);
    pane.setMaxWidth(maxWidth);
    pane.setPannable(false);
    pane.setFocusTraversable(false);
    pane.setOnKeyPressed(event -> {
      if (event.getCode() == KeyCode.DOWN || event.getCode() == KeyCode.UP
          || event.getCode() == KeyCode.LEFT || event.getCode() == KeyCode.RIGHT) {
        event.consume();
      }
    });
    return pane;
  }

  public void createPopUpWindow(int width, int height, Parent root, String title) {
    Stage popup = new Stage();
    javafx.scene.Scene scene = new javafx.scene.Scene(root, width, height);
    popup.setScene(scene);
    popup.setTitle(title);
    popup.show();

  }

}

