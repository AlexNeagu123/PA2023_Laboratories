package com.ro.server.game;

import lombok.Getter;
import lombok.extern.log4j.Log4j2;

import java.util.concurrent.TimeUnit;

/**
 * This class implements the blitz functionality.
 * <p>
 * Before a {@link Player} starts the game, a <tt>Time Keeper</tt> thread is assigned as a daemon.
 *
 * @author Alex Neagu
 */
@Log4j2
public class TimeKeeper extends Thread {
    private final int seconds;
    private boolean running;
    private boolean disabled;
    @Getter
    private int remainingTime;

    public TimeKeeper(int seconds) {
        this.seconds = seconds;
        this.remainingTime = seconds;
        this.running = true;
        this.disabled = false;
    }

    public void disableClock() {
        this.disabled = true;
    }

    public void resumeClock() {
        this.running = true;
    }

    public void freezeClock() {
        this.running = false;
    }

    @Override
    public void run() {
        for (int i = 0; i < seconds && !disabled; ++i) {
            try {
                while (!running && !disabled) {
                    sleep(0);
                }
                TimeUnit.SECONDS.sleep(1);
                remainingTime--;
            } catch (InterruptedException e) {
                log.error(e.getMessage());
            }
        }
    }
}
