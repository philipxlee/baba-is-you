package oogasalad.model.gameplay.handlers;

import oogasalad.controller.gameplay.GameOverController;
import oogasalad.model.gameplay.blocks.AbstractBlock;
import oogasalad.model.gameplay.grid.Grid;
import oogasalad.model.gameplay.grid.GridHelper;
import oogasalad.model.gameplay.strategies.Stoppable;
import oogasalad.model.gameplay.strategies.Winnable;

import java.util.List;
import java.util.Optional;

public abstract class KeyHandler {
    private Grid grid;
    private GridHelper gridHelper;
    private GameOverController gameOverController;

    public KeyHandler(Grid grid, GameOverController gameOverController) {
      this.grid = grid;
      this.gameOverController = gameOverController;
      grid.checkForRules();
    }

    public abstract void execute();

    private void moveBlock(int i, int j, int k, int deltaI, int deltaJ){
      int nextI = i + deltaI;
      int nextJ = j + deltaJ;
      if(!isValidMove(nextI, nextJ, k)){
        return;
      }
      nextIsWinningBlock(nextI, nextJ);
      calculateLength(i, j, k, deltaI, deltaJ) //calculates length to move and calls perfrom movement on the length
          .ifPresent(length -> performMovement(i, j, k, deltaI, deltaJ, length));

    }

    protected void handleKeyPress(int deltaI, int deltaJ){
        grid.checkBehaviors();
        List<int[]> controllableBlockPositions = grid.findControllableBlock();
        if (!controllableBlockPositions.isEmpty()){
          for(int[] element : controllableBlockPositions){
            moveBlock(element[0], element[1], element[2], deltaI, deltaJ);
          }
        } else {
          gameOverController.displayGameOver(false);
        }
      grid.checkForRules();
      grid.notifyObserver();
    }

    private void performMovement(int i, int j, int k, int deltaI, int deltaJ, int length) {
      // Move all blocks
      for (int m = length - 1; m > 0; m--) {
        int currentI = i + m * deltaI;
        int currentJ = j + m * deltaJ;
        int nextI = currentI + deltaI;
        int nextJ = currentJ + deltaJ;
        //move all the pushable stuffs into the next cell
        List<Integer> indicesToMove = grid.allPushableBlocksIndex(currentI, currentJ);
        for (Integer index : indicesToMove) {
          grid.moveBlock(currentI, currentJ, index, nextI, nextJ);
        }
      }
      // Move controllable block last
      grid.moveBlock(i, j, k, i+deltaI, j+deltaJ);
    }


  private Optional<Integer> calculateLength(int i, int j, int k, int deltaI, int deltaJ) {
    int length = 1;


      while (true) {
          int nextI = i + length * deltaI; //gets next cell
          int nextJ = j + length * deltaJ; // gets next cell
          System.out.printf("hasPushable returned " + grid.cellHasPushable(nextI, nextJ));
          if (isValidMove(nextI, nextJ, k) && grid.cellHasPushable(nextI, nextJ) && !grid.cellHasStoppable(nextI, nextJ)) {
              length++;
          } else {
              break;
          }
      }

      int endI = i + length * deltaI;
      int endJ = j + length * deltaJ;
      if (!isValidMove(endI, endJ, k) || !grid.isMovableToMargin(endI, endJ, k, i, j, k) || grid.cellHasStoppable(endI, endJ)) {
          return Optional.empty(); // No space to move the chain
      }
      System.out.println("length of things to push is " + length);
      return Optional.of(length);
  }


  private void nextIsWinningBlock(int nextI, int nextJ){
    if (grid.cellHasWinning(nextI, nextJ)){
      gameOverController.displayGameOver(true);
    }
  }
  private boolean isValidMove(int i, int j, int k){
    return grid.isNotOutOfBounds(i, j);
  }

}
