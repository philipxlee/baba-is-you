//package oogasalad.view.authoring;
//
//import javafx.application.Platform;
//import javafx.scene.control.Button;
//import javafx.scene.control.ComboBox;
//
//import org.junit.jupiter.api.*;
//
//import java.util.concurrent.CountDownLatch;
//import java.util.concurrent.TimeUnit;
//
//import static org.junit.jupiter.api.Assertions.*;
//
///**
// * Test suite for various authoring view components in a single file.
// */
//public class AuthoringViewTests {
//
//    @BeforeAll
//    static void setUpClass() {
//        // Initialize JavaFX environment
//        Platform.startup(() -> {});
//    }
//
//    @AfterAll
//    static void tearDownClass() {
//        Platform.exit();
//    }
//
//    @Test
//    @DisplayName("Test Help Wizard Dialog Initialization")
//    void testHelpWizardDialogInitialization() throws InterruptedException {
//        CountDownLatch latch = new CountDownLatch(1);
//
//        Platform.runLater(() -> {
//            try {
//                HelpWizardDialog.showWizardDialog(); // This method interacts with JavaFX components
//                latch.countDown(); // Decrease count of latch, allowing test to proceed after GUI operations are done
//            } catch (Exception e) {
//                fail("Failed to initialize Help Wizard Dialog: " + e.getMessage());
//            }
//        });
//
//        assertTrue(latch.await(5, TimeUnit.SECONDS), "Timeout waiting for HelpWizardDialog to initialize.");
//    }
//
//    @Test
//    @DisplayName("Test Tooltip Setup")
//    void testTooltipSetup() {
//        ComboBox<String> categoryComboBox = new ComboBox<>();
//        ComboBox<String> difficultyComboBox = new ComboBox<>();
//        Button changeGridSizeButton = new Button();
//        Button removeButton = new Button();
//        Button gptButton = new Button();
//        Button saveJsonButton = new Button();
//        Button loadLevelButton = new Button();
//
//        assertDoesNotThrow(() -> TooltipManager.setTooltips(categoryComboBox, difficultyComboBox,
//                changeGridSizeButton, removeButton, gptButton, saveJsonButton, loadLevelButton));
//        assertNotNull(categoryComboBox.getTooltip(), "Tooltip should be set for category combo box");
//    }
//
//}
