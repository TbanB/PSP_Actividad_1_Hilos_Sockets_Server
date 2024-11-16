package com.psp_actividad1.server;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class Book {
    private String isbn;
    private String title;
    private String author;
    private double price;

    public String toString() {
        return "ISBN: " + isbn + ", Título: " + title + ", Autor: " + author + ", Precio: " + price + "€";
    }
}
