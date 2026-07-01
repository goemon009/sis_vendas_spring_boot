package com.ifmt.sisvendas.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.ifmt.sisvendas.model.Produto;

/**
 * Repositório responsável pelas operações de persistência da entidade Produto.
 *
 * Estende JpaRepository para herdar operações básicas de banco de dados,
 * como salvar, buscar, listar e excluir registros.
 */
public interface ProdutoRepository extends JpaRepository<Produto, Integer> {

    /**
     * Busca produtos de uma categoria ordenados pelo nome.
     */
    List<Produto> findByCategoriaProdutoIdCategoriaProdutoOrderByNomeAsc(Integer idCategoria);

    /**
     * Busca produtos de uma categoria ordenados pela quantidade em estoque.
     */
    List<Produto> findByCategoriaProdutoIdCategoriaProdutoOrderByQtdEstoqueDesc(Integer idCategoria);

    /**
     * Busca produtos com estoque abaixo da quantidade mínima cadastrada.
     */
    @Query("""
            select p
            from Produto p
            where p.qtdEstoque < p.qntdMinEstoque
            order by p.qtdEstoque asc
            """)
    List<Produto> buscarProdutosAbaixoDoEstoqueMinimo();
}
