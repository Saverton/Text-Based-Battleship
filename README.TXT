PROJECT TITLE: Text-Based Battleship
PURPOSE OF PROJECT: My first attempt at creating a game within Java, testing my own abilities with Java.
VERSION or DATE: 12/21/2021 (created)
AUTHOR: Scott Meadows

HOW TO START THIS PROJECT: Program is text-based in the console. Navigate to the directory containing the BattleshipText.java file, and compile/run it using java. When run in the command prompt, text will guide the user on the basics of the game and how to interact with the program.

USER INSTRUCTIONS: Instructions for how to play are found in game. Avoid sending input until the game prompts the user to do so. This will not break anything, but may interfere with the display of the text that makes up the game. If unsure on the basic rules of 'Battleship', refer to "https://www.hasbro.com/common/instruct/battleship.pdf".

PROJECT DESCRIPTION: (THIS WAS CREATED BEFORE I KNEW HOW TO IMPLEMENT OBJECT-ORIENTED-PROGRAMMING). The game is controlled by a loop that displays the turn number, the player boards, followed by an input prompt for the player's attack coordinate. All this is done through ordered print statements and the boards are saved as 2d arrays of chars. When an attack is made, it checks the board position for a hit and updates accordingly, when all of one player's ships are destroyed, the game is finished and the player with remaining ships is declared the winner. 
	Each player begins with 4 ships with lengths of 2, 3, 4, and 5 spaces. Ships are placed randomly for each player on the board. The player can see his board in its entirety (including the location of the ships) whereas the opponent board does not display ship locations.
	The opponent has a somewhat simple AI algorithm that determines where it places its attacks. The algorithm chooses random locations until it has a successful hit. Following this hit, the algorithm checks all directly adjacent coordinates until it successfully hits another segment. The algorithm then proceeds to fire at coordinates extending in that direction until it misses. Then, it will attack in the opposite direction from the first hit location to knock out the entire ship.

MIT License

Copyright (c) 2022 Scott Meadows

Permission is hereby granted, free of charge, to any person obtaining a copy
of this software and associated documentation files (the "Software"), to deal
in the Software without restriction, including without limitation the rights
to use, copy, modify, merge, publish, distribute, sublicense, and/or sell
copies of the Software, and to permit persons to whom the Software is
furnished to do so, subject to the following conditions:

The above copyright notice and this permission notice shall be included in all
copies or substantial portions of the Software.

THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE
AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN THE
SOFTWARE.
	