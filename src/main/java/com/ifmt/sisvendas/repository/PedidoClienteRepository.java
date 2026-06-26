package com.ifmt.sisvendas.repository;

import java.time.LocalDate;
import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.ifmt.sisvendas.model.PedidoCliente;

public interface PedidoClienteRepository
        extends JpaRepository<PedidoCliente, Integer> {

    List<PedidoCliente> findByStatus(String status);

    List<PedidoCliente> findByPromotorIdPromotorAndDtSolicitacaoBetween(
            Integer idPromotor,
            LocalDate dataInicio,
            LocalDate dataFim
    );
}