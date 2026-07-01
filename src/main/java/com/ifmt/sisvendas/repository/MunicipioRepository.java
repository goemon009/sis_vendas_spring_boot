package com.ifmt.sisvendas.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.ifmt.sisvendas.model.Municipio;

/**
 * Repositório responsável pelas operações de persistência da entidade Municipio.
 *
 * Estende JpaRepository para herdar operações básicas de banco de dados,
 * como salvar, buscar, listar e excluir registros.
 */
public interface MunicipioRepository extends JpaRepository<Municipio, Integer> {

    /**
     * Busca municípios de uma região ordenados pelo nome.
     */
    List<Municipio> findByRegiaoIdOrderByNomeAsc(Integer idRegiao);

}
