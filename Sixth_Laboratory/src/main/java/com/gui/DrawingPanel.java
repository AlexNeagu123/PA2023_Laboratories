package com.gui;

import com.exceptions.InvalidEndOfTurn;
import com.exceptions.NoLinesInGame;
import com.exceptions.WinnerHasBeenFound;
import com.geometry.Line;
import com.geometry.Point;
import com.geometry.Triangle;
import com.utils.GameUtils;
import lombok.extern.log4j.Log4j2;

import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.image.BufferedImage;

/**
 * The <tt>DrawingPanel</tt> class is responsible for the functionality of the canvas container, where all the lines are drawn by the players.
 * <p>
 * A 800x600 canvas in the form of a {@link BufferedImage} is created on the screen.
 * <p>
 * Several methods for drawing lines are implemented in this class.
 * <p>
 * If the game is being played as Human vs Human, the lines are drawn one at a time, with the specific color of the player who currently takes the decision
 * <p>
 * If the game is being played as Human vs AI, the lines are drawn two at a time, one chosen by the human player and the other automatically
 * chosen by the AI.
 */
@Log4j2
public class DrawingPanel extends JPanel {
    final MainFrame frame;
    private boolean frozenCanvas = false;
    BufferedImage image;
    Graphics2D graphics;
    final static int W = 800, H = 600;

    public DrawingPanel(MainFrame frame) {
        this.frame = frame;
        initPanel();
        createBoard();
    }

    public void initPanel() {
        createOffscreenImage();
        setPreferredSize(new Dimension(W, H));
        setBorder(BorderFactory.createEtchedBorder());
        this.addMouseListener(new MouseAdapter() {
            @Override
            public void mousePressed(MouseEvent e) {
                if (frozenCanvas) {
                    return;
                }
                processCanvasPress(e.getX(), e.getY());
            }
        });
    }

    public final void createBoard() {
        drawVertices();
        drawInitialLines();
        drawOccupiedLines();
        repaint();
    }

    private void processCanvasPress(int x, int y) {
        try {
            Point point = new Point(x, y);
            updateGameState(point);
        } catch (InvalidEndOfTurn invalidTurn) {
            log.error(invalidTurn.getMessage());
        } catch (WinnerHasBeenFound winnerFound) {
            drawWinnersTriangle(winnerFound.getTriangle());
            frozenCanvas = true;
        } catch (NoLinesInGame nolLines) {
            log.error(nolLines.getMessage());
            frozenCanvas = true;
        }
    }

    public void drawWinnersTriangle(Triangle triangle) {
        graphics.setColor(Color.GREEN);
        for (Line line : triangle.getLines()) {
            graphics.drawLine(line.getX().getX(), line.getX().getY(), line.getY().getX(), line.getY().getY());
        }
        repaint();
    }

    private void updateGameState(Point point) throws NoLinesInGame, InvalidEndOfTurn, WinnerHasBeenFound {
        updateLineByPoint(point);
        if (frame.game.AI_GAME) {
            updateLineByAIDecision();
        }
    }

    private void createOffscreenImage() {
        image = new BufferedImage(W, H, BufferedImage.TYPE_INT_ARGB);
        graphics = image.createGraphics();
        graphics.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
        graphics.setColor(Color.WHITE);
        graphics.fillRect(0, 0, 800, 600);
    }

    private void drawInitialLines() {
        graphics.setColor(Color.BLACK);
        for (Line line : frame.game.getAllLines()) {
            final Point firstPoint = line.getX();
            final Point secondPoint = line.getY();
            graphics.drawLine(firstPoint.getX(), firstPoint.getY(), secondPoint.getX(), secondPoint.getY());
        }
    }

    private void drawVertices() {
        graphics.setColor(Color.BLACK);
        for (Point point : frame.game.getCircle()) {
            graphics.fillOval(point.getX() - 5, point.getY() - 5, 10, 10);
        }
    }

    /**
     * This method is useful when a saved state (in a .gga file) of a game is being loaded.
     * It extracts from that {@link com.game.Game} instance all the lines that were drawn by both players
     * and recreates them on the screen.
     */
    void drawOccupiedLines() {
        for (int playerId = 0; playerId < 2; ++playerId) {
            Color color = frame.game.getColorByPlayerId(playerId);
            graphics.setColor(color);
            for (Line line : frame.game.getLinesByPlayerId(playerId)) {
                final Point firstPoint = line.getX();
                final Point secondPoint = line.getY();
                graphics.drawLine(firstPoint.getX(), firstPoint.getY(), secondPoint.getX(), secondPoint.getY());
            }
        }
    }

    /**
     * Given a cartesian point chosen by a Human Player (by clicking on it on the canvas), determines the closest line
     * to it and colors it in the player's corresponding color.
     *
     * @param point The cartesian point clicked by the Human Player
     * @throws WinnerHasBeenFound If, after updating the current player's state, he wins the game
     * @throws InvalidEndOfTurn   If the closest line to the chosen point is already colored
     * @throws NoLinesInGame      If there are no lines left in the game to color
     */
    private void updateLineByPoint(Point point) throws WinnerHasBeenFound, InvalidEndOfTurn, NoLinesInGame {
        Line line = frame.game.getClosestLine(point);
        Color turnColor = frame.game.getTurnColor(line);
        graphics.setColor(turnColor);
        graphics.drawLine(line.getX().getX(), line.getX().getY(), line.getY().getX(), line.getY().getY());
        repaint();
        frame.game.addLineAndCheckWinner(line);
    }

    /**
     * Extracts the best possible line chosen the AI Player and colors it in the player's corresponding color.
     *
     * @throws WinnerHasBeenFound If, after updating the current player's state, he wins the game
     * @throws InvalidEndOfTurn   If the closest line to the chosen point is already colored
     * @throws NoLinesInGame      If there are no lines left in the game to color
     */
    private void updateLineByAIDecision() throws WinnerHasBeenFound, InvalidEndOfTurn, NoLinesInGame {
        Line line = frame.game.getBestLineForTurn();
        Color turnColor = frame.game.getTurnColor(line);
        graphics.setColor(turnColor);
        graphics.drawLine(line.getX().getX(), line.getX().getY(), line.getY().getX(), line.getY().getY());
        repaint();
        frame.game.addLineAndCheckWinner(line);
    }

    @Override
    protected void paintComponent(Graphics graphics) {
        graphics.drawImage(image, 0, 0, this);
    }

}
