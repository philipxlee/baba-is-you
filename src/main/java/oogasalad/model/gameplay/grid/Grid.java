package oogasalad.model.gameplay.grid;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.stream.IntStream;
import oogasalad.model.gameplay.blocks.AbstractBlock;
import oogasalad.model.gameplay.blocks.visualblocks.EmptyVisualBlock;
import oogasalad.model.gameplay.factory.BlockFactory;
import oogasalad.model.gameplay.interpreter.RuleInterpreter;
import oogasalad.model.gameplay.strategies.attributes.Controllable;
import oogasalad.model.gameplay.strategies.attributes.Pushable;
import oogasalad.model.gameplay.strategies.attributes.Stoppable;
import oogasalad.model.gameplay.strategies.attributes.Winnable;
import oogasalad.model.gameplay.utils.exceptions.InvalidBlockName;
import oogasalad.model.gameplay.utils.exceptions.VisitorReflectionException;
import oogasalad.shared.observer.Observable;
import oogasalad.shared.observer.Observer;

public class Grid implements Observable<Grid> {

  String[][][] tempConfiguration = {
      {
          {"EmptyVisualBlock"}, {"EmptyVisualBlock"}, {"EmptyVisualBlock"}, {"EmptyVisualBlock"},
          {"EmptyVisualBlock"}, {"EmptyVisualBlock"}, {"EmptyVisualBlock"}, {"EmptyVisualBlock"},
          {"EmptyVisualBlock"}, {"EmptyVisualBlock"}, {"EmptyVisualBlock"}, {"EmptyVisualBlock"},
          {"EmptyVisualBlock"}, {"EmptyVisualBlock"}, {"EmptyVisualBlock"}
      },
      {
          {"EmptyVisualBlock"}, {"EmptyVisualBlock"}, {"EmptyVisualBlock"}, {"EmptyVisualBlock"},
          {"EmptyVisualBlock"}, {"EmptyVisualBlock", "EmptyTextBlock"}, {"EmptyVisualBlock"},
          {"EmptyVisualBlock", "RockVisualBlock"},
          {"EmptyVisualBlock"}, {"EmptyVisualBlock"}, {"EmptyVisualBlock"}, {"EmptyVisualBlock"},
          {"EmptyVisualBlock"}, {"EmptyVisualBlock"}, {"EmptyVisualBlock"}
      },
      {
          {"EmptyVisualBlock"}, {"EmptyVisualBlock"}, {"EmptyVisualBlock"}, {"EmptyVisualBlock"},
          {"EmptyVisualBlock"}, {"EmptyVisualBlock"}, {"EmptyVisualBlock", "IsTextBlock"},
          {"EmptyVisualBlock", "WallTextBlock"},
          {"EmptyVisualBlock"}, {"EmptyVisualBlock"}, {"EmptyVisualBlock"}, {"EmptyVisualBlock"},
          {"EmptyVisualBlock"}, {"EmptyVisualBlock"}, {"EmptyVisualBlock"}
      },
      {
          {"EmptyVisualBlock"}, {"EmptyVisualBlock", "BabaTextBlock"},
          {"EmptyVisualBlock", "IsTextBlock"}, {"EmptyVisualBlock", "YouTextBlock"},
          {"EmptyVisualBlock"}, {"EmptyVisualBlock"}, {"EmptyVisualBlock"}, {"EmptyVisualBlock"},
          {"EmptyVisualBlock"}, {"EmptyVisualBlock", "WinTextBlock"},
          {"EmptyVisualBlock", "WallVisualBlock"}, {"EmptyVisualBlock"},
          {"EmptyVisualBlock"}, {"EmptyVisualBlock"}, {"EmptyVisualBlock"}
      },
      {
          {"EmptyVisualBlock"}, {"EmptyVisualBlock"}, {"EmptyVisualBlock"}, {"EmptyVisualBlock"},
          {"EmptyVisualBlock"}, {"EmptyVisualBlock"}, {"EmptyVisualBlock"}, {"EmptyVisualBlock"},
          {"EmptyVisualBlock"}, {"EmptyVisualBlock", "WallVisualBlock"},
          {"EmptyVisualBlock", "WallVisualBlock"}, {"EmptyVisualBlock"},
          {"EmptyVisualBlock"}, {"EmptyVisualBlock"}, {"EmptyVisualBlock"}
      },
      {
          {"EmptyVisualBlock"}, {"EmptyVisualBlock"}, {"EmptyVisualBlock"},
          {"EmptyVisualBlock", "WallVisualBlock"},
          {"EmptyVisualBlock", "WallVisualBlock"}, {"EmptyVisualBlock"}, {"EmptyVisualBlock"},
          {"EmptyVisualBlock"},
          {"EmptyVisualBlock"}, {"EmptyVisualBlock", "WallVisualBlock"}, {"EmptyVisualBlock"},
          {"EmptyVisualBlock", "StopTextBlock"},
          {"EmptyVisualBlock"}, {"EmptyVisualBlock", "BabaTextBlock"}, {"EmptyVisualBlock"}
      },
      {
          {"EmptyVisualBlock"}, {"EmptyVisualBlock"}, {"EmptyVisualBlock"},
          {"EmptyVisualBlock", "WallVisualBlock"},
          {"EmptyVisualBlock"}, {"EmptyVisualBlock", "PushTextBlock"}, {"EmptyVisualBlock"},
          {"EmptyVisualBlock"},
          {"EmptyVisualBlock"}, {"EmptyVisualBlock"}, {"EmptyVisualBlock"}, {"EmptyVisualBlock"},
          {"EmptyVisualBlock"}, {"EmptyVisualBlock"}, {"EmptyVisualBlock"}
      },
      {
          {"EmptyVisualBlock"}, {"EmptyVisualBlock"}, {"EmptyVisualBlock"},
          {"EmptyVisualBlock", "WallVisualBlock"},
          {"EmptyVisualBlock", "BabaVisualBlock"}, {"EmptyVisualBlock", "WallVisualBlock"},
          {"EmptyVisualBlock"}, {"EmptyVisualBlock"},
          {"EmptyVisualBlock"}, {"EmptyVisualBlock", "FlagTextBlock"}, {"EmptyVisualBlock"},
          {"EmptyVisualBlock"},
          {"EmptyVisualBlock"}, {"EmptyVisualBlock"}, {"EmptyVisualBlock"}
      },
      {
          {"EmptyVisualBlock"}, {"EmptyVisualBlock"}, {"EmptyVisualBlock"},
          {"EmptyVisualBlock", "WallVisualBlock"},
          {"EmptyVisualBlock", "WallVisualBlock"}, {"EmptyVisualBlock"}, {"EmptyVisualBlock"},
          {"EmptyVisualBlock"},
          {"EmptyVisualBlock"}, {"EmptyVisualBlock"}, {"EmptyVisualBlock"}, {"EmptyVisualBlock"},
          {"EmptyVisualBlock"}, {"EmptyVisualBlock"}, {"EmptyVisualBlock"}
      },
      {
          {"EmptyVisualBlock"}, {"EmptyVisualBlock"}, {"EmptyVisualBlock"}, {"EmptyVisualBlock"},
          {"EmptyVisualBlock"}, {"EmptyVisualBlock", "FlagVisualBlock"}, {"EmptyVisualBlock"},
          {"EmptyVisualBlock"},
          {"EmptyVisualBlock"}, {"EmptyVisualBlock"}, {"EmptyVisualBlock"}, {"EmptyVisualBlock"},
          {"EmptyVisualBlock"}, {"EmptyVisualBlock", "WallVisualBlock"}, {"EmptyVisualBlock"}
      },
      {
          {"EmptyVisualBlock"}, {"EmptyVisualBlock", "WallVisualBlock"}, {"EmptyVisualBlock"},
          {"EmptyVisualBlock"},
          {"EmptyVisualBlock"}, {"EmptyVisualBlock"}, {"EmptyVisualBlock"}, {"EmptyVisualBlock"},
          {"EmptyVisualBlock"}, {"EmptyVisualBlock"}, {"EmptyVisualBlock"}, {"EmptyVisualBlock"},
          {"EmptyVisualBlock"}, {"EmptyVisualBlock", "WallVisualBlock"}, {"EmptyVisualBlock"}
      },
      {
          {"EmptyVisualBlock"}, {"EmptyVisualBlock", "WallVisualBlock"}, {"EmptyVisualBlock"},
          {"EmptyVisualBlock"},
          {"EmptyVisualBlock", "IsTextBlock"}, {"EmptyVisualBlock", "YouTextBlock"},
          {"EmptyVisualBlock"}, {"EmptyVisualBlock"},
          {"EmptyVisualBlock"}, {"EmptyVisualBlock"}, {"EmptyVisualBlock", "PushTextBlock"},
          {"EmptyVisualBlock"},
          {"EmptyVisualBlock"}, {"EmptyVisualBlock", "WallVisualBlock"}, {"EmptyVisualBlock"}
      },
      {
          {"EmptyVisualBlock"}, {"EmptyVisualBlock"}, {"EmptyVisualBlock"}, {"EmptyVisualBlock"},
          {"EmptyVisualBlock"}, {"EmptyVisualBlock"}, {"EmptyVisualBlock"}, {"EmptyVisualBlock"},
          {"EmptyVisualBlock"}, {"EmptyVisualBlock"}, {"EmptyVisualBlock"}, {"EmptyVisualBlock"},
          {"EmptyVisualBlock"}, {"EmptyVisualBlock"}, {"EmptyVisualBlock"}
      },
      {
          {"EmptyVisualBlock"}, {"EmptyVisualBlock"}, {"EmptyVisualBlock", "RockTextBlock"},
          {"EmptyVisualBlock"},
          {"EmptyVisualBlock"}, {"EmptyVisualBlock"}, {"EmptyVisualBlock"},
          {"EmptyVisualBlock", "WallVisualBlock"},
          {"EmptyVisualBlock", "WallVisualBlock"}, {"EmptyVisualBlock", "WallVisualBlock"},
          {"EmptyVisualBlock"}, {"EmptyVisualBlock", "WallTextBlock"},
          {"EmptyVisualBlock", "IsTextBlock"}, {"EmptyVisualBlock", "StopTextBlock"},
          {"EmptyVisualBlock"}
      },
      {
          {"EmptyVisualBlock"}, {"EmptyVisualBlock"}, {"EmptyVisualBlock"}, {"EmptyVisualBlock"},
          {"EmptyVisualBlock"}, {"EmptyVisualBlock"}, {"EmptyVisualBlock"}, {"EmptyVisualBlock"},
          {"EmptyVisualBlock"}, {"EmptyVisualBlock"}, {"EmptyVisualBlock"}, {"EmptyVisualBlock"},
          {"EmptyVisualBlock"}, {"EmptyVisualBlock"}, {"EmptyVisualBlock"}
      }
  };
  private final List<Observer<Grid>> observers = new ArrayList<>();
  private final List<AbstractBlock>[][] grid;
  private final RuleInterpreter parser;
  private final BlockFactory factory;
  private final BlockUpdater blockUpdater;
  private final String[][][] initialConfiguration;

