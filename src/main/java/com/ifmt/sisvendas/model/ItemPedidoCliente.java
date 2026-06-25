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

@Entity
@Table(name = "itempedidocliente")
public class ItemPedidoCliente {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_itempedidocliente")
    private Integer idItemPedidoCliente;

    @Column(name = "qtd", nullable = false)
    private Integer qtd;

    @Column(name = "vl_unitario", nullable = false)
    private BigDecimal vlUnitario;

    @ManyToOne
    @JoinColumn(name = "id_pedidocliente", nullable = false)
    private PedidoCliente pedidoCliente;

    @ManyToOne
    @JoinColumn(name = "id_produto", nullable = false)
    private Produto produto;

    public Integer getIdItemPedidoCliente() {
        return idItemPedidoCliente;
    }

    public void setIdItemPedidoCliente(Integer idItemPedidoCliente) {
        this.idItemPedidoCliente = idItemPedidoCliente;
    }

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

    public PedidoCliente getPedidoCliente() {
        return pedidoCliente;
    }

    public void setPedidoCliente(PedidoCliente pedidoCliente) {
        this.pedidoCliente = pedidoCliente;
    }

    public Produto getProduto() {
        return produto;
    }

    public void setProduto(Produto produto) {
        this.produto = produto;
    }
}