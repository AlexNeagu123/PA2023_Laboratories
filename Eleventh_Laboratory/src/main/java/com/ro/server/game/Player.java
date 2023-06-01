package com.ro.server.game;

import lombok.Data;
import lombok.EqualsAndHashCode;

import java.io.BufferedReader;
import java.io.PrintWriter;

/**
 * A player of the Gomoku game.
 * <p>
 * This class is strongly connected to the {@link Game} class. Valuable information of the human client who plays the game
 * are stored in this class.
 *
 * @author Alex Neagu
 */
@Data
@EqualsAndHashCode
public class Player {
    private String name;
    private Character color;
    private Long id;

    @EqualsAndHashCode.Exclude
    private TimeKeeper clock;

    @EqualsAndHashCode.Exclude
    private final BufferedReader inputStream;
    @EqualsAndHashCode.Exclude
    private final PrintWriter outputStream;
    @EqualsAndHashCode.Exclude
    private Game game;
    @EqualsAndHashCode.Exclude
    private boolean ready = false;

    public Player(BufferedReader inputStream, PrintWriter outputStream) {
        this.inputStream = inputStream;
        this.outputStream = outputStream;
    }

    public void startClock() {
        this.clock.start();
    }

    public void freezeClock() {
        this.clock.freezeClock();
    }

    public void resumeClock() {
        this.clock.resumeClock();
    }

    public boolean inGame() {
        return game != null && game.isRunning();
    }
}
