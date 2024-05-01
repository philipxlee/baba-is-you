# oogasalad

## BabaIsUs

## Philip Lee, Divyansh Jain, Nikita Daga, Jonathan Esponada, Arnav Nayak, Joseph Ogunbadewa, Yasha Doddabele

This project implements an authoring environment and player for multiple related games.

### Timeline

* Start Date:
  * March 19

* Finish Date:
  * May 1

* Hours Spent:
  * 300

### Attributions

* Resources used for learning (including AI assistance)

  * StackOverflow (to find helpful code snippets)
  * JavaFX Documentation
  * JavaFX Tutorials
  * ChatGPT (to debug and ideate)

* Resources used directly (including AI assistance)

  * StackOverflow (to find helpful code snippets)
  * JavaFX Documentation

### Running the Program

* Main class:
  * Main.java

* Data files needed:
  * Can optionally load .json files in /data
  * However, a connection to the MongoDB database for leaderboard and social center functionality is
  hidden in .gitignore. As such, users will need to create their own MongoDB Atlas connection string
  with a database called "BabaData", and two collections called "data" and "comments".

* Interesting data files:
  * Tunnel Of The Crab King: an easy level that introduces opposing artificial players that may 
  kill Baba.
  * Dutilleul The Wall Phaser: an easy level that requires Baba to phase through walls

* Key/Mouse inputs:

  * Arrow Keys: Up, Down, Left, Right to move controllable characters.
  * Cheat Keys:
    * `R`: Restart the level in gameplay OR go back to the splash screen from authoring
    * `W`: Win the level
    * `L`: Lose the level in gameplay OR load a level into the authoring env
    * `E`: Spawn an enemy (crab)
    * `X`: Easter egg: Turn Baba more girly with a bow and a skirt!
    * `S` : Save a level from the authoring environment

### Notes/Assumptions

* Assumptions or Simplifications:

  * Simplification: Only allow n x n grids for levels.

* Known Bugs:
  
  * Clicking on the "load" button to load a new level may cause all crabs to clear from the screen,
  even if a user decides to click cancel on the file chooser dialog.
  * When a block is removed in the authoring environment, it visually disappears on the view side 
  but is still present in the model/json. Hence, when the saved file is loaded, it begins to show
  the removed block again.

* Features implemented:
  
  * Level Editor: Users can create their own levels and save them to a file.
  * Level Building Help: Users can hover over buttons to get directions on building a level
  * Level Loader: Users can load levels from a file and edit them.
  * Level Player: Users can play levels.
  * GPT generation: Users can generate levels using GPT-3.5.
  * Leaderboard: Users can view the top 10 scores for each level.
  * Social Center: Users can view comments on each level, and reply to them.
  * Cheat Keys: Users can cheat to win or lose levels or reset a level.
  * Easter Egg: Users can turn Baba more girly with a bow and a skirt.
  * Artificial Player: Spawn crabs that move towards a controllable character and kill them.
  * [PUT MORE HERE]

* Features unimplemented:
  
  * Pull functionality that may have allowed a controllable character to pull blocks as opposed to
  always pushing them.

* Noteworthy Features:
  
    * Error handling in inputs for username creation during the gameplay, and for level creation
    during specifying the size of the grid.
    * Basic level validation to ensure that empty/incomplete levels are not saved
    * GPT generation: Users can generate levels using GPT-3.5.
    * Ability to play as a guest or as a user, which would control the access you have to saving 
    data to the leaderboard and social center.
    * Artificial player that moves towards a controllable character and kills them.

### Assignment Impressions

* This project was a great way to learn how to work with a large team and manage a large codebase.
* The project required good usage of data files to integrate between the model and view of both 
authoring and gameplay applications.
* [PUT MORE HERE]

    


