package oogasalad.view.gameplay;

import javafx.geometry.Pos;
import javafx.scene.Node;
import javafx.scene.layout.HBox;
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

  public HBox generateHeader(int width) {
    Text header = new Text("Baba Is You");
    header.getStyleClass().add("header");
    return wrapInHBox(header, width);
  }

  protected HBox wrapInHBox(Node toBeWrapped, int width) {
    HBox hbox = new HBox();
    hbox.getChildren().add(toBeWrapped);
    hbox.setPrefWidth(width);
    hbox.setAlignment(Pos.CENTER);
    return hbox;
  }

}

