package com.ifmt.sisvendas.model;

import jakarta.persistence.*;
import java.math.BigDecimal;

@Entity
@Table(name = "itementradamercadoria")
public class ItemEntradaMercadoria {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_itementradamercadoria")
    private Integer idItemEntradaMercadoria;

    @Column(name = "qtd", nullable = false)
    private Integer qtd;

    @Column(name = "vl_unitario", nullable = false)
    private BigDecimal vlUnitario;

    @ManyToOne
    @JoinColumn(name = "id_entradamercadoria", nullable = false)
    private EntradaMercadoria entradaMercadoria;

    @ManyToOne
    @JoinColumn(name = "id_produto", nullable = false)
    private Produto produto;

    public Integer getIdItemEntradaMercadoria() {
        return idItemEntradaMercadoria;
    }

    public void setIdItemEntradaMercadoria(Integer idItemEntradaMercadoria) {
        this.idItemEntradaMercadoria = idItemEntradaMercadoria;
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

    public EntradaMercadoria getEntradaMercadoria() {
        return entradaMercadoria;
    }

    public void setEntradaMercadoria(EntradaMercadoria entradaMercadoria) {
        this.entradaMercadoria = entradaMercadoria;
    }

    public Produto getProduto() {
        return produto;
    }

    public void setProduto(Produto produto) {
        this.produto = produto;
    }
}