package ro.app;

import ro.exceptions.InvalidTimeLimitException;

import java.util.concurrent.TimeUnit;

/**
 * The <tt>TimeKeeper</tt> class is responsible for setting a time limit for the exploration process.
 * <p>
 * A {@link TimeKeeper} object is a daemon that is decreasingly counting seconds until no time is left.
 */
public class TimeKeeper extends Thread {
    private final int seconds;

    /**
     * @param seconds The number of seconds to count
     * @throws InvalidTimeLimitException If less than 10 seconds are provided
     */
    public TimeKeeper(int seconds) throws InvalidTimeLimitException {
        if (seconds < 10) {
            throw new InvalidTimeLimitException("The minimum time limit for the exploration should be 10 seconds.");
        }
        this.seconds = seconds;
    }

    @Override
    public void run() {
        for (int i = 0; i < seconds; ++i) {
            if (i > 0 && (seconds - i <= 10 || i % 10 == 0)) {
                System.out.println(i + " seconds passed. " + (seconds - i) + " seconds remaining");
            }
            try {
                TimeUnit.SECONDS.sleep(1);
            } catch (InterruptedException e) {
                System.out.println(e.getMessage());
            }
        }
        System.out.println("Time limit has been exceeded. The exploration is stopped.");
    }
}
