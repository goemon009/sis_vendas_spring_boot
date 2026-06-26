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

@Entity
@Table(name = "contareceber")
public class ContaReceber {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_contareceber")
    private Integer idContaReceber;

    @Column(name = "numero_parcela", nullable = false)
    private Integer numeroParcela;

    @Column(name = "valor", nullable = false)
    private BigDecimal valor;

    @Column(name = "dt_vencimento", nullable = false)
    private LocalDate dtVencimento;

    @Column(name = "dt_pagamento")
    private LocalDate dtPagamento;

    @Column(name = "status", nullable = false, length = 30)
    private String status;

    @ManyToOne
    @JoinColumn(name = "id_pedidocliente", nullable = false)
    private PedidoCliente pedidoCliente;

    public Integer getIdContaReceber() {
        return idContaReceber;
    }

    public void setIdContaReceber(Integer idContaReceber) {
        this.idContaReceber = idContaReceber;
    }

    public Integer getNumeroParcela() {
        return numeroParcela;
    }

    public void setNumeroParcela(Integer numeroParcela) {
        this.numeroParcela = numeroParcela;
    }

    public BigDecimal getValor() {
        return valor;
    }

    public void setValor(BigDecimal valor) {
        this.valor = valor;
    }

    public LocalDate getDtVencimento() {
        return dtVencimento;
    }

    public void setDtVencimento(LocalDate dtVencimento) {
        this.dtVencimento = dtVencimento;
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

    public PedidoCliente getPedidoCliente() {
        return pedidoCliente;
    }

    public void setPedidoCliente(PedidoCliente pedidoCliente) {
        this.pedidoCliente = pedidoCliente;
    }
}