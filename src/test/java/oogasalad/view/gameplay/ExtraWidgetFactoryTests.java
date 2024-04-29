package oogasalad.view.gameplay;

import static org.junit.jupiter.api.Assertions.assertEquals;

import javafx.application.Platform;
import javafx.scene.control.Button;
import javafx.scene.control.TextArea;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import oogasalad.shared.widgetfactory.WidgetConfiguration;
import oogasalad.shared.widgetfactory.WidgetFactory;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.testfx.framework.junit5.ApplicationTest;

public class ExtraWidgetFactoryTests extends ApplicationTest {

  private WidgetFactory factory;
  private static String lang = "English";

  @BeforeEach
  void setUp() {
    factory = new WidgetFactory();
  }

  @Test
  public void testDynamicButton() {
    Button button = factory.makeButton(new WidgetConfiguration(120, 50,
        "white-button", lang), "dynamic");
    assertEquals("dynamic", button.getText());
    assertEquals(120.0, button.getPrefWidth());
    assertEquals(50.0, button.getPrefHeight());
  }

  @Test
  public void testCreatePopUpWindow() {
    Platform.runLater(() -> {
      VBox root = new VBox();
      Stage popup = new Stage();

      factory.createPopUpWindow(new WidgetConfiguration(400, 300, "BIU",
          "root", lang), root, popup);
      assertEquals(400, popup.getScene().getWidth());
      assertEquals(300, popup.getScene().getHeight());
      assertEquals("Baba Is You", popup.getTitle());
    });
  }

  @Test
  public void testTextArea() {
    TextArea replyInput = factory.createTextArea(new WidgetConfiguration(600, 100,
        "CommentPrompter", "text-field", lang));
    assertEquals(600, replyInput.getMaxWidth());
    assertEquals(100, replyInput.getPrefHeight());
    assertEquals("Enter comments here...", replyInput.getPromptText());
  }

}
