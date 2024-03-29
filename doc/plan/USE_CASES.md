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

* 

--- 

### Configuration Files

---

* Use case 1: loadFromFile():
  * This method is called when the user presses the load option either in the game player or the
  authoring environment.
  * It will pass the information from the json file to the respective controllers of the authoring 
  environment or the game player (depending on where you are trying to load). 
  * The data will then be parsed in the controller and the correct information will load on screen. 
* Use case 2: saveToFile():
  * This method is called when a user wants to save the current game or the authoring environment
  level.
  * It is used to save the information into a JSON file format.
* Use case 3: getValue():
  * This method is called to get the value from a JSON object based on the corresponding key.
  * For example, jsonManager.getValue(levelOneObject, "grid") would return the value for the key 
  grid in the levelOneObject jsonObject.
* Use case 4: setValue():
  * This method is called to set the value of a JSON object based on the corresponding key.
  * For example, jsonManager.setValue(levelOneObject, "author", "Team 2") would set the value 
  for the key author in the levelOneObject jsonObject to "Team 2".

```java
class JsonManager {
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
}
```

Examplea of the JsonManager in use:
```java
//This code would load in the selected file and the loadFromFile method would communicate 
//with the controller so that it has the necessary information to parse.
JsonManager jsonManager = new JsonManager();
jsonManager.loadFromFile("selected_file");
```

```java
//This code would save the designated json object into the file a user chooses.
JsonManager jsonManager = new JsonManager();
jsonManager.saveToFile(levelOneObject, primaryStage);
```

```java
//This code would return the value of the grid in the json with the corresponding key
JsonManager jsonManager = new JsonManager();
jsonManager.getValue(levelOneObject, "grid");
```

```java
//This code would update the jsonObject, with the corresponding key, to the set object value
//This would result in a grid that is 1x3 with a babaBlock followed by a textBlock and wallBlock.
JsonManager jsonManager = new JsonManager();
jsonManager.setValue(levelOneObject, "grid", ["babaBlock", "textBlock", "wallBlack"]);
```