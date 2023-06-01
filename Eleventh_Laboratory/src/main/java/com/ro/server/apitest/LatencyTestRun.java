package com.ro.server.apitest;

import lombok.extern.log4j.Log4j2;

import java.util.ArrayList;
import java.util.List;

@Log4j2
public class LatencyTestRun {
    public static void main(String[] args) {
        Long begin = System.currentTimeMillis();
        List<Thread> threads = new ArrayList<>();
        for (int i = 0; i < 1000; ++i) {
            Thread newThread = new LatencyTestThread(i);
            threads.add(newThread);
            newThread.start();
        }
        for (Thread t : threads) {
            try {
                t.join();
            } catch (InterruptedException ie) {
                log.error(ie.getMessage());
            }
        }
        Long end = System.currentTimeMillis();
        System.out.println("1000 Api Calls Have Been made in " + (end - begin) + " ms");
    }
}
