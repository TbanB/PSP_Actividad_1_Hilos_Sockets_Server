package com.psp_actividad1.server;

/**
 *  He decidido importar Lombok para agilizar la declaración de la clase y ahorrarme código repetitivo cómo getters y setters.
 */
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
/**
 * La definición de la clase book, que representa a un libro y los datos necesarios  para poder gestionarlos desde una librería/biblioteca.
 */
public class Book {
    /**
     * Se declaran los atributos y accedemos a ellos mediante los métodos generados por Lombok
     */
    private String isbn;
    private String title;
    private String author;
    private double price;

    public String toString() {
        return "ISBN: " + isbn + ", Título: " + title + ", Autor: " + author + ", Precio: " + price + "€";
    }
}
