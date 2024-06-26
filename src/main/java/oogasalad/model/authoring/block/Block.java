package oogasalad.model.authoring.block;

/**
 * Block abstraction. Initialized with a BlockType.
 *
 * @param type The BlockType of the block.
 */
public record Block(BlockType type) {

  public Block(BlockType type) {
    this.type = type;
  }

  public BlockType getType() {
    return type;
  }
}