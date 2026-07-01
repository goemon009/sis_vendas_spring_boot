package com.ifmt.sisvendas.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.ifmt.sisvendas.model.CategoriaProduto;

/**
 * Repositório responsável pelas operações de persistência da entidade CategoriaProduto.
 *
 * Estende JpaRepository para herdar operações básicas de banco de dados,
 * como salvar, buscar, listar e excluir registros.
 */
public interface CategoriaProdutoRepository
        extends JpaRepository<CategoriaProduto, Integer> {

    /**
     * Busca todas as categorias de produto ordenadas pelo nome.
     */
    List<CategoriaProduto> findAllByOrderByNomeAsc();

}
