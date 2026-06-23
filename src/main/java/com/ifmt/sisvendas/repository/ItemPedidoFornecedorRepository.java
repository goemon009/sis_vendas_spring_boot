package com.ifmt.sisvendas.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.ifmt.sisvendas.model.ItemPedidoFornecedor;

public interface ItemPedidoFornecedorRepository extends JpaRepository<ItemPedidoFornecedor, Integer> {

    List<ItemPedidoFornecedor> findByPedidoFornecedorIdPedidoFornecedor(Integer idPedidoFornecedor);
}
