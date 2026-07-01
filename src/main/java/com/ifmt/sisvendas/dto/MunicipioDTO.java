package com.ifmt.sisvendas.dto;

/**
 * DTO usado para receber dados de entrada da entidade Municipio.
 *
 * Essa classe simplifica o corpo das requisições e evita que o cliente
 * da API precise enviar a entidade completa com todos os relacionamentos.
 */
public class MunicipioDTO {

    private String nome;
    private String uf;
    private Integer idRegiao;

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

    public Integer getIdRegiao() {
        return idRegiao;
    }

    public void setIdRegiao(Integer idRegiao) {
        this.idRegiao = idRegiao;
    }
}
