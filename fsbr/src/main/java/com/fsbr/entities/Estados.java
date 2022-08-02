package com.fsbr.entities;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

@Entity
public class Estados {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private int id;

    private String estado;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getNome() {
        return estado;
    }

    public void setNome(String nome) {
        this.estado = nome;
    }

    public Estados() {  }

    public Estados(String estado) {
        this.estado = estado;
    }

    @Override
    public String toString() {
        return "FsbrEstado{" +
                "id=" + id +
                ", estado='" + estado + "'" +
                '}';
    }
}