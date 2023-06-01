package com.ro.server.game;

import com.ro.server.exception.CellOccupiedException;

public class Board {
    private final char[][] state;
    public static final int HEIGHT = 15;
    public static final int WIDTH = 15;
    public static final int TOKENS_IN_ROW = 2;

    public Board() {
        state = new char[HEIGHT][WIDTH];
    }

    public void initializeBoard() {
        for (int row = 0; row < HEIGHT; ++row) {
            for (int col = 0; col < WIDTH; ++col) {
                state[row][col] = '-';
            }
        }
    }

    public static boolean isOutOfBorder(int row, int col) {
        return row < 0 || col < 0 || row >= HEIGHT || col >= WIDTH;
    }

    private boolean checkRowWin(int row, int col, char token) {
        for (int delta = 0; delta < TOKENS_IN_ROW; ++delta) {
            if (isOutOfBorder(row + delta, col) || state[row + delta][col] != token) {
                return false;
            }
        }
        return true;
    }

    private boolean checkColWin(int row, int col, char token) {
        for (int delta = 0; delta < TOKENS_IN_ROW; ++delta) {
            if (isOutOfBorder(row, col + delta) || state[row][col + delta] != token) {
                return false;
            }
        }
        return true;
    }

    private boolean checkMainDiagWin(int row, int col, char token) {
        for (int delta = 0; delta < TOKENS_IN_ROW; ++delta) {
            if (isOutOfBorder(row + delta, col + delta) || state[row + delta][col + delta] != token) {
                return false;
            }
        }
        return true;
    }

    private boolean checkSecondaryDiagWin(int row, int col, char token) {
        for (int delta = 0; delta < TOKENS_IN_ROW; ++delta) {
            if (isOutOfBorder(row + delta, col + delta) || state[row + delta][col + delta] != token) {
                return false;
            }
        }
        return true;
    }

    public Character getStateOnCell(int row, int col) {
        return state[row][col];
    }

    public void setCell(int row, int col, char token) throws CellOccupiedException {
        if (state[row][col] != '-') {
            throw new CellOccupiedException(row, col, token);
        }
        state[row][col] = token;
    }

    public boolean checkIfTokenWon(char token) {
        for (int row = 0; row < HEIGHT; ++row) {
            for (int col = 0; col < WIDTH; ++col) {
                if (checkRowWin(row, col, token) || checkColWin(row, col, token)
                        || checkMainDiagWin(row, col, token) || checkSecondaryDiagWin(row, col, token)) {
                    return true;
                }
            }
        }
        return false;
    }
}
