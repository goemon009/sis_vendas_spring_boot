package com.ifmt.sisvendas.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.ifmt.sisvendas.model.PedidoCliente;

public interface PedidoClienteRepository
        extends JpaRepository<PedidoCliente, Integer> {

}