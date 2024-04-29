//package oogasalad.view.authoring;
//
//import javafx.application.Platform;
//import javafx.scene.Scene;
//import javafx.scene.control.SplitPane;
//import javafx.scene.layout.Pane;
//import javafx.stage.Stage;
//import oogasalad.controller.authoring.LevelController;
//import oogasalad.controller.authoring.SceneController;
//import oogasalad.model.authoring.level.LevelMetadata;
//import org.junit.jupiter.api.*;
//
//import static org.junit.jupiter.api.Assertions.*;
//
///**
// * Tests for the MainScene class.
// */
//public class MainSceneTests {
//
//    private MainScene mainScene;
//    private LevelController mockLevelController;
//    private SceneController mockSceneController;
//
//    private Stage stage;
//
//    private LevelMetadata levelMetadata;
//
//    @BeforeAll
//    static void setUpClass() {
//        // Initialize JavaFX environment.
//        Platform.startup(() -> {});
//    }
//
//    @AfterAll
//    static void tearDownClass() {
//        Platform.exit();
//    }
//
//    @BeforeEach
//    void setUp() {
//        // Assuming these controllers are necessary and there are simple ways to instantiate them.
//        // Mocks or simple stubs would typically be used here.
//        mockLevelController = new LevelController(levelMetadata); // Adjust based on actual constructors or use mocks
//        mockSceneController = new SceneController(stage, mockLevelController); // Adjust based on actual constructors or use mocks
//
//        mainScene = new MainScene(mockLevelController, mockSceneController);
//    }
//
//    @Test
//    @DisplayName("Main Scene Initialization Test")
//    void testMainSceneInitialization() {
//        // Act: Initialize the scene with expected dimensions.
//        mainScene.initializeScene(800, 600);
//
//        // Assert: Ensure the scene is properly initialized.
//        Scene scene = mainScene.getScene();
//        assertNotNull(scene, "Scene should not be null after initialization");
//        assertEquals(800, scene.getWidth(), "Scene width should be initialized correctly");
//        assertEquals(600, scene.getHeight(), "Scene height should be initialized correctly");
//
//        // Check for correct structure: MainScene should contain a SplitPane with two panes.
//        assertTrue(scene.getRoot() instanceof SplitPane, "Root should be a SplitPane");
//        SplitPane splitPane = (SplitPane) scene.getRoot();
//        assertEquals(2, splitPane.getItems().size(), "SplitPane should contain two items");
//
//        // Further checks could include verifying the specific types of panes if necessary.
//        assertTrue(splitPane.getItems().get(0) instanceof Pane, "First item should be a Pane (BuilderPane)");
//        assertTrue(splitPane.getItems().get(1) instanceof Pane, "Second item should be a Pane (ElementsPane)");
//    }
//}
//
