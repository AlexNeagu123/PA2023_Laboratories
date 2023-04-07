package com.utils;

import com.exceptions.NoLinesInGame;
import com.game.Game;
import com.geometry.Line;
import com.geometry.Point;

import javax.imageio.ImageIO;
import javax.swing.*;
import javax.swing.filechooser.FileNameExtensionFilter;
import javax.swing.filechooser.FileSystemView;
import java.awt.image.BufferedImage;
import java.io.*;
import java.util.List;
import java.util.Optional;
import java.util.Set;

/**
 * This class contains several useful methods regarding {@link Game} serialization, deserialization and useful mathematical operations.
 */
public class GameUtils {
    /**
     * @param path The path of a given file (with extension .gga) where a {@link Game} instance has been serialized
     * @return The {@link Game} instance obtained after deserialization
     * @throws IOException            If the file doesn't exist
     * @throws ClassNotFoundException If that file is corrupt
     */
    public static Game deserializeFileIntoGame(String path) throws IOException, ClassNotFoundException {
        FileInputStream fis = new FileInputStream(path);
        ObjectInputStream in = new ObjectInputStream(fis);
        Game game = (Game) in.readObject();
        fis.close();
        return game;
    }

    /**
     * @param game A {@link Game} instance that should be serialized
     * @param path The path of a given file (with extension .gga) where the game should be serialized
     * @throws IOException If the file doesn't exist
     */
    public static void serializeGameIntoFile(Game game, String path) throws IOException {
        FileOutputStream fos = new FileOutputStream(path);
        ObjectOutputStream out = new ObjectOutputStream(fos);
        out.writeObject(game);
        out.flush();
        fos.close();
    }

    public static void chooseFileAndSerializeGame(Game game) throws IOException {
        JFileChooser fileChooser = new JFileChooser(FileSystemView.getFileSystemView().getHomeDirectory());
        fileChooser.setDialogTitle("Choose the destination directory to save the game status");
        fileChooser.setFileSelectionMode(JFileChooser.DIRECTORIES_ONLY);
        int savedDataStatus = fileChooser.showSaveDialog(null);
        if (savedDataStatus == JFileChooser.APPROVE_OPTION) {
            serializeGameIntoFile(game, fileChooser.getSelectedFile() + "\\GameState.gga");
        }
    }

    public static Optional<Game> chooseFileAndDeserializeGame() throws IOException, ClassNotFoundException {
        JFileChooser fileChooser = new JFileChooser(FileSystemView.getFileSystemView().getHomeDirectory());
        fileChooser.setDialogTitle("Select a game .gga file");
        fileChooser.setAcceptAllFileFilterUsed(false);
        fileChooser.addChoosableFileFilter(new FileNameExtensionFilter("Only .gga type of files", "gga"));
        int loadDataStatus = fileChooser.showOpenDialog(null);
        Game game = null;
        if (loadDataStatus == JFileChooser.APPROVE_OPTION) {
            game = GameUtils.deserializeFileIntoGame(fileChooser.getSelectedFile().getPath());
        }
        return Optional.ofNullable(game);
    }

    public static void chooseFileAndSaveImage(BufferedImage image) throws IOException {
        JFileChooser fileChooser = new JFileChooser(FileSystemView.getFileSystemView().getHomeDirectory());
        fileChooser.setDialogTitle("Choose the destination directory to save the game image");
        fileChooser.setFileSelectionMode(JFileChooser.DIRECTORIES_ONLY);
        int savedDataStatus = fileChooser.showSaveDialog(null);
        if (savedDataStatus == JFileChooser.APPROVE_OPTION) {
            ImageIO.write(image, "png", new File(fileChooser.getSelectedFile() + "\\GameImage.png"));
        }
    }

    public static Line getRandomLine(List<Line> lines) {
        int randomPos = (int) (Math.random() * (lines.size() - 1));
        return lines.get(randomPos);
    }

    /**
     * Using the <b>cross product</b> properties, this method finds the closest line to a point from a set of lines
     *
     * @param point    A specified {@link Point} object
     * @param allLines A set of {@link Line} objects
     * @return The closest {@link Line} to the specified point
     * @throws NoLinesInGame If there are no lines available left in the game
     */
    public static Line getClosestLine(Point point, Set<Line> allLines) throws NoLinesInGame {
        int minCloseness = Integer.MAX_VALUE;
        Line closestLine = null;
        for (Line line : allLines) {
            int closeness = line.getCloseness(point);
            if (closeness < minCloseness) {
                minCloseness = closeness;
                closestLine = line;
            }
        }
        if (closestLine == null) {
            throw new NoLinesInGame();
        }
        return closestLine;
    }
}
