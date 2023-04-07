package com.game;

import com.exceptions.NoLinesInGame;
import com.geometry.Line;
import com.geometry.Triangle;
import com.utils.GameUtils;
import com.utils.TriangleStatus;
import lombok.Getter;

import java.awt.*;
import java.io.Serializable;
import java.util.List;
import java.util.*;

/**
 * The <tt>Player</tt> class represents a player from a certain {@link Game} instance.
 */
@Getter
public class Player implements Serializable {
    Color color;
    Set<Line> occupiedLines;
    Set<Line> freeLines;
    final Game game;

    /**
     * @param color The {@link Color} that this player will have during the game
     * @param game  The {@link Game} in which this player participates
     */
    public Player(Color color, Game game) {
        this.color = color;
        this.game = game;
        this.occupiedLines = new HashSet<>();
        this.freeLines = game.getAllLines();
    }

    void addLine(Line line) {
        occupiedLines.add(line);
        freeLines.remove(line);
    }

    /**
     * Notifies this player that the opponent has colored a specific line.
     *
     * @param line The {@link Line} colored by the opponent
     */
    void notifyOpponentLine(Line line) {
        freeLines.remove(line);
    }

    boolean checkIfLineOccupied(Line line) {
        return occupiedLines.contains(line);
    }

    /**
     * Analyses the lines of a given {@link Triangle} and creates some {@link TriangleStatus} statistics based on it.
     *
     * @param triangle The <tt>Triangle</tt> that is currently analysed
     * @return The {@link TriangleStatus} object that contains relevant information about this triangle
     */
    TriangleStatus getTriangleLineStatus(Triangle triangle) {
        TriangleStatus triangleStatus = new TriangleStatus();
        for (Line line : triangle.getLines()) {
            if (!game.allLines.contains(line)) {
                triangleStatus.nonExistentLines++;
                continue;
            }
            if (occupiedLines.contains(line)) {
                triangleStatus.ownLines++;
            } else if (!freeLines.contains(line))
                triangleStatus.opponentLines++;
            else {
                triangleStatus.freeLines.add(line);
            }
        }
        return triangleStatus;
    }

    /**
     * Based on the {@link TriangleStatus} of a certain triangle, determines if this triangle contains a line that, if ignored,
     * might be colored by the opponent and make him win the game
     *
     * @param triangle A certain {@link Triangle}
     * @return Either the described line or <tt>null</tt> if the described line doesn't exist
     */
    Optional<Line> getLineInDanger(Triangle triangle) {
        Line lineInDanger = null;
        TriangleStatus triangleStatus = getTriangleLineStatus(triangle);
        if (triangleStatus.opponentLines == 2 && triangleStatus.ownLines == 0
                && triangleStatus.freeLines.size() > 0) {
            // only one free line is expected
            lineInDanger = triangleStatus.freeLines.get(0);
        }
        return Optional.ofNullable(lineInDanger);
    }

    /**
     * Based on the {@link TriangleStatus} of a certain triangle, determines if this triangle contains a line that, if colored,
     * will make the current player win the game.
     *
     * @param triangle A certain {@link Triangle}
     * @return Either the described line or <tt>null</tt> if the described line doesn't exist
     */
    Optional<Line> getLineToWin(Triangle triangle) {
        Line lineToWin = null;
        TriangleStatus triangleStatus = getTriangleLineStatus(triangle);
        if (triangleStatus.opponentLines == 0 && triangleStatus.ownLines == 2
                && triangleStatus.freeLines.size() > 0) {
            // only one free line is expected
            lineToWin = triangleStatus.freeLines.get(0);
        }
        return Optional.ofNullable(lineToWin);
    }

    /**
     * Based on the {@link TriangleStatus} of a certain triangle, determines if this triangle contains a line that, if colored,
     * will lead to progress in the current player's situation in the game.
     *
     * @param triangle A certain {@link Triangle}
     * @return Either the described line or <tt>null</tt> if the described line doesn't exist
     */
    Optional<Line> getProgressLine(Triangle triangle) {
        Line progressLine = null;
        TriangleStatus triangleStatus = getTriangleLineStatus(triangle);
        if (triangleStatus.ownLines > 1 && triangleStatus.freeLines.size() > 0 && triangleStatus.nonExistentLines == 0) {
            progressLine = GameUtils.getRandomLine(triangleStatus.freeLines);
        }
        return Optional.ofNullable(progressLine);
    }

    /**
     * Analyses all the possible triangles and extracts different type of lines according to their priority:
     * win lines, danger lines, progress lines or randomly chosen lines.
     *
     * @return A line with the highest priority. If multiple lines with the same highest priority exists, a random line between them returned.
     * @throws NoLinesInGame If there are no available lines left in the game
     */
    Line getBestLineToColor() throws NoLinesInGame {
        List<Line> dangerLines = new ArrayList<>();
        List<Line> winLines = new ArrayList<>();
        List<Line> progressLines = new ArrayList<>();
        for (int i = 0; i < game.pointCount; ++i) {
            for (int j = i + 1; j < game.pointCount; ++j) {
                for (int k = j + 1; k < game.pointCount; ++k) {
                    Triangle currentTriangle = new Triangle(game.circle.get(i), game.circle.get(j), game.circle.get(k));
                    if (!game.isValidTriangle(currentTriangle)) {
                        continue;
                    }
                    getLineToWin(currentTriangle).ifPresent(winLines::add);
                    getLineInDanger(currentTriangle).ifPresent(dangerLines::add);
                    getProgressLine(currentTriangle).ifPresent(progressLines::add);
                }
            }
        }
        if (!winLines.isEmpty()) {
            return GameUtils.getRandomLine(winLines);
        }
        if (!dangerLines.isEmpty()) {
            return GameUtils.getRandomLine(dangerLines);
        }
        if (!progressLines.isEmpty()) {
            return GameUtils.getRandomLine(progressLines);
        }
        if (!freeLines.isEmpty()) {
            return GameUtils.getRandomLine(new ArrayList<>(freeLines));
        }
        throw new NoLinesInGame();
    }

    boolean isColoredTriangle(Triangle triangle) {
        for (Line line : triangle.getLines()) {
            if (!occupiedLines.contains(line) && !occupiedLines.contains(line.reversedLine())) {
                return false;
            }
        }
        return true;
    }

    /**
     * Checks if this player colored lines so far in such a way that a {@link Triangle} was obtained.
     *
     * @return The {@link Triangle} colored by this player or <tt>null</tt> if it doesn't exist
     */
    Optional<Triangle> getTriangle() {
        Triangle triangle = null;
        searchTriangle:
        for (int i = 0; i < game.pointCount; ++i) {
            for (int j = i + 1; j < game.pointCount; ++j) {
                for (int k = j + 1; k < game.pointCount; ++k) {
                    Triangle currentTriangle = new Triangle(game.circle.get(i), game.circle.get(j), game.circle.get(k));
                    if (isColoredTriangle(currentTriangle)) {
                        triangle = currentTriangle;
                        break searchTriangle;
                    }
                }
            }
        }
        return Optional.ofNullable(triangle);
    }
}
