package com.ifmt.sisvendas.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.ifmt.sisvendas.model.EntradaMercadoria;

/**
 * Repositório responsável pelas operações de persistência da entidade EntradaMercadoria.
 *
 * Estende JpaRepository para herdar operações básicas de banco de dados,
 * como salvar, buscar, listar e excluir registros.
 */
public interface EntradaMercadoriaRepository extends JpaRepository<EntradaMercadoria, Integer> {
}
