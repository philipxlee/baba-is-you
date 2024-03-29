## API Examples - Use Cases

---

### Authoring Environment

Use Case : Adding element from view to model through the controller. Implementation of Controller and Model interfaces for demo :

```java

interface AuthoringEnvironmentController {
void addGameElement(Object element, int row, int column);
}

interface AuthoringEnvironmentModel {
void addGameElement(Object element, int row, int column);
}

class AuthoringEnvironmentControllerImpl implements AuthoringEnvironmentController {
private final AuthoringEnvironmentModel model;

    public AuthoringEnvironmentControllerImpl(AuthoringEnvironmentModel model) {
        this.model = model;
    }

    @Override
    public void addGameElement(Object element, int row, int column) {
        model.addGameElement(element, row, column);
    }
}

class AuthoringEnvironmentModelImpl implements AuthoringEnvironmentModel {
private Object[][] grid; // Representing the grid of the authoring environment

    public AuthoringEnvironmentModelImpl(int rows, int columns) {
        this.grid = new Object[rows][columns];
    }

    @Override
    public void addGameElement(Object element, int row, int column) {
        if (row >= 0 && row < grid.length && column >= 0 && column < grid[0].length) {
            grid[row][column] = element; //not exactly sure of the implementation/structure of grid cells, but conveys the idea.
                  //might have to do something like grid[row][column].append(element);
            System.out.println("Added " + element.toString() + " to grid cell [" + row + ", " + column + "]");
        } else {
            System.out.println("Invalid grid cell coordinates");
        }
    }
}

public class Main {
public static void main(String[] args) {
// First creating an instance of the AuthoringEnvironmentModel. choosing random arbitrary for now. 
AuthoringEnvironmentModel model = new AuthoringEnvironmentModelImpl(5, 5);

        // instance of the AuthoringEnvironmentController and injecting the model
        AuthoringEnvironmentController controller = new AuthoringEnvironmentControllerImpl(model);

        // Adding a game element such as like a block to a specific grid cell
        controller.addGameElement("Block", 2, 3);
    }
}

```

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

