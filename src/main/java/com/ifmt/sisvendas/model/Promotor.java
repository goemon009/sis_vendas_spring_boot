package com.ifmt.sisvendas.model;

import jakarta.persistence.*;

@Entity
@Table(name = "promotor")
public class Promotor {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_promotor")
    private Integer idPromotor;

    @Column(name = "nome", nullable = false, length = 100)
    private String nome;

    @Column(name = "cpf", length = 14)
    private String cpf;

    public Integer getIdPromotor() {
        return idPromotor;
    }

    public void setIdPromotor(Integer idPromotor) {
        this.idPromotor = idPromotor;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public String getCpf() {
        return cpf;
    }

    public void setCpf(String cpf) {
        this.cpf = cpf;
    }
}