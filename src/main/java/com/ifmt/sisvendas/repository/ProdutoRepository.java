package com.ifmt.sisvendas.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.ifmt.sisvendas.model.Produto;

public interface ProdutoRepository extends JpaRepository<Produto, Integer> {

    List<Produto> findByCategoriaProdutoIdCategoriaProdutoOrderByNomeAsc(Integer idCategoriaProduto);

    List<Produto> findByCategoriaProdutoIdCategoriaProdutoOrderByQtdEstoqueDesc(Integer idCategoriaProduto);

    @Query("SELECT p FROM Produto p WHERE p.qtdEstoque < p.qtdMinEstoque")
    List<Produto> buscarProdutosAbaixoDoEstoqueMinimo();
}