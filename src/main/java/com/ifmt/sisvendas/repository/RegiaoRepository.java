package com.ifmt.sisvendas.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.ifmt.sisvendas.model.Regiao;

/**
 * Repositório responsável pelas operações de persistência da entidade Regiao.
 *
 * Estende JpaRepository para herdar operações básicas de banco de dados,
 * como salvar, buscar, listar e excluir registros.
 */
public interface RegiaoRepository extends JpaRepository<Regiao, Integer> {
    
}
