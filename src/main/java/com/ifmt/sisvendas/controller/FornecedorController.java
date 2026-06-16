package com.ifmt.sisvendas.controller;

import com.ifmt.sisvendas.model.Fornecedor;
import com.ifmt.sisvendas.repository.FornecedorRepository;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/fornecedores")
public class FornecedorController {

    private final FornecedorRepository repository;

    public FornecedorController(FornecedorRepository repository) {
        this.repository = repository;
    }

    @GetMapping
    public List<Fornecedor> listar() {
        return repository.findAll();
    }

    @PostMapping
    public Fornecedor cadastrar(@RequestBody Fornecedor fornecedor) {
        return repository.save(fornecedor);
    }

    @GetMapping("/{id}")
    public Fornecedor buscarPorId(@PathVariable Integer id) {
        return repository.findById(id).orElse(null);
    }

    @PutMapping("/{id}")
    public Fornecedor atualizar(@PathVariable Integer id, @RequestBody Fornecedor dados) {
        Fornecedor fornecedor = repository.findById(id).orElse(null);

        if (fornecedor == null) {
            return null;
        }

        fornecedor.setNome(dados.getNome());
        fornecedor.setCnpj(dados.getCnpj());

        return repository.save(fornecedor);
    }

    @DeleteMapping("/{id}")
    public void excluir(@PathVariable Integer id) {
        repository.deleteById(id);
    }
}