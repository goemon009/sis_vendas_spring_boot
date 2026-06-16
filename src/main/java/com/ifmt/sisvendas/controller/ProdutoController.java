package com.ifmt.sisvendas.controller;

import java.util.List;

import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.ifmt.sisvendas.model.Produto;
import com.ifmt.sisvendas.repository.ProdutoRepository;

@RestController
@RequestMapping("/produtos")
public class ProdutoController {

    private final ProdutoRepository repository;

    public ProdutoController(ProdutoRepository repository) {
        this.repository = repository;
    }

    @GetMapping
    public List<Produto> listar() {
        return repository.findAll();
    }

    @PostMapping
    public Produto cadastrar(@RequestBody Produto produto) {
        return repository.save(produto);
    }

    @GetMapping("/{id}")
    public Produto buscarPorId(@PathVariable Integer id) {
        return repository.findById(id).orElse(null);
    }

    @PutMapping("/{id}")
    public Produto atualizar(@PathVariable Integer id, @RequestBody Produto dados) {
        Produto produto = repository.findById(id).orElse(null);

        if (produto == null) {
            return null;
        }

        produto.setNome(dados.getNome());
        produto.setVlCusto(dados.getVlCusto());
        produto.setQtdEstoque(dados.getQtdEstoque());
        produto.setQtdReservadaProduto(dados.getQtdReservadaProduto());
        produto.setQtdMinEstoque(dados.getQtdMinEstoque());
        produto.setQtdMaxEstoque(dados.getQtdMaxEstoque());
        produto.setPercentualComissao(dados.getPercentualComissao());
        produto.setPercentualPromocao(dados.getPercentualPromocao());
        produto.setMargemLucro(dados.getMargemLucro());
        produto.setCategoriaProduto(dados.getCategoriaProduto());

        return repository.save(produto);
    }

    @DeleteMapping("/{id}")
    public void excluir(@PathVariable Integer id) {
        repository.deleteById(id);
    }
}