package com.example.projeto;

public class Contato {
    public String id;
    public String nome;
    public String email;
    public String endereco;
    public String genero;
    public String celular;

    public Contato(String id, String nome, String email, String endereco, String genero, String celular) {
        this.id = id;
        this.nome = nome;
        this.email = email;
        this.endereco = endereco;
        this.genero = genero;
        this.celular = celular;
    }

    public Contato() {
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getEndereco() {
        return endereco;
    }

    public void setEndereco(String endereco) {
        this.endereco = endereco;
    }

    public String getGenero() {
        return genero;
    }

    public void setGenero(String genero) {
        this.genero = genero;
    }

    public String getCelular() {
        return celular;
    }

    public void setCelular(String celular) {
        this.celular = celular;
    }
}
