package com.ifmt.sisvendas.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.ifmt.sisvendas.model.Promotor;

/**
 * Repositório responsável pelas operações de persistência da entidade Promotor.
 *
 * Estende JpaRepository para herdar operações básicas de banco de dados,
 * como salvar, buscar, listar e excluir registros.
 */
public interface PromotorRepository extends JpaRepository<Promotor, Integer> {

}
