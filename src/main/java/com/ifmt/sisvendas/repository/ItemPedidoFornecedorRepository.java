package com.ifmt.sisvendas.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.ifmt.sisvendas.model.ItemPedidoFornecedor;

/**
 * Repositório responsável pelas operações de persistência da entidade ItemPedidoFornecedor.
 *
 * Estende JpaRepository para herdar operações básicas de banco de dados,
 * como salvar, buscar, listar e excluir registros.
 */
public interface ItemPedidoFornecedorRepository extends JpaRepository<ItemPedidoFornecedor, Integer> {

    /**
     * Busca itens vinculados a um pedido de fornecedor.
     */
    List<ItemPedidoFornecedor> findByPedidoFornecedorIdPedidoFornecedor(Integer idPedidoFornecedor);
}
