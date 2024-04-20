package oogasalad.model.gameplay.grid;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import oogasalad.model.gameplay.blocks.AbstractBlock;
import oogasalad.model.gameplay.factory.BlockFactory;
import oogasalad.model.gameplay.interpreter.RuleInterpreter;
import oogasalad.model.gameplay.exceptions.InvalidBlockName;
import oogasalad.model.gameplay.exceptions.VisitorReflectionException;
import oogasalad.shared.observer.Observable;
import oogasalad.shared.observer.Observer;

public class Grid extends GridHelper implements Observable<Grid> {
  private final List<Observer<Grid>> observers = new ArrayList<>();
  private final RuleInterpreter parser;
  private final BlockFactory factory;
  private final BlockUpdater blockUpdater;
  private final String[][][] initialConfiguration;


  /**
   * Constructs a grid with the specified dimensions and initial configuration.
   *
   * @param rows                The number of rows in the grid.
   * @param cols                The number of columns in the grid.
   * @param initialConfiguration The initial configuration of the grid.
   * @throws InvalidBlockName if an invalid block name is encountered.
   */
  public Grid(int rows, int cols, String[][][] initialConfiguration) throws InvalidBlockName {
    super(rows, cols);
    this.parser = new RuleInterpreter();
    this.factory = new BlockFactory();
    this.blockUpdater = new BlockUpdater(this, factory);
    this.initialConfiguration = initialConfiguration; // Initialize initial configuration
    InitializeGrid();
  }

  /**
   * Initializes the grid with blocks based on the initial configuration.
   */
  private void InitializeGrid() {
    // Initializing elements
    for (int i = 0; i < grid.length; i++) {
      for (int j = 0; j < grid[i].length; j++) {
        grid[i][j] = new ArrayList<AbstractBlock>();
        createBlocks(grid[i][j], initialConfiguration[i][j], i, j);
      }
    }
  }

  /**
   * Retrieves an iterator for the specified cell.
   *
   * @param row The row index of the cell.
   * @param col The column index of the cell.
   * @return A CellIterator for the specified cell.
   */
  private CellIterator iterator(int row, int col) {
    return new CellIterator(grid, row, col);
  }


  /**
   * Retrieves the block at the specified indices.
   *
   * @param i The row index.
   * @param j The column index.
   * @param k The index of the block in the cell.
   * @return The block at the specified indices.
   */
  public AbstractBlock getBlock(int i, int j, int k) {
    return grid[i][j].get(k);
  }

  /**
   * Retrieves the grid.
   *
   * @return The grid.
   */
  public List<AbstractBlock>[][] getGrid() {
    sortCellsForRender();
    return this.grid;
  }

  /**
   * Retrieves the width of the grid.
   *
   * @return The width of the grid.
   */
  public int getGridWidth() {
    return grid.length;
  }

  /**
   * Retrieves the height of the grid.
   *
   * @return The height of the grid.
   */
  public int getGridHeight() {
    return grid[0].length;
  }


  /**
   * Retrieves the size of the cell at the specified indices.
   *
   * @param i The row index of the cell.
   * @param j The column index of the cell.
   * @return The size of the cell at the specified indices.
   */
  public int cellSize(int i, int j) {
    return grid[i][j].size();
  }


  /**
   * Executes behaviors for all blocks in the grid.
   */
  public void checkBehaviors() {
    for (int i = 0; i < grid.length; i++) {
      for (int j = 0; j < grid[i].length; j++) {
        for (int k = 0; k < grid[i][j].size(); k++) {
          AbstractBlock block = grid[i][j].get(k);
          block.executeBehaviors(block, blockUpdater, iterator(i, j));
        }
      }
    }
  }

  /**
   * Checks if the specified indices are within the bounds of the grid.
   *
   * @param i The row index.
   * @param j The column index.
   * @return True if the indices are within the bounds of the grid, false otherwise.
   */
  public boolean isNotOutOfBounds(int i, int j) {
    return i >= 0 && i < grid.length && j >= 0 && j < grid[i].length;
  }


  /**
   * Checks for rules based on the current state of the grid.
   *
   * @throws VisitorReflectionException if an error occurs during rule interpretation.
   */
  public void checkForRules() throws VisitorReflectionException {
    parser.interpretRules(grid);
  }

  /**
   * Adds an observer to the grid.
   *
   * @param o The observer to add.
   */
  @Override
  public void addObserver(Observer<Grid> o) {
    observers.add(o);
  }

  /**
   * Notifies all observers of changes in the grid.
   */
  @Override
  public void notifyObserver() {
    for (Observer<Grid> observer : observers) {
      observer.update(this);
    }
  }

  /**
   * Creates blocks based on the provided block types and adds them to the specified cell in the grid.
   *
   * @param AbstractBlocks The list of abstract blocks representing the cell in the grid.
   * @param Blocktypes     The array of block types to create.
   * @param row            The row index of the cell.
   * @param col            The column index of the cell.
   */
  private void createBlocks(List<AbstractBlock> AbstractBlocks, String[] Blocktypes, int row,
      int col) {
    for (int i = 0; i < Blocktypes.length; i++) {
      AbstractBlocks.add(factory.createBlock(Blocktypes[i], row, col));
    }
  }


  /**
   * This method sorts each cell's list so that EmptyVisualBlock instances come first.
   */
  private void sortCellsForRender() {
    for (List<AbstractBlock>[] row : grid) {
      for (List<AbstractBlock> cell : row) {
        cell.sort(new Comparator<AbstractBlock>() {
          @Override
          public int compare(AbstractBlock block1, AbstractBlock block2) {
            if (block1.getBlockName().equals("EmptyVisualBlock") && !(block2.getBlockName()
                    .equals("EmptyVisualBlock"))) {
              return -1;
            } else if (!(block1.getBlockName().equals("EmptyVisualBlock"))
                    && block2.getBlockName().equals("EmptyVisualBlock")) {
              return 1;
            }
            return 0;
          }
        });
      }
    }
  }


}
