package com.ifmt.sisvendas.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.ifmt.sisvendas.model.Produto;

public interface ProdutoRepository extends JpaRepository<Produto, Integer> {
    
}
