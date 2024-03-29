
## Authoring Environment
### View

#### Design Goals:

The API is designed to be easy to understand and use. While providing a straightforward way to 
perform common tasks, the API also allows for more complex operations and customizations.
The API's structure supports easy extension and modification, allowing for future enhancements 
without breaking existing functionality.

#### API and Abstractions:

#### Level Management
Represents a game level, including its dimensions and the objects it contains.

```java

public class LevelManager {

    public Level createLevel(String name, int width, int height) {
    }

    public Level loadLevel(String levelId) {
    }

    public void saveLevel(String levelId) {
    }

    public List<Level> listLevels() {
    }
}
```
#### Object Management
Represents an entity within a level, such as blocks, characters, or text elements, along 
with its properties and position.

```java
public class ObjectManager {

    public BlockView createObject(String type, int x, int y) {
    }

    public void moveObject(BlockView object, int newX, int newY) {
    }

    public void deleteObject(BlockView object) {
    }

    public void updateObjectProperties(BlockView object, Map<String, Object> properties) {
    }
}
```

#### Rule Management
Dictates the behavior and interactions between objects within the level based on predefined logic.

```java
public class RuleManager {

    public Rule createRule(String subject, String verb, String object) {
    }

    public void deleteRule(String ruleId) {
    }
}

```


### Controller

---
```java
addGameElement(Enum element, int row, int column) {}

```
- Adds a game element to the specified grid cell. Handles user actions 
  to add game elements onto the grid in the Authoring Environment.
---

### Model

---
```java
modifyGridDimensions(int rows, int column){}
```
- Modifies the dimensions of the grid : updates the internal grid representation to reflect the modified dimensions.
---


## GamePlayer
### View

--- 

```java
import java.util.ArrayList;

//Cell wrapper to hold multiple blocks in one grid of the cell
public class CellView {

  private List<BlockView> content;

  public CellView() {
    this.content = new ArrayList<>();
  }

  public List<BlockView> getViews() {
    return content.clone();
  }
}

public abstract class BlockView {
  private StackPane stackPane;
  public BlockView initializeBlock(String imgPath) {
    //Initializes some JavaFX object with the image from the imagePath
  }
  //Or whatever JavaFX obj it is
  public StackPane getView() {
    return this.stackPane;
  }
}

  //For each subclass
  public class BabaView extends BlockView {
  }
  public class WallView extends BlockView {
  }
  //etc...
}
```
- Abstract BlockView paradigm for the visual representations of the game objects.
  - Ideally has a hierarchy, different block types are abstraction by the superclass BlockView
  - CellView is made to keep track of and hold multiple blocks that can exist in the same grid cell. 
  Its contents should be ordered to determine the overlap and which block(s) Baba can interact with.
  - This makes the front-end grid easier to deal with because it will just be a 2D array of CelLView
  objects.
---  



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