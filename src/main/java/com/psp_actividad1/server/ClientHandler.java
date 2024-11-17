package com.psp_actividad1.server;

import java.io.*;
import java.net.Socket;

/**
 * Clase creadda para gestionar a un cliente especifico, se implementa Runnable para poder
 * ejecutarla en un hilo independiente
 */
public class ClientHandler implements Runnable {
    /**
     * el atributo Socket clienteSocket representa el punto de conexión con el cliente.
     * BooksManager es la clase creada para gestionar las diferentes operaciones de la aplicación
     */
    private final Socket clientSocket;
    private final BooksManager booksManager;

    /**
     * Inicializamos el socket de conexión con el cliente y el booksmanager
     * @param clientSocket
     * @param booksManager
     */
    public ClientHandler(Socket clientSocket, BooksManager booksManager) {
        this.clientSocket = clientSocket;
        this.booksManager = booksManager;
    }

    /**
     * sobrescribimos el método run de la interfaz runnable
     */
    @Override
    public void run() {
        System.out.println("Conectando con cliente: " + clientSocket.getInetAddress());

        /**
         * Abrimos los flujos de entrada (InputStream + BufferedReader) y salida (OutputStream + PrintWriter)
         * del socket del cliente para leer los datos enviados por el cliente y enviarle respuestas.
         * Al estar en un bloque try evitamos que los flujos queden abiertos en caso de fallo
         *
         */
        try (
                InputStream input = clientSocket.getInputStream();
                OutputStream output = clientSocket.getOutputStream();
                BufferedReader reader = new BufferedReader(new InputStreamReader(input));
                PrintWriter writer = new PrintWriter(output, true);
        ) {
            /**
             * dentro del bucle while leemos la respuesta del cliente y la envío al método requestFormatter()
             * para interpretar el comando que el cliente ha enviado al servidor,
             * con writer.printLn le enviamos la respuesta al cliente.
             */
            String request;
            while ((request = reader.readLine()) != null) {
                System.out.println("Se ha recibido una petición");
                String res = requestFormatter(request);
                writer.println(res);
            }
        } catch (IOException e) {
            throw new RuntimeException(e);
        } finally {
            try {
                clientSocket.close();
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        }
    }

    /**
     * Con este método se interpreta la petición del cliente, recogemos el comando envíado y
     * dentro del switch case ejecutamos el caso correspondiente al comando
     * @param request
     * @return
     */
    private String requestFormatter(String request) {
        String[] commandParts = request.split(", ");
        String command = commandParts[0].toUpperCase();

        switch (command) {
            case "GET_BY_ISBN":
                String isbn = commandParts[1];
                Book book = booksManager.getBookByIsbn(isbn);
                return book != null ? book.toString() : "Error: no se ha encontrado el libro";

            case "GET_BY_TITLE":
                String title = commandParts[1];
                return booksManager.getBooksByTitle(title).toString();

            case "GET_BY_AUTHOR":
                String author = commandParts[1];
                return booksManager.getBooksByAuthor(author).toString();

            case "ADD_BOOK":
                if (commandParts.length < 5) return "Error: no se han encontrado suficientes parámtros para agregar un nuevo libro.";
                try {
                    String newIsbn = commandParts[1];
                    String newTitle = commandParts[2];
                    String newAuthor = commandParts[3];
                    double newPrice = Double.parseDouble(commandParts[4]);
                    booksManager.addNewBook(new Book(newIsbn, newTitle, newAuthor, newPrice));
                    return "Se agregado el libro correctamente";
                } catch (NumberFormatException e) {
                    throw new RuntimeException(e);
                }

            default:
                return "Error: no se ha elegido una opción correcta";
        }
    }
}
