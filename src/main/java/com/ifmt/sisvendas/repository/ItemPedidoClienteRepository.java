package com.ifmt.sisvendas.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.ifmt.sisvendas.model.ItemPedidoCliente;

public interface ItemPedidoClienteRepository extends JpaRepository<ItemPedidoCliente, Integer> {

    List<ItemPedidoCliente> findByPedidoClienteIdPedidoCliente(Integer idPedidoCliente);
}