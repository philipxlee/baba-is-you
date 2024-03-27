
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


## JsonManager (Jonathan)

#### Design Goals
* The design goal for the JsonManager class is to have a versatile and easily usable design that 
allows the team to be able to work with JSON data easily. The ideal outcome is to allow for the 
authoring environment and the game player to be able to obtain json data for their parsers or to 
be able to turn data into json so that they can be saved. 

#### Primary Abstractions
The primary abstractions are going to be that the loading, saving, and acquisition or transforming 
of json data will be abstracted away into this JsonManager that will include methods that
encapsulate their implementation such as:

```java
public JSONObject loadFromFile(String fileName) {
    // load JSONObject/(s) from files
}

public void saveToFile(JSONObject jsonObject, Stage stage) {
  // save JSON data to a file chosen by the user
}

public void getValue(JSONObject jsonObject, String key) {
  // get value from JSON with corresponding key
}

public Object setValue(JSONObject jsonObject, String key, Object value) {
  // set value with corresponding key to new object value
}
```

#### Usefulness for Developers

This class will be useful for developers as they will be able to 
obtain and set JSON values whilst also being able to load them in 
or save them. This information can then go to the parsers that 
the authoring environment and game player will have set up and that
will allow us to integrate our game information with json and 
vice versa. 