package ro.client;

import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;

import java.io.BufferedReader;
import java.io.IOException;

/**
 * This thread is responsible for reading lines of text from the server (via the Server Socket) and displaying them
 * on the screen.
 */
@RequiredArgsConstructor
@Log4j2
public class ServerReader extends Thread {
    private final BufferedReader inputStream;
    private boolean running = true;

    public void run() {
        try {
            while (running) {
                String response = readLineFromServerAndDisplay();
                if (stopCommandCompleted(response)) {
                    this.running = false;
                }
            }
        } catch (IOException ex) {
            log.error("Error while reading from the server: " + ex.getMessage());
        }
    }

    public boolean stopCommandCompleted(String response) {
        return response.contains("Stopped");
    }

    private String readLineFromServerAndDisplay() throws IOException {
        String response = inputStream.readLine();
        System.out.println(response);
        return response;
    }
}
