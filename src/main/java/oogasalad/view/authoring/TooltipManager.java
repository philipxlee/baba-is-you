package oogasalad.view.authoring;

import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Tooltip;
import java.util.ResourceBundle;

public class TooltipManager {

  /**
   * Sets tooltips for various UI components in the authoring environment to enhance user experience
   * by providing quick guidance on the function of each control. Tooltips are loaded from a
   * resource bundle, allowing for easy localization and updates.
   *
   * @param categoryComboBox A combo box for selecting categories that should display a tooltip
   * explaining its purpose.
   * @param difficultyComboBox A combo box for setting the difficulty level that should display a
   * tooltip with guidance on how to use it.
   * @param changeGridSizeButton A button for changing grid size that should display a tooltip
   * describing its functionality.
   * @param removeButton A button for enabling the removal mode that should display a tooltip
   * explaining how to use it.
   * @param gptButton A button intended to trigger GPT-related actions, equipped with a tooltip that
   * describes what clicking it will do.
   * @param saveJsonButton A button for saving configurations to JSON format that should display a
   * tooltip explaining the save functionality.
   * @param loadLevelButton A button for loading levels that should display a tooltip providing
   * information on how to use it.
   */
  private static final ResourceBundle tooltips = ResourceBundle.getBundle("auth_view.tooltips");

  public static void setTooltips(ComboBox<String> categoryComboBox,
      ComboBox<String> difficultyComboBox,
      Button changeGridSizeButton,
      Button removeButton,
      Button gptButton,
      Button saveJsonButton, Button loadLevelButton) {
    Tooltip categoryTooltip = new Tooltip(tooltips.getString("tooltipCategory"));
    Tooltip difficultyTooltip = new Tooltip(tooltips.getString("tooltipDifficulty"));
    Tooltip changeGridSizeTooltip = new Tooltip(tooltips.getString("tooltipChangeGridSize"));
    Tooltip removeButtonTooltip = new Tooltip(tooltips.getString("tooltipRemoveMode"));
    Tooltip gptButtonTooltip = new Tooltip(tooltips.getString("tooltipGenerateGPT"));
    Tooltip saveJsonButtonTooltip = new Tooltip(tooltips.getString("tooltipSaveJson"));
    Tooltip loadLevelTooltip = new Tooltip(tooltips.getString("tooltipLoadLevel"));

    categoryComboBox.setTooltip(categoryTooltip);
    difficultyComboBox.setTooltip(difficultyTooltip);
    changeGridSizeButton.setTooltip(changeGridSizeTooltip);
    removeButton.setTooltip(removeButtonTooltip);
    gptButton.setTooltip(gptButtonTooltip);
    saveJsonButton.setTooltip(saveJsonButtonTooltip);
    loadLevelButton.setTooltip(loadLevelTooltip);
  }
}
