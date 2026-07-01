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
 * Entidade que representa um pedido realizado a um fornecedor.
 *
 * Esse pedido registra uma solicitação de compra de produtos
 * para reposição de estoque.
 */
@Entity
@Table(name = "pedidofornecedor")
public class PedidoFornecedor {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_pedidofornecedor")
    private Integer idPedidoFornecedor;

    @Column(name = "data", nullable = false)
    private LocalDate data;

    @Column(name = "vl_total", nullable = false)
    private BigDecimal vlTotal;

    @Column(name = "status", nullable = false, length = 50)
    private String status;

    @ManyToOne
    @JoinColumn(name = "id_fornecedor", nullable = false)
    private Fornecedor fornecedor;

    public Integer getIdPedidoFornecedor() {
        return idPedidoFornecedor;
    }

    public void setIdPedidoFornecedor(Integer idPedidoFornecedor) {
        this.idPedidoFornecedor = idPedidoFornecedor;
    }

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

    public Fornecedor getFornecedor() {
        return fornecedor;
    }

    public void setFornecedor(Fornecedor fornecedor) {
        this.fornecedor = fornecedor;
    }
}
