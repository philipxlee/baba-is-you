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
  private final String defaultImagePath = "/";

  public BlockFactory() {}

  public AbstractBlock createBlock(String blockName) {
    return switch (blockName) {
      case "EmptyVisualBlock" -> new EmptyVisualBlock("EmptyVisualBlock", defaultImagePath);
      case "RockTextBlock" -> new RockTextBlock("RockTextBlock", defaultImagePath);
      case "BabaTextBlock" -> new BabaTextBlock("BabaTextBlock", defaultImagePath);
      case "IsTextBlock" -> new IsTextBlock("IsTextBlock", defaultImagePath);
      case "YouTextBlock" -> new YouTextBlock("YouTextBlock", defaultImagePath);
      case "BabaVisualBlock" -> new BabaVisualBlock("BabaVisualBlock", defaultImagePath);
      case "RockVisualBlock" -> new RockVisualBlock("RockVisualBlock", defaultImagePath);
      default -> throw new IllegalArgumentException("Invalid block name");
    };
  }

}
