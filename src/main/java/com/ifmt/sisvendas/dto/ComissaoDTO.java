package com.ifmt.sisvendas.dto;

import java.math.BigDecimal;
import java.time.LocalDate;

public class ComissaoDTO {

    private BigDecimal valor;
    private LocalDate data;
    private String status;
    private Integer idPromotor;
    private Integer idPedidoCliente;

    public BigDecimal getValor() {
        return valor;
    }

    public void setValor(BigDecimal valor) {
        this.valor = valor;
    }

    public LocalDate getData() {
        return data;
    }

    public void setData(LocalDate data) {
        this.data = data;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public Integer getIdPromotor() {
        return idPromotor;
    }

    public void setIdPromotor(Integer idPromotor) {
        this.idPromotor = idPromotor;
    }

    public Integer getIdPedidoCliente() {
        return idPedidoCliente;
    }

    public void setIdPedidoCliente(Integer idPedidoCliente) {
        this.idPedidoCliente = idPedidoCliente;
    }
}