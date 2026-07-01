package com.ifmt.sisvendas.model;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

/**
 * Entidade que representa uma forma de pagamento.
 *
 * É utilizada para registrar como os pedidos ou compras serão pagos,
 * como boleto, PIX, cartão de crédito ou débito.
 */
@Entity
@Table(name = "formapagamento")
public class FormaPagamento {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_formapagamento")
    private Integer idFormaPagamento;

    @Column(name = "descricao", nullable = false, length = 60)
    private String descricao;

    @Column(name = "tipo", nullable = false, length = 50)
    private String tipo;

    public Integer getIdFormaPagamento() {
        return idFormaPagamento;
    }

    public void setIdFormaPagamento(Integer idFormaPagamento) {
        this.idFormaPagamento = idFormaPagamento;
    }

    public String getDescricao() {
        return descricao;
    }

    public void setDescricao(String descricao) {
        this.descricao = descricao;
    }


    public String getTipo() {
        return tipo;
    }

    public void setTipo(String tipo) {
        this.tipo = tipo;
    }
}
