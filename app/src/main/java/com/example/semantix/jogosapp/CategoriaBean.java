package com.example.semantix.jogosapp;

import java.io.Serializable;

public class CategoriaBean implements Serializable {

    private int id_cat;
    private String tipo;

    public CategoriaBean (){}
    public CategoriaBean(int id_cat, String tipo){
        this.id_cat = id_cat;
        this.tipo = tipo;
    }

    public int getId() {
        return id_cat;
    }

    public void setId(int id) {
        this.id_cat = id;
    }

    public String getTipo() {
        return tipo;
    }

    public void setTipo(String tipo) {
        this.tipo = tipo;
    }

    public String toString(){
        return this.tipo;
    }
}
