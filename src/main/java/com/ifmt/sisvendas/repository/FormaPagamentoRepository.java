package com.ifmt.sisvendas.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.ifmt.sisvendas.model.FormaPagamento;

public interface FormaPagamentoRepository extends JpaRepository<FormaPagamento, Integer> {
}