package ro.client;

import lombok.extern.log4j.Log4j2;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;
import java.util.Scanner;

/**
 * This class is responsible for obtaining access to the Server Socket.
 * <p>
 * After the connection is set, commands from the clients are read from the terminal and sent to the server.
 */
@Log4j2
public class GameClient {
    public final static String serverAddress = "127.0.0.1";
    public static final int PORT = 8100;

    public static void main(String[] args) {
        try (Socket socket = new Socket(serverAddress, PORT);
             PrintWriter out = new PrintWriter(socket.getOutputStream(), true);
             BufferedReader in = new BufferedReader(new InputStreamReader(socket.getInputStream()))) {
            CommandScanner commandScanner = new CommandScanner(new Scanner(System.in));
            ServerReader serverReader = new ServerReader(in);
            serverReader.start();
            while (serverReader.isAlive()) {
                readFromClientAndSend(commandScanner, out);
            }
        } catch (IOException ex) {
            log.error("Error while establishing connection with the server: " + ex.getMessage());
        }
    }

    public static void readFromClientAndSend(CommandScanner commandScanner, PrintWriter out) {
        String command = commandScanner.readCommand();
        out.println(command);
    }
}