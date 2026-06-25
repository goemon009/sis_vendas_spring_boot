package com.ifmt.sisvendas.repository;

import java.time.LocalDate;
import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.ifmt.sisvendas.model.Cliente;

public interface ClienteRepository extends JpaRepository<Cliente, Integer> {

    Cliente findByCnpj(String cnpj);

    @Query("""
        SELECT c, SUM(p.vlTotal)
        FROM PedidoCliente p
        JOIN p.cliente c
        WHERE p.promotor.idPromotor = :idPromotor
        AND p.dtSolicitacao BETWEEN :dataInicio AND :dataFim
        GROUP BY c
        ORDER BY SUM(p.vlTotal) DESC
    """)
    List<Object[]> buscarClientesPorPromotorOrdenadosPorValorVendido(
            @Param("idPromotor") Integer idPromotor,
            @Param("dataInicio") LocalDate dataInicio,
            @Param("dataFim") LocalDate dataFim
    );
}