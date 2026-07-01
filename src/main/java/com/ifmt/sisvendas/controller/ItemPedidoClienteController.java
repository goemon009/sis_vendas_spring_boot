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

import com.ifmt.sisvendas.dto.ItemPedidoClienteDTO;
import com.ifmt.sisvendas.model.ItemPedidoCliente;
import com.ifmt.sisvendas.model.PedidoCliente;
import com.ifmt.sisvendas.model.Produto;
import com.ifmt.sisvendas.repository.ItemPedidoClienteRepository;
import com.ifmt.sisvendas.repository.PedidoClienteRepository;
import com.ifmt.sisvendas.repository.ProdutoRepository;

/**
 * Controller responsável pelos endpoints REST dos itens de pedido de cliente.
 *
 * Os itens representam os produtos, quantidades e valores vinculados
 * a um pedido.
 */
@RestController
@RequestMapping("/itens-pedido-cliente")
public class ItemPedidoClienteController {

    private final ItemPedidoClienteRepository repository;
    private final PedidoClienteRepository pedidoClienteRepository;
    private final ProdutoRepository produtoRepository;

    public ItemPedidoClienteController(
            ItemPedidoClienteRepository repository,
            PedidoClienteRepository pedidoClienteRepository,
            ProdutoRepository produtoRepository) {
        this.repository = repository;
        this.pedidoClienteRepository = pedidoClienteRepository;
        this.produtoRepository = produtoRepository;
    }

    /**
     * Retorna todos os itens cadastrados.
     */
    @GetMapping
    public List<ItemPedidoCliente> listar() {
        return repository.findAll();
    }

    /**
     * Cadastra um novo item vinculado ao respectivo documento.
     */
    @PostMapping
    public ItemPedidoCliente cadastrar(@RequestBody ItemPedidoClienteDTO itemDTO) {
        PedidoCliente pedidoCliente =
                pedidoClienteRepository.findById(itemDTO.getIdPedidoCliente()).orElse(null);

        Produto produto =
                produtoRepository.findById(itemDTO.getIdProduto()).orElse(null);

        if (pedidoCliente == null || produto == null) {
            return null;
        }

        ItemPedidoCliente item = new ItemPedidoCliente();
        aplicarDadosDTO(item, itemDTO, pedidoCliente, produto);

        return repository.save(item);
    }

    /**
     * Busca um item pelo seu identificador.
     */
    @GetMapping("/{id}")
    public ItemPedidoCliente buscarPorId(@PathVariable Integer id) {
        return repository.findById(id).orElse(null);
    }

    /**
     * Atualiza os dados de um item existente.
     */
    @PutMapping("/{id}")
    public ItemPedidoCliente atualizar(
            @PathVariable Integer id,
            @RequestBody ItemPedidoClienteDTO itemDTO) {

        ItemPedidoCliente item = repository.findById(id).orElse(null);

        if (item == null) {
            return null;
        }

        PedidoCliente pedidoCliente =
                pedidoClienteRepository.findById(itemDTO.getIdPedidoCliente()).orElse(null);

        Produto produto =
                produtoRepository.findById(itemDTO.getIdProduto()).orElse(null);

        if (pedidoCliente == null || produto == null) {
            return null;
        }

        aplicarDadosDTO(item, itemDTO, pedidoCliente, produto);

        return repository.save(item);
    }

    /**
     * Remove um item pelo seu identificador.
     */
    @DeleteMapping("/{id}")
    public void excluir(@PathVariable Integer id) {
        repository.deleteById(id);
    }

    private void aplicarDadosDTO(
            ItemPedidoCliente item,
            ItemPedidoClienteDTO itemDTO,
            PedidoCliente pedidoCliente,
            Produto produto) {

        item.setQtd(itemDTO.getQtd());
        item.setVlUnitario(itemDTO.getVlUnitario());
        item.setPedidoCliente(pedidoCliente);
        item.setProduto(produto);
    }
}
