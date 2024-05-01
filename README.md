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
  * Can optionally load .slogo files in /data
  * However, a connection to the MongoDB database for leaderboard and social center functionality is
  hidden in .gitignore. As such, users will need to create their own MongoDB Atlas connection string
  with a database called "BabaData", and two collections called "data" and "comments".

* Interesting data files:
  * Tunnel Of The Crab King: an easy level that introduces opposing artificial players that may 
  kill Baba.
  * Who is You: A medium level that demonstrates the feature of crabs moving towards the closest controllable block to them rather than all crabs moving towards one controllable. 
  * [PUT MORE HERE]

* Key/Mouse inputs:

  * Arrow Keys: Up, Down, Left, Right to move controllable characters.
  * Cheat Keys:
    * `R`: Restart the level
    * `W`: Win the level
    * `L`: Lose the level
    * `E`: Spawn an enemy (crab)
    * `X`: Easter egg: Turn Baba more girly with a bow and a skirt!

### Notes/Assumptions

* Assumptions or Simplifications:

  * Simplification: Only allow n x n grids for levels.

* Known Bugs:
  
  * Clicking on the "load" button to load a new level may cause all crabs to clear from the screen,
  even if a user decides to click cancel on the file chooser dialog.

* Features implemented:
  
  * Level Editor: Users can create their own levels and save them to a file.
  * Level Loader: Users can load levels from a file.
  * Level Player: Users can play levels.
  * GPT generation: Users can generate levels using GPT-3.5.
  * Leaderboard: Users can view the top 10 scores for each level.
  * Social Center: Users can view comments on each level, and reply to them.
  * Cheat Keys: Users can cheat to win or lose levels or reset a level.
  * Easter Egg: Users can turn Baba more girly with a bow and a skirt.
  * Artificial Player: Spawn crabs that move towards their closest controllable character and kill them.
  * KeyPress Highlighter: shows which arrow key is being pressed as user plays the game
  * Loading and Saving: Users can load and save files in both the authoring environment and the game-player.

* Features unimplemented:
  
  * Pull functionality that may have allowed a controllable character to pull blocks as opposed to
  always pushing them.
  * A way to create new rules and blocks to give level creators more flexibility.

* Noteworthy Features:
  
    * Error handling in inputs for username creation during the gameplay, and for level creation
    during specifying the size of the grid.
    * GPT generation: Users can generate levels using GPT-3.5.
    * Ability to play as a guest or as a user, which would control the access you have to saving 
    data to the leaderboard and social center.
    * Artificial player that moves towards a controllable character and kills them.
    * Ability to control multiple elements on the grid simultaneously.
    * Dynamic rule updating in game. 
    * Ability to have multiple windows running the game.

### Assignment Impressions

* This project was a great way to learn how to work with a large team and manage a large codebase.
* This project was a way to challenge ourselves and acquire new technical and interpersonal skills along the way.
* It taught us to appreciate the synergy that comes from working together toward a common goal.
* It also taught us to embrace constructive criticism as an opportunity for growth and improvement, as depicted by changes we made from the feedback we go after demos.
* The project was also very helpful in being able to experience agile in a team and to see what it 
is like to work with multiple people using sprints.
* This project also reinforced the importance of setting realistic objectives and forcing yourself 
to meet your deadlines so that the team is not experiencing blockers.

    


