package oogasalad.model.gameplay;

import oogasalad.model.gameplay.blocks.blockvisitor.AttributeVisitor;
import oogasalad.model.gameplay.blocks.visualblocks.*;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

public class AttributeVisitorTest {

  @Test
  public void testAttributeControllable() {
    String testAttribute = "You";
    String modifiedAttribute = "Controllable";
    AttributeVisitor attributeVisitor =  new AttributeVisitor(testAttribute);

    // Create an instance of each block type
    BabaVisualBlock baba = new BabaVisualBlock("BabaVisualBlock", 0, 0);
    WallVisualBlock wall = new WallVisualBlock("WallVisualBlock", 0, 0);
    RockVisualBlock rock = new RockVisualBlock("RockVisualBlock", 0, 0);
    FlagVisualBlock flag = new FlagVisualBlock("FlagVisualBlock", 0, 0);
    EmptyVisualBlock empty = new EmptyVisualBlock("EmptyVisualBlock", 0, 0);
    LavaVisualBlock lava = new LavaVisualBlock("LavaVisualBlock", 0, 0);
    WaterVisualBlock water = new WaterVisualBlock("WaterVisualBlock", 0, 0);

    // Apply the visitor to each block
    baba.accept(attributeVisitor);
    wall.accept(attributeVisitor);
    rock.accept(attributeVisitor);
    flag.accept(attributeVisitor);
    empty.accept(attributeVisitor);
    lava.accept(attributeVisitor);
    water.accept(attributeVisitor);

    // Assert that the attribute has been set
    assertTrue(baba.getAttribute(modifiedAttribute), "Baba should have the attribute set.");
    assertTrue(wall.getAttribute(modifiedAttribute), "Wall should have the attribute set.");
    assertTrue(rock.getAttribute(modifiedAttribute), "Rock should have the attribute set.");
    assertTrue(flag.getAttribute(modifiedAttribute), "Flag should have the attribute set.");
    assertTrue(empty.getAttribute(modifiedAttribute), "Empty should have the attribute set.");
    assertTrue(lava.getAttribute(modifiedAttribute), "Lava should have the attribute set.");
    assertTrue(water.getAttribute(modifiedAttribute), "Water should have the attribute set.");
  }

  @Test
  public void testAttributePushable() {
String testAttribute = "Push";
    String modifiedAttribute = "Pushable";
    AttributeVisitor attributeVisitor =  new AttributeVisitor(testAttribute);

    // Create an instance of each block type
    BabaVisualBlock baba = new BabaVisualBlock("BabaVisualBlock", 0, 0);
    WallVisualBlock wall = new WallVisualBlock("WallVisualBlock", 0, 0);
    RockVisualBlock rock = new RockVisualBlock("RockVisualBlock", 0, 0);
    FlagVisualBlock flag = new FlagVisualBlock("FlagVisualBlock", 0, 0);
    EmptyVisualBlock empty = new EmptyVisualBlock("EmptyVisualBlock", 0, 0);
    LavaVisualBlock lava = new LavaVisualBlock("LavaVisualBlock", 0, 0);
    WaterVisualBlock water = new WaterVisualBlock("WaterVisualBlock", 0, 0);

    // Apply the visitor to each block
    baba.accept(attributeVisitor);
    wall.accept(attributeVisitor);
    rock.accept(attributeVisitor);
    flag.accept(attributeVisitor);
    empty.accept(attributeVisitor);
    lava.accept(attributeVisitor);
    water.accept(attributeVisitor);

    // Assert that the attribute has been set
    assertTrue(baba.getAttribute(modifiedAttribute), "Baba should have the attribute set.");
    assertTrue(wall.getAttribute(modifiedAttribute), "Wall should have the attribute set.");
    assertTrue(rock.getAttribute(modifiedAttribute), "Rock should have the attribute set.");
    assertTrue(flag.getAttribute(modifiedAttribute), "Flag should have the attribute set.");
    assertTrue(empty.getAttribute(modifiedAttribute), "Empty should have the attribute set.");
    assertTrue(lava.getAttribute(modifiedAttribute), "Lava should have the attribute set.");
    assertTrue(water.getAttribute(modifiedAttribute), "Water should have the attribute set.");
  }

