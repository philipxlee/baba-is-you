package oogasalad.model.gameplay.factory;

import oogasalad.model.gameplay.blocks.AbstractBlock;
import oogasalad.model.gameplay.blocks.visualblocks.BabaVisualBlock;
import oogasalad.model.gameplay.blocks.visualblocks.EmptyVisualBlock;
import oogasalad.model.gameplay.blocks.textblocks.nouns.BabaTextBlock;
import oogasalad.model.gameplay.blocks.textblocks.verbs.IsTextBlock;
import oogasalad.model.gameplay.blocks.textblocks.nouns.RockTextBlock;
import oogasalad.model.gameplay.blocks.textblocks.properties.YouTextBlock;
import oogasalad.model.gameplay.blocks.visualblocks.RockVisualBlock;


public class BlockFactory {

  public BlockFactory() {}

  public AbstractBlock createBlock(String blockName) {
    return switch (blockName) {
      case "EmptyVisualBlock" -> new EmptyVisualBlock("EmptyVisualBlock");
      case "RockTextBlock" -> new RockTextBlock("RockTextBlock");
      case "BabaTextBlock" -> new BabaTextBlock("BabaTextBlock");
      case "IsTextBlock" -> new IsTextBlock("IsTextBlock");
      case "YouTextBlock" -> new YouTextBlock("YouTextBlock");
      case "BabaVisualBlock" -> new BabaVisualBlock("BabaVisualBlock");
      case "RockVisualBlock" -> new RockVisualBlock("RockVisualBlock");
      default -> throw new IllegalArgumentException("Invalid block name");
    };
  }

}
