package com.example.semantix.jogosapp;

import java.io.Serializable;

public class JogoUsuBean implements Serializable {

    private int id;
    private UsuarioBean usu;
    private JogoBean jogo;

    public JogoUsuBean(){}
    public JogoUsuBean(int id, UsuarioBean usu, JogoBean jogo) {
        this.id = id;
        this.usu = usu;
        this.jogo = jogo;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public UsuarioBean getUsu() {
        return usu;
    }

    public void setUsu(UsuarioBean usu) {
        this.usu = usu;
    }

    public JogoBean getJogo() {
        return jogo;
    }

    public void setJogo(JogoBean jogo) {
        this.jogo = jogo;
    }

    public String toString(){
        return "Jogo: " + this.getJogo().getNome() + "      Categoria: " + this.getJogo().getCat().getTipo();
    }
}
