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

```java
class Winnable implements Component {
    private boolean isWinCondition = false;

    @Override
    public void applyRule(String behavior) {
        isWinCondition = "Win".equals(behavior);
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

* Use case 3: void visit() in the BlockVisitor
  * This method is called to accept a visitor object that will perform an operation on the block.
  * It is used to implement the visitor pattern to perform operations on the block without changing the block's class.
  * For example, a visitor object can be used to check if a block is a win condition.

```java
public interface BlockVisitor {
  void visit(BabaVisualBlock baba);
  void visit(WallVisualBlock wall);
  void visit(RockVisualBlock rock);
  void visit(FlagVisualBlock flag);
}

```
--- 


* Use case 3: vistor.accept() in the Model/Block class
  * This method is called to accept a visitor object that will perform an operation on the block.
  * It is used to implement the visitor pattern to perform operations on the block without changing the block's class.
  * For example, a visitor object can be used to check if a block is a win condition.

```java
public interface BlockVisitor {
  void visit(BabaVisualBlock baba);
  void visit(WallVisualBlock wall);
  void visit(RockVisualBlock rock);
  void visit(FlagVisualBlock flag);
}

```

--- 

* Use case 4: addBehavior() in the Model/Block class
  * This method is called to add a behavior to the block.
  * It is used to implement the strategy pattern to add behaviors to the block.
  * For example, a behavior can be added to a block to make it a win condition.

```java
  public void addBehavior(Strategy behavior) {
    this.behaviors.add(behavior);
  }
```

---

* Use case 5: isTextBlock() in the Model/Block class
  * This method is called to check if the block is a text block.
  * This helps to prevent instanceof checks between AbstractVisualBlocks and AbstractTextBlocks
  * For example, a block can be checked if it is a text block before applying a rule to it.

```java

public class AbstractBlock {

  public boolean isTextBlock() {
    return false;
  }
}

```

---

* Use case 6: accept() in the Model/Block class
  * This method is called to accept a visitor object that will perform an operation on the block.
  * It is used to implement the visitor pattern to perform operations on the block without changing the block's class.
  * For example, a visitor object can be used to check if a block is a win condition.

```java
  
    public void accept(BlockVisitor visitor) {
        visitor.visit(this);
    }
```

---


