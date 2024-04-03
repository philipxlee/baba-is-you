package oogasalad.model.authoring;

import oogasalad.model.gameplay.blocks.AbstractBlock;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class Level {
    private String name;
    private String description;
    private String author;
    private List<List<List<AbstractBlock>>> grid; // 3D grid for 2D objects

    private BlockTypeManager blockTypeManager;

    // Constructor
    public Level(String name, String description, String author, int width, int height, String blockPropertiesFilePath) throws IOException {
        this.name = name;
        this.description = description;
        this.author = author;
        this.grid = new ArrayList<>();
        this.blockTypeManager = new BlockTypeManager(blockPropertiesFilePath); // Initialize BlockTypeManager
        initializeGrid(width, height);
    }

    private void initializeGrid(int width, int height) {
        for (int i = 0; i < height; i++) {
            List<List<AbstractBlock>> row = new ArrayList<>();
            for (int j = 0; j < width; j++) {
                row.add(new ArrayList<>()); // Each cell can have multiple blocks (one on top of another)
            }
            grid.add(row);
        }
    }

    // allowing to add a block to the grid at the specified position
    public void addBlock(String blockTypeName, int x, int y) {
        try {
            AbstractBlock block = blockTypeManager.findBlockTypeByName(blockTypeName).createBlock();
            grid.get(y).get(x).add(block);
        } catch (IOException | ClassNotFoundException e) {
            System.err.println("Error adding block: " + e.getMessage());
        } catch (Exception e) {
            System.err.println("Invalid block name: " + blockTypeName);
        }
    }

    // allowing to remove a block from the grid at the specified position
    public void removeBlock(AbstractBlock block, int x, int y) {
        grid.get(y).get(x).remove(block);
    }

    // getter to get the blocks at the specified position
    public List<AbstractBlock> getBlocksAt(int x, int y) {
        return grid.get(y).get(x);
    }

    // Getters and Setters for now
    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getAuthor() {
        return author;
    }

    public void setAuthor(String author) {
        this.author = author;
    }
}

