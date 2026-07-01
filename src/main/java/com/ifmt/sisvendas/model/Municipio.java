package com.ifmt.sisvendas.model;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;

/**
 * Entidade que representa um município.
 *
 * O município é usado para organizar a área de atuação dos promotores
 * e a localização dos clientes.
 */
@Entity
@Table(name = "municipio")
public class Municipio {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_municipio")
    private Integer idMunicipio;

    @Column(name = "nome", nullable = false, length = 100)
    private String nome;

    @Column(name = "uf", nullable = false, length = 2)
    private String uf;

    @ManyToOne
    @JoinColumn(name = "id_regiao", nullable = false)
    private Regiao regiao;

    public Integer getIdMunicipio() {
        return idMunicipio;
    }

    public void setIdMunicipio(Integer idMunicipio) {
        this.idMunicipio = idMunicipio;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }


    public String getUf() {
        return uf;
    }

    public void setUf(String uf) {
        this.uf = uf;
    }

    public Regiao getRegiao() {
        return regiao;
    }

    public void setRegiao(Regiao regiao) {
        this.regiao = regiao;
    }

    public void setIdRegiao(Integer idRegiao) {
        if (idRegiao == null) {
            return;
        }

        garantirRegiao().setId(idRegiao);
    }

    public void setId_regiao(Integer idRegiao) {
        setIdRegiao(idRegiao);
    }

    private Regiao garantirRegiao() {
        if (regiao == null) {
            regiao = new Regiao();
        }

        return regiao;
    }
}
