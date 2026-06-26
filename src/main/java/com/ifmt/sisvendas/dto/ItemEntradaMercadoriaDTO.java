package com.ifmt.sisvendas.dto;

import java.math.BigDecimal;

public class ItemEntradaMercadoriaDTO {

    private Integer qtd;
    private BigDecimal vlUnitario;
    private Integer idEntradaMercadoria;
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

    public Integer getIdEntradaMercadoria() {
        return idEntradaMercadoria;
    }

    public void setIdEntradaMercadoria(Integer idEntradaMercadoria) {
        this.idEntradaMercadoria = idEntradaMercadoria;
    }

    public Integer getIdProduto() {
        return idProduto;
    }

    public void setIdProduto(Integer idProduto) {
        this.idProduto = idProduto;
    }
}