package oogasalad.model.authoring.block;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.Test;

public class BlockTest {

  @Test
  public void testBlockInitialization() {
    // Setup
    String expectedTypeName = "TestType";
    BlockType testType = new BlockType(expectedTypeName);

    // Execution
    Block block = new Block(testType);

    // Assertions
    assertEquals(testType, block.type(),
        "The block type should match the type it was initialized with.");
  }

  @Test
  public void testBlockTypeProperty() {
    // Setup
    String typeName = "AnotherTestType";
    BlockType anotherTestType = new BlockType(typeName);

    // Execution
    Block block = new Block(anotherTestType);

    // Assertions
    assertEquals(typeName, block.type().name(),
        "The block's type name should be the same as the BlockType it was initialized with.");
  }
}