  public Grid(int rows, int cols, String[][][] initialConfiguration) throws InvalidBlockName {
    this.grid = new ArrayList[rows][cols];
    this.parser = new RuleInterpreter();
    this.factory = new BlockFactory();
    this.blockUpdater = new BlockUpdater(this, factory);
    this.initialConfiguration = initialConfiguration; // Initialize initial configuration
    InitializeGrid();
  }

  // Call everytime there's a handle key press
  public void checkForRules() throws VisitorReflectionException {
    parser.interpretRules(grid);
  }

  public CellIterator iterator(int row, int col) {
    return new CellIterator(grid, row, col);
  }

  public void moveBlock(int fromI, int fromJ, int fromK, int ToI, int ToJ) {
    AbstractBlock block = grid[fromI][fromJ].get(fromK);
    grid[ToI][ToJ].add(block);
    grid[fromI][fromJ].remove(fromK);

    // Important: Set the row and col of visual blocks to the new position if moved
    if (!block.isTextBlock()) {
      block.setRow(ToI);
      block.setCol(ToJ);
    }

    if (grid[fromI][fromJ].isEmpty()) {
      addBlock(fromI, fromJ, "EmptyVisualBlock");
    }
  }

  private void addBlock(int i, int j, String BlockType) {
    grid[i][j].add(factory.createBlock(BlockType, i, j));
  }

