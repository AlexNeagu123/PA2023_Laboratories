package com.game;

import com.exceptions.InvalidEndOfTurn;
import com.exceptions.NoLinesInGame;
import com.exceptions.WinnerHasBeenFound;
import com.geometry.Line;
import com.geometry.Point;
import com.geometry.Triangle;
import com.utils.GameUtils;
import lombok.AccessLevel;
import lombok.Getter;

import java.awt.*;
import java.io.Serializable;
import java.util.*;
import java.util.List;

/**
 * The <tt>Game</tt> class represents a game played by two players, where a number of points and certain lines between them are given.
 * <p>
 * In each turn, a player may choose a certain line and color it in a specific color (blue or red). The goal of each player
 * is to form a triangle, where each line is colored entirely by him.
 * <p>
 * The points are given in cartesian form and are placed on an 800 x 600 plane. All the points are arranged in a circular way.
 * <p>
 * A <tt>Line</tt> may connect two distinct points with a given probability. An initial build procedure is called and some random
 * build process of the lines takes place.
 * <p>
 * This class provides useful methods for the {@link com.gui.DrawingPanel} container of the GUI.
 */
@Getter
public class Game implements Serializable {
    @Getter(AccessLevel.NONE)
    private final Player[] players;
    private int turn;
    final int pointCount;
    final List<Point> circle;
    @Getter(AccessLevel.NONE)
    final Set<Line> allLines;
    public final static int W = 800, H = 600;
    public static final int PLAYERS_NUM = 2;
    public final boolean AI_GAME;

    /**
     * @param pointCount      The number of cartesian points in the game
     * @param edgeProbability The probability that a certain line between two fixed points exists
     * @param AI_GAME         is <tt>true</tt> if one player on the game is human and the other is AI, and <tt>false</tt> otherwise
     */
    public Game(int pointCount, double edgeProbability, boolean AI_GAME) {
        this.AI_GAME = AI_GAME;
        this.turn = 0;
        this.pointCount = pointCount;

        circle = new ArrayList<>();
        allLines = new HashSet<>();
        buildCircle();
        buildLines(edgeProbability);

        this.players = new Player[PLAYERS_NUM];
        this.players[0] = new Player(Color.BLUE, this);
        this.players[1] = new Player(Color.RED, this);
    }

    public Set<Line> getAllLines() {
        return new HashSet<>(allLines);
    }

    /**
     * Iterates through every pair of distinct points and adds a given line between them with probability {@code edgeProbability}
     *
     * @param edgeProbability The probability of adding a line between two fixed points
     */
    private void buildLines(double edgeProbability) {
        for (int i = 0; i < pointCount; ++i) {
            for (int j = i + 1; j < pointCount; ++j) {
                if (Math.random() >= edgeProbability) {
                    continue;
                }
                final Point firstPoint = circle.get(i);
                final Point secondPoint = circle.get(j);
                allLines.add(new Line(firstPoint, secondPoint));
            }
        }
    }

    /**
     * Creates n points in a cartesian 800 x 600 plane, arranged in a circular way
     */
    private void buildCircle() {
        int x0 = W / 2;
        int y0 = H / 2;
        int radius = H / 2 - 10;
        double alpha = 2 * Math.PI / pointCount;
        for (int i = 0; i < pointCount; i++) {
            circle.add(new Point(x0 + (int) (radius * Math.cos(alpha * i)), y0 + (int) (radius * Math.sin(alpha * i))));
        }
    }

    /**
     * Given a certain point in the cartesian plane, determines the closest line to this point.
     * <p>
     * The <tt>Cross product</tt> operation from linear algebra is used.
     *
     * @param point A specified {@link Point} in the cartesian plane
     * @return The closest {@link Line} to the specified point
     * @throws NoLinesInGame If there are no longer available lines to color in the game
     */
    public Line getClosestLine(Point point) throws NoLinesInGame {
        return GameUtils.getClosestLine(point, allLines);
    }

    /**
     * @param line A specified {@link Line} that was chosen by a player
     * @return The {@link Color} that corresponds to the player that has the turn
     * @throws InvalidEndOfTurn If the specified line is already occupied, thus the player made an invalid move
     */
    public Color getTurnColor(Line line) throws InvalidEndOfTurn {
        if (players[turn].checkIfLineOccupied(line)) {
            throw new InvalidEndOfTurn();
        }
        if (players[1 - turn].checkIfLineOccupied(line)) {
            throw new InvalidEndOfTurn();
        }
        return players[turn].getColor();
    }

    /**
     * This method analyses the current state of the game and returns the "best possible choice" according to some greedy heuristics
     * with randomization
     *
     * @return A {@link Line} that seems to be the best choice at the moment
     * @throws NoLinesInGame If there are no current available lines in the game
     */
    public Line getBestLineForTurn() throws NoLinesInGame {
        return players[turn].getBestLineToColor();
    }

    /**
     * Adds a line to the current player's set of occupied lines and checks if he won the game by forming a triangle of his color
     *
     * @param line The line drawn by the current player
     * @throws WinnerHasBeenFound If, after this move, the current player won the game
     */
    public void addLineAndCheckWinner(Line line) throws WinnerHasBeenFound {
        players[turn].addLine(line);
        players[1 - turn].notifyOpponentLine(line);
        Optional<Triangle> triangle = players[turn].getTriangle();
        if (triangle.isPresent()) {
            throw new WinnerHasBeenFound(triangle.get());
        }
        turn = 1 - turn;
    }

    /**
     * @param playerId Either 0 or 1, depending on which player we want to choose
     * @return The color of the player with the specified id
     */
    public Color getColorByPlayerId(int playerId) {
        return players[playerId].color;
    }

    /**
     * @param playerId Either 0 or 1, depending on which player we want to choose
     * @return All the lines occupied by the player with the specified id
     */
    public List<Line> getLinesByPlayerId(int playerId) {
        return new ArrayList<>(players[playerId].occupiedLines);
    }

    /**
     * @param triangle A specified {@link Triangle}
     * @return <tt>If all the lines of the {@link Triangle} exists in the game and false otherwise</tt>
     */
    boolean isValidTriangle(Triangle triangle) {
        for (Line line : triangle.getLines()) {
            if (!allLines.contains(line) && !allLines.contains(line.reversedLine())) {
                return false;
            }
        }
        return true;
    }
}
