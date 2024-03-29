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
* Use case 3: Winnable():
  * This method is called after the level is created
  * Checks to ensure that the author hasn't created an impossible level
  * For example: if flag is win and flag is not present on the level, this represents an unwinnable game. 
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

* 

--- 