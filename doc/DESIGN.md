# DESIGN Document for OOGASalad

### Team 2

### Yasha Doddabele, Nikita Daga, Arnav Nayak, Philip Lee, Divyansh Jain, Joseph Ogunbadewa, Jonathan Esponda

## Team Roles and Responsibilities

* Team Member #1

* Team Member #2

* Team Member #3

* Team Member #4

* Team Member #5

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

* Goal #2

* Goal #3
    * We wanted the authoring environment and game player to be well-connected with a process to
      save
      from the authoring environment and load it into the game player, whilst also being able to do
      the reverse. This was important in being able to take an existing level and editing it in the
      authoring environment.

#### How were Specific Features Made Easy to Add

* Feature #1

* Feature #2

* Feature #3: It is easy to add new blocks and their categories to the authoring view since all
  of this information is read from a json file. Only the file needs to be updated for new blocks and
  one line of code for new category types.

## High-level Design

#### Core Classes and Abstractions, their Responsibilities and Collaborators

* Class #1

* Class #2

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

* Decision #3

* Decision #4

## Changes from the Original Plan

* Change #1: We changed the format of our planned JSon. In the plan the grid was a 2D array where
  each array was a row and the elements within it were columns. But then we changed this to ensure
  that each element itself was a dynamically resizable arraylist allowing phasability of elements.

* Change #2

* Change #3

* Change #4

## How to Add New Features

#### Features Designed to be Easy to Add

* Feature #1

* Feature #2

* Feature #3

* Feature #4:
    * It is very easy to add new blocks to the authoring environment and categorize them into the
      existing categories of (Visual and Text) or add another category as needed.

#### Features Not Yet Done

* Feature #1

* Feature #2

* Feature #3
    * One of the bugs in our features in the removal of blocks from the backend after they are
      removed from the authoring frontend. Currently, despite not showing up on the frontend, blocks
      will be saved in their past position and show up when a file is loaded.

* Feature #4
    * One of the features that we could not fully implement was handling the saving and loading of
      rectangular grids. Towards the end of our submission, we encountered a bug that did not allow
      for the correct handling of non-square grids. Therefore, we just set square grids as the only
      option for creating levels.
 