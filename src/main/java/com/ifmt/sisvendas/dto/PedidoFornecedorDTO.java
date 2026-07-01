package com.ifmt.sisvendas.dto;

import java.math.BigDecimal;
import java.time.LocalDate;

/**
 * DTO usado para receber dados de entrada da entidade PedidoFornecedor.
 *
 * Essa classe simplifica o corpo das requisições e evita que o cliente
 * da API precise enviar a entidade completa com todos os relacionamentos.
 */
public class PedidoFornecedorDTO {

    private LocalDate data;
    private BigDecimal vlTotal;
    private String status;
    private Integer idFornecedor;

    public LocalDate getData() {
        return data;
    }

    public void setData(LocalDate data) {
        this.data = data;
    }

    public BigDecimal getVlTotal() {
        return vlTotal;
    }

    public void setVlTotal(BigDecimal vlTotal) {
        this.vlTotal = vlTotal;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public Integer getIdFornecedor() {
        return idFornecedor;
    }

    public void setIdFornecedor(Integer idFornecedor) {
        this.idFornecedor = idFornecedor;
    }
}
