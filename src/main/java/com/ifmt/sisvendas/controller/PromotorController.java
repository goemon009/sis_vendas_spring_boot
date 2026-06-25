package com.ifmt.sisvendas.controller;

import java.util.List;

import org.springframework.web.bind.annotation.*;

import com.ifmt.sisvendas.dto.PromotorDTO;
import com.ifmt.sisvendas.model.Municipio;
import com.ifmt.sisvendas.model.Promotor;
import com.ifmt.sisvendas.model.Regiao;
import com.ifmt.sisvendas.repository.MunicipioRepository;
import com.ifmt.sisvendas.repository.PromotorRepository;
import com.ifmt.sisvendas.repository.RegiaoRepository;

@RestController
@RequestMapping("/promotores")
public class PromotorController {

    private final PromotorRepository repository;
    private final MunicipioRepository municipioRepository;
    private final RegiaoRepository regiaoRepository;

    public PromotorController(
            PromotorRepository repository,
            MunicipioRepository municipioRepository,
            RegiaoRepository regiaoRepository) {
        this.repository = repository;
        this.municipioRepository = municipioRepository;
        this.regiaoRepository = regiaoRepository;
    }

    @GetMapping
    public List<Promotor> listar() {
        return repository.findAll();
    }

    @PostMapping
    public Promotor cadastrar(@RequestBody PromotorDTO promotorDTO) {
        Regiao regiao = regiaoRepository.findById(promotorDTO.getIdRegiao()).orElse(null);

        if (regiao == null) {
            return null;
        }

        Promotor promotor = new Promotor();
        aplicarDadosDTO(promotor, promotorDTO, regiao);

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
    public Promotor atualizar(@PathVariable Integer id, @RequestBody PromotorDTO promotorDTO) {
        Promotor promotor = repository.findById(id).orElse(null);

        if (promotor == null) {
            return null;
        }

        Regiao regiao = regiaoRepository.findById(promotorDTO.getIdRegiao()).orElse(null);

        if (regiao == null) {
            return null;
        }

        aplicarDadosDTO(promotor, promotorDTO, regiao);

        return repository.save(promotor);
    }

    @DeleteMapping("/{id}")
    public void excluir(@PathVariable Integer id) {
        repository.deleteById(id);
    }

    private void aplicarDadosDTO(
            Promotor promotor,
            PromotorDTO promotorDTO,
            Regiao regiao) {

        promotor.setNome(promotorDTO.getNome());
        promotor.setCpf(promotorDTO.getCpf());
        promotor.setRegiao(regiao);
    }
}
}
