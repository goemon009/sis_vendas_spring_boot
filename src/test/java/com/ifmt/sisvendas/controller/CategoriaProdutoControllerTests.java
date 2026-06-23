package com.ifmt.sisvendas.controller;

import static org.junit.jupiter.api.Assertions.assertSame;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.util.List;

import org.junit.jupiter.api.Test;

import com.ifmt.sisvendas.model.CategoriaProduto;
import com.ifmt.sisvendas.repository.CategoriaProdutoRepository;

class CategoriaProdutoControllerTests {

    @Test
    void deveListarCategoriasOrdenadasPorNome() {
        CategoriaProdutoRepository repository = mock(CategoriaProdutoRepository.class);
        CategoriaProdutoController controller = new CategoriaProdutoController(repository);
        List<CategoriaProduto> categorias = List.of(new CategoriaProduto(), new CategoriaProduto());

        when(repository.findAllByOrderByNomeAsc()).thenReturn(categorias);

        List<CategoriaProduto> resultado = controller.listarOrdenadasPorNome();

        assertSame(categorias, resultado);
        verify(repository).findAllByOrderByNomeAsc();
    }
}
