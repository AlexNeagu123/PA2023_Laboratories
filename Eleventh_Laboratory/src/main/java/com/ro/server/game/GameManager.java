package com.ro.server.game;

import com.ro.server.exception.NoAvailableGamesException;

import java.util.LinkedList;
import java.util.Queue;

/**
 * The <tt>Game Manager</tt> singleton manipulates a global Queue of {@link Game} instances, where there is only one
 * player connected.
 *
 * @author Alex Neagu
 */
public class GameManager {
    private static GameManager instance;
    private final Queue<Game> gamesInQueue;

    private GameManager() {
        gamesInQueue = new LinkedList<>();
    }

    synchronized public void addGame(Game game) {
        gamesInQueue.add(game);
    }

    synchronized public Game getGame() throws NoAvailableGamesException {
        if (gamesInQueue.isEmpty()) {
            throw new NoAvailableGamesException();
        }
        return gamesInQueue.poll();
    }

    public static GameManager getInstance() {
        if (instance == null) {
            instance = new GameManager();
        }
        return instance;
    }
}
