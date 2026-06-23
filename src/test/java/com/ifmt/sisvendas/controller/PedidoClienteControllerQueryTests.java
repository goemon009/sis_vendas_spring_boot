package com.ifmt.sisvendas.controller;

import static org.junit.jupiter.api.Assertions.assertSame;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.util.List;
import java.util.Map;
import java.util.Optional;

import org.junit.jupiter.api.Test;

import com.ifmt.sisvendas.model.ItemPedidoCliente;
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

    @Test
    void deveBuscarDetalhesDoPedidoComDadosGeraisEItens() {
        PedidoClienteRepository repository = mock(PedidoClienteRepository.class);
        ItemPedidoClienteRepository itemRepository = mock(ItemPedidoClienteRepository.class);
        PedidoClienteController controller = new PedidoClienteController(
                repository,
                itemRepository,
                mock(ProdutoRepository.class),
                mock(ComissaoRepository.class));
        PedidoCliente pedido = new PedidoCliente();
        List<ItemPedidoCliente> itens = List.of(new ItemPedidoCliente());

        when(repository.findById(10)).thenReturn(Optional.of(pedido));
        when(itemRepository.findByPedidoClienteIdPedidoCliente(10)).thenReturn(itens);

        Map<String, Object> resultado = controller.buscarDetalhes(10);

        assertSame(pedido, resultado.get("pedido"));
        assertSame(itens, resultado.get("itens"));
        verify(repository).findById(10);
        verify(itemRepository).findByPedidoClienteIdPedidoCliente(10);
    }
}
