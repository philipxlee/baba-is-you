package oogasalad.model.authoring;

public class Block {

  private BlockType type;

  public Block(BlockType type) {
    this.type = type;
  }

  public BlockType getType() {
    return type;
  }
}