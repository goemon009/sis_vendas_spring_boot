package com.ifmt.sisvendas.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.ifmt.sisvendas.model.PagamentoFornecedor;

/**
 * Repositório responsável pelas operações de persistência da entidade PagamentoFornecedor.
 *
 * Estende JpaRepository para herdar operações básicas de banco de dados,
 * como salvar, buscar, listar e excluir registros.
 */
public interface PagamentoFornecedorRepository extends JpaRepository<PagamentoFornecedor, Integer> {
}
