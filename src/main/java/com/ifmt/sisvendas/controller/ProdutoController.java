package com.ifmt.sisvendas.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
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

    @Autowired
    private ProdutoRepository repository;

    @GetMapping
    public List<Produto> listar() {
        return repository.findAll();
    }

    @GetMapping("/{id}")
    public Produto buscar(@PathVariable Integer id) {
        return repository.findById(id).orElse(null);
    }

    @PostMapping
    public Produto inserir(@RequestBody Produto produto) {
        return repository.save(produto);
    }

    @PutMapping("/{id}")
    public Produto alterar(@PathVariable Integer id,
                           @RequestBody Produto produto) {

        Produto produtoBanco =
                repository.findById(id).orElse(null);

        if (produtoBanco == null) {
            return null;
        }

        produto.setIdProduto(id);

        return repository.save(produto);
    }

    @DeleteMapping("/{id}")
    public void excluir(@PathVariable Integer id) {
        repository.deleteById(id);
    }
}
