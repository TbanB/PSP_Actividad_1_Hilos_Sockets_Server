package com.psp_actividad1.server;

import java.io.*;
import java.net.Socket;

public class ClientHandler implements Runnable {
    private final Socket clientSocket;
    private final BooksManager booksManager;

    public ClientHandler(Socket clientSocket, BooksManager booksManager) {
        this.clientSocket = clientSocket;
        this.booksManager = booksManager;
    }

    @Override
    public void run() {
        try (
            InputStream inputStream = clientSocket.getInputStream();
            OutputStream outputStream = clientSocket.getOutputStream();
            BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(inputStream));
            PrintWriter printWriter = new PrintWriter(outputStream, true);
        ) {
            String request;
            while ((request = bufferedReader.readLine()) != null) {
                String response = processRequest(request);
                printWriter.println(response);
            }
        } catch (IOException e) {
            System.err.println("Error en la comunicación con el cliente: " + e.getMessage());
        } finally {
            try {
                clientSocket.close();
                System.out.println("Conexión con el cliente cerrada.");
            } catch (IOException e) {
                System.err.println("Error al cerrar el socket del cliente: " + e.getMessage());
            }
        }
    }

    private String processRequest(String request) {
        String[] parts = request.split(":");
        String command = parts[0]; // El comando enviado por el cliente
        switch (command) {
            case "GET_BOOK_BY_ISBN":
                if (parts.length > 1) {
                    String isbn = parts[1];
                    Book book = booksManager.getBookByIsbn(isbn);
                    return book != null ? book.toString() : "Libro no encontrado.";
                }
                return "Solicitud mal formada. Falta el ISBN.";

            case "GET_BOOKS_BY_TITLE":
                if (parts.length > 1) {
                    String title = parts[1];
                    return booksManager.getBooksByTitle(title).toString();
                }
                return "Solicitud mal formada. Falta la palabra clave.";

            case "ADD_BOOK":
                if (parts.length == 4) {
                    String isbn = parts[1];
                    String title = parts[2];
                    String author = parts[3];
                    double price = Double.parseDouble(parts[4]);
                    booksManager.addNewBook(new Book(isbn, title, author, price));
                    return "Libro añadido correctamente.";
                }
                return "Solicitud mal formada. Faltan datos.";

            default:
                return "Comando no reconocido.";
        }
    }
}
