package com.ifmt.sisvendas.controller;

import java.util.List;
import java.util.Map;

import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.ifmt.sisvendas.dto.PedidoFornecedorDTO;
import com.ifmt.sisvendas.model.Fornecedor;
import com.ifmt.sisvendas.model.ItemPedidoFornecedor;
import com.ifmt.sisvendas.model.PedidoFornecedor;
import com.ifmt.sisvendas.repository.FornecedorRepository;
import com.ifmt.sisvendas.repository.ItemPedidoFornecedorRepository;
import com.ifmt.sisvendas.model.PedidoFornecedor;
import com.ifmt.sisvendas.repository.PedidoFornecedorRepository;

@RestController
@RequestMapping("/pedidos-fornecedor")
public class PedidoFornecedorController {

    private final PedidoFornecedorRepository repository;
    private final ItemPedidoFornecedorRepository itemRepository;
    private final FornecedorRepository fornecedorRepository;

    public PedidoFornecedorController(
            PedidoFornecedorRepository repository,
            ItemPedidoFornecedorRepository itemRepository,
            FornecedorRepository fornecedorRepository) {
        this.repository = repository;
        this.itemRepository = itemRepository;
        this.fornecedorRepository = fornecedorRepository;

    public PedidoFornecedorController(PedidoFornecedorRepository repository) {
        this.repository = repository;
    }

    @GetMapping
    public List<PedidoFornecedor> listar() {
        return repository.findAll();
    }

    @PostMapping
    public PedidoFornecedor cadastrar(@RequestBody PedidoFornecedorDTO pedidoFornecedorDTO) {
        Fornecedor fornecedor =
                fornecedorRepository.findById(pedidoFornecedorDTO.getIdFornecedor()).orElse(null);

        if (fornecedor == null) {
            return null;
        }

        PedidoFornecedor pedidoFornecedor = new PedidoFornecedor();
        aplicarDadosDTO(pedidoFornecedor, pedidoFornecedorDTO, fornecedor);

    public PedidoFornecedor cadastrar(@RequestBody PedidoFornecedor pedidoFornecedor) {
        return repository.save(pedidoFornecedor);
    }

    @GetMapping("/{id}")
    public PedidoFornecedor buscarPorId(@PathVariable Integer id) {
        return repository.findById(id).orElse(null);
    }

    @GetMapping("/{id}/detalhes")
    public Map<String, Object> buscarPedidoFornecedorComItens(@PathVariable Integer id) {
        PedidoFornecedor pedido = repository.findById(id).orElse(null);

        if (pedido == null) {
            return null;
        }

        List<ItemPedidoFornecedor> itens =
                itemRepository.findByPedidoFornecedorIdPedidoFornecedor(id);

        return Map.of(
                "pedido", pedido,
                "itens", itens
        );
    }

    @PutMapping("/{id}")
    public PedidoFornecedor atualizar(
            @PathVariable Integer id,
            @RequestBody PedidoFornecedorDTO pedidoFornecedorDTO) {

    @PutMapping("/{id}")
    public PedidoFornecedor atualizar(@PathVariable Integer id, @RequestBody PedidoFornecedor dados) {
        PedidoFornecedor pedidoFornecedor = repository.findById(id).orElse(null);

        if (pedidoFornecedor == null) {
            return null;
        }

        Fornecedor fornecedor =
                fornecedorRepository.findById(pedidoFornecedorDTO.getIdFornecedor()).orElse(null);

        if (fornecedor == null) {
            return null;
        }

        aplicarDadosDTO(pedidoFornecedor, pedidoFornecedorDTO, fornecedor);
        pedidoFornecedor.setData(dados.getData());
        pedidoFornecedor.setVlTotal(dados.getVlTotal());
        pedidoFornecedor.setStatus(dados.getStatus());
        pedidoFornecedor.setFornecedor(dados.getFornecedor());

        return repository.save(pedidoFornecedor);
    }

    @DeleteMapping("/{id}")
    public void excluir(@PathVariable Integer id) {
        repository.deleteById(id);
    }

    private void aplicarDadosDTO(
            PedidoFornecedor pedidoFornecedor,
            PedidoFornecedorDTO pedidoFornecedorDTO,
            Fornecedor fornecedor) {

        pedidoFornecedor.setData(pedidoFornecedorDTO.getData());
        pedidoFornecedor.setVlTotal(pedidoFornecedorDTO.getVlTotal());
        pedidoFornecedor.setStatus(pedidoFornecedorDTO.getStatus());
        pedidoFornecedor.setFornecedor(fornecedor);
    }
}