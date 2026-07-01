package com.ifmt.sisvendas.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.ifmt.sisvendas.model.ItemEntradaMercadoria;

/**
 * Repositório responsável pelas operações de persistência da entidade ItemEntradaMercadoria.
 *
 * Estende JpaRepository para herdar operações básicas de banco de dados,
 * como salvar, buscar, listar e excluir registros.
 */
public interface ItemEntradaMercadoriaRepository extends JpaRepository<ItemEntradaMercadoria, Integer> {

    /**
     * Busca itens vinculados a uma entrada de mercadoria.
     */
    List<ItemEntradaMercadoria> findByEntradaMercadoriaIdEntradaMercadoria(Integer idEntradaMercadoria);
}
