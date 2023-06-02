package ro.command;

import lombok.RequiredArgsConstructor;
import ro.LocaleExplorer;
import ro.shell.Shell;

import java.text.MessageFormat;
import java.util.Locale;
import java.util.ResourceBundle;

@RequiredArgsConstructor
public class SetLocaleCommand implements Command {
    private final String tag;

    @Override
    public void execute(Shell shell) {
        shell.setLocale(new Locale(tag, tag));
        shell.setResourceBundle(ResourceBundle.getBundle(LocaleExplorer.BASE, shell.getLocale()));

        String displayedMessage = MessageFormat.format(shell.getResourceBundle().getString("locale.set"), shell.getLocale().getCountry());
        System.out.println(displayedMessage);
    }
}
