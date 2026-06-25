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

import com.ifmt.sisvendas.dto.FornecedorDTO;
import com.ifmt.sisvendas.model.Fornecedor;
import com.ifmt.sisvendas.repository.FornecedorRepository;
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
    public Fornecedor cadastrar(@RequestBody FornecedorDTO fornecedorDTO) {

        Fornecedor fornecedor = new Fornecedor();

        aplicarDadosDTO(fornecedor, fornecedorDTO);

    public Fornecedor cadastrar(@RequestBody Fornecedor fornecedor) {
        return repository.save(fornecedor);
    }

    @GetMapping("/{id}")
    public Fornecedor buscarPorId(@PathVariable Integer id) {
        return repository.findById(id).orElse(null);
    }

    @PutMapping("/{id}")
    public Fornecedor atualizar(
            @PathVariable Integer id,
            @RequestBody FornecedorDTO fornecedorDTO) {

        Fornecedor fornecedor =
                repository.findById(id).orElse(null);
    public Fornecedor atualizar(@PathVariable Integer id, @RequestBody Fornecedor dados) {
        Fornecedor fornecedor = repository.findById(id).orElse(null);

        if (fornecedor == null) {
            return null;
        }

        aplicarDadosDTO(
                fornecedor,
                fornecedorDTO
        );
        fornecedor.setNome(dados.getNome());
        fornecedor.setCnpj(dados.getCnpj());

        return repository.save(fornecedor);
    }

    @DeleteMapping("/{id}")
    public void excluir(@PathVariable Integer id) {
        repository.deleteById(id);
    }

    private void aplicarDadosDTO(
            Fornecedor fornecedor,
            FornecedorDTO fornecedorDTO) {

        fornecedor.setNome(fornecedorDTO.getNome());
        fornecedor.setCnpj(fornecedorDTO.getCnpj());
    }
}