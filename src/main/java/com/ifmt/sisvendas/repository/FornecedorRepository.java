package com.ifmt.sisvendas.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.ifmt.sisvendas.model.Fornecedor;

public interface FornecedorRepository extends JpaRepository<Fornecedor, Integer> {
}