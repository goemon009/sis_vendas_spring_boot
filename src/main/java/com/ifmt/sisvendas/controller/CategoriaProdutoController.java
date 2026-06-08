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

import com.ifmt.sisvendas.model.CategoriaProduto;
import com.ifmt.sisvendas.repository.CategoriaProdutoRepository;

@RestController
@RequestMapping("/categorias")
public class CategoriaProdutoController {

    private final CategoriaProdutoRepository repository;

    public CategoriaProdutoController(CategoriaProdutoRepository repository) {
        this.repository = repository;
    }

    @GetMapping
    public List<CategoriaProduto> listar() {
        return repository.findAll();
    }

    @PostMapping
    public CategoriaProduto cadastrar(@RequestBody CategoriaProduto categoriaProduto) {
        return repository.save(categoriaProduto);
    }

    @GetMapping("/{id}")
    public CategoriaProduto buscarPorId(@PathVariable Integer id) {
        return repository.findById(id).orElse(null);
    }

    @PutMapping("/{id}")
    public CategoriaProduto atualizar(@PathVariable Integer id, @RequestBody CategoriaProduto dados) {
        CategoriaProduto categoria = repository.findById(id).orElse(null);

        if (categoria == null) {
            return null;
        }
        
        categoria.setNome(dados.getNome());
        categoria.setPercentualComissao(dados.getPercentualComissao());
        categoria.setPercentualDesconto(dados.getPercentualDesconto());
        
        return repository.save(categoria);
    }
    
    @DeleteMapping("/{id}")
    public void excluir(@PathVariable Integer id) {
        repository.deleteById(id);
    }

}