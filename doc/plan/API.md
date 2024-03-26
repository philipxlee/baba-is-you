
## Authoring Environment
### View

### Controller

### Model


## GamePlayer
### View



### Controller

---
```java
handleUserInput(keyPress input) {}
```
- Handles the inputs of the user, such as moving a block with arrow keys.
---
```java
getTranslation() {}
```
- Gets the translation of a text from a Language controller.
---




### Model

---
```java 
isWinnable(Level level) {}
```
- Checks if the provided game level is winnable by ensuring it meets certain criteria, such as having at least one win condition and necessary game objects.
---
```java
 public void interpretRules(Grid grid) {
  // Scan the grid for rule patterns
  // For each found rule, update the components of the relevant blocks.
  // For example, if you find "ROCK IS PUSH", locate all ROCK blocks and add/update the Pushable component.
}
```
-  Helps to update the different game states based on the rules that are present in the game.
---
```java
@Override
public void applyRule(String behavior) {
    // Apply the rule to the game
}

```
- Applies a rule to the game, such as "ROCK IS PUSH" or "BABA IS YOU" or "FLAG IS WIN".
---
```java
@Override
public void interact(Block actingBlock, Block receivingBlock) {
    // Handle interactions between a block and a receiving block.
}
```
- Interacts with the blocks in the game, such as when a block is pushed or when a block is moved to a win condition.
- If the win condition is met, the game context is notified of the win.
