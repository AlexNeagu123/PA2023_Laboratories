package com.gui;

import com.game.Game;
import com.utils.GameUtils;
import lombok.extern.log4j.Log4j2;

import javax.imageio.ImageIO;
import javax.swing.*;
import javax.swing.filechooser.FileNameExtensionFilter;
import javax.swing.filechooser.FileSystemView;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.io.File;
import java.io.IOException;
import java.util.Optional;

/**
 * The <tt>ControlPanel</tt> class is responsible for the layout and functionality of the control components.
 * <p>
 * For instance, a save button for saving the state of the current game in a .gga file, a load button for loading a local .gga file
 * and recreate it on the screen, an export button for saving an image of the game in a local .png file, a reset button for restarting the game and an
 * exit button are all components of this container.
 */
@Log4j2
public class ControlPanel extends JPanel {
    final MainFrame frame;
    JButton exitButton;
    JButton loadButton;
    JButton resetButton;
    JButton exportButton;
    JButton saveButton;

    public ControlPanel(MainFrame frame) {
        this.frame = frame;
        init();
    }

    private void init() {
        //change the default layout manager (just for fun)
        setLayout(new GridLayout(1, 5));
        initializeLoadButton();
        initializeSaveButton();
        initializeExportButton();
        initializeResetButton();
        initializeExitButton();
    }

    void resetGame(ActionEvent e) {
        frame.configPanel.initializeGame(frame.game.AI_GAME);
    }

    void initializeLoadButton() {
        loadButton = new JButton("Load Game");
        loadButton.addActionListener(this::loadGameState);
        add(loadButton);
    }

    void initializeSaveButton() {
        saveButton = new JButton("Save Game");
        saveButton.addActionListener(this::saveGameState);
        add(saveButton);
    }

    void initializeExportButton() {
        exportButton = new JButton("Export Image");
        exportButton.addActionListener(this::exportGameImage);
        add(exportButton);
    }

    void initializeResetButton() {
        resetButton = new JButton("Reset");
        resetButton.addActionListener(this::resetGame);
        add(resetButton);
    }

    void initializeExitButton() {
        exitButton = new JButton("Exit");
        exitButton.addActionListener(this::exitGame);
        add(exitButton);
    }

    private void exitGame(ActionEvent e) {
        frame.dispose();
    }

    public void saveGameState(ActionEvent e) {
        try {
            GameUtils.chooseFileAndSerializeGame(frame.game);
        } catch (IOException exception) {
            log.error(exception.getMessage());
        }
    }

    public void loadGameState(ActionEvent e) {
        try {
            Optional<Game> game = GameUtils.chooseFileAndDeserializeGame();
            game.ifPresent(value -> frame.configPanel.initializeGame(value));
        } catch (IOException exception) {
            log.error(exception.getMessage());
        } catch (ClassNotFoundException ex) {
            log.error("Corrupt .gga file: " + ex.getMessage());
        }
    }

    public void exportGameImage(ActionEvent e) {
        try {
            GameUtils.chooseFileAndSaveImage(frame.canvas.image);
        } catch (IOException exception) {
            log.error(exception.getMessage());
        }
    }
}
