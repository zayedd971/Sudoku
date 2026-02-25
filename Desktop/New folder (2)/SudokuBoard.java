// Zayed Alshamsi
// CS 143
// HW Core Topics: 2D arrays, reading from a file, creating a class (fields, constructors, toString),
//                 sets, maps, efficiency, boolean zen, recursive backtracking
//
// This program represents a 9x9 Sudoku board. It reads a .sdk file into a
// 2D char array (Part 1), prints the board via toString(), provides
// isValid() to check whether the board follows Sudoku rules and isSolved()
// to check whether the puzzle is fully and correctly completed (Part 2),
// and solve() which uses recursive backtracking to solve the puzzle (Part 3).

import java.io.File;
import java.io.FileNotFoundException;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Scanner;
import java.util.Set;

public class SudokuBoard {
    private char[][] board;

    // pre:  the given filename exists and contains a 9-line Sudoku board
    // post: constructs the board by reading characters from the file;
    //       prints an error message if the file is not found
    public SudokuBoard(String filename) {
        board = new char[9][9];
        try {
            Scanner input = new Scanner(new File(filename));
            for (int r = 0; r < 9; r++) {
                String line = input.nextLine();
                for (int c = 0; c < 9; c++) {
                    board[r][c] = line.charAt(c);
                }
            }
            input.close();
        } catch (FileNotFoundException e) {
            System.out.println("Error: file not found: " + filename);
        }
    }

    // post: returns true if every cell contains only a valid value
    //       ('1'-'9' or '.' for empty); uses a HashSet of valid chars
    //       for O(1) lookup per cell, making the overall check O(n)
    private boolean hasValidData() {
        Set<Character> validChars = new HashSet<>();
        validChars.add('.');
        for (char ch = '1'; ch <= '9'; ch++) {
            validChars.add(ch);
        }
        for (int r = 0; r < 9; r++) {
            for (int c = 0; c < 9; c++) {
                if (!validChars.contains(board[r][c])) {
                    return false;
                }
            }
        }
        return true;
    }

    // post: returns true if no row contains a duplicate digit (1-9);
    //       multiple '.' (empty) values in a row are allowed
    private boolean rowsValid() {
        for (int r = 0; r < 9; r++) {
            Set<Character> seen = new HashSet<>();
            for (int c = 0; c < 9; c++) {
                char val = board[r][c];
                if (val != '.' && !seen.add(val)) {
                    return false;
                }
            }
        }
        return true;
    }

    // post: returns true if no column contains a duplicate digit (1-9);
    //       multiple '.' (empty) values in a column are allowed
    private boolean colsValid() {
        for (int c = 0; c < 9; c++) {
            Set<Character> seen = new HashSet<>();
            for (int r = 0; r < 9; r++) {
                char val = board[r][c];
                if (val != '.' && !seen.add(val)) {
                    return false;
                }
            }
        }
        return true;
    }

    // post: returns true if no 3x3 mini-square contains a duplicate digit (1-9);
    //       multiple '.' (empty) values in a mini-square are allowed
    private boolean miniSquaresValid() {
        for (int spot = 1; spot <= 9; spot++) {
            char[][] mini = miniSquare(spot);
            Set<Character> seen = new HashSet<>();
            for (int r = 0; r < 3; r++) {
                for (int c = 0; c < 3; c++) {
                    char val = mini[r][c];
                    if (val != '.' && !seen.add(val)) {
                        return false;
                    }
                }
            }
        }
        return true;
    }

    // post: returns true if the board has valid data AND no row, column,
    //       or mini-square contains a duplicate digit; an incomplete board
    //       (with '.' empty cells) can still return true
    public boolean isValid() {
        return hasValidData() && rowsValid() && colsValid() && miniSquaresValid();
    }

    // post: returns true if the board passes isValid() AND every digit
    //       '1'-'9' appears exactly 9 times 
    public boolean isSolved() {
        if (!isValid()) {
            return false;
        }
        Map<Character, Integer> counts = new HashMap<>();
        for (int r = 0; r < 9; r++) {
            for (int c = 0; c < 9; c++) {
                char val = board[r][c];
                counts.put(val, counts.getOrDefault(val, 0) + 1);
            }
        }
        for (char num = '1'; num <= '9'; num++) {
            if (counts.getOrDefault(num, 0) != 9) {
                return false;
            }
        }
        return true;
    }

    // pre:  spot is an integer 1-9 
    // post: returns the 3x3 mini-square at that position as a new 2D char array
    private char[][] miniSquare(int spot) {
        char[][] mini = new char[3][3];
        for (int r = 0; r < 3; r++) {
            for (int c = 0; c < 3; c++) {
                mini[r][c] = board[(spot - 1) / 3 * 3 + r][(spot - 1) % 3 * 3 + c];
            }
        }
        return mini;
    }

    // post: attempts to solve the board using recursive backtracking.
    //       returns false immediately if the board is invalid.
    //       returns true immediately if the board is already solved.
    //       otherwise, tries digits '1'-'9' in each empty cell,
    //       recursing after each attempt; undoes the attempt if it
    //       does not lead to a solution. returns true if solved,
    //       false if no solution exists.
    public boolean solve() {
        if (!isValid()) {
            return false;
        }
        if (isSolved()) {
            return true;
        }
        for (int r = 0; r < 9; r++) {
            for (int c = 0; c < 9; c++) {
                if (board[r][c] == '.') {
                    for (char num = '1'; num <= '9'; num++) {
                        board[r][c] = num;         // try
                        if (solve()) {
                            return true;           // recurse - solution found
                        }
                        board[r][c] = '.';         // undo
                    }
                    return false;                  // no digit worked, backtrack
                }
            }
        }
        return false;
    }

    // post: returns a string representation of the 9x9 board,
    //       with values separated by spaces and rows on new lines
    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        for (int r = 0; r < 9; r++) {
            for (int c = 0; c < 9; c++) {
                sb.append(board[r][c]);
                if (c < 8) sb.append(" ");
            }
            sb.append("\n");
        }
        return sb.toString();
    }
}

/*
Paste the output from running SudokuSolverEngine here.
*/