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

import com.ifmt.sisvendas.dto.ItemPedidoFornecedorDTO;
import com.ifmt.sisvendas.model.ItemPedidoFornecedor;
import com.ifmt.sisvendas.model.PedidoFornecedor;
import com.ifmt.sisvendas.model.Produto;
import com.ifmt.sisvendas.repository.ItemPedidoFornecedorRepository;
import com.ifmt.sisvendas.repository.PedidoFornecedorRepository;
import com.ifmt.sisvendas.repository.ProdutoRepository;

/**
 * Controller responsável pelos endpoints REST dos itens de pedido ao fornecedor.
 *
 * Os itens representam os produtos, quantidades e valores vinculados
 * a um pedido.
 */
@RestController
@RequestMapping("/itens-pedido-fornecedor")
public class ItemPedidoFornecedorController {

    private final ItemPedidoFornecedorRepository repository;
    private final PedidoFornecedorRepository pedidoFornecedorRepository;
    private final ProdutoRepository produtoRepository;

    public ItemPedidoFornecedorController(
            ItemPedidoFornecedorRepository repository,
            PedidoFornecedorRepository pedidoFornecedorRepository,
            ProdutoRepository produtoRepository) {
        this.repository = repository;
        this.pedidoFornecedorRepository = pedidoFornecedorRepository;
        this.produtoRepository = produtoRepository;
    }

    /**
     * Retorna todos os itens cadastrados.
     */
    @GetMapping
    public List<ItemPedidoFornecedor> listar() {
        return repository.findAll();
    }

    /**
     * Cadastra um novo item vinculado ao respectivo documento.
     */
    @PostMapping
    public ItemPedidoFornecedor cadastrar(@RequestBody ItemPedidoFornecedorDTO itemDTO) {
        PedidoFornecedor pedidoFornecedor =
                pedidoFornecedorRepository.findById(itemDTO.getIdPedidoFornecedor()).orElse(null);

        Produto produto =
                produtoRepository.findById(itemDTO.getIdProduto()).orElse(null);

        if (pedidoFornecedor == null || produto == null) {
            return null;
        }

        ItemPedidoFornecedor item = new ItemPedidoFornecedor();
        aplicarDadosDTO(item, itemDTO, pedidoFornecedor, produto);

        return repository.save(item);
    }

    /**
     * Busca um item pelo seu identificador.
     */
    @GetMapping("/{id}")
    public ItemPedidoFornecedor buscarPorId(@PathVariable Integer id) {
        return repository.findById(id).orElse(null);
    }

    /**
     * Atualiza os dados de um item existente.
     */
    @PutMapping("/{id}")
    public ItemPedidoFornecedor atualizar(
            @PathVariable Integer id,
            @RequestBody ItemPedidoFornecedorDTO itemDTO) {

        ItemPedidoFornecedor item = repository.findById(id).orElse(null);

        if (item == null) {
            return null;
        }

        PedidoFornecedor pedidoFornecedor =
                pedidoFornecedorRepository.findById(itemDTO.getIdPedidoFornecedor()).orElse(null);

        Produto produto =
                produtoRepository.findById(itemDTO.getIdProduto()).orElse(null);

        if (pedidoFornecedor == null || produto == null) {
            return null;
        }

        aplicarDadosDTO(item, itemDTO, pedidoFornecedor, produto);

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
            ItemPedidoFornecedor item,
            ItemPedidoFornecedorDTO itemDTO,
            PedidoFornecedor pedidoFornecedor,
            Produto produto) {

        item.setQtd(itemDTO.getQtd());
        item.setVlUnitario(itemDTO.getVlUnitario());
        item.setPedidoFornecedor(pedidoFornecedor);
        item.setProduto(produto);
    }
}
