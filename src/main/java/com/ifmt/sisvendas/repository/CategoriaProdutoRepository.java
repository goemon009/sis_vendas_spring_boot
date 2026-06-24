package com.ifmt.sisvendas.repository;

import com.ifmt.sisvendas.model.CategoriaProduto;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface CategoriaProdutoRepository extends JpaRepository<CategoriaProduto, Integer> {

    List<CategoriaProduto> findAllByOrderByNomeAsc();
}