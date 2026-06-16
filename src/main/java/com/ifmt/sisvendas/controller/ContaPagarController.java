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

import com.ifmt.sisvendas.model.ContaPagar;
import com.ifmt.sisvendas.repository.ContaPagarRepository;

@RestController
@RequestMapping("/contas-pagar")
public class ContaPagarController {

    private final ContaPagarRepository repository;

    public ContaPagarController(ContaPagarRepository repository) {
        this.repository = repository;
    }

    @GetMapping
    public List<ContaPagar> listar() {
        return repository.findAll();
    }

    @PostMapping
    public ContaPagar cadastrar(@RequestBody ContaPagar contaPagar) {
        return repository.save(contaPagar);
    }

    @GetMapping("/{id}")
    public ContaPagar buscarPorId(@PathVariable Integer id) {
        return repository.findById(id).orElse(null);
    }

    @PutMapping("/{id}")
    public ContaPagar atualizar(@PathVariable Integer id, @RequestBody ContaPagar dados) {
        ContaPagar contaPagar = repository.findById(id).orElse(null);

        if (contaPagar == null) {
            return null;
        }

        contaPagar.setNumeroParcela(dados.getNumeroParcela());
        contaPagar.setValor(dados.getValor());
        contaPagar.setDtVencimento(dados.getDtVencimento());
        contaPagar.setDtPagamento(dados.getDtPagamento());
        contaPagar.setStatus(dados.getStatus());
        contaPagar.setPedidoFornecedor(dados.getPedidoFornecedor());

        return repository.save(contaPagar);
    }

    @DeleteMapping("/{id}")
    public void excluir(@PathVariable Integer id) {
        repository.deleteById(id);
    }
}