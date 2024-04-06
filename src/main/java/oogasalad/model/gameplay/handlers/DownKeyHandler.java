package oogasalad.model.gameplay.handlers;

import oogasalad.model.gameplay.grid.Grid;

public class DownKeyHandler extends KeyHandler {

    public DownKeyHandler(Grid grid){
        super(grid);
    }
    @Override
    public void execute(){
        handleKeyPress(1, 0);
    }
}
