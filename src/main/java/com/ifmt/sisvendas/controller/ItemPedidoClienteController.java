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

import com.ifmt.sisvendas.model.ItemPedidoCliente;
import com.ifmt.sisvendas.repository.ItemPedidoClienteRepository;

@RestController
@RequestMapping("/itens-pedido-cliente")
public class ItemPedidoClienteController {

    private final ItemPedidoClienteRepository repository;

    public ItemPedidoClienteController(ItemPedidoClienteRepository repository) {
        this.repository = repository;
    }

    @GetMapping
    public List<ItemPedidoCliente> listar() {
        return repository.findAll();
    }

    @PostMapping
    public ItemPedidoCliente cadastrar(@RequestBody ItemPedidoCliente item) {
        return repository.save(item);
    }

    @GetMapping("/{id}")
    public ItemPedidoCliente buscarPorId(@PathVariable Integer id) {
        return repository.findById(id).orElse(null);
    }

    @PutMapping("/{id}")
    public ItemPedidoCliente atualizar(@PathVariable Integer id, @RequestBody ItemPedidoCliente dados) {
        ItemPedidoCliente item = repository.findById(id).orElse(null);

        if (item == null) {
            return null;
        }

        item.setQtd(dados.getQtd());
        item.setVlUnitario(dados.getVlUnitario());
        item.setPedidoCliente(dados.getPedidoCliente());
        item.setProduto(dados.getProduto());

        return repository.save(item);
    }

    @DeleteMapping("/{id}")
    public void excluir(@PathVariable Integer id) {
        repository.deleteById(id);
    }
}