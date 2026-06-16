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

import com.ifmt.sisvendas.model.PagamentoFornecedor;
import com.ifmt.sisvendas.repository.PagamentoFornecedorRepository;

@RestController
@RequestMapping("/pagamentos-fornecedor")
public class PagamentoFornecedorController {

    private final PagamentoFornecedorRepository repository;

    public PagamentoFornecedorController(PagamentoFornecedorRepository repository) {
        this.repository = repository;
    }

    @GetMapping
    public List<PagamentoFornecedor> listar() {
        return repository.findAll();
    }

    @PostMapping
    public PagamentoFornecedor cadastrar(@RequestBody PagamentoFornecedor pagamentoFornecedor) {
        return repository.save(pagamentoFornecedor);
    }

    @GetMapping("/{id}")
    public PagamentoFornecedor buscarPorId(@PathVariable Integer id) {
        return repository.findById(id).orElse(null);
    }

    @PutMapping("/{id}")
    public PagamentoFornecedor atualizar(@PathVariable Integer id, @RequestBody PagamentoFornecedor dados) {
        PagamentoFornecedor pagamentoFornecedor = repository.findById(id).orElse(null);

        if (pagamentoFornecedor == null) {
            return null;
        }

        pagamentoFornecedor.setValor(dados.getValor());
        pagamentoFornecedor.setParcelas(dados.getParcelas());
        pagamentoFornecedor.setDtPagamento(dados.getDtPagamento());
        pagamentoFornecedor.setStatus(dados.getStatus());
        pagamentoFornecedor.setPedidoFornecedor(dados.getPedidoFornecedor());

        return repository.save(pagamentoFornecedor);
    }

    @DeleteMapping("/{id}")
    public void excluir(@PathVariable Integer id) {
        repository.deleteById(id);
    }
}