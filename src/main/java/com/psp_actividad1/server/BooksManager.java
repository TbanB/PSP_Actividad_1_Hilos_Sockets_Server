package com.psp_actividad1.server;

import java.util.ArrayList;
import java.util.List;

public class BooksManager {
    private final List<Book> booksList;

    public BooksManager() {
        booksList = new ArrayList<>();
        booksList.add(new Book("1", "Java Avanzado", "Autor 1", 39.99));
        booksList.add(new Book("2", "Estructuras de Datos", "Autor 2", 29.99));
        booksList.add(new Book("3", "Redes", "Autor 3", 49.99));
        booksList.add(new Book("4", "Algoritmos", "Autor 4", 19.99));
        booksList.add(new Book("5", "Bases de Datos", "Autor 5", 59.99));
    }

    public synchronized Book getBookByIsbn(String isbn) {
        if (isbn == null) {
            return null;
        }
        return booksList.stream().filter(book1 -> isbn.equals(book1.getIsbn())).findFirst().orElse(null);
    }

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

    public synchronized int getTotalNumberBooks() {
        return booksList.size();
    }

    public synchronized void addNewBook(Book book) {
        booksList.add(book);
    }

}
