package com.ifmt.sisvendas.dto;

import java.math.BigDecimal;

public class ItemPedidoClienteDTO {

    private Integer qtd;
    private BigDecimal vlUnitario;
    private Integer idPedidoCliente;
    private Integer idProduto;

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

    public Integer getIdPedidoCliente() {
        return idPedidoCliente;
    }

    public void setIdPedidoCliente(Integer idPedidoCliente) {
        this.idPedidoCliente = idPedidoCliente;
    }

    public Integer getIdProduto() {
        return idProduto;
    }

    public void setIdProduto(Integer idProduto) {
        this.idProduto = idProduto;
    }
}