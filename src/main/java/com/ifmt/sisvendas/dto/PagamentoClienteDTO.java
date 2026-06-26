package com.ifmt.sisvendas.dto;

import java.math.BigDecimal;
import java.time.LocalDate;

public class PagamentoClienteDTO {

    private BigDecimal valor;
    private Integer parcelas;
    private LocalDate dtPagamento;
    private String status;
    private Integer idFormaPagamento;
    private Integer idPedidoCliente;

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

    public Integer getIdFormaPagamento() {
        return idFormaPagamento;
    }

    public void setIdFormaPagamento(Integer idFormaPagamento) {
        this.idFormaPagamento = idFormaPagamento;
    }

    public Integer getIdPedidoCliente() {
        return idPedidoCliente;
    }

    public void setIdPedidoCliente(Integer idPedidoCliente) {
        this.idPedidoCliente = idPedidoCliente;
    }
}