package com.ifmt.sisvendas.dto;

import java.math.BigDecimal;

/**
 * DTO usado para receber dados de entrada da entidade ItemPedidoFornecedor.
 *
 * Essa classe simplifica o corpo das requisições e evita que o cliente
 * da API precise enviar a entidade completa com todos os relacionamentos.
 */
public class ItemPedidoFornecedorDTO {

    private Integer qtd;
    private BigDecimal vlUnitario;
    private Integer idPedidoFornecedor;
    private Integer idProduto;

    public Integer getQtd() {
        return qtd;
    }

    public void setQtd(Integer qtd) {
        this.qtd = qtd;
    }

    public BigDecimal getVlUnitario() {
        return vlUnitario;
    }

    public void setVlUnitario(BigDecimal vlUnitario) {
        this.vlUnitario = vlUnitario;
    }

    public Integer getIdPedidoFornecedor() {
        return idPedidoFornecedor;
    }

    public void setIdPedidoFornecedor(Integer idPedidoFornecedor) {
        this.idPedidoFornecedor = idPedidoFornecedor;
    }

    public Integer getIdProduto() {
        return idProduto;
    }

    public void setIdProduto(Integer idProduto) {
        this.idProduto = idProduto;
    }
}
