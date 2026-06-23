package com.ifmt.sisvendas.repository;

import java.time.LocalDate;
import java.util.List;

import com.ifmt.sisvendas.model.Comissao;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ComissaoRepository extends JpaRepository<Comissao, Integer> {

    List<Comissao> findByStatusAndPromotorIdPromotorAndDataBetween(
            String status,
            Integer idPromotor,
            LocalDate dataInicio,
            LocalDate dataFim
    );
}