  public AbstractBlock getBlock(int i, int j, int k) {
    return grid[i][j].get(k);
  }

  public List<AbstractBlock>[][] getGrid() {
    sortCellsForRender();
    return this.grid;
  }


  @Override
  public void addObserver(Observer<Grid> o) {
    observers.add(o);
  }

  @Override
  public void notifyObserver() {
    for (Observer<Grid> observer : observers) {
      observer.update(this);
    }
  }

  public int gridWidth() {
    return grid.length;
  }

  public int gridHeight() {
    return grid[0].length;
  }

  public int cellSize(int i, int j) {
    return grid[i][j].size();
  }

  public List<int[]> findControllableBlock() { //record class
    List<int[]> allControllableBlocks = new ArrayList<>();
    for (int i = 0; i < grid.length; i++) {
      for (int j = 0; j < grid[i].length; j++) {
        for (int k = 0; k < grid[i][j].size(); k++) {
          AbstractBlock block = grid[i][j].get(k);
          if (block != null && block.hasBehavior(Controllable.class)) {
            int[] a = {i, j, k};
            allControllableBlocks.add(a);
          }
        }
      }
    }
    return allControllableBlocks;
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
            if (block1.getBlockName().equals("EmptyVisualBlock") && !(block2.getBlockName().equals("EmptyVisualBlock") )) {
              return -1;
            } else if (!(block1.getBlockName().equals("EmptyVisualBlock") )
                && block2.getBlockName().equals("EmptyVisualBlock")) {
              return 1;
            }
            return 0;
          }
        });
      }
    }
  }

  public boolean isMovableToMargin(int endI, int endJ, int endK, int controllableintialI,
      int controllableintialJ, int controllableinitialK) {
    //grid.isMovableToMargin(endI, endJ, k, i, j, k)
    boolean already_in_margin = isAlreadyInMargin(controllableintialI, controllableintialJ);
    if (already_in_margin) {
      return true;
    }
    if (!isMovingToMargin(endI, endJ)) {
      return true;
    }
    if ((endI == grid.length - 1 || endI == 0)) {
      int indexI;
      indexI = (endI == 0) ? endI + 1 : endI - 1;

      //return grid[indexI][endJ].get(controllableinitialK).hasBehavior(Controllable.class);
      return grid[indexI][endJ].stream().anyMatch(block -> block.hasBehavior(Controllable.class));
    } else if ((endJ == grid[0].length - 1 || endJ == 0)) {
      int indexJ;
      indexJ = (endJ == 0) ? endJ + 1 : endJ - 1;
      //return grid[endI][indexJ].get(controllableinitialK).hasBehavior(Controllable.class);
      return grid[endI][indexJ].stream().anyMatch(block -> block.hasBehavior(Controllable.class));
    } else {
      return false;
    }
  }

  private boolean isAlreadyInMargin(int i, int j) {
    return ((i == grid.length - 1 || i == 0) || (j == grid[0].length - 1 || j == 0));
  }

  private boolean isMovingToMargin(int nextI, int nextJ) {
    return ((nextI == grid.length - 1 || nextI == 0) || (nextJ == grid[0].length - 1
        || nextJ == 0));
  }

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

  public boolean cellHasStoppable(int i, int j) {
    for (AbstractBlock block : grid[i][j]) {
      if (block.hasBehavior(Stoppable.class)) {
        return true;
      }
    }
    return false;
  }

  public boolean cellHasPushable(int i, int j) {
    boolean hasPushable = false;
    boolean textBlock = false;
    for (AbstractBlock block : grid[i][j]) {
      if (block.getBlockName().endsWith("TextBlock")) {
        textBlock = true;
      }
      if (block.hasBehavior(Pushable.class)) {
        hasPushable = true;
      }
    }
    return hasPushable || textBlock;
  }

  public boolean cellHasControllable(int i, int j) {
    return grid[i][j].stream().anyMatch(block -> block.hasBehavior(Controllable.class));
  }

  public boolean cellHasWinning(int i, int j) {
    return grid[i][j].stream().anyMatch(block -> block.hasBehavior(Winnable.class));
  }

  public List<Integer> allPushableBlocksIndex(int i,
      int j) { //Cant use stream and ForEach because we want to ensure order of element in arraylist are kept same way in indiceslist
    List<Integer> indicesList = new ArrayList<>();
    for (int index = 0; index < grid[i][j].size(); index++) {
      AbstractBlock block = grid[i][j].get(index);
      if (block.getBlockName().endsWith("TextBlock") || (
          block.getBlockName().endsWith("VisualBlock") && block.hasBehavior(Pushable.class))) {
        indicesList.add(index);
      }
    }

    return indicesList;
  }

  public boolean isNotOutOfBounds(int i, int j) {
    return i >= 0 && i < grid.length && j >= 0 && j < grid[i].length;
  }

  private void createBlocks(List<AbstractBlock> AbstractBlocks, String[] Blocktypes, int row,
      int col) {
    for (int i = 0; i < Blocktypes.length; i++) {
      AbstractBlocks.add(factory.createBlock(Blocktypes[i], row, col));
    }
  }

  private void InitializeGrid() {
    // Initializing elements
    for (int i = 0; i < grid.length; i++) {
      for (int j = 0; j < grid[i].length; j++) {
        grid[i][j] = new ArrayList<AbstractBlock>();
        createBlocks(grid[i][j], tempConfiguration[i][j], i, j);
      }
    }
  }

  public void resetAllBlocks() {
    List<AbstractBlock>[][] grid = getGrid();
    for (List<AbstractBlock>[] blocksRow : grid) {
      for (List<AbstractBlock> cell : blocksRow) {
        for (AbstractBlock block : cell) {
          if (!block.isTextBlock()) {
            block.resetAllBehaviors();
          }
        }
      }
    }
  }

}
