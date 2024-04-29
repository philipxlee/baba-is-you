package oogasalad.controller.gameplay;

import javafx.scene.input.KeyCode;
import oogasalad.model.gameplay.grid.Grid;
import oogasalad.model.gameplay.handlers.*;

public class KeyHandlerController {

    private final GameStateController gameStateController;
    private KeyHandler keyHandler;
    private Grid grid;


    private EnemyKeyHandler enemyKeyHandler;

    /**
     * Constructs a new instance of the KeyHandler class.
     * The constructor initializes the game grid and game state controller.
     * @param gameStateController The controller responsible for managing the game state.
     */
    public KeyHandlerController(GameStateController gameStateController) {
        this.gameStateController = gameStateController;
    }

    /**
     * Executes the corresponding action based on the provided key code.
     * If the key code matches any predefined cheat keys, the corresponding action is executed immediately.
     * Otherwise, a key handler is instantiated based on the key code and executed to perform the appropriate action.
     * @param grid The grid object representing the game state.
     * @param code The key code corresponding to the pressed key.
     * @return True if the action was executed successfully, false otherwise.
     */
    public boolean executeKey(Grid grid, KeyCode code) {
        this.grid = grid;
        //Cheat keys: implement further ones here
        switch (code) {
            case W -> gameStateController.displayGameOver(true);
            case L -> gameStateController.displayGameOver(false);
            case R -> gameStateController.restartGame();
            default -> {
            }
        }
        keyHandler = switch (code) {
            case UP -> new UpKeyHandler(grid, gameStateController);
            case DOWN -> new DownKeyHandler(grid, gameStateController);
            case LEFT -> new LeftKeyHandler(grid, gameStateController);
            case RIGHT -> new RightKeyHandler(grid, gameStateController);
            case E -> new EnemyKeyHandler(grid, gameStateController);
            default -> null;
        };

        if (keyHandler != null) {
            keyHandler.execute();
            return true;
        }
        return false;
    }

    /**
     * Moves the enemy in the game grid.
     * This method instantiates an enemy key handler and calls its moveEnemy method to move the enemy.
     */
    public void moveEnemy() {
        enemyKeyHandler = new EnemyKeyHandler(this.grid, this.gameStateController);
        enemyKeyHandler.moveEnemy();
    }

}

