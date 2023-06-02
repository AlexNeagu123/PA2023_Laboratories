package ro.command;

import ro.shell.Shell;

import java.util.Locale;

public class DisplayLocalesCommand implements Command {

    @Override
    public void execute(Shell shell) {
        System.out.println(shell.getResourceBundle().getString("locales"));
        for (Locale currentLocale : Locale.getAvailableLocales()) {
            System.out.println(currentLocale.getDisplayCountry() + "\t" + currentLocale.getDisplayLanguage(currentLocale));
        }
    }
}
