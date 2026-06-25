package com.ifmt.sisvendas.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.ifmt.sisvendas.model.CategoriaProduto;

public interface CategoriaProdutoRepository
        extends JpaRepository<CategoriaProduto, Integer> {

    List<CategoriaProduto> findAllByOrderByNomeAsc();

}