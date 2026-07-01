package com.ifmt.sisvendas.repository;

import java.time.LocalDate;
import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.ifmt.sisvendas.model.PedidoCliente;

/**
 * Repositório responsável pelas operações de persistência da entidade PedidoCliente.
 *
 * Estende JpaRepository para herdar operações básicas de banco de dados,
 * como salvar, buscar, listar e excluir registros.
 */
public interface PedidoClienteRepository
        extends JpaRepository<PedidoCliente, Integer> {

    /**
     * Busca pedidos de cliente por status.
     */
    List<PedidoCliente> findByStatus(String status);

    /**
     * Busca pedidos de cliente por promotor e intervalo de data de solicitação.
     */
    List<PedidoCliente> findByPromotorIdPromotorAndDtSolicitacaoBetween(
            Integer idPromotor,
            LocalDate dataInicio,
            LocalDate dataFim
    );
}
