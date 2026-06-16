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

import com.ifmt.sisvendas.model.ItemPedidoFornecedor;
import com.ifmt.sisvendas.repository.ItemPedidoFornecedorRepository;

@RestController
@RequestMapping("/itens-pedido-fornecedor")
public class ItemPedidoFornecedorController {

    private final ItemPedidoFornecedorRepository repository;

    public ItemPedidoFornecedorController(ItemPedidoFornecedorRepository repository) {
        this.repository = repository;
    }

    @GetMapping
    public List<ItemPedidoFornecedor> listar() {
        return repository.findAll();
    }

    @PostMapping
    public ItemPedidoFornecedor cadastrar(@RequestBody ItemPedidoFornecedor item) {
        return repository.save(item);
    }

    @GetMapping("/{id}")
    public ItemPedidoFornecedor buscarPorId(@PathVariable Integer id) {
        return repository.findById(id).orElse(null);
    }

    @PutMapping("/{id}")
    public ItemPedidoFornecedor atualizar(@PathVariable Integer id, @RequestBody ItemPedidoFornecedor dados) {
        ItemPedidoFornecedor item = repository.findById(id).orElse(null);

        if (item == null) {
            return null;
        }

        item.setQtd(dados.getQtd());
        item.setVlUnitario(dados.getVlUnitario());
        item.setPedidoFornecedor(dados.getPedidoFornecedor());
        item.setProduto(dados.getProduto());

        return repository.save(item);
    }

    @DeleteMapping("/{id}")
    public void excluir(@PathVariable Integer id) {
        repository.deleteById(id);
    }
}