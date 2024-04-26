//package oogasalad.model.authoring.level;
//
//
//import static org.junit.jupiter.api.Assertions.assertEquals;
//import static org.junit.jupiter.api.Assertions.assertNotNull;
//import static org.junit.jupiter.api.Assertions.assertThrows;
//import static org.mockito.Mockito.mock;
//import static org.mockito.Mockito.verify;
//
//import oogasalad.shared.observer.Observer;
//import org.junit.Before;
//import org.junit.Test;
//
//public class LevelTest {
//
//  private Level level;
//  private LevelMetadata levelMetadata;
//  private Observer<Level> observer;
//
//  @Before
//  public void setUp() throws Exception {
//    // Create LevelMetadata instance
//    levelMetadata = new LevelMetadata("Level1", "First Level", 5, 5);
//    // Initialize Level
//    level = new Level(levelMetadata);
//    // Mock the Observer
//    observer = (Observer<Level>) mock(Observer.class);
//  }
//
//  @Test
//  public void testLevelConstructor() {
//    assertNotNull(level);
//    assertEquals(levelMetadata, level.getLevelMetadata());
//  }
//
//  @Test
//  public void testSetCellValidBlock() throws Exception {
//    // Assuming the blockTypeManager can handle the blockName without throwing an exception
//    level.addBlockToCell(1, 1, "EmptyVisualBlock");
//    // Success if no exception is thrown
//  }
//
//  @Test
//  public void testSetCellInvalidBlock() {
//    // Assuming the blockTypeManager can handle the blockName without throwing an exception
//    assertThrows(Exception.class, () -> {
//      level.addBlockToCell(1, 1, "invalidBlockName");
//    }, "Expected setCell to throw, but it didn't");
//  }
//
//  @Test
//  public void testAddObserverAndNotifyObserver() {
//    level.addObserver(observer);
//    level.notifyObserver();
//    // Verify the observer's update method was called with the level instance
//    verify(observer).update(level);
//  }
//}
