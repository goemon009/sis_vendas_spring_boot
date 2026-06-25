package com.ifmt.sisvendas.model;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;

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

    @ManyToOne
    @JoinColumn(name = "id_regiao")
    private Regiao regiao;

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

    public Regiao getRegiao() {
        return regiao;
    }

    public void setRegiao(Regiao regiao) {
        this.regiao = regiao;
    }
}
}
