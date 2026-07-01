package com.ifmt.sisvendas.dto;

import java.math.BigDecimal;

import tools.jackson.databind.JsonNode;

/**
 * DTO usado para receber dados de entrada da entidade Produto.
 *
 * Essa classe simplifica o corpo das requisições e evita que o cliente
 * da API precise enviar a entidade completa com todos os relacionamentos.
 */
public class ProdutoDTO {

    private String nome;
    private BigDecimal vlCusto;
    private Integer qtdEstoque;
    private Integer qtdReservadaProduto;
    private Integer qtdMinEstoque;
    private Integer qtdMaxEstoque;
    private BigDecimal percentualComissao;
    private BigDecimal percentualPromocao;
    private BigDecimal margemLucro;
    private Integer idCategoriaProduto;
    private JsonNode categoriaProduto;

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }


    public BigDecimal getVlCusto() {
        return vlCusto;
    }

    public void setVlCusto(BigDecimal vlCusto) {
        this.vlCusto = vlCusto;
    }

    public Integer getQtdEstoque() {
        return qtdEstoque;
    }

    public void setQtdEstoque(Integer qtdEstoque) {
        this.qtdEstoque = qtdEstoque;
    }

    public Integer getQtdReservadaProduto() {
        return qtdReservadaProduto;
    }

    public void setQtdReservadaProduto(Integer qtdReservadaProduto) {
        this.qtdReservadaProduto = qtdReservadaProduto;
    }

    public void setQntdReservadaProduto(Integer qntdReservadaProduto) {
        this.qtdReservadaProduto = qntdReservadaProduto;
    }

    public Integer getQtdMinEstoque() {
        return qtdMinEstoque;
    }

    public void setQtdMinEstoque(Integer qtdMinEstoque) {
        this.qtdMinEstoque = qtdMinEstoque;
    }

    public void setQntdMinEstoque(Integer qntdMinEstoque) {
        this.qtdMinEstoque = qntdMinEstoque;
    }

    public Integer getQtdMaxEstoque() {
        return qtdMaxEstoque;
    }

    public void setQtdMaxEstoque(Integer qtdMaxEstoque) {
        this.qtdMaxEstoque = qtdMaxEstoque;
    }

    public void setQntdMaxEstoque(Integer qntdMaxEstoque) {
        this.qtdMaxEstoque = qntdMaxEstoque;
    }

    public BigDecimal getPercentualComissao() {
        return percentualComissao;
    }

    public void setPercentualComissao(BigDecimal percentualComissao) {
        this.percentualComissao = percentualComissao;
    }

    public BigDecimal getPercentualPromocao() {
        return percentualPromocao;
    }

    public void setPercentualPromocao(BigDecimal percentualPromocao) {
        this.percentualPromocao = percentualPromocao;
    }

    public BigDecimal getMargemLucro() {
        return margemLucro;
    }

    public void setMargemLucro(BigDecimal margemLucro) {
        this.margemLucro = margemLucro;
    }

    public Integer getIdCategoriaProduto() {
        return idCategoriaProduto;
    }

    public void setIdCategoriaProduto(Integer idCategoriaProduto) {
        this.idCategoriaProduto = idCategoriaProduto;
    }

    public JsonNode getCategoriaProduto() {
        return categoriaProduto;
    }

    public void setCategoriaProduto(JsonNode categoriaProduto) {
        this.categoriaProduto = categoriaProduto;
    }
}
