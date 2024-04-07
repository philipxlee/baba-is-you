package oogasalad.model.gameplay.handlers;

import oogasalad.controller.gameplay.GameOverController;
import oogasalad.model.gameplay.grid.Grid;

public class RightKeyHandler extends KeyHandler {

    public RightKeyHandler(Grid grid, GameOverController gameOverController){
        super(grid, gameOverController);
    }
    @Override
    public void execute(){
        handleKeyPress(0, 1);
    }
}
