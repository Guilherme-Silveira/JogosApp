package com.example.semantix.jogosapp;

import java.io.Serializable;

public class UsuarioBean implements Serializable {

    private int id;
    private String login;
    private String senha;
    private String status;
    private String tipo;

    public UsuarioBean(){}
    public UsuarioBean(int id, String login, String senha, String status, String tipo){
        this.id = id;
        this.login = login;
        this.senha = senha;
        this.status = status;
        this.tipo = tipo;
    }

    public int getId() {
        return this.id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getLogin() {
        return this.login;
    }

    public void setLogin(String login) {
        this.login = login;
    }

    public String getSenha() {
        return this.senha;
    }

    public void setSenha(String senha) {
        this.senha = senha;
    }

    public String getStatus() {
        return this.status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getTipo() {
        return this.tipo;
    }

    public void setTipo(String tipo) {
        this.tipo = tipo;
    }

    @Override
    public String toString() {
        return "id=" + id + ", login=" + login + ", senha=" + senha + ", status=" + status + ", tipo=" + tipo;
    }

}

