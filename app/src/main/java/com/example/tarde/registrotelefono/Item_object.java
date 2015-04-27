package com.example.tarde.registrotelefono;

/**
 * Created by tarde on 20/04/15.
 */
public class Item_object {
    private String titulo;
    private int icono; //pq es recurso


    public Item_object(String titulo, int icono) {
        this.titulo = titulo;
        this.icono = icono;
    }

    public String getTitulo() {
        return titulo;
    }

    public void setTitulo(String titulo) {
        this.titulo = titulo;
    }

    public int getIcono() {
        return icono;
    }

    public void setIcono(int icono) {
        this.icono = icono;
    }
}
