package oogasalad.view.authoring.jsonOps;

import com.google.gson.JsonObject;
import java.io.IOException;
import oogasalad.controller.authoring.LevelController;
import oogasalad.shared.config.JsonManager;
import oogasalad.view.authoring.BuilderPane;

public class JsonLoader {
  private final LevelController levelController;
  private final BuilderPane builderPane;

  public JsonLoader(LevelController levelController, BuilderPane builderPane) {
    this.levelController = levelController;
    this.builderPane = builderPane;
  }
  public void loadLevel(){

  }
}
