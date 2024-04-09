package oogasalad.view.gameplay;

import java.util.List;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import javafx.scene.text.Font;
import javafx.scene.text.Text;
import oogasalad.shared.scene.Scene;

/**
 * Factory class for making general UI widgets.
 */
public class WidgetFactory {
  private oogasalad.shared.scene.Scene scene;
  private Font bahianaFont;

  public WidgetFactory() {
    this.bahianaFont = Font.loadFont(
        getClass().getResourceAsStream("/fonts/Bahiana-Regular.ttf"), 12);
  }

  public Rectangle interactionPanel(int width, int height) {
    Rectangle panel = new Rectangle(0, 0, width, height);
    panel.getStyleClass().add("interaction-background");
    return panel;
  }

  public Text generateHeader(String title) {
    Text header = new Text(title);
    header.getStyleClass().add("header");
    return header;
  }

  public Text generateLine(String content) {
    Text line = new Text(content);
    line.getStyleClass().add("paragraph");
    return line;
  }

  protected HBox wrapInHBox(List<Node> toBeWrapped, int width) {
    HBox hbox = new HBox();
    hbox.getChildren().addAll(toBeWrapped);
    hbox.setPrefWidth(width);
    hbox.setAlignment(Pos.CENTER);
    return hbox;
  }

  /**
   * The single node usage.
   * @param toBeWrapped
   * @param width
   * @return
   */
  protected HBox wrapInHBox(Node toBeWrapped, int width) {
    HBox hbox = new HBox(10);
    hbox.getChildren().add(toBeWrapped);
    hbox.setPrefWidth(width);
    hbox.setAlignment(Pos.CENTER);
    return hbox;
  }

  protected VBox wrapInVBox(List<Node> toBeWrapped, int height) {
    VBox vbox = new VBox(10);
    vbox.getChildren().addAll(toBeWrapped);
    vbox.setPrefHeight(height);
    vbox.setAlignment(Pos.CENTER);
    return vbox;
  }

  public Button makeButton(String label, int width, int height) {
    Button button = new Button(label);
    button.setPrefWidth(width);
    button.setPrefHeight(height);
    button.setPadding(new Insets(10));
    button.getStyleClass().add("button");
    return button;
  }

}

