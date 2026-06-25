package com.ifmt.sisvendas.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.ifmt.sisvendas.model.PagamentoCliente;

public interface PagamentoClienteRepository extends JpaRepository<PagamentoCliente, Integer> {
}