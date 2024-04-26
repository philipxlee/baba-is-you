package oogasalad.view.authoring;

import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Tooltip;
public class TooltipManager {
  public static void setTooltips(ComboBox<String> categoryComboBox,
      ComboBox<String> difficultyComboBox,
      Button changeGridSizeButton,
      Button removeButton,
      Button gptButton,
      Button saveJsonButton, Button loadLevelButton) {
        Tooltip categoryTooltip = new Tooltip("Select the category of elements to display");
        Tooltip difficultyTooltip = new Tooltip("Select the difficulty level of elements");
        Tooltip changeGridSizeTooltip = new Tooltip("Click to change the size of the grid");
        Tooltip removeButtonTooltip = new Tooltip("Toggle remove mode to delete blocks");
        Tooltip gptButtonTooltip = new Tooltip("Generate a level using GPT");
        Tooltip saveJsonButtonTooltip = new Tooltip("Save the level configuration as JSON");
        Tooltip loadLevelTooltip = new Tooltip("Load a saved level configuration");

        categoryComboBox.setTooltip(categoryTooltip);
        difficultyComboBox.setTooltip(difficultyTooltip);
        changeGridSizeButton.setTooltip(changeGridSizeTooltip);
        removeButton.setTooltip(removeButtonTooltip);
        gptButton.setTooltip(gptButtonTooltip);
        saveJsonButton.setTooltip(saveJsonButtonTooltip);
        loadLevelButton.setTooltip(loadLevelTooltip);

  }
}
