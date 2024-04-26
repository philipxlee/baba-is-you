//package oogasalad.controller.authoring;
//
//import static org.junit.Assert.assertThrows;
//import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
//import static org.junit.jupiter.api.Assertions.assertNotNull;
//import static org.junit.jupiter.api.Assertions.assertTrue;
//
//import oogasalad.model.authoring.level.LevelMetadata;
//import org.junit.Before;
//import org.junit.Test;
//
//public class LevelControllerTest {
//
//  private LevelController levelController;
//
//  @Before
//  public void setUp() throws Exception {
//    // Setup mock LevelController
//    LevelMetadata levelMetadata = new LevelMetadata("testLevel", "testDesc", 7, 7);
//    levelController = new LevelController(levelMetadata);
//  }
//
//  @Test
//  public void testLevelControllerConstructor() {
//    assertNotNull(levelController);
//    // Additional assertions can be made here if LevelController stores more state
//  }
//
//  @Test
//  public void testSetCellValidBlock() {
//    // Assuming "validBlockName" is considered valid by the mocked BlockTypeManager
//    assertDoesNotThrow(() -> levelController.addBlockToCell(1, 1, "EmptyVisualBlock"));
//  }
//
//  @Test
//  public void testSetCellInvalidBlock() {
//    Exception exception = assertThrows(Exception.class, () -> {
//      levelController.addBlockToCell(1, 1, "invalidBlockName");
//    });
//
//    assertTrue(exception.getMessage().contains("Invalid block type"));
//  }
//}
