package com.ifmt.sisvendas.controller;

import static org.junit.jupiter.api.Assertions.assertSame;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.util.List;
import java.util.Map;
import java.util.Optional;

import org.junit.jupiter.api.Test;

import com.ifmt.sisvendas.model.EntradaMercadoria;
import com.ifmt.sisvendas.model.ItemEntradaMercadoria;
import com.ifmt.sisvendas.repository.EntradaMercadoriaRepository;
import com.ifmt.sisvendas.repository.ItemEntradaMercadoriaRepository;
import com.ifmt.sisvendas.repository.ProdutoRepository;

class EntradaMercadoriaControllerQueryTests {

    @Test
    void deveBuscarDetalhesDaEntradaComDadosGeraisEItens() {
        EntradaMercadoriaRepository repository = mock(EntradaMercadoriaRepository.class);
        ItemEntradaMercadoriaRepository itemRepository = mock(ItemEntradaMercadoriaRepository.class);
        EntradaMercadoriaController controller = new EntradaMercadoriaController(
                repository,
                itemRepository,
                mock(ProdutoRepository.class));
        EntradaMercadoria entrada = new EntradaMercadoria();
        List<ItemEntradaMercadoria> itens = List.of(new ItemEntradaMercadoria());

        when(repository.findById(12)).thenReturn(Optional.of(entrada));
        when(itemRepository.findByEntradaMercadoriaIdEntradaMercadoria(12)).thenReturn(itens);

        Map<String, Object> resultado = controller.buscarDetalhes(12);

        assertSame(entrada, resultado.get("entradaMercadoria"));
        assertSame(itens, resultado.get("itens"));
        verify(repository).findById(12);
        verify(itemRepository).findByEntradaMercadoriaIdEntradaMercadoria(12);
    }
}
