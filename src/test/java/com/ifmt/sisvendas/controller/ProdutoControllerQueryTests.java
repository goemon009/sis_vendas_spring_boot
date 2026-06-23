package com.ifmt.sisvendas.controller;

import static org.junit.jupiter.api.Assertions.assertSame;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.util.List;

import org.junit.jupiter.api.Test;

import com.ifmt.sisvendas.model.Produto;
import com.ifmt.sisvendas.repository.ProdutoRepository;

class ProdutoControllerQueryTests {

    private final ProdutoRepository repository = mock(ProdutoRepository.class);
    private final ProdutoController controller = new ProdutoController(repository);
    private final List<Produto> produtos = List.of(new Produto(), new Produto());

    @Test
    void deveListarProdutosDaCategoriaOrdenadosPorNome() {
        when(repository.findByCategoriaProdutoIdCategoriaProdutoOrderByNomeAsc(1)).thenReturn(produtos);

        List<Produto> resultado = controller.listarPorCategoriaOrdenadoPorNome(1);

        assertSame(produtos, resultado);
        verify(repository).findByCategoriaProdutoIdCategoriaProdutoOrderByNomeAsc(1);
    }

    @Test
    void deveListarProdutosDaCategoriaOrdenadosPorEstoque() {
        when(repository.findByCategoriaProdutoIdCategoriaProdutoOrderByQtdEstoqueDesc(1)).thenReturn(produtos);

        List<Produto> resultado = controller.listarPorCategoriaOrdenadoPorEstoque(1);

        assertSame(produtos, resultado);
        verify(repository).findByCategoriaProdutoIdCategoriaProdutoOrderByQtdEstoqueDesc(1);
    }

    @Test
    void deveListarProdutosAbaixoDoEstoqueMinimo() {
        when(repository.buscarProdutosAbaixoDoEstoqueMinimo()).thenReturn(produtos);

        List<Produto> resultado = controller.listarAbaixoDoEstoqueMinimo();

        assertSame(produtos, resultado);
        verify(repository).buscarProdutosAbaixoDoEstoqueMinimo();
    }
}
