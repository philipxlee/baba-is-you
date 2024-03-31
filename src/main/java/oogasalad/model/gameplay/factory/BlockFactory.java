package oogasalad.model.gameplay.factory;

import oogasalad.model.gameplay.utils.exceptions.InvalidBlockName;
import oogasalad.shared.blocks.AbstractBlock;
import oogasalad.shared.blocks.textblocks.nouns.EmptyTextBlock;
import oogasalad.shared.blocks.visualblocks.BabaVisualBlock;
import oogasalad.shared.blocks.visualblocks.EmptyVisualBlock;
import oogasalad.shared.blocks.textblocks.nouns.BabaTextBlock;
import oogasalad.shared.blocks.textblocks.verbs.IsTextBlock;
import oogasalad.shared.blocks.textblocks.nouns.RockTextBlock;
import oogasalad.shared.blocks.textblocks.properties.YouTextBlock;
import oogasalad.shared.blocks.visualblocks.RockVisualBlock;


public class BlockFactory {

  public BlockFactory() {}

  public AbstractBlock createBlock(String blockName) throws InvalidBlockName {
    return switch (blockName) {
      case "EmptyVisualBlock" -> new EmptyVisualBlock("EmptyVisualBlock");
      case "RockTextBlock" -> new RockTextBlock("RockTextBlock");
      case "BabaTextBlock" -> new BabaTextBlock("BabaTextBlock");
      case "IsTextBlock" -> new IsTextBlock("IsTextBlock");
      case "YouTextBlock" -> new YouTextBlock("YouTextBlock");
      case "BabaVisualBlock" -> new BabaVisualBlock("BabaVisualBlock");
      case "RockVisualBlock" -> new RockVisualBlock("RockVisualBlock");
      case "EmptyTextBlock" -> new EmptyTextBlock("EmptyTextBlock");
      default -> throw new InvalidBlockName("Invalid block name " + blockName);
    };
  }

}
