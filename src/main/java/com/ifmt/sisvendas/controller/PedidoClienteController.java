package com.ifmt.sisvendas.controller;

import java.util.List;

import org.springframework.web.bind.annotation.*;

import com.ifmt.sisvendas.model.PedidoCliente;
import com.ifmt.sisvendas.repository.PedidoClienteRepository;

@RestController
@RequestMapping("/pedidos")
public class PedidoClienteController {

    private final PedidoClienteRepository repository;

    public PedidoClienteController(
            PedidoClienteRepository repository) {

        this.repository = repository;
    }

    @GetMapping
    public List<PedidoCliente> listar() {
        return repository.findAll();
    }

    @PostMapping
    public PedidoCliente cadastrar(
            @RequestBody PedidoCliente pedido) {

        return repository.save(pedido);
    }

    @GetMapping("/{id}")
    public PedidoCliente buscarPorId(
            @PathVariable Integer id) {

        return repository.findById(id).orElse(null);
    }

    @PutMapping("/{id}")
    public PedidoCliente atualizar(
            @PathVariable Integer id,
            @RequestBody PedidoCliente dados) {

        PedidoCliente pedido =
                repository.findById(id).orElse(null);

        if (pedido == null) {
            return null;
        }

        pedido.setVlTotal(dados.getVlTotal());
        pedido.setDtSolicitacao(dados.getDtSolicitacao());
        pedido.setDtProgramacaoEntrega(
                dados.getDtProgramacaoEntrega());
        pedido.setStatus(dados.getStatus());
        pedido.setCliente(dados.getCliente());
        pedido.setPromotor(dados.getPromotor());

        return repository.save(pedido);
    }

    @DeleteMapping("/{id}")
    public void excluir(@PathVariable Integer id) {
        repository.deleteById(id);
    }
}
