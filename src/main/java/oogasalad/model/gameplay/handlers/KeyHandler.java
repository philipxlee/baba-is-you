package oogasalad.model.gameplay.handlers;

import oogasalad.model.gameplay.blocks.AbstractBlock;
import oogasalad.model.gameplay.grid.Grid;
import oogasalad.model.gameplay.strategies.Stoppable;
import oogasalad.model.gameplay.strategies.Winnable;

import java.util.List;
import java.util.Optional;

public abstract class KeyHandler {
    private Grid grid;
    public KeyHandler(Grid grid) {
        this.grid = grid;
        grid.checkForRules();
    }
    public abstract void execute();
    protected void handleKeyPress(int deltaI, int deltaJ){
        grid.checkBehaviors();
        List<int[]> controllableBlockPositions = grid.findControllableBlock();
        if(controllableBlockPositions.get(0) != null){
            for(int[] element : controllableBlockPositions){
                moveBlock(element[0], element[1], element[2], deltaI, deltaJ);
            }
        }
        grid.checkForRules();
        grid.renderChanges();
    }
    private void moveBlock(int i, int j, int k, int deltaI, int deltaJ){
        int nextI = i + deltaI;
        int nextJ = j + deltaJ;
        if(!isValidMove(nextI, nextJ, k)){
            return;
        }
        nextIsWinningBlock(nextI, nextJ, k);
        calculateLength(i, j, k, deltaI, deltaJ)
                .ifPresent(length -> performMovement(i, j, k, deltaI, deltaJ, length));

    }
    private Optional<Integer> calculateLength(int i, int j, int k, int deltaI, int deltaJ) {
        int length = 1;

        while (true) {
            int nextI = i + length * deltaI; //gets next cell
            int nextJ = j + length * deltaJ; // gets next cell
            if (isValidMove(nextI, nextJ, k) && !"EmptyVisualBlock".equals(grid.getBlock(nextI, nextJ,k).getBlockName()) && !grid.getBlock(nextI, nextJ,k).hasBehavior(Stoppable.class)) {
                length++;
            } else {
                break;
            }
        }

        int endI = i + length * deltaI;
        int endJ = j + length * deltaJ;
        if (!isValidMove(endI, endJ, k) || !"EmptyVisualBlock".equals(grid.getGrid()[endI][endJ].get(k).getBlockName()) || !grid.isMovableToMargin(endI, endJ, k, i, j, k)) {
            return Optional.empty(); // No space to move the chain
        }
        return Optional.of(length);
    }
    private void performMovement(int i, int j, int k, int deltaI, int deltaJ, int length) {
        // Move all blocks
        for (int m = length - 1; m > 0; m--) {
            int currentI = i + m * deltaI;
            int currentJ = j + m * deltaJ;
            int nextI = currentI + deltaI;
            int nextJ = currentJ + deltaJ;
            grid.moveBlock(currentI, currentJ, k, nextI, nextJ, k);
        }
        // Move controllable block last
        grid.moveBlock(i, j, k, i+deltaI, j+deltaJ, k);
        grid.setBlock(i, j, k, "EmptyVisualBlock");
    }
    private void nextIsWinningBlock(int nextI, int nextJ, int k){
        AbstractBlock nextBlock = grid.getBlock(nextI, nextJ, k);
        if (nextBlock != null && nextBlock.hasBehavior(Winnable.class)) {
            System.out.println("Game over, you won!");
            System.exit(0);
        }
    }
    private boolean isValidMove(int i, int j, int k){
        return grid.isNotOutOfBounds(i, j);
    }
}
