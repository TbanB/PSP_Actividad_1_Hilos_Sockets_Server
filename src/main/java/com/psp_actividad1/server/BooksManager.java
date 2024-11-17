package com.psp_actividad1.server;

import java.util.ArrayList;
import java.util.List;

/**
 * En esta clase gestiono la lista de libros desde crearla hasta realizar las diferentes operaciones en la lista.
 */
public class BooksManager {
    /**
     * booksList es el atributo principal, al declararla cómo final la lista es mutable pero no se puede asignar una
     * nueva lista a este atributo una vez iniciado.
     */
    private final List<Book> booksList;

    public BooksManager() {
        /**
         * Inicializamos el atributo y se añaden los ejemplos.
         */
        booksList = new ArrayList<>();
        booksList.add(new Book("1", "Java Avanzado", "Autor 1", 39.99));
        booksList.add(new Book("2", "Estructuras de Datos", "Autor 2", 29.99));
        booksList.add(new Book("3", "Redes", "Autor 3", 49.99));
        booksList.add(new Book("4", "Algoritmos", "Autor 4", 19.99));
        booksList.add(new Book("5", "Bases de Datos", "Autor 5", 59.99));
    }

    /**
     * Con este método recogemos los libros por su ISBN, he decidido declararlo cómo synchronized para que no pueda
     * haber conflictos en caso que se haga una petición mientras se esta agregando algún nuevo libro.
     * @param isbn
     * @return
     */
    public synchronized Book getBookByIsbn(String isbn) {
        if (isbn == null) {
            return null;
        }
        /**
         * Se convierte el arrayList en un flujo con el método .stream() para poder operar con la lista,
         * filtramos la lista por el ISBN y devolverá el primer libro que tenga ese isbn en caso contrario retorna un Null
         */
        return booksList.stream().filter(book -> isbn.equals(book.getIsbn())).findFirst().orElse(null);
    }

    /**
     * Método para recoger todos los libros que contengan la palabra enviada por el usuario. También sincronizada para
     * evitar conflictos en caso que varios hilos intenten operar al mismo tiempo.
     * @param title
     * @return
     */
    public synchronized List<Book> getBooksByTitle(String title) {
        if (title == null) {
            return null;
        }
        List<Book> res = new ArrayList<>();
        for (Book book : booksList) {
            if (book.getTitle().toLowerCase().contains(title.toLowerCase())) {
                res.add(book);
            }
        }
        return res;
    }

    /**
     * Método para recoger los libros por autor
     * @param author
     * @return
     */
    public synchronized List<Book> getBooksByAuthor(String author) {
        if (author == null) {
            return null;
        }
        List<Book> res = new ArrayList<>();
        for (Book book : booksList) {
            if (book.getAuthor().toLowerCase().contains(author.toLowerCase())) {
                res.add(book);
            }
        }
        return res;
    }

    /**
     * método para añadir un nuevo libro a la lista.
     * @param book
     */
    public synchronized void addNewBook(Book book) {
        booksList.add(book);
    }

}
