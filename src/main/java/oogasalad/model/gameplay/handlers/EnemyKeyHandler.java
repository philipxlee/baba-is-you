package oogasalad.model.gameplay.handlers;

import oogasalad.controller.gameplay.GameStateController;
import oogasalad.model.gameplay.grid.Grid;
import oogasalad.model.gameplay.grid.GridHelper;

public class EnemyKeyHandler extends KeyHandler{
    private final Grid grid;
    private final GameStateController gameStateController;
    public EnemyKeyHandler(Grid grid, GameStateController gameStateController) {
        super(grid, gameStateController);
        this.grid = grid;
        this.gameStateController = gameStateController;

    }

    @Override
    public void execute() {
        if(grid.hasEnemy()){
            return;
        }
        else{
            createEnemy();
        }
    }

    private void createEnemy(){

    }
    public void moveEnemy(){
        if(!grid.hasEnemy()){
            return;
        }
        else{

        }
    }
}
