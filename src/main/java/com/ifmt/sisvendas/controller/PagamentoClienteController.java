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

import com.ifmt.sisvendas.model.PagamentoCliente;
import com.ifmt.sisvendas.repository.PagamentoClienteRepository;

@RestController
@RequestMapping("/pagamentos-cliente")
public class PagamentoClienteController {

    private final PagamentoClienteRepository repository;

    public PagamentoClienteController(PagamentoClienteRepository repository) {
        this.repository = repository;
    }

    @GetMapping
    public List<PagamentoCliente> listar() {
        return repository.findAll();
    }

    @PostMapping
    public PagamentoCliente cadastrar(@RequestBody PagamentoCliente pagamentoCliente) {
        return repository.save(pagamentoCliente);
    }

    @GetMapping("/{id}")
    public PagamentoCliente buscarPorId(@PathVariable Integer id) {
        return repository.findById(id).orElse(null);
    }

    @PutMapping("/{id}")
    public PagamentoCliente atualizar(@PathVariable Integer id, @RequestBody PagamentoCliente dados) {
        PagamentoCliente pagamentoCliente = repository.findById(id).orElse(null);

        if (pagamentoCliente == null) {
            return null;
        }

        pagamentoCliente.setValor(dados.getValor());
        pagamentoCliente.setParcelas(dados.getParcelas());
        pagamentoCliente.setDtPagamento(dados.getDtPagamento());
        pagamentoCliente.setStatus(dados.getStatus());
        pagamentoCliente.setFormaPagamento(dados.getFormaPagamento());
        pagamentoCliente.setPedidoCliente(dados.getPedidoCliente());

        return repository.save(pagamentoCliente);
    }

    @DeleteMapping("/{id}")
    public void excluir(@PathVariable Integer id) {
        repository.deleteById(id);
    }
}