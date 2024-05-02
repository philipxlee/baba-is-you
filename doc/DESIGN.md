# DESIGN Document for OOGASalad

### Team 2

### Yasha Doddabele, Nikita Daga, Arnav Nayak, Philip Lee, Divyansh Jain, Joseph Ogunbadewa, Jonathan Esponda

## Team Roles and Responsibilities

* Team Member #1 (Philip Lee)
  * Responsible for the rule interpreter, strategy and visitor pattern that assigns strategies to 
   corresponding blocks.
  * Worked on controllers such as GameState controller which is responsible for winnign and losing logic.
  * Also developed the social center and leaderboard by creating database connections and classes to 
    interact with the MongoDB Atlas interface.

* Team Member #2: (Yasha Doddabele)
  * Responsible for front-end gameplay, entrypoint, making all of the images/UI/widget
  factory, and making the properties files for languages
  * Also helped with refactoring some of the frontend on the Authoring env side
  * Created the GameGridObserver, implemented the Observer paradigm in the Gameplay view (with my
  teammates code for the interfaces)
  * Worked on a couple other controllers


* Team Member #3

* Team Member #4 

* Team Member #5 (Divyansh Jain)
    * Responsible for the front end of the authoring environment 
    * Worked on core drag and drop functionality, remove blocks functionality, help system (hover and dialog box),
      saving and loading of files from authoring environment, error handling for grid size, cheat keys and resource bundles.
    * Also helped with the overall loading functionality/debugging, adding methods to levelcontroller, Grid and JSONLoader classes.
    

* Team Member #6 (Nikita Daga)
    * Responsible for the authoring environment view
    * Worked on the block viewing scroll pane and categorization of blocks here. Also worked on
      allowing
      grid size changes, and adapting the drag and drop functionality to align with new grids. Was
      also
      responsible for creating level validations when saving.

* Team Member #7 (Jonathan Esponda)
    * Responsible for JsonManager class and the loading and saving of different games and levels.
    * Helped initially with the view of the game player and also helped with the authoring
      environment
      model.

## Design Goals

* Goal #1
  * We wanted to create a game engine that could adapt to new blocks easily. As such, we created a
    block factory that could create new blocks based on their type and category, as well as implemented
    a visitor pattern to assign strategies to blocks with minimal code changes.


* Goal #2
  * Design is intuitive, neat, and standardized
  * No hardcoded values in the view, so almost all widget text are located in property files
  * UI widgets are shared between the two environments

* Goal #3
    * We wanted the authoring environment and game player to be well-connected with a process to
      save from the authoring environment and load it into the game player, whilst also being able to do
      the reverse. This was important in being able to take an existing level and editing it in the
      authoring environment.

#### How were Specific Features Made Easy to Add

* Feature #1: Adding a new block to the game engine and making it work with attributes was made easy by block 
    factory and visitor pattern. The block factory could create new blocks based on their type and 
    category, and the visitor pattern could assign strategies to blocks with minimal code changes.

* Feature #2: It's easy to add new languages. Simply add the language name as a parameter to a combo box, 
  then make a properties file with all necessary texts/buttons converted to the language you want.

* Feature #3: It is easy to add new blocks and their categories to the authoring view since all
  of this information is read from a json file. Only the file needs to be updated for new blocks and
  one line of code for new category types.

## High-level Design

#### Core Classes and Abstractions, their Responsibilities and Collaborators

* Class #1
  * A core class was the rule interpreter which was responsible for interpreting the rules of the
    game. This class was important in the gameplay as it was responsible for interpreting the rules
    of the game and applying them to the blocks on the grid. It was abstracted as it followed single
    responsibility, and was only used to assign strategies to blocks.

* Class #2
    * Core abstraction: Scene interface (and all Scene classes)
    * The Scene interface is helped to standardize View-related classes. All highlevel view classes
      like MainScene implement the Scene interface, and they are all managed by a SceneController class.
      For more complex functionality, classes within scenes are split into Panes (aka, MainScene contains
      the GamePane and InteractionPane) to follow the multiple classes principle.


* Class #3

* Class #4
    * A core class for our project was the JsonManager class. This class was an example of the
      facade design pattern as it was created to encapsulate the GSON library that we used to be
      able to handle our json files. This class was important in the parsing of levels to json and
      vice versa, whilst also being important to the level controllers being able to save and load.

## Assumptions or Simplifications

* Decision #1
    * A simplification we introduced towards the deadline was not allowing for rectangular grids, as
      they did not respond well to the updated editing and saving. As of right now we utilize square
      grids so that there are no bugs.

* Decision #2
   * An assumption we made was to only allow guest users to reply to comments on levels and not have the
      ability to save their comments to the database. This was done to simplify the social center and leaderboard, 
      but to also encourage users to create an account to have more functionality.

## Changes from the Original Plan

* Change #1: We changed the format of our planned JSon. In the plan the grid was a 2D array where
  each array was a row and the elements within it were columns. But then we changed this to ensure
  that each element itself was a dynamically resizable arraylist allowing phasability of elements.

* Change #2: We changed the way UI widgets work. Originally, gameplay and authoring environment had
separate UIs. Then, we decided it would make sense to have a shared API to standardize making widgets
and streamlining the process.

* Change #3

* Change #4

## How to Add New Features

#### Features Designed to be Easy to Add

* Feature #1: 
  * It's easy to add new languages. Simply add the language name as a parameter to a combo box,
    then make a properties file with all necessary texts/buttons converted to the language you want.

* Feature #2:  
  * To add a new visual block, just extend the BlockVisitor by adding a new visit method, as well as the
    visitor classes that implement the BlockVisitor.

* Feature #3

* Feature #4:
    * It is very easy to add new blocks to the authoring environment and categorize them into the
      existing categories of (Visual and Text) or add another category as needed.

#### Features Not Yet Done

* Feature #1: Multiple themes
  * We wanted to make multiple themes, such as languages, but ran out of time. It should be relatively
  easy however; you just follow the same controller paradigm that languages follows.

* Feature #2
  * Pull functionality, which could allow a controllable block to pull blocks towards them as opposed to 
  always pushing them.

* Feature #3
    * One of the bugs in our features in the removal of blocks from the backend after they are
      removed from the authoring frontend. Currently, despite not showing up on the frontend, blocks
      will be saved in their past position and show up when a file is loaded.

* Feature #4
    * One of the features that we could not fully implement was handling the saving and loading of
      rectangular grids. Towards the end of our submission, we encountered a bug that did not allow
      for the correct handling of non-square grids. Therefore, we just set square grids as the only
      option for creating levels.
 