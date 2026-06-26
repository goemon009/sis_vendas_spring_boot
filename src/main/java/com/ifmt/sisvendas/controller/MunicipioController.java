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

import com.ifmt.sisvendas.dto.MunicipioDTO;
import com.ifmt.sisvendas.model.Municipio;
import com.ifmt.sisvendas.model.Regiao;
import com.ifmt.sisvendas.repository.MunicipioRepository;
import com.ifmt.sisvendas.repository.RegiaoRepository;

@RestController
@RequestMapping("/municipios")
public class MunicipioController {

    private final MunicipioRepository repository;
    private final RegiaoRepository regiaoRepository;

    public MunicipioController(
            MunicipioRepository repository,
            RegiaoRepository regiaoRepository) {
        this.repository = repository;
        this.regiaoRepository = regiaoRepository;
    }

    @GetMapping
    public List<Municipio> listar() {
        return repository.findAll();
    }

    @PostMapping
    public Municipio cadastrar(@RequestBody MunicipioDTO municipioDTO) {
        Regiao regiao = regiaoRepository.findById(municipioDTO.getIdRegiao()).orElse(null);

        if (regiao == null) {
            return null;
        }

        Municipio municipio = new Municipio();
        aplicarDadosDTO(municipio, municipioDTO, regiao);

        return repository.save(municipio);
    }

    @GetMapping("/{id}")
    public Municipio buscarPorId(@PathVariable Integer id) {
        return repository.findById(id).orElse(null);
    }

    @PutMapping("/{id}")
    public Municipio atualizar(@PathVariable Integer id, @RequestBody MunicipioDTO municipioDTO) {
        Municipio municipio = repository.findById(id).orElse(null);

        if (municipio == null) {
            return null;
        }

        Regiao regiao = regiaoRepository.findById(municipioDTO.getIdRegiao()).orElse(null);

        if (regiao == null) {
            return null;
        }

        aplicarDadosDTO(municipio, municipioDTO, regiao);

        return repository.save(municipio);
    }

    @DeleteMapping("/{id}")
    public void excluir(@PathVariable Integer id) {
        repository.deleteById(id);
    }

    private void aplicarDadosDTO(
            Municipio municipio,
            MunicipioDTO municipioDTO,
            Regiao regiao) {

        municipio.setNome(municipioDTO.getNome());
        municipio.setUf(municipioDTO.getUf());
        municipio.setRegiao(regiao);
    }
}