package com.ifmt.sisvendas.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.ifmt.sisvendas.model.Municipio;

public interface MunicipioRepository extends JpaRepository<Municipio, Integer> {

    @Query("""
            SELECT DISTINCT c.municipio
            FROM Cliente c
            WHERE c.promotor.idPromotor = :idPromotor
            ORDER BY c.municipio.nome ASC
            """)
    List<Municipio> buscarMunicipiosPorPromotor(@Param("idPromotor") Integer idPromotor);
}
