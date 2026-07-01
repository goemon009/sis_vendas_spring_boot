package com.ifmt.sisvendas.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.ifmt.sisvendas.model.ItemPedidoCliente;

/**
 * Repositório responsável pelas operações de persistência da entidade ItemPedidoCliente.
 *
 * Estende JpaRepository para herdar operações básicas de banco de dados,
 * como salvar, buscar, listar e excluir registros.
 */
public interface ItemPedidoClienteRepository extends JpaRepository<ItemPedidoCliente, Integer> {

    /**
     * Busca itens vinculados a um pedido de cliente.
     */
    List<ItemPedidoCliente> findByPedidoClienteIdPedidoCliente(Integer idPedidoCliente);
}
