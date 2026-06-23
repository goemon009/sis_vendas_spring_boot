package com.ifmt.sisvendas.controller;

import static org.junit.jupiter.api.Assertions.assertSame;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.util.List;

import org.junit.jupiter.api.Test;

import com.ifmt.sisvendas.model.PedidoCliente;
import com.ifmt.sisvendas.repository.ComissaoRepository;
import com.ifmt.sisvendas.repository.ItemPedidoClienteRepository;
import com.ifmt.sisvendas.repository.PedidoClienteRepository;
import com.ifmt.sisvendas.repository.ProdutoRepository;

class PedidoClienteControllerQueryTests {

    @Test
    void deveListarPedidosPorStatusOrdenadosPorDataDeSolicitacao() {
        PedidoClienteRepository repository = mock(PedidoClienteRepository.class);
        PedidoClienteController controller = new PedidoClienteController(
                repository,
                mock(ItemPedidoClienteRepository.class),
                mock(ProdutoRepository.class),
                mock(ComissaoRepository.class));
        List<PedidoCliente> pedidos = List.of(new PedidoCliente(), new PedidoCliente());

        when(repository.findByStatusOrderByDtSolicitacaoAsc("SOLICITADO")).thenReturn(pedidos);

        List<PedidoCliente> resultado = controller.listarPorStatus("SOLICITADO");

        assertSame(pedidos, resultado);
        verify(repository).findByStatusOrderByDtSolicitacaoAsc("SOLICITADO");
    }
}
