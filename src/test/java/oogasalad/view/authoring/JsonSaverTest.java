//package oogasalad.view.authoring;
//
//import javafx.application.Platform;
//import javafx.scene.control.Alert;
//import javafx.scene.control.ButtonType;
//import javafx.stage.Stage;
//import oogasalad.controller.authoring.LevelController;
//import oogasalad.view.authoring.BuilderPane;
//import oogasalad.view.authoring.jsonOps.JsonSaver;
//import org.junit.jupiter.api.Test;
//import org.testfx.api.FxAssert;
//import org.testfx.framework.junit5.ApplicationTest;
//import org.testfx.util.WaitForAsyncUtils;
//
//import java.io.IOException;
//
//import static org.mockito.Mockito.*;
//
//public class JsonSaverTest extends ApplicationTest {
//
//    private JsonSaver jsonSaver;
//
//    @Override
//    public void start(Stage stage) {
//        // Initialize your JavaFX components here if necessary
//    }
//
//    @Test
//    public void testSaveJsonConfirmation() throws IOException {
//        // Mock LevelController and BuilderPane
//        LevelController levelController = mock(LevelController.class);
//        BuilderPane builderPane = mock(BuilderPane.class);
//
//        // Create an instance of JsonSaver
//        jsonSaver = new JsonSaver(levelController, builderPane);
//
//        // Mock the confirmation alert
//        Alert confirmation = mock(Alert.class);
//        when(confirmation.showAndWait()).thenReturn(java.util.Optional.of(ButtonType.YES));
//        when(confirmation.getDialogPane()).thenReturn(mock(javafx.scene.control.DialogPane.class));
//
//        // Call the saveJson method on the JavaFX application thread
//        Platform.runLater(() -> {
//            jsonSaver.saveJson("Medium");
//        });
//
//        // Delay the verification until the JavaFX application thread finishes processing
//        WaitForAsyncUtils.waitForFxEvents();
//
//        // Verify that handleConfirmation method is called
//        verify(jsonSaver).handleConfirmation(eq(ButtonType.YES), any());
//    }
//
//    @Test
//    public void testSaveJsonCancellation() throws IOException {
//        // Mock LevelController and BuilderPane
//        LevelController levelController = mock(LevelController.class);
//        BuilderPane builderPane = mock(BuilderPane.class);
//
//        // Create an instance of JsonSaver
//        jsonSaver = new JsonSaver(levelController, builderPane);
//
//        // Mock the confirmation alert
//        Alert confirmation = mock(Alert.class);
//        when(confirmation.showAndWait()).thenReturn(java.util.Optional.of(ButtonType.NO));
//        when(confirmation.getDialogPane()).thenReturn(mock(javafx.scene.control.DialogPane.class));
//
//        // Call the saveJson method on the JavaFX application thread
//        Platform.runLater(() -> {
//            jsonSaver.saveJson("Medium");
//        });
//
//        // Delay the verification until the JavaFX application thread finishes processing
//        WaitForAsyncUtils.waitForFxEvents();
//
//        // Verify that handleConfirmation method is not called
//        verify(jsonSaver, never()).handleConfirmation(eq(ButtonType.NO), any());
//    }
//
////    @Test
////    public void testSaveJsonConfirmation() throws IOException {
////        // Mock LevelController and BuilderPane
////        LevelController levelController = mock(LevelController.class);
////        BuilderPane builderPane = mock(BuilderPane.class);
////
////        // Create an instance of JsonSaver
////        jsonSaver = new JsonSaver(levelController, builderPane);
////
////        // Call the saveJson method on the JavaFX application thread
////        Platform.runLater(() -> {
////            jsonSaver.saveJson("Medium");
////        });
////
////        // Delay the verification until the JavaFX application thread finishes processing
////        WaitForAsyncUtils.waitForFxEvents();
////
////        // Mock the confirmation alert
////        Alert confirmation = mock(Alert.class);
////        when(confirmation.showAndWait()).thenReturn(java.util.Optional.of(ButtonType.YES));
////        when(confirmation.getDialogPane()).thenReturn(mock(javafx.scene.control.DialogPane.class));
////
////        // Verify that handleConfirmation method is called
////        verify(jsonSaver).handleConfirmation(eq(ButtonType.YES), any());
////    }
////
////    @Test
////    public void testSaveJsonCancellation() throws IOException {
////        // Mock LevelController and BuilderPane
////        LevelController levelController = mock(LevelController.class);
////        BuilderPane builderPane = mock(BuilderPane.class);
////
////        // Create an instance of JsonSaver
////        jsonSaver = new JsonSaver(levelController, builderPane);
////
////        // Call the saveJson method on the JavaFX application thread
////        Platform.runLater(() -> {
////            jsonSaver.saveJson("Medium");
////        });
////
////        // Delay the verification until the JavaFX application thread finishes processing
////        WaitForAsyncUtils.waitForFxEvents();
////
////        // Mock the confirmation alert
////        Alert confirmation = mock(Alert.class);
////        when(confirmation.showAndWait()).thenReturn(java.util.Optional.of(ButtonType.NO));
////        when(confirmation.getDialogPane()).thenReturn(mock(javafx.scene.control.DialogPane.class));
////
////        // Verify that handleConfirmation method is not called
////        verify(jsonSaver, never()).handleConfirmation(eq(ButtonType.NO), any());
////    }
//}
