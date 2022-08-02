package com.fsbr.entities;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

import com.fsbr.utils.DisplayAs;

@Entity
public class Cadastramento {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @Column(name="nome", nullable=false)
    private String nome;

    @Column(name="cpf", nullable=false)
    private String cpf;

    @Column(name="cidade", nullable=false)
    private String cidade;

    private String estado;
    
    @DisplayAs(value = "ID", index = 0)
    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    @DisplayAs(value = "Nome", index = 1)
    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    @DisplayAs(value = "CPF", index = 2)
    public String getCpf() {
        return cpf;
    }

    public void setCpf(String cpf) {
        this.cpf = cpf;
    }

    @DisplayAs(value = "Cidade", index = 3)
    public String getCidade() {
        return cidade;
    }

    public void setCidade(String cidade) {
        this.cidade = cidade;
    }

    public String getEstado() {
        return estado;
    }

    public void setEstado(String estado) {
        this.estado = estado;
    }

    public Cadastramento() {  }

    public Cadastramento(String nome, String cpf) {
        this.nome = nome;
        this.cpf = cpf;
    }

    public Cadastramento(String nome, String cpf, String cidade) {
        this.nome = nome;
        this.cpf = cpf;
        this.cidade = cidade;
    }

    public Cadastramento(String nome, String cpf, String cidade, String estado) {
        this.nome = nome;
        this.cpf = cpf;
        this.cidade = cidade;
        this.estado = estado;
    }

    @Override
    public String toString() {
        return "{" +
                "\"nome\":\"" + nome + "\"" +
                ",\"cpf\":\"" + cpf + "\"" +
                ",\"cidade\":\"" + cidade + "\"" +
                ",\"estado\":\"" + estado + "\"" +
                "}";
    }
}