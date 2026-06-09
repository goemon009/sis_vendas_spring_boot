package com.ifmt.sisvendas.dto;

import java.math.BigDecimal;

import tools.jackson.databind.JsonNode;

public class ProdutoRequest {

    private String nome;
    private BigDecimal vlCusto;
    private Integer qtdEstoque;
    private Integer qntdReservadaProduto;
    private Integer qntdMinEstoque;
    private Integer qntdMaxEstoque;
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

    public Integer getQntdReservadaProduto() {
        return qntdReservadaProduto;
    }

    public void setQntdReservadaProduto(Integer qntdReservadaProduto) {
        this.qntdReservadaProduto = qntdReservadaProduto;
    }

    public Integer getQntdMinEstoque() {
        return qntdMinEstoque;
    }

    public void setQntdMinEstoque(Integer qntdMinEstoque) {
        this.qntdMinEstoque = qntdMinEstoque;
    }

    public Integer getQntdMaxEstoque() {
        return qntdMaxEstoque;
    }

    public void setQntdMaxEstoque(Integer qntdMaxEstoque) {
        this.qntdMaxEstoque = qntdMaxEstoque;
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
