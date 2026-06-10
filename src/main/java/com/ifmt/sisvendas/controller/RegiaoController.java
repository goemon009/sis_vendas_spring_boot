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

import com.ifmt.sisvendas.model.Regiao;
import com.ifmt.sisvendas.repository.RegiaoRepository;

@RestController
@RequestMapping("/regioes")
public class RegiaoController {
    
    private final RegiaoRepository repository;

    public RegiaoController(RegiaoRepository repository) {
        this.repository = repository;
    }

    @GetMapping
    public List<Regiao> listar() {
        return repository.findAll();
    }

    @PostMapping
    public Regiao cadastrar(@RequestBody Regiao regiao){
        return repository.save(regiao);
    }
    
    @GetMapping("/{id}")
    public Regiao buscarPorId(@PathVariable Integer id) {
        return repository.findById(id).orElse(null);
    }

    @PutMapping("/{id}")
    public Regiao atualizar(@PathVariable Integer id, @RequestBody Regiao dados) {
        Regiao regiao = repository.findById(id).orElse(null);

        if (regiao == null) {
            return null;
        }

        regiao.setNome(dados.getNome());

        return repository.save(regiao);
    }

    @DeleteMapping("/{id}")
    public void excluir(@PathVariable Integer id) {
        repository.deleteById(id);
    }
}
