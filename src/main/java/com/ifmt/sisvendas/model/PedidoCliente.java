package com.ifmt.sisvendas.model;

import java.math.BigDecimal;
import java.time.LocalDate;

import jakarta.persistence.*;

@Entity
@Table(name = "pedidocliente")
public class PedidoCliente {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_pedidocliente")
    private Integer idPedidoCliente;

    @Column(name = "vl_total")
    private BigDecimal vlTotal;

    @Column(name = "dt_solicitacao")
    private LocalDate dtSolicitacao;

    @Column(name = "dt_programacaoentrega")
    private LocalDate dtProgramacaoEntrega;

    @Column(name = "status")
    private String status;

    @ManyToOne
    @JoinColumn(name = "id_cliente")
    private Cliente cliente;

    @ManyToOne
    @JoinColumn(name = "id_promotor")
    private Promotor promotor;

    public Integer getIdPedidoCliente() {
        return idPedidoCliente;
    }

    public void setIdPedidoCliente(Integer idPedidoCliente) {
        this.idPedidoCliente = idPedidoCliente;
    }

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

    public Cliente getCliente() {
        return cliente;
    }

    public void setCliente(Cliente cliente) {
        this.cliente = cliente;
    }

    public Promotor getPromotor() {
        return promotor;
    }

    public void setPromotor(Promotor promotor) {
        this.promotor = promotor;
    }
}
