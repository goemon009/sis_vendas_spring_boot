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

import com.ifmt.sisvendas.model.FormaPagamento;
import com.ifmt.sisvendas.repository.FormaPagamentoRepository;

@RestController
@RequestMapping("/formas-pagamento")
public class FormaPagamentoController {

    private final FormaPagamentoRepository repository;

    public FormaPagamentoController(FormaPagamentoRepository repository) {
        this.repository = repository;
    }

    @GetMapping
    public List<FormaPagamento> listar() {
        return repository.findAll();
    }

    @PostMapping
    public FormaPagamento cadastrar(@RequestBody FormaPagamento formaPagamento) {
        return repository.save(formaPagamento);
    }

    @GetMapping("/{id}")
    public FormaPagamento buscarPorId(@PathVariable Integer id) {
        return repository.findById(id).orElse(null);
    }

    @PutMapping("/{id}")
    public FormaPagamento atualizar(@PathVariable Integer id, @RequestBody FormaPagamento dados) {
        FormaPagamento formaPagamento = repository.findById(id).orElse(null);

        if (formaPagamento == null) {
            return null;
        }

        formaPagamento.setDescricao(dados.getDescricao());
        formaPagamento.setTipo(dados.getTipo());

        return repository.save(formaPagamento);
    }

    @DeleteMapping("/{id}")
    public void excluir(@PathVariable Integer id) {
        repository.deleteById(id);
    }
}