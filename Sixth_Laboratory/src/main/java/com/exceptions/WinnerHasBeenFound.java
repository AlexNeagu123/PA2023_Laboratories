package com.exceptions;

import com.geometry.Triangle;
import lombok.Getter;

public class WinnerHasBeenFound extends Exception {
    @Getter
    private final Triangle triangle;

    public WinnerHasBeenFound(Triangle triangle) {
        super("No point to continue the game, the winner has been found!");
        this.triangle = triangle;
    }
}
