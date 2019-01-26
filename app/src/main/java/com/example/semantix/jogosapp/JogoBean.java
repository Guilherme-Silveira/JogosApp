package com.example.semantix.jogosapp;

import java.io.Serializable;

public class JogoBean implements Serializable {
    private int id_jogo;
    private String nome;
    private String descricao;
    private CategoriaBean cat;

    public JogoBean(){}

    public JogoBean(int id_jogo, String nome, String descricao, CategoriaBean cat) {
        this.id_jogo = id_jogo;
        this.nome = nome;
        this.descricao = descricao;
        this.cat = cat;
    }

    public int getId_jogo() {
        return id_jogo;
    }

    public void setId_jogo(int id_jogo) {
        this.id_jogo = id_jogo;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public String getDescricao() {
        return descricao;
    }

    public void setDescricao(String descricao) {
        this.descricao = descricao;
    }

    public CategoriaBean getCat() {
        return cat;
    }

    public void setCat(CategoriaBean cat) {
        this.cat = cat;
    }

    public String toString(){
        return "Jogo: " + this.nome + "      " + "Categoria: " + this.cat.getTipo();
    }
}
