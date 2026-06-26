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

import com.ifmt.sisvendas.dto.PagamentoFornecedorDTO;
import com.ifmt.sisvendas.model.PagamentoFornecedor;
import com.ifmt.sisvendas.model.PedidoFornecedor;
import com.ifmt.sisvendas.repository.PagamentoFornecedorRepository;
import com.ifmt.sisvendas.repository.PedidoFornecedorRepository;

@RestController
@RequestMapping("/pagamentos-fornecedor")
public class PagamentoFornecedorController {

    private final PagamentoFornecedorRepository repository;
    private final PedidoFornecedorRepository pedidoFornecedorRepository;

    public PagamentoFornecedorController(
            PagamentoFornecedorRepository repository,
            PedidoFornecedorRepository pedidoFornecedorRepository) {
        this.repository = repository;
        this.pedidoFornecedorRepository = pedidoFornecedorRepository;
    }

    @GetMapping
    public List<PagamentoFornecedor> listar() {
        return repository.findAll();
    }

    @PostMapping
    public PagamentoFornecedor cadastrar(@RequestBody PagamentoFornecedorDTO pagamentoFornecedorDTO) {
        PedidoFornecedor pedidoFornecedor =
                pedidoFornecedorRepository.findById(pagamentoFornecedorDTO.getIdPedidoFornecedor()).orElse(null);

        if (pedidoFornecedor == null) {
            return null;
        }

        PagamentoFornecedor pagamentoFornecedor = new PagamentoFornecedor();
        aplicarDadosDTO(pagamentoFornecedor, pagamentoFornecedorDTO, pedidoFornecedor);

        return repository.save(pagamentoFornecedor);
    }

    @GetMapping("/{id}")
    public PagamentoFornecedor buscarPorId(@PathVariable Integer id) {
        return repository.findById(id).orElse(null);
    }

    @PutMapping("/{id}")
    public PagamentoFornecedor atualizar(
            @PathVariable Integer id,
            @RequestBody PagamentoFornecedorDTO pagamentoFornecedorDTO) {

        PagamentoFornecedor pagamentoFornecedor =
                repository.findById(id).orElse(null);

        if (pagamentoFornecedor == null) {
            return null;
        }

        PedidoFornecedor pedidoFornecedor =
                pedidoFornecedorRepository.findById(pagamentoFornecedorDTO.getIdPedidoFornecedor()).orElse(null);

        if (pedidoFornecedor == null) {
            return null;
        }

        aplicarDadosDTO(pagamentoFornecedor, pagamentoFornecedorDTO, pedidoFornecedor);

        return repository.save(pagamentoFornecedor);
    }

    @DeleteMapping("/{id}")
    public void excluir(@PathVariable Integer id) {
        repository.deleteById(id);
    }

    private void aplicarDadosDTO(
            PagamentoFornecedor pagamentoFornecedor,
            PagamentoFornecedorDTO pagamentoFornecedorDTO,
            PedidoFornecedor pedidoFornecedor) {

        pagamentoFornecedor.setValor(pagamentoFornecedorDTO.getValor());
        pagamentoFornecedor.setParcelas(pagamentoFornecedorDTO.getParcelas());
        pagamentoFornecedor.setDtPagamento(pagamentoFornecedorDTO.getDtPagamento());
        pagamentoFornecedor.setStatus(pagamentoFornecedorDTO.getStatus());
        pagamentoFornecedor.setPedidoFornecedor(pedidoFornecedor);
    }
}