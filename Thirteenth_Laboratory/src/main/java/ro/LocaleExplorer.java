package ro;

import ro.shell.CommandParser;
import ro.shell.CommandScanner;
import ro.shell.Shell;

import java.util.Locale;
import java.util.ResourceBundle;
import java.util.Scanner;

public class LocaleExplorer {
    public final static String BASE = "Messages";

    public static void main(String[] args) {
        Locale locale = Locale.getDefault();
        ResourceBundle resourceBundle = ResourceBundle.getBundle(BASE, locale);
        Shell shell = new Shell(new CommandScanner(new Scanner(System.in)), new CommandParser(), locale, resourceBundle);
        shell.execute();
    }
}
