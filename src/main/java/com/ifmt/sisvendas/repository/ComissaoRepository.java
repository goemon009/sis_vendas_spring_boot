package com.ifmt.sisvendas.repository;

import java.time.LocalDate;
import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.ifmt.sisvendas.model.Comissao;

/**
 * Repositório responsável pelas operações de persistência da entidade Comissao.
 *
 * Estende JpaRepository para herdar operações básicas de banco de dados,
 * como salvar, buscar, listar e excluir registros.
 */
public interface ComissaoRepository extends JpaRepository<Comissao, Integer> {

    /**
     * Busca comissões por status, promotor e intervalo de data.
     */
    List<Comissao> findByStatusAndPromotorIdPromotorAndDataBetween(
            String status,
            Integer idPromotor,
            LocalDate dataInicio,
            LocalDate dataFim
    );
}
