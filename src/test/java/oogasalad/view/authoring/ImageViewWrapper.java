package oogasalad.view.authoring;

import javafx.scene.image.ImageView;

public class ImageViewWrapper {

  private final ImageView imageView;

  public ImageViewWrapper(ImageView imageView) {
    this.imageView = imageView;
  }

  public void setOnMouseClicked(
      javafx.event.EventHandler<? super javafx.scene.input.MouseEvent> value) {
    imageView.setOnMouseClicked(value);
  }

  public javafx.scene.image.ImageView getImageView() {
    return imageView;
  }
}