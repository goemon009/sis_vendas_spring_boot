package com.ifmt.sisvendas.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.ifmt.sisvendas.model.PedidoCliente;

public interface PedidoClienteRepository extends JpaRepository<PedidoCliente, Integer> {

    List<PedidoCliente> findByStatusOrderByDtSolicitacaoAsc(String status);
}
