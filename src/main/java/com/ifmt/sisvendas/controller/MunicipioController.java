package com.ifmt.sisvendas.controller;

import java.util.List;
import java.util.Map;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.ifmt.sisvendas.model.Municipio;
import com.ifmt.sisvendas.model.Regiao;
import com.ifmt.sisvendas.repository.MunicipioRepository;
import com.ifmt.sisvendas.repository.RegiaoRepository;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;

@RestController
@RequestMapping("/municipios")
@Tag(
        name = "Municípios",
        description = "Operações de cadastro, manutenção e consulta de municípios."
)
public class MunicipioController {

    private final MunicipioRepository repository;
    private final RegiaoRepository regiaoRepository;

    public MunicipioController(MunicipioRepository repository,
                               RegiaoRepository regiaoRepository) {
        this.repository = repository;
        this.regiaoRepository = regiaoRepository;
    }

    @GetMapping
    public List<Municipio> listar() {
        return repository.findAll();
    }

    @PostMapping
    public ResponseEntity<?> cadastrar(@RequestBody Municipio municipio) {
        Regiao regiao = buscarRegiao(municipio);

        if (regiao == null) {
            return ResponseEntity.badRequest().body(Map.of("message", "Regiao invalida ou inexistente."));
        }

        municipio.setRegiao(regiao);

        return ResponseEntity.ok(repository.saveAndFlush(municipio));
    }

    @Operation(
            summary = "Listar municípios atendidos por promotor",
            description = "Retorna os municípios dos clientes vinculados a um promotor de venda, ordenados por nome."
    )
    @ApiResponse(responseCode = "200", description = "Municípios retornados com sucesso")
    @GetMapping("/promotor/{idPromotor}")
    public List<Municipio> listarPorPromotor(@PathVariable Integer idPromotor) {
        return repository.buscarMunicipiosPorPromotor(idPromotor);
    }

    @GetMapping("/{id}")
    public Municipio buscarPorId(@PathVariable Integer id) {
        return repository.findById(id).orElse(null);
    }

    @PutMapping("/{id}")
    public ResponseEntity<?> atualizar(@PathVariable Integer id, @RequestBody Municipio dados) {
        Municipio municipio = repository.findById(id).orElse(null);

        if (municipio == null) {
            return ResponseEntity.notFound().build();
        }

        Regiao regiao = buscarRegiao(dados);

        if (regiao == null) {
            return ResponseEntity.badRequest().body(Map.of("message", "Regiao invalida ou inexistente."));
        }

        aplicarDados(municipio, dados, regiao);

        return ResponseEntity.ok(repository.saveAndFlush(municipio));
    }

    @DeleteMapping("/{id}")
    public void excluir(@PathVariable Integer id) {
        repository.deleteById(id);
    }

    private void aplicarDados(Municipio municipio, Municipio dados, Regiao regiao) {
        municipio.setNome(dados.getNome());
        municipio.setUf(dados.getUf());
        municipio.setRegiao(regiao);
    }

    private Regiao buscarRegiao(Municipio dados) {
        if (dados.getRegiao() == null || dados.getRegiao().getId() == null) {
            return null;
        }

        return regiaoRepository.findById(dados.getRegiao().getId()).orElse(null);
    }
}
