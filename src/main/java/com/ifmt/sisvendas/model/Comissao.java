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
@Table(name = "comissao")
public class Comissao {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_comissao")
    private Integer idComissao;

    @Column(name = "valor", nullable = false)
    private BigDecimal valor;

    @Column(name = "data", nullable = false)
    private LocalDate data;

    @Column(name = "status", nullable = false, length = 20)
    private String status;

    @ManyToOne
    @JoinColumn(name = "id_promotor", nullable = false)
    private Promotor promotor;

    @ManyToOne
    @JoinColumn(name = "id_pedidocliente", nullable = false)
    private PedidoCliente pedidoCliente;

    public Integer getIdComissao() {
        return idComissao;
    }

    public void setIdComissao(Integer idComissao) {
        this.idComissao = idComissao;
    }

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

    public Promotor getPromotor() {
        return promotor;
    }

    public void setPromotor(Promotor promotor) {
        this.promotor = promotor;
    }

    public PedidoCliente getPedidoCliente() {
        return pedidoCliente;
    }

    public void setPedidoCliente(PedidoCliente pedidoCliente) {
        this.pedidoCliente = pedidoCliente;
    }
}