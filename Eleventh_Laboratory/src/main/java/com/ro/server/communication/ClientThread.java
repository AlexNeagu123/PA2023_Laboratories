package com.ro.server.communication;

import com.ro.server.game.Player;
import lombok.extern.log4j.Log4j2;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;

/**
 * An instance of this class will be responsible
 * with communicating with a client Socket.
 *
 * @author Alex Neagu
 */
@Log4j2
public class ClientThread extends Thread {
    private final Socket socket;

    public ClientThread(Socket socket) {
        this.socket = socket;
    }

    public void run() {
        try (BufferedReader in = new BufferedReader(new InputStreamReader(socket.getInputStream()));
             PrintWriter out = new PrintWriter(socket.getOutputStream(), true)) {
            Player player = new Player(in, out);
            Shell shell = new Shell(player, new CommandParser(player));
            shell.execute();
        } catch (IOException ex) {
            log.error("Communication error with the client: " + ex.getMessage());
        }
    }
}
