// Zayed Alshamsi
// CS 143
// HW Core Topics: 2D arrays, reading from a file, creating a class (fields, constructors, toString)
//
// This program creates a SudokuBoard object by reading a .sdk file and
// prints the resulting board to the screen.

public class PlaySudoku {

    // pre:  data1.sdk exists in the same folder as this program
    // post: prints the sudoku board
    public static void main(String[] args) {
        SudokuBoard board = new SudokuBoard("data1.sdk");
        System.out.println(board);
    }
}

/*  2 . . 1 . 5 . . 3
 . 5 4 . . . 7 1 .
 . 1 . 2 . 3 . 8 .
 6 . 2 8 . 7 3 . 4
 . . . . . . . . .
 1 . 5 3 . 9 8 . 6
 . 2 . 7 . 1 . 6 .
 . 8 1 . . . 2 4 .
 7 . . 4 . 2 . . 1
 
 
  ----jGRASP: Operation complete.
  */