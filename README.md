# Overview 

A match 3 game created for a CS project in a mobile application course. The source code for the game is written 
in Kotlin. Since the majority of the development time for the project went into creating the sprites and algorithm
for finding matches, some mechanics of a match 3 game were not fully implemented. <br/>

When opening the app, there will be 3 buttons on the homescreen.
The 'start game' button will begin the game.
The 'credit' button will lists the names of the people/tools that contributed to the project.
The 'how to play' button will explain the rules of the game.

<img width="452" height="723" alt="Screenshot 2025-08-10 235652" src="https://github.com/user-attachments/assets/e54d668b-18e0-4b3b-9621-6c4c40a8b918" />)

## Gameplay
The game has the matching mechanic working along with updating the score whenever a match is made.
The number of turns remaining will decrement by one whenever user moves a piece on the board, however any movement
outside the grid will also count as a move needs to be addressed. 

<img width="418" height="708" alt="Screenshot 2025-08-10 235734" src="https://github.com/user-attachments/assets/79e7afb4-46af-4da4-ad35-7b2d2a5d613b" />
<img width="418" height="708" alt="Screenshot 2025-08-10 235759" src="https://github.com/user-attachments/assets/fbbf775a-1c45-4581-ad1e-1b28193cf2b3" />

<br/>It also need to be noted that the game currently does not generate new orbs to replace the matched ones nor does it
move existing orbs down on the grid to fill in any empty spaces./
When the number of turns reaches 0, the game will be over and a menu will pop up prompting the user to quit or replay the game. 

## What needs to be improved
As stated above, the project is far from complete to be considered a proper match 3 game like that of Candy Crush.
Since this was a final project for a CS course, time was limited to approximately 3 weeks. The missing features that needs to be implemented/fixed are the following:

1.Move existing orbs down on the grid to fill in any matches that were made\
2.Generate new orbs to fill in empty spots\
3.Add a pause menu to resume, retry, or quit\
4.A move will only count (decrement turns) if a match is made\

Some QOL improvments that could be added to the game:

1.Proper animation when swapping the pieces\
2.SFX when pieces match\
3.Levels/difficulties\
4.Fever bars or obstacles on grid to make gameplay more engaging\
5.Settings option to adjust volume for sfx, bgm


