package oogasalad.view.authoring;

import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Tooltip;
import java.util.ResourceBundle;

public class TooltipManager {
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
