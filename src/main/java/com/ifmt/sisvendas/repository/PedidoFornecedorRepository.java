package com.ifmt.sisvendas.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.ifmt.sisvendas.model.PedidoFornecedor;

/**
 * Repositório responsável pelas operações de persistência da entidade PedidoFornecedor.
 *
 * Estende JpaRepository para herdar operações básicas de banco de dados,
 * como salvar, buscar, listar e excluir registros.
 */
public interface PedidoFornecedorRepository extends JpaRepository<PedidoFornecedor, Integer> {
}
