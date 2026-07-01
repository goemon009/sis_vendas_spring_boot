package com.ifmt.sisvendas.model;

import java.math.BigDecimal;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;

/**
 * Entidade que representa um produto comercializado pela empresa.
 *
 * Armazena dados de cadastro, valores comerciais, controle de estoque,
 * promoção e percentual de comissão.
 */
@Entity
@Table(name = "produto")
public class Produto {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_produto")
    private Integer idProduto;

    @Column(name = "nome", nullable = false, length = 100)
    private String nome;

    @Column(name = "vl_custo", nullable = false)
    private BigDecimal vlCusto;

    @Column(name = "qtd_estoque", nullable = false)
    private Integer qtdEstoque;

    @Column(name = "qntd_reservada_produto", nullable = false)
    private Integer qntdReservadaProduto;

    @Column(name = "qntd_min_estoque", nullable = false)
    private Integer qntdMinEstoque;

    @Column(name = "qntd_max_estoque", nullable = false)
    private Integer qntdMaxEstoque;

    @Column(name = "percentual_comissao", nullable = false)
    private BigDecimal percentualComissao;

    @Column(name = "percentual_promocao")
    private BigDecimal percentualPromocao;

    @Column(name = "margem_lucro", nullable = false)
    private BigDecimal margemLucro;

    @ManyToOne
    @JoinColumn(name = "id_categoria_produto", nullable = false)
    private CategoriaProduto categoriaProduto;

    public Integer getIdProduto() {
        return idProduto;
    }

    public void setIdProduto(Integer idProduto) {
        this.idProduto = idProduto;
    }

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

    public CategoriaProduto getCategoriaProduto() {
        return categoriaProduto;
    }

    public void setCategoriaProduto(CategoriaProduto categoriaProduto) {
        this.categoriaProduto = categoriaProduto;
    }

    public void setQtdReservadaProduto(Integer qtdReservadaProduto) {
        this.qntdReservadaProduto = qtdReservadaProduto;
    }
}
