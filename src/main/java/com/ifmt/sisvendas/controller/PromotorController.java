package com.ifmt.sisvendas.controller;

import java.util.List;

import org.springframework.web.bind.annotation.*;

import com.ifmt.sisvendas.model.Promotor;
import com.ifmt.sisvendas.repository.PromotorRepository;

@RestController
@RequestMapping("/promotores")
public class PromotorController {

    private final PromotorRepository repository;

    public PromotorController(PromotorRepository repository) {
        this.repository = repository;
    }

    @GetMapping
    public List<Promotor> listar() {
        return repository.findAll();
    }

    @PostMapping
    public Promotor cadastrar(@RequestBody Promotor promotor) {
        return repository.save(promotor);
    }

    @GetMapping("/{id}")
    public Promotor buscarPorId(@PathVariable Integer id) {
        return repository.findById(id).orElse(null);
    }

    @PutMapping("/{id}")
    public Promotor atualizar(@PathVariable Integer id,
                              @RequestBody Promotor dados) {

        Promotor promotor =
                repository.findById(id).orElse(null);

        if (promotor == null) {
            return null;
        }

        promotor.setNome(dados.getNome());
        promotor.setCpf(dados.getCpf());

        return repository.save(promotor);
    }

    @DeleteMapping("/{id}")
    public void excluir(@PathVariable Integer id) {
        repository.deleteById(id);
    }
}
