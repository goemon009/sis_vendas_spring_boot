package com.ifmt.sisvendas.controller;

import static org.junit.jupiter.api.Assertions.assertSame;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.util.List;
import java.util.Map;
import java.util.Optional;

import org.junit.jupiter.api.Test;

import com.ifmt.sisvendas.model.ItemPedidoFornecedor;
import com.ifmt.sisvendas.model.PedidoFornecedor;
import com.ifmt.sisvendas.repository.ItemPedidoFornecedorRepository;
import com.ifmt.sisvendas.repository.PedidoFornecedorRepository;

class PedidoFornecedorControllerQueryTests {

    @Test
    void deveBuscarDetalhesDoPedidoFornecedorComDadosGeraisEItens() {
        PedidoFornecedorRepository repository = mock(PedidoFornecedorRepository.class);
        ItemPedidoFornecedorRepository itemRepository = mock(ItemPedidoFornecedorRepository.class);
        PedidoFornecedorController controller = new PedidoFornecedorController(
                repository,
                itemRepository);
        PedidoFornecedor pedido = new PedidoFornecedor();
        List<ItemPedidoFornecedor> itens = List.of(new ItemPedidoFornecedor());

        when(repository.findById(15)).thenReturn(Optional.of(pedido));
        when(itemRepository.findByPedidoFornecedorIdPedidoFornecedor(15)).thenReturn(itens);

        Map<String, Object> resultado = controller.buscarDetalhes(15);

        assertSame(pedido, resultado.get("pedidoFornecedor"));
        assertSame(itens, resultado.get("itens"));
        verify(repository).findById(15);
        verify(itemRepository).findByPedidoFornecedorIdPedidoFornecedor(15);
    }
}
