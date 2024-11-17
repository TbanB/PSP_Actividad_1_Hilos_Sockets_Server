package com.psp_actividad1.server;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;

/**
 * Clase creada para gestionar las conexiones con lso clientes
 */
public class Server {
    /**
     * He definido el valor del atributo de PORT cómo estático y privado,
     * este será el puerto en el que el servidor escuchará las conexiones con el cliente.
     *
     * Nos aseguramos que el listado de libros no se puedea reasignar declarando el atributo BooksManager cómo final.
     */
    private static final int PORT = 8080;
    private final BooksManager booksManager;

    public Server() {
        /**
         * Iniciamos la instancia de booksmanager, una vez iniciada la instancia todos
         * los clientes trabajarán con el mismo listado.
         */
        this.booksManager = new BooksManager();
    }

    public static void main(String[] args) {
        /**
         * Punto de entrada de la aplicación al iniciarla, creamos la instancia del servidor
         * y lo iniciamos con el método .start()
         */
        Server server = new Server();
        server.start();
    }

    public void start() {
        System.out.println("Iniciando servidor en puerto: " + PORT);
        /**
         * En el bloque try catch creamos un socket del servidor que escucha las conexiones al puerto asignado.
         * Dentro del while el servidor se mantiene esperando a nuevas conexiones del cliente
          */
        try (ServerSocket serverSocket = new ServerSocket(PORT)) {
            System.out.println("Servidor funcionando esperando conexión...");
            while (true) {
                /**
                 * Al declarar client socket esperamos que un cliente se conecte.
                 * Una vez conectado creamos un nuevo hilo para gestionar la conexión con este cliente.
                 */
                Socket clientSocket = serverSocket.accept();
                new Thread(new ClientHandler(clientSocket, booksManager)).start();
            }
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}
