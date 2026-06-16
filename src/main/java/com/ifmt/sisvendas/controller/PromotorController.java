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

import com.ifmt.sisvendas.model.Municipio;
import com.ifmt.sisvendas.model.Promotor;
import com.ifmt.sisvendas.repository.MunicipioRepository;
import com.ifmt.sisvendas.repository.PromotorRepository;

@RestController
@RequestMapping("/promotores")
public class PromotorController {

    private final PromotorRepository repository;
    private final MunicipioRepository municipioRepository;

    public PromotorController(
            PromotorRepository repository,
            MunicipioRepository municipioRepository) {
        this.repository = repository;
        this.municipioRepository = municipioRepository;
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

    @GetMapping("/{id}/municipios")
    public List<Municipio> listarMunicipiosAtendidos(@PathVariable Integer id) {
        Promotor promotor = repository.findById(id).orElse(null);

        if (promotor == null) {
            return null;
        }

        return municipioRepository.findByRegiaoIdOrderByNomeAsc(
        promotor.getRegiao().getId()
);
    }

    @PutMapping("/{id}")
    public Promotor atualizar(@PathVariable Integer id,
                              @RequestBody Promotor dados) {

        Promotor promotor = repository.findById(id).orElse(null);

        if (promotor == null) {
            return null;
        }

        promotor.setNome(dados.getNome());
        promotor.setCpf(dados.getCpf());
        promotor.setRegiao(dados.getRegiao());

        return repository.save(promotor);
    }

    @DeleteMapping("/{id}")
    public void excluir(@PathVariable Integer id) {
        repository.deleteById(id);
    }
}