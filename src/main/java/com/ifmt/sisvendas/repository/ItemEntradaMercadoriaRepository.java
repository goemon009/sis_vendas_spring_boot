package com.ifmt.sisvendas.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.ifmt.sisvendas.model.ItemEntradaMercadoria;

public interface ItemEntradaMercadoriaRepository extends JpaRepository<ItemEntradaMercadoria, Integer> {

    List<ItemEntradaMercadoria> findByEntradaMercadoriaIdEntradaMercadoria(Integer idEntradaMercadoria);
}