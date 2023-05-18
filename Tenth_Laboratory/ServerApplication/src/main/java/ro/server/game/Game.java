package ro.server.game;

import lombok.Getter;
import lombok.Setter;
import ro.server.exception.CellOccupiedException;

import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * Game of Gomoku (Five in a row)
 *
 * @author Alex Neagu
 * @see <a href="https://en.wikipedia.org/wiki/Gomoku">Gomoku</a>
 */
public class Game {
    private final List<Player> players;
    private final Board board;
    private final static int BLITZ_TIME = 200;
    @Getter
    @Setter
    private boolean running;
    @Getter
    private char currentTurn;

    public Game() {
        this.players = new ArrayList<>();
        this.board = new Board();
        this.running = false;
    }

    /**
     * Add a player to the game.
     * <p>
     * The game needs two players in order to start.
     *
     * @param player The newly inserted {@link Player}
     */
    public void addPlayer(Player player) {
        players.add(player);
        player.setGame(this);
        TimeKeeper playerClock = new TimeKeeper(BLITZ_TIME);
        playerClock.setDaemon(true);
        player.setClock(playerClock);
    }

    public boolean isAvailable() {
        return players.size() == 2;
    }

    public Player getGuestPlayer() {
        return players.size() == 2 ? players.get(1) : null;
    }

    public Player getHostPlayer() {
        return players.size() >= 1 ? players.get(0) : null;
    }

    /**
     * This method is used by both players as a synchronization point
     *
     * @return <tt>true</tt> if both ready are ready to start the game, <tt>false</tt> otherwise
     */
    public boolean canStart() {
        if (!isAvailable()) {
            return false;
        }
        for (Player player : players) {
            if (!player.isReady()) {
                return false;
            }
        }
        return true;
    }

    /**
     * Starts the game of Gomoku.
     * <p>
     * In this process, the board is being initialized and explicit messages are sent to both players.
     */
    public void start() {
        this.running = true;
        this.currentTurn = 'w';
        this.board.initializeBoard();

        generateColors();
        sendColorsToPlayers();

        for (Player player : players) {
            player.freezeClock();
            player.startClock();
        }
        sendRoundInformation();
    }

    /**
     * Sends the current state of the board and who has the turn to both players.
     */
    public void sendRoundInformation() {
        sendBoardStateToPlayers();
        sendTurnMessageToPlayers();
    }

    /**
     * Terminates the game if one of the players exceeded the time limit.
     */
    public void timeExceededFinish() {
        sendTimeExceededInformation();
        terminateGame();
    }

    /**
     * Terminates the game if one of the players won.
     */
    public void normalFinish() {
        sendBoardStateToPlayers();
        sendWinnerInformation();
        terminateGame();
    }

    /**
     * Disables the clocks of both players
     */
    private void terminateGame() {
        this.running = false;
        for (Player player : players) {
            if (player.getClock().isAlive()) {
                player.getClock().disableClock();
            }
            player.setGame(null);
        }
    }

    public boolean makeMove(int row, int col) throws CellOccupiedException {
        board.setCell(row, col, currentTurn);
        if (board.checkIfTokenWon(currentTurn)) {
            return true;
        }
        currentTurn = currentTurn == 'w' ? 'b' : 'w';
        return false;
    }

    private void sendTimeExceededInformation() {
        for (Player player : players) {
            if (player.getColor().equals(currentTurn)) {
                player.getOutputStream().println("Unfortunately, your clock time already passed and your opponent won! " +
                        "You have to be more cautious next time. If you want to play another game, you can join or create another room.");
            } else {
                player.getOutputStream().println("Your opponent has passed his time limit on the clock! Thus, you are the official winner of this match. "
                        + "If you want to play another game, you can join or create another room.");
            }
        }
    }

    private void sendWinnerInformation() {
        for (Player player : players) {
            if (player.getColor().equals(currentTurn)) {
                player.getOutputStream().println("Congratulations! You have won the game! If you want to play another game, " +
                        "you can join or create another room.");
            } else {
                player.getOutputStream().println("Your opponent has won the match! It was a tight round and you have " +
                        "fought until the end. If you want to play another game, you can join or create another room.");
            }
        }
    }

    /**
     * Generates the colors of the players randomly (either 'b' or 'r')
     */
    private void generateColors() {
        List<Character> availableColors = Arrays.asList('b', 'w');
        int index = (int) (Math.random() * 100000) % 2;
        players.get(0).setColor(availableColors.get(index));
        players.get(1).setColor(availableColors.get(1 - index));
    }

    private void sendColorsToPlayers() {
        for (Player player : players) {
            player.getOutputStream().println("The colors have been decided! You will move with the " + player.getColor() +
                    " token, while your adversary will move with the " + (player.getColor() == 'b' ? 'w' : 'b') + " token.");
        }
    }

    private void sendTurnMessageToPlayers() {
        for (Player player : players) {
            if (player.getColor().equals(currentTurn)) {
                player.getOutputStream().println("It's your turn! You can move your " + player.getColor() + " token " +
                        "on the board. You have " + player.getClock().getRemainingTime() + " seconds left on the clock!");
                player.resumeClock();
            } else {
                player.getOutputStream().println("It's you opponent's turn! You will be notified when he will make a move.");
                player.freezeClock();
            }
        }
    }

    private void sendBoardStateToPlayers() {
        for (Player player : players) {
            sendBoardToClient(player.getOutputStream());
        }
    }

    private void sendBoardToClient(PrintWriter outputStream) {
        outputStream.println("This is the current state of the board: ");
        for (int i = 0; i < Board.HEIGHT; ++i) {
            StringBuilder line = new StringBuilder();
            for (int j = 0; j < Board.WIDTH; ++j) {
                line.append(board.getStateOnCell(i, j));
                if (j < Board.WIDTH - 1) {
                    line.append(" ");
                }
            }
            outputStream.println(line);
        }
    }
}
