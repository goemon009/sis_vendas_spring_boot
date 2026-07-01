package com.ifmt.sisvendas.model;

import java.math.BigDecimal;
import java.time.LocalDate;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;

/**
 * Entidade que representa um pagamento realizado a fornecedor.
 *
 * Registra informações de pagamento associadas às compras feitas
 * pela empresa.
 */
@Entity
@Table(name = "pagamentofornecedor")
public class PagamentoFornecedor {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_pagamentofornecedor")
    private Integer idPagamentoFornecedor;

    @Column(name = "valor", nullable = false)
    private BigDecimal valor;

    @Column(name = "parcelas", nullable = false)
    private Integer parcelas;

    @Column(name = "dt_pagamento")
    private LocalDate dtPagamento;

    @Column(name = "status", nullable = false, length = 50)
    private String status;

    @ManyToOne
    @JoinColumn(name = "id_pedidofornecedor", nullable = false)
    private PedidoFornecedor pedidoFornecedor;

    public Integer getIdPagamentoFornecedor() {
        return idPagamentoFornecedor;
    }

    public void setIdPagamentoFornecedor(Integer idPagamentoFornecedor) {
        this.idPagamentoFornecedor = idPagamentoFornecedor;
    }

    public BigDecimal getValor() {
        return valor;
    }

    public void setValor(BigDecimal valor) {
        this.valor = valor;
    }

    public Integer getParcelas() {
        return parcelas;
    }

    public void setParcelas(Integer parcelas) {
        this.parcelas = parcelas;
    }

    public LocalDate getDtPagamento() {
        return dtPagamento;
    }

    public void setDtPagamento(LocalDate dtPagamento) {
        this.dtPagamento = dtPagamento;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public PedidoFornecedor getPedidoFornecedor() {
        return pedidoFornecedor;
    }

    public void setPedidoFornecedor(PedidoFornecedor pedidoFornecedor) {
        this.pedidoFornecedor = pedidoFornecedor;
    }
}
