package ro.command;

import ro.shell.Shell;

import java.text.DateFormat;
import java.text.DateFormatSymbols;
import java.text.MessageFormat;
import java.util.Arrays;
import java.util.Currency;
import java.util.Date;
import java.util.Locale;

public class InfoCommand implements Command {
    @Override
    public void execute(Shell shell) {
        Locale locale = shell.getLocale();
        Locale enLocale = Locale.getDefault();

        String displayedMessage = MessageFormat.format(shell.getResourceBundle().getString("info"), shell.getLocale().getCountry());
        System.out.println(displayedMessage);

        System.out.println("Country: " + locale.getDisplayCountry(enLocale) + " (" + locale.getDisplayCountry(locale) + ")");
        System.out.println("Language: " + locale.getDisplayLanguage(enLocale) + " (" + locale.getDisplayLanguage(locale) + ")");

        try {
            System.out.println("Currency: " + Currency.getInstance(locale).getDisplayName(enLocale) +
                    " (" + Currency.getInstance(locale).getCurrencyCode() + ")");
        } catch (IllegalArgumentException ex) {
            System.out.println("Cannot display currency for the current locale..");
        }

        String[] weekDays = new DateFormatSymbols(locale).getWeekdays();
        System.out.println("Weekdays: " + Arrays.toString(weekDays));
        String[] months = new DateFormatSymbols(locale).getMonths();
        System.out.println("Months: " + Arrays.toString(months));
        System.out.println("Today: " + DateFormat.getDateTimeInstance(DateFormat.LONG, DateFormat.SHORT, locale).format(new Date()));
    }
}
