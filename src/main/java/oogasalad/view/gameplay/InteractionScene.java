package oogasalad.view.gameplay;

import java.io.InputStream;
import javafx.scene.Group;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.HBox;
import javafx.scene.paint.Color;
import javafx.scene.paint.LinearGradient;
import javafx.scene.paint.Stop;
import javafx.scene.shape.Rectangle;
import oogasalad.shared.blockviews.AbstractBlockView;
import oogasalad.shared.scene.Scene;

public class InteractionScene {
  private Group root;
  private MainScene scene;

  public void initializeInteractionPane(int width, int height, MainScene scene) {
    this.root = new Group();

    Rectangle rectangle = new Rectangle(0, 0, width, height);

    Color start = Color.web("#777DA1");
    Color end = Color.web("9773FD");

    Stop[] stops = new Stop[] {
        new Stop(0, start),
        new Stop(1, end)
    };
    LinearGradient linearGradient = new LinearGradient(0, 0, 0, 1,
        true, null, stops);

    rectangle.setFill(linearGradient);
    root.getChildren().addAll(rectangle);

    HBox hbox = new HBox();
    hbox.setPrefSize(width, height);
    InputStream inputStream = InteractionScene.class.getResourceAsStream("/images/BabaIsYouHeader.png");
    Image image = new Image(inputStream);
    ImageView imageView = new ImageView(image);
    hbox.getChildren().add(imageView);

    root.getChildren().add(hbox);


  }

  public Group getPane() {
    return this.root;
  }
}