  @Test
  public void testAttributeMeltable() {
    String testAttribute = "Melt";
    String modifiedAttribute = "Meltable";
    AttributeVisitor attributeVisitor =  new AttributeVisitor(testAttribute);

    // Create an instance of each block type
    BabaVisualBlock baba = new BabaVisualBlock("BabaVisualBlock", 0, 0);
    WallVisualBlock wall = new WallVisualBlock("WallVisualBlock", 0, 0);
    RockVisualBlock rock = new RockVisualBlock("RockVisualBlock", 0, 0);
    FlagVisualBlock flag = new FlagVisualBlock("FlagVisualBlock", 0, 0);
    EmptyVisualBlock empty = new EmptyVisualBlock("EmptyVisualBlock", 0, 0);
    LavaVisualBlock lava = new LavaVisualBlock("LavaVisualBlock", 0, 0);
    WaterVisualBlock water = new WaterVisualBlock("WaterVisualBlock", 0, 0);

    // Apply the visitor to each block
    baba.accept(attributeVisitor);
    wall.accept(attributeVisitor);
    rock.accept(attributeVisitor);
    flag.accept(attributeVisitor);
    empty.accept(attributeVisitor);
    lava.accept(attributeVisitor);
    water.accept(attributeVisitor);

    // Assert that the attribute has been set
    assertTrue(baba.getAttribute(modifiedAttribute), "Baba should have the attribute set.");
    assertTrue(wall.getAttribute(modifiedAttribute), "Wall should have the attribute set.");
    assertTrue(rock.getAttribute(modifiedAttribute), "Rock should have the attribute set.");
    assertTrue(flag.getAttribute(modifiedAttribute), "Flag should have the attribute set.");
    assertTrue(empty.getAttribute(modifiedAttribute), "Empty should have the attribute set.");
    assertTrue(lava.getAttribute(modifiedAttribute), "Lava should have the attribute set.");
    assertTrue(water.getAttribute(modifiedAttribute), "Water should have the attribute set.");
  }

  @Test
  public void testAttributeSinkable() {
    String testAttribute = "Sink";
    String modifiedAttribute = "Sinkable";
    AttributeVisitor attributeVisitor =  new AttributeVisitor(testAttribute);

    // Create an instance of each block type
    BabaVisualBlock baba = new BabaVisualBlock("BabaVisualBlock", 0, 0);
    WallVisualBlock wall = new WallVisualBlock("WallVisualBlock", 0, 0);
    RockVisualBlock rock = new RockVisualBlock("RockVisualBlock", 0, 0);
    FlagVisualBlock flag = new FlagVisualBlock("FlagVisualBlock", 0, 0);
    EmptyVisualBlock empty = new EmptyVisualBlock("EmptyVisualBlock", 0, 0);
    LavaVisualBlock lava = new LavaVisualBlock("LavaVisualBlock", 0, 0);
    WaterVisualBlock water = new WaterVisualBlock("WaterVisualBlock", 0, 0);

    // Apply the visitor to each block
    baba.accept(attributeVisitor);
    wall.accept(attributeVisitor);
    rock.accept(attributeVisitor);
    flag.accept(attributeVisitor);
    empty.accept(attributeVisitor);
    lava.accept(attributeVisitor);
    water.accept(attributeVisitor);

    // Assert that the attribute has been set
    assertTrue(baba.getAttribute(modifiedAttribute), "Baba should have the attribute set.");
    assertTrue(wall.getAttribute(modifiedAttribute), "Wall should have the attribute set.");
    assertTrue(rock.getAttribute(modifiedAttribute), "Rock should have the attribute set.");
    assertTrue(flag.getAttribute(modifiedAttribute), "Flag should have the attribute set.");
    assertTrue(empty.getAttribute(modifiedAttribute), "Empty should have the attribute set.");
    assertTrue(lava.getAttribute(modifiedAttribute), "Lava should have the attribute set.");
    assertTrue(water.getAttribute(modifiedAttribute), "Water should have the attribute set.");
  }

}
