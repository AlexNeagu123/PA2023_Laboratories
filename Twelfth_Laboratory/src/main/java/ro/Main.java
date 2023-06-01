package ro;

import ro.shell.CommandParser;
import ro.shell.CommandScanner;
import ro.shell.Shell;

import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
        CommandScanner commandScanner = new CommandScanner(new Scanner(System.in));
        Shell shell = new Shell(commandScanner, new CommandParser());
        shell.execute();
    }
}