package com.gui;

import com.game.Game;

import javax.swing.*;
import java.awt.*;

/**
 * The <tt>MainFrame</tt> class describes and layouts all the graphical containers of the application and initializes the {@link Game} instance.
 */
public class MainFrame extends JFrame {
    ConfigPanel configPanel;
    ControlPanel controlPanel;
    DrawingPanel canvas;
    Game game;

    /**
     * The frame is being initialized and an instance of a normal 1v1 {@link Game} with 6 vertices and 1.0 line probability is created.
     */
    public MainFrame() {
        super("Drawing Game");
        game = new Game(6, 1.0, false);
        initializeFrame();
    }

    private void initPanels() {
        configPanel = new ConfigPanel(this);
        controlPanel = new ControlPanel(this);
        canvas = new DrawingPanel(this);
    }

    private void initLayouts() {
        add(configPanel, BorderLayout.NORTH);
        add(controlPanel, BorderLayout.SOUTH);
        add(canvas, BorderLayout.CENTER);
    }

    public void initializeFrame() {
        setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        initPanels();
        initLayouts();
        pack();
    }
}
