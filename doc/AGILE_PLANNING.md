# OOGA Agile Plan Discussion

## FUN Team Name: BabaIsUs

## Student Names Arnav Nayak, Nikita Daga, Divyansh Jain, Joseph Ogunbadewa, Jonathan Esponda, Philip Lee, Yasha Doddabele.

### Backlog (Feature Requirements)

* Feature 1: Authoring Environment - Frontend

    * Use Case 1: Tools to create and edit the game's initial rules (e.g., what conditions lead to
      winning,
      how Baba can interact with each element).

    * Use Case 2: A split screen on the authoring environment has a set of buttons/widgets where you
      can
      drag/drop elements onto a grid and build a gameplay foreground.


* Feature 2: Game Player - Frontend

    * Use Case 1: A user uses a set of keyboard keys to move an icon representing a game character
      around the screen.

    * Use Case 2: A user can use the game character to interact with objects on the screen and
      achieve
      a variety of outcomes, such as moving the position of objects or deleting objects from the
      screen.


* Feature 3: Authoring Environment - Backend

    * Use Case 1: A user can create and modify a level abstraction.

    * Use Case 2: A user can validate and serialize any level abstraction into JSON.


* Feature 4: Game Player - Backend

    * Use Case 1: A user can play any given configuration of "Baba Is You" that follows a specific
      set of rules

    * Use Case 2: The model offers easy to use abstractions for the Controller to use.

### Sprint 1 (Test)

* Team Member 1: Jonathan - Frontend: game play + authoring engine (split)
    * Feature/Use Case 1 When a level or game is created in the authoring engine, the created level
      is used to make a configuration (JSON) file that can then be loaded by the game player.

    * Feature/Use Case 2 Making sure that we can drag and drop elements from the authoring engine
      into the expected level that will be created

* Team Member 2: Arnav - Backend Enginge/Data
    * Feature/Use Case 1: handle user input and update the view accordingly with user move
    * Feature/Use Case 2: set up mvc architecture
    * Feature/Use Case 3: Model should use Block API to create Level Abstraction
    * Feature/Use Case 4: Controllers for view to interact with the Level abstraction's grid.

* Team Member 3: Yasha - Frontend: game play
    * Feature/Use Case 1: Basic splash screen + game environment exists for at least one level. User
      can move a character around on the screen.

    * Feature/Use Case 2: See information about the game/level and pause, end, or continue the
      gameplay.

* Team Member 4: Joseph - Backend engine
    * Feature/Use Case 1 : create an interface for all possible objects on the screen.
    * Feature/Use Case 2 : create an abstraction for the properties that these objects can extend.
    * Feature/Use Case 3 : create basic validation to ensure game is winnable.
    * Feature/Use Case 4 : read JSON file to figure out which objects have which properties.
    * Feature/Use Case 5 : read JSON file to inform placement of objects on the intial Game player
      screen
    * Feature/Use Case 6 : inform model of the properties objects have to know which abstractions to
      extend.

* Team Member 5: Philip - Backend engine
    * Feature/Use Case 1: Creating BaBa is you and its movement along the grid.
    * Feature/Use Case 2: Start with the reading of the rules (The algorithm to process and update
      new state changes)

* Team Member 6: Nikita - Frontend: authoring engine
    * Feature/Use Case 1: Creating two panes -- one for the grid and one for the elements

    * Feature/Use Case 2: Enabling drag and drop and once done: a validation + save config

* Team Member 7: Divyansh - Backend: data/engine
    * Feature/Use Case 1: creating level management abstractions for authoring environment

    * Feature/Use Case 2: adding functionality for new elements in the authoring environment

### Sprint 2 (Your Choice)

* Team Member 1
    * Feature/Use Case 1

    * Feature/Use Case 2

* Team Member 2
    * Feature/Use Case 1

    * Feature/Use Case 2

* Team Member 3
    * Feature/Use Case 1

    * Feature/Use Case 2

* Team Member 4
    * Feature/Use Case 1

    * Feature/Use Case 2

* Team Member 5
    * Feature/Use Case 1

    * Feature/Use Case 2

* Team Member 6
    * Feature/Use Case 1

    * Feature/Use Case 2

* Team Member 7
    * Feature/Use Case 1

    * Feature/Use Case 2

### Sprint 3 (Your Choice)

### Sprint 4 (Complete)
