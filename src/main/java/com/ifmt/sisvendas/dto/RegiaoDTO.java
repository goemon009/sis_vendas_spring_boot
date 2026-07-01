package com.ifmt.sisvendas.dto;

/**
 * DTO usado para receber dados de entrada da entidade Regiao.
 *
 * Essa classe simplifica o corpo das requisições e evita que o cliente
 * da API precise enviar a entidade completa com todos os relacionamentos.
 */
public class RegiaoDTO {

    private String nome;

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }
}
