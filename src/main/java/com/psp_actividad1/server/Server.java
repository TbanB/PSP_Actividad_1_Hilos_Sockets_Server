package com.psp_actividad1.server;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;

public class Server {
    private static final int PORT = 8080;
    private final BooksManager booksManager;

    public Server() {
        booksManager = new BooksManager();
    }

    public void start() {
        try (ServerSocket serverSocket = new ServerSocket(PORT)) {
            System.out.println("Iniciado servidor. Puerto: " + PORT);

            while (true) {
                System.out.println("Conectando con cliente...");
                Socket clientSocket = serverSocket.accept();
                System.out.println("Cliente conectado desde " + clientSocket.getInetAddress());

                ClientHandler clientHandler = new ClientHandler(clientSocket, booksManager);
                new Thread(clientHandler).start(); // Inicia el hilo del cliente
            }
        } catch (IOException e) {
            System.err.println("Error en el servidor: " + e.getMessage());
        }
    }

    public static void main(String[] args) {
        Server server = new Server();
        server.start();
    }
}
