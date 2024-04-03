package oogasalad.model.authoring;

import oogasalad.model.gameplay.blocks.AbstractBlock;

/**
 * BlockType abstraction. Represents a type as an object to specify different types of Blocks.
 *
 * @param name The name of the Block Type.
 */
public record BlockType(String name) {
    /**
     * Factory method to create a block of the specified type.
     *
     * @return The created block.
     */
    public AbstractBlock createBlock() {
        switch (name.toLowerCase()) {
//            case "baba":
//                return new BabaBlock();
//            case "wall":
//                return new WallBlock();
            // Add cases for other block types as needed
            default:
                throw new IllegalArgumentException("Unknown block type: " + name);
        }
    }
}