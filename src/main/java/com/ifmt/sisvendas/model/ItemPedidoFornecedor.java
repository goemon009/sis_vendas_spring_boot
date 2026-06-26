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
@Table(name = "itempedidofornecedor")
public class ItemPedidoFornecedor {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_itempedidofornecedor")
    private Integer idItemPedidoFornecedor;

    @Column(name = "qtd", nullable = false)
    private Integer qtd;

    @Column(name = "vl_unitario", nullable = false)
    private BigDecimal vlUnitario;

    @ManyToOne
    @JoinColumn(name = "id_pedidofornecedor", nullable = false)
    private PedidoFornecedor pedidoFornecedor;

    @ManyToOne
    @JoinColumn(name = "id_produto", nullable = false)
    private Produto produto;

    public Integer getIdItemPedidoFornecedor() {
        return idItemPedidoFornecedor;
    }

    public void setIdItemPedidoFornecedor(Integer idItemPedidoFornecedor) {
        this.idItemPedidoFornecedor = idItemPedidoFornecedor;
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

    public PedidoFornecedor getPedidoFornecedor() {
        return pedidoFornecedor;
    }

    public void setPedidoFornecedor(PedidoFornecedor pedidoFornecedor) {
        this.pedidoFornecedor = pedidoFornecedor;
    }

    public Produto getProduto() {
        return produto;
    }

    public void setProduto(Produto produto) {
        this.produto = produto;
    }
}