package com.gui;

import com.game.Game;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * The <tt>ConfigPanel</tt> class is responsible for the layout and functionality of the configuration components.
 * For instance, a spinner where the number of points (between 3 and 50) is set, a ComboBox where the probability of
 * existence for each line is chosen, two play buttons (one for Human vs Human and the other for Human vs AI) are all managed
 * by this class.
 */
public class ConfigPanel extends JPanel {
    final MainFrame frame;
    JLabel dotsLabel, linesLabel;
    JSpinner dotsSpinner;
    JComboBox<Double> linesCombo;
    JButton humanPlayButton;
    JButton AIPlayButton;

    public ConfigPanel(MainFrame frame) {
        this.frame = frame;
        initializeConfig();
    }

    /**
     * Extracts the number of points from the spinner and the edge probability from the combo box.
     * <p>
     * Creates the coordinates for a set of cartesian points arranged in circular order.
     * <p>
     * Creates the lines between points respecting the <tt>edgeProbability</tt>.
     *
     * @param AI_GAME <tt>true</tt> if the Human vs AI button was pressed and <tt>false</tt> otherwise
     */
    void initializeGame(boolean AI_GAME) {
        int pointNumber = (Integer) frame.configPanel.dotsSpinner.getValue();
        double edgeProbability = (Double) frame.configPanel.linesCombo.getSelectedItem();
        frame.game = new Game(pointNumber, edgeProbability, AI_GAME);
        frame.remove(frame.canvas);
        frame.canvas = new DrawingPanel(frame);
        frame.add(frame.canvas, BorderLayout.CENTER);
        frame.pack();
    }

    /**
     * Clears the current <tt>canvas</tt> component from the frame, instantiate a new one, and recreates the state of a
     * given <tt>Game</tt> instance
     *
     * @param game The game instance that will be recreated on the screen
     */
    void initializeGame(Game game) {
        frame.game = game;
        frame.remove(frame.canvas);
        frame.canvas = new DrawingPanel(frame);
        frame.add(frame.canvas, BorderLayout.CENTER);
        frame.pack();
    }

    /**
     * The action listener for the "Play with friend" button that initializes a new instance of the game on the screen
     */
    void initializeNormalGame(ActionEvent actionEvent) {
        initializeGame(false);
    }

    /**
     * The action listener for the "Play with AI" button that initializes a new instance of the game on the screen
     */
    void initializeAIGame(ActionEvent actionEvent) {
        initializeGame(true);
    }

    private void initializeConfig() {
        initializeDotsSpinner();
        initializeLinesCombo();
        initializeHumanPlayButton();
        initializeAIPlayButton();
    }

    void initializeDotsSpinner() {
        dotsLabel = new JLabel("Number of dots:");
        dotsSpinner = new JSpinner(new SpinnerNumberModel(6, 3, 50, 1));
        add(dotsLabel);
        add(dotsSpinner);
    }

    void initializeLinesCombo() {
        linesLabel = new JLabel("Line probability: ");
        linesCombo = new JComboBox<>();
        List<Double> linesComboOptions = new ArrayList<>(Arrays.asList(1.0, 0.75, 0.50, 0.25, 0.10));
        for (Double option : linesComboOptions) {
            linesCombo.addItem(option);
        }
        add(linesLabel);
        add(linesCombo);
    }

    void initializeHumanPlayButton() {
        humanPlayButton = new JButton("Play with a friend");
        add(humanPlayButton);
        humanPlayButton.addActionListener(this::initializeNormalGame);
    }

    void initializeAIPlayButton() {
        AIPlayButton = new JButton("Play with AI");
        add(AIPlayButton);
        AIPlayButton.addActionListener(this::initializeAIGame);
    }
}
