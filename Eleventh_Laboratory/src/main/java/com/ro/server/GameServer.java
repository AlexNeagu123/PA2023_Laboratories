package com.ro.server;

import com.ro.server.communication.ClientThread;
import lombok.Data;
import lombok.extern.log4j.Log4j2;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;

/**
 * An instance of this class will create a
 * ServerSocket running at a specified port.
 * <p>
 * The server will receive and execute several requests from clients.
 *
 * @author Alex Neagu
 */
@Log4j2
@Data
public class GameServer {
    public static final int PORT = 8100;
    public boolean running;

    public GameServer() {
        try (ServerSocket serverSocket = new ServerSocket(PORT)) {
            while (true) {
                Socket socket = serverSocket.accept();
                new ClientThread(socket).start();
            }
        } catch (IOException ex) {
            log.error(ex.getMessage());
        }
    }

    public static void main(String[] args) {
        new GameServer();
    }
}