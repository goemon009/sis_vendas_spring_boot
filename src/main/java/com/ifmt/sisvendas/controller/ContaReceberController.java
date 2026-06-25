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

import com.ifmt.sisvendas.model.ContaReceber;
import com.ifmt.sisvendas.repository.ContaReceberRepository;

@RestController
@RequestMapping("/contas-receber")
public class ContaReceberController {

    private final ContaReceberRepository repository;

    public ContaReceberController(ContaReceberRepository repository) {
        this.repository = repository;
    }

    @GetMapping
    public List<ContaReceber> listar() {
        return repository.findAll();
    }

    @PostMapping
    public ContaReceber cadastrar(@RequestBody ContaReceber contaReceber) {
        return repository.save(contaReceber);
    }

    @GetMapping("/{id}")
    public ContaReceber buscarPorId(@PathVariable Integer id) {
        return repository.findById(id).orElse(null);
    }

    @PutMapping("/{id}")
    public ContaReceber atualizar(@PathVariable Integer id, @RequestBody ContaReceber dados) {
        ContaReceber contaReceber = repository.findById(id).orElse(null);

        if (contaReceber == null) {
            return null;
        }

        contaReceber.setNumeroParcela(dados.getNumeroParcela());
        contaReceber.setValor(dados.getValor());
        contaReceber.setDtVencimento(dados.getDtVencimento());
        contaReceber.setDtPagamento(dados.getDtPagamento());
        contaReceber.setStatus(dados.getStatus());
        contaReceber.setPedidoCliente(dados.getPedidoCliente());

        return repository.save(contaReceber);
    }

    @DeleteMapping("/{id}")
    public void excluir(@PathVariable Integer id) {
        repository.deleteById(id);
    }
}