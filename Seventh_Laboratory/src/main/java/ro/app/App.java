package ro.app;

import ro.game.explorations.Exploration;
import ro.utils.CommandParser;
import ro.utils.CommandScanner;

import java.util.Scanner;

public class App {
    public static void main(String[] args) {
        CommandScanner commandScanner = new CommandScanner(new Scanner(System.in));
        TimeKeeper timeKeeper = commandScanner.collectTimeKeeper();
        Exploration exploration = commandScanner.collectExploration(timeKeeper);
        CommandParser commandParser = new CommandParser(exploration);
        Shell shell = new Shell(commandScanner, commandParser);
        shell.execute();
    }
}
