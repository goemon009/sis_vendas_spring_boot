package com.ifmt.sisvendas.dto;

/**
 * DTO usado para receber dados de entrada da entidade Fornecedor.
 *
 * Essa classe simplifica o corpo das requisições e evita que o cliente
 * da API precise enviar a entidade completa com todos os relacionamentos.
 */
public class FornecedorDTO {

    private String nome;
    private String cnpj;

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public String getCnpj() {
        return cnpj;
    }

    public void setCnpj(String cnpj) {
        this.cnpj = cnpj;
    }
}
