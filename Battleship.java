/**
 * Program to play battleship against an AI opponent (or player 2?)
 * 
 * @author Scott M.
 * Battleship.java
 */

import java.util.Scanner;

public class Battleship {
    /**
     * Main method to run program.
     */
    public static void main(String[] args) {
        Scanner kb = new Scanner (System.in);
        int pBoats = 14, oBoats = 14, attackRow, attackColumn, turn = 1, orientation = 0;
        String pInput, lastAttack = "";
        boolean foundShip = false, streak = false, turnAround = false;
        
        //tutorial
        System.out.println("BATTLESHIP");
        System.out.println("Defeat your opponent's battleships before they can defeat yours.");
        System.out.println("Attacks are entered according to a coordinate grid.");
        System.out.println("You have 4 ships, with lengths 2, 3, 4, and 5.");
        System.out.println("Press ENTER to begin");
        System.out.println();
        kb.nextLine();
        
        //Create the boards, fill with zeros
        int[][] opponentBoard = new int[10][10];
        int[][] playerBoard = new int[10][10];
        char[][] opponentDisplay = new char[10][10];
        char[][] playerDisplay = new char[10][10];
        fill(opponentBoard, 0);
        fill(playerBoard, 0);
        fill(opponentDisplay, ' ');
        fill(playerDisplay, ' ');
        
        //place the opponent boats down
        placeBoats(opponentBoard);
        placeBoats(playerBoard);
        for (int i = 0; i < playerBoard.length; i++) {
            for (int j = 0; j < playerBoard[0].length; j++) {
                if (playerBoard[i][j] != 0) {
                    playerDisplay[i][j] = 'B';
                }
            }
        }
        
        //loop turns
        while (pBoats > 0 && oBoats > 0) {
            System.out.println("Turn " + turn + "\n");
            //print the player and opponent boards
            System.out.print("Player's Board:");
            for (int i = 0; i < 50; i++) {
                System.out.print(' ');
            }
            System.out.println("Opponent's Board:");
            printBoards(playerDisplay, opponentDisplay);
            System.out.println();
            
            //make attack input
            System.out.print("Enter the coordinate to attack (ex: B3): ");
            pInput = kb.nextLine().toUpperCase();
            if (pInput.length() != 2 || !Character.isLetter(pInput.charAt(0)) || !Character.isDigit(pInput.charAt(1))) {
                System.out.println("Error: invalid attack coordinates.");
                continue;
            }
            attackColumn = pInput.charAt(1) - '0';
            attackRow = pInput.charAt(0) - 'A';
            
            //execute attack
            if (attack(opponentBoard, attackRow, attackColumn)) {
                System.out.println("Player Hit!");
                opponentDisplay[attackRow][attackColumn] = 'X';
                opponentBoard[attackRow][attackColumn] = 0;
                oBoats--;
            }
            else {
                System.out.println("Player Miss.");
                opponentDisplay[attackRow][attackColumn] = 'O';
            }
            
            //make opponent attack choice
            lastAttack = comAttackChooseAdv(playerDisplay, foundShip, streak, turnAround, orientation);
            attackColumn = lastAttack.charAt(1) - '0';
            attackRow = lastAttack.charAt(0) - '0';
            orientation = lastAttack.charAt(2) - '0';
            
            //execute opponent attack
            if (attack(playerBoard, attackRow, attackColumn)) {
                System.out.println("Opponent Hit!");
                //replace last turn's attack 'X' with 'x';
                for (int i = 0; i < playerDisplay.length; i++) {
                    for (int j = 0; j < playerDisplay[0].length; j++) {
                        if (playerDisplay[i][j] == 'X') {
                            playerDisplay[i][j] = 'x';
                        }
                    }
                }
                playerDisplay[attackRow][attackColumn] = 'X';
                if (foundShip) {
                    streak = true;
                }
                foundShip = true;
                playerBoard[attackRow][attackColumn] = 0;
                pBoats--;
            }
            else {
                System.out.println("Opponent Miss.");
                if (playerDisplay[attackRow][attackColumn] != 'X' && playerDisplay[attackRow][attackColumn] != 'x') {
                    playerDisplay[attackRow][attackColumn] = 'O';
                }
                if (turnAround) {
                    turnAround = false;
                }
                if (streak) {
                    foundShip = false;
                    streak = false;
                    turnAround = true;
                    orientation = (orientation + 2) % 4;
                }
            }
            turn++;
            System.out.println();
            wait(1);
        }
        if (pBoats > oBoats) {
            System.out.print("Player wins");
        }
        else {
            System.out.print("Computer wins");
        }
    }
    /**
     * Method to fill an array entirely with one element.
     */
    public static void fill(int[][] array, int element) {
        for (int i = 0; i < array.length; i++) {
            for (int j = 0; j < array[0].length; j++) {
                array[i][j] = element;
            }
        }
    }
    /**
     * Method to fill a char array entirely with one element.
     */
    public static void fill(char[][] array, char element) {
        for (int i = 0; i < array.length; i++) {
            for (int j = 0; j < array[0].length; j++) {
                array[i][j] = element;
            }
        }
    }
    /**
     * Method to randomly place the 4 boats down (2, 3, 4, 5)
     */
    public static void placeBoats(int[][] array) {
        int row, column, orientation;
        
        for (int i = 5; i >= 2; i--) {
            while (true) {
                //randomize ship placement
                row = (int)(Math.random() * 10);
                column = (int)(Math.random() * 10);
                if (array[row][column] != 0) {
                    continue;
                }
                orientation = (int)(Math.random() * 2);
                //check ship fit
                if (!shipFit(array, i, row, column, orientation)) {
                    continue;
                }
                //place ships
                if (orientation == 0) {
                    //horz
                    for (int j = column; (j - column) < i; j++) {
                        array[row][j] = i;
                    }
                }
                else {
                    //vert
                    for (int k = row; (k - row) < i; k++) {
                        array[k][column] = i;
                    }
                }
                break;
            }
        }
    }
    /**
     * Method to check if a ship fits.
     */
    public static boolean shipFit(int[][] array, int shipLength, int row, int column, int orientation) {
        if (orientation == 0) {
            for (int i = column; (i - column) < shipLength; i++) {
                if (i < array[0].length && array[row][i] == 0) {
                    continue;
                }
                return false;
            }
            return true;
        }
        else {
            for (int j = row; (j - row) < shipLength; j++) {
                if (j < array.length && array[j][column] == 0) {
                    continue;
                }
                return false;
            }
            return true;
        }
    }
    /**
     * Method to display the player board.
     */
    public static void printBoard(char[][] array) {
        //print number row
        System.out.print("\t");
        for (int i = 0; i < array[0].length; i++) {
            System.out.printf("| %-2d", i);
        }
        System.out.println("|");
        //print line
        for (int i = 0; i < 49; i++) {
            System.out.print('-');
        }
        System.out.println();
        //print each line of the board separated by a line.
        for (int i = 0; i < array.length; i++) {
            System.out.print((char)(i + 'A') + "\t");
            for (int j = 0; j < array[0].length; j++) {
                System.out.print("| " + array[i][j] + " ");
            }
            System.out.println("|");
            for (int k = 0; k < 49; k++) {
                System.out.print('-');
            }
            System.out.println();
        }
    }
    /**
     * Method to execute an attack on an opponent.
     */
    public static boolean attack(int[][] board, int row, int column) {
        if (board[row][column] == 0) {
            return false;
        }
        else {
            return true;
        }
    }
    /**
     * Method to choose where the computer will attack.
     */
    public static String comAttackChoose(char[][] playerDisplay) {
        String attack;
        do {
            attack = "";
            attack += (int)(Math.random() * 10);
            attack += (int)(Math.random() * 10);
            //check if already used
        } while (playerDisplay[attack.charAt(0) - '0'][attack.charAt(1) - '0'] == 'O' || playerDisplay[attack.charAt(0) - '0'][attack.charAt(1) - '0'] == 'X');
        return attack;
    }
    /**
     * Method to choose computer attacks in a better way.
     */
    public static String comAttackChooseAdv(char[][] playerDisplay, boolean foundShip, boolean streak, boolean turnAround, int orientation) {
        int count = 0;
        out:
        while (true) {
            String attack, lastAttack;
            int row, column, reruns = 0;
            if (streak || turnAround) {
                attack = "";
                //attack adjacently to the last hit
                lastAttack = searchAttack(playerDisplay);
                row = lastAttack.charAt(0) - '0';
                column = lastAttack.charAt(1) - '0';
                do {
                    switch (orientation) {
                        case 0: column += 1;
                                break;
                        case 1: row += 1;
                                break;
                        case 2: column -= 1;
                                break;
                        case 3: row -= 1;
                    }
                    if ((row < 0 || row > 9 || column < 0 || column > 9) || playerDisplay[row][column] == 'O') {
                        if (turnAround) {
                            turnAround = false;
                            attack += comAttackChoose(playerDisplay);
                            attack += orientation;
                            return attack;
                        }
                        if (streak) {
                            turnAround = true;
                            streak = false;
                            orientation = (orientation + 2) % 4;
                            continue out;
                        }
                    }
                } while (playerDisplay[row][column] == 'X' || playerDisplay[row][column] == 'x');
                attack += row;
                attack += column;
                attack += orientation;
            }
            else if (foundShip) {
                do {
                    attack = "";
                    //attack adjacently to the last hit
                    lastAttack = searchAttack(playerDisplay);
                    row = lastAttack.charAt(0) - '0';
                    column = lastAttack.charAt(1) - '0';
                    orientation = (orientation < 3) ? orientation + 1 : 0;
                    switch (orientation) {
                        case 0: column += (column == 9) ? -1 : 1;
                                break;
                        case 1: row += (row == 9) ? -1 : 1;
                                break;
                        case 2: column += (column != 0) ? -1 : 1;
                                break;
                        case 3: row += (row != 0) ? -1 : 1;
                    }
                    if (count > 4) {
                        attack = comAttackChoose(playerDisplay);
                        attack += orientation;
                        break;
                    }
                    count++;
                } while (playerDisplay[row][column] == 'O' || playerDisplay[row][column] == 'X' || playerDisplay[row][column] == 'x');
                attack += row;
                attack += column;
                attack += orientation;
            }
            else {
                attack = comAttackChoose(playerDisplay);
                attack += orientation;
            }
            return attack;
        }
    }
    /**
     * Method to find the coords of the last successful attack on the player board.
     */
    public static String searchAttack(char[][] playerDisplay) {
        for (int i = 0; i < playerDisplay.length; i++) {
            for (int j = 0; j < playerDisplay[0].length; j++) {
                if (playerDisplay[i][j] == 'X') {
                    return "" + i + j;
                }
            }
        }
        return "00";
    }
    /**
     * Method to wait a certain amount of seconds.
     */
    public static void wait(int secs) {
        long startTime = System.currentTimeMillis(), totalTime;
        do {
            totalTime = System.currentTimeMillis();
        } while ((totalTime - startTime) / 1000 < secs);
    }
    /**
     * Method to display player and opponent boards.
     */
    public static void printBoards(char[][] array1, char[][] array2) {
        //print number rows
        printHeader(array1);
        System.out.print("\t\t");
        printHeader(array2);
        System.out.println();
        //print line
        printLineAcrossBoards();
        //print each line of the board separated by a line.
        for (int i = 0; i < array1.length; i++) {
            printRow(array1, i);
            System.out.print("\t\t");
            printRow(array2, i);
            System.out.println();
            printLineAcrossBoards();
        }
    }
    /**
     * Print the top row (header row) of a player board.
     */
    public static void printHeader(char[][] array) {
        System.out.print("\t");
        for (int i = 0; i < array[0].length; i++) {
            System.out.printf("| %-2d", i);
        }
        System.out.print("|");
    }
    /**
     * Print a line across both boards followed by line break
     */
    public static void printLineAcrossBoards() {
        printLine();
        System.out.print("\t\t");
        printLine();
        System.out.println();
    }
    /**
     * Print a line across the width of a board.
     */
    public static void printLine() {
        for (int i = 0; i < 49; i++) {
            System.out.print('-');
        }
    }
    /**
     * Print a row in a board.
     */
    public static void printRow(char[][] array, int rowIndex) {
        System.out.print((char)(rowIndex + 'A') + "\t");
        for (int j = 0; j < array[0].length; j++) {
            System.out.print("| " + array[rowIndex][j] + " ");
        }
        System.out.print("|");
    }
}