package com.ifmt.sisvendas.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.ifmt.sisvendas.model.Produto;

public interface ProdutoRepository extends JpaRepository<Produto, Integer> {

    List<Produto> findByCategoriaProdutoIdCategoriaProdutoOrderByNomeAsc(Integer idCategoria);

    List<Produto> findByCategoriaProdutoIdCategoriaProdutoOrderByQtdEstoqueDesc(Integer idCategoria);

    @Query("""
            select p
            from Produto p
            where p.qtdEstoque < p.qntdMinEstoque
            order by p.qtdEstoque asc
            """)
    List<Produto> buscarProdutosAbaixoDoEstoqueMinimo();
}
}
