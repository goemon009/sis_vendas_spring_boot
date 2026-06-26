package com.ifmt.sisvendas.controller;

import java.util.List;

import org.springframework.web.bind.annotation.*;

import com.ifmt.sisvendas.dto.PagamentoClienteDTO;
import com.ifmt.sisvendas.model.FormaPagamento;
import com.ifmt.sisvendas.model.PagamentoCliente;
import com.ifmt.sisvendas.model.PedidoCliente;
import com.ifmt.sisvendas.repository.FormaPagamentoRepository;
import com.ifmt.sisvendas.repository.PagamentoClienteRepository;
import com.ifmt.sisvendas.repository.PedidoClienteRepository;

@RestController
@RequestMapping("/pagamentos-cliente")
public class PagamentoClienteController {

    private final PagamentoClienteRepository repository;
    private final FormaPagamentoRepository formaPagamentoRepository;
    private final PedidoClienteRepository pedidoClienteRepository;

    public PagamentoClienteController(
            PagamentoClienteRepository repository,
            FormaPagamentoRepository formaPagamentoRepository,
            PedidoClienteRepository pedidoClienteRepository) {
        this.repository = repository;
        this.formaPagamentoRepository = formaPagamentoRepository;
        this.pedidoClienteRepository = pedidoClienteRepository;
    }

    @GetMapping
    public List<PagamentoCliente> listar() {
        return repository.findAll();
    }

    @PostMapping
    public PagamentoCliente cadastrar(@RequestBody PagamentoClienteDTO pagamentoClienteDTO) {

        FormaPagamento formaPagamento =
                formaPagamentoRepository.findById(pagamentoClienteDTO.getIdFormaPagamento()).orElse(null);

        PedidoCliente pedidoCliente =
                pedidoClienteRepository.findById(pagamentoClienteDTO.getIdPedidoCliente()).orElse(null);

        if (formaPagamento == null || pedidoCliente == null) {
            return null;
        }

        PagamentoCliente pagamentoCliente = new PagamentoCliente();
        aplicarDadosDTO(
                pagamentoCliente,
                pagamentoClienteDTO,
                formaPagamento,
                pedidoCliente
        );

        return repository.save(pagamentoCliente);
    }

    @GetMapping("/{id}")
    public PagamentoCliente buscarPorId(@PathVariable Integer id) {
        return repository.findById(id).orElse(null);
    }

    @PutMapping("/{id}")
    public PagamentoCliente atualizar(
            @PathVariable Integer id,
            @RequestBody PagamentoClienteDTO pagamentoClienteDTO) {

        PagamentoCliente pagamentoCliente =
                repository.findById(id).orElse(null);

        if (pagamentoCliente == null) {
            return null;
        }

        FormaPagamento formaPagamento =
                formaPagamentoRepository.findById(pagamentoClienteDTO.getIdFormaPagamento()).orElse(null);

        PedidoCliente pedidoCliente =
                pedidoClienteRepository.findById(pagamentoClienteDTO.getIdPedidoCliente()).orElse(null);

        if (formaPagamento == null || pedidoCliente == null) {
            return null;
        }

        aplicarDadosDTO(
                pagamentoCliente,
                pagamentoClienteDTO,
                formaPagamento,
                pedidoCliente
        );

        return repository.save(pagamentoCliente);
    }

    @DeleteMapping("/{id}")
    public void excluir(@PathVariable Integer id) {
        repository.deleteById(id);
    }

    private void aplicarDadosDTO(
            PagamentoCliente pagamentoCliente,
            PagamentoClienteDTO pagamentoClienteDTO,
            FormaPagamento formaPagamento,
            PedidoCliente pedidoCliente) {

        pagamentoCliente.setValor(pagamentoClienteDTO.getValor());
        pagamentoCliente.setParcelas(pagamentoClienteDTO.getParcelas());
        pagamentoCliente.setDtPagamento(pagamentoClienteDTO.getDtPagamento());
        pagamentoCliente.setStatus(pagamentoClienteDTO.getStatus());
        pagamentoCliente.setFormaPagamento(formaPagamento);
        pagamentoCliente.setPedidoCliente(pedidoCliente);
    }
}