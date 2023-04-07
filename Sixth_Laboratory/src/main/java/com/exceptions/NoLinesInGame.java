package com.exceptions;

public class NoLinesInGame extends Exception {
    public NoLinesInGame() {
        super("There are no lines left to color in the canvas..");
    }
}
