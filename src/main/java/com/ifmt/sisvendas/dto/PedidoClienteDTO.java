package com.ifmt.sisvendas.dto;

import java.math.BigDecimal;
import java.time.LocalDate;

/**
 * DTO usado para receber dados de entrada da entidade PedidoCliente.
 *
 * Essa classe simplifica o corpo das requisições e evita que o cliente
 * da API precise enviar a entidade completa com todos os relacionamentos.
 */
public class PedidoClienteDTO {

    private BigDecimal vlTotal;
    private LocalDate dtSolicitacao;
    private LocalDate dtProgramacaoEntrega;
    private String status;
    private Integer idCliente;
    private Integer idPromotor;

    public BigDecimal getVlTotal() {
        return vlTotal;
    }

    public void setVlTotal(BigDecimal vlTotal) {
        this.vlTotal = vlTotal;
    }

    public LocalDate getDtSolicitacao() {
        return dtSolicitacao;
    }

    public void setDtSolicitacao(LocalDate dtSolicitacao) {
        this.dtSolicitacao = dtSolicitacao;
    }

    public LocalDate getDtProgramacaoEntrega() {
        return dtProgramacaoEntrega;
    }

    public void setDtProgramacaoEntrega(LocalDate dtProgramacaoEntrega) {
        this.dtProgramacaoEntrega = dtProgramacaoEntrega;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public Integer getIdCliente() {
        return idCliente;
    }

    public void setIdCliente(Integer idCliente) {
        this.idCliente = idCliente;
    }

    public Integer getIdPromotor() {
        return idPromotor;
    }

    public void setIdPromotor(Integer idPromotor) {
        this.idPromotor = idPromotor;
    }
}
