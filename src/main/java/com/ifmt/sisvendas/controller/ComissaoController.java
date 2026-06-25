package com.ifmt.sisvendas.controller;

import java.time.LocalDate;
import java.util.List;

import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.ifmt.sisvendas.model.Comissao;
import com.ifmt.sisvendas.repository.ComissaoRepository;

@RestController
@RequestMapping("/comissoes")
public class ComissaoController {

    private final ComissaoRepository repository;

    public ComissaoController(ComissaoRepository repository) {
        this.repository = repository;
    }

    @GetMapping("/status/{status}/promotor/{idPromotor}")
    public List<Comissao> listarPorStatusPromotorEPeriodo(
            @PathVariable String status,
            @PathVariable Integer idPromotor,
            @RequestParam LocalDate dataInicio,
            @RequestParam LocalDate dataFim) {

        return repository.findByStatusAndPromotorIdPromotorAndDataBetween(
                status,
                idPromotor,
                dataInicio,
                dataFim
        );
    }

    @GetMapping
    public List<Comissao> listar() {
        return repository.findAll();
    }

    @PostMapping
    public Comissao cadastrar(@RequestBody Comissao comissao) {
        return repository.save(comissao);
    }

    @GetMapping("/{id}")
    public Comissao buscarPorId(@PathVariable Integer id) {
        return repository.findById(id).orElse(null);
    }

    @PutMapping("/{id}")
    public Comissao atualizar(@PathVariable Integer id, @RequestBody Comissao dados) {
        Comissao comissao = repository.findById(id).orElse(null);

        if (comissao == null) {
            return null;
        }

        comissao.setValor(dados.getValor());
        comissao.setData(dados.getData());
        comissao.setStatus(dados.getStatus());
        comissao.setPromotor(dados.getPromotor());
        comissao.setPedidoCliente(dados.getPedidoCliente());

        return repository.save(comissao);
    }

    @DeleteMapping("/{id}")
    public void excluir(@PathVariable Integer id) {
        repository.deleteById(id);
    }
}
}
