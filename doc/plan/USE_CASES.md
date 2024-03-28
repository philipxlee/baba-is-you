## API Examples - Use Cases

---

### Authoring Environment


---

### Game Player

---

* Use case 1: applyRule():
  * This method is called when a block is placed on the grid. 
  * It is used to apply the rules of the game to the block.
  * For example, if a block is placed on the grid with the text "Win", the block should be marked as a win condition.
* Use case 2: interact():
  * This method is called when two blocks interact with each other.
  * It is used to determine the outcome of the interaction based on the rules of the game.
  * For example, if a player block moves onto a win condition block, the game should be marked as won.

```java
class Winnable implements Component {
    private boolean isWinCondition = false;

    @Override
    public void applyRule(String behavior) {
        isWinCondition = "Win".equals(behavior);
    }

    @Override
    public void interact(Block actingBlock, Block receivingBlock) {
        if (isWinCondition) {
            GameContext.notifyWin();
        }
    }
}
```

--- 

* Use case 2: renderGrid() in the View/GameScene class
  * This method is called to iterate over the CellView[][] frontend grid and render each object in the
  screen.
  * It is rendered everytime there is a backend change that needs to be reflected in the frontend.

```java
class GameScene implements Scene {
  private GridPane grid;
  private int rows;
  private int cols;
  public GameScene() {
    //Initializes the scene here with the grid...
  }
  
  //Calls everytime the backend game objects change/every iteration
  public void renderObjects(CellView[][] cells) {
    for (int i=0; i<rows; i++) {
      for (int j=0; j<cols; j++) {
        //Whatever the JavaFX object is...
        for (BlockView block: cell[i][j].getViews()) {
          StackPane pane = block.getView();
          grid.add(pane, j, i);
        }
      }
    }
  }
}

```
--- 
