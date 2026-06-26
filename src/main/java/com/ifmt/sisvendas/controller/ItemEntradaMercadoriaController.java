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

import com.ifmt.sisvendas.dto.ItemEntradaMercadoriaDTO;
import com.ifmt.sisvendas.model.EntradaMercadoria;
import com.ifmt.sisvendas.model.ItemEntradaMercadoria;
import com.ifmt.sisvendas.model.Produto;
import com.ifmt.sisvendas.repository.EntradaMercadoriaRepository;
import com.ifmt.sisvendas.repository.ItemEntradaMercadoriaRepository;
import com.ifmt.sisvendas.repository.ProdutoRepository;

@RestController
@RequestMapping("/itens-entrada-mercadoria")
public class ItemEntradaMercadoriaController {

    private final ItemEntradaMercadoriaRepository repository;
    private final EntradaMercadoriaRepository entradaMercadoriaRepository;
    private final ProdutoRepository produtoRepository;

    public ItemEntradaMercadoriaController(
            ItemEntradaMercadoriaRepository repository,
            EntradaMercadoriaRepository entradaMercadoriaRepository,
            ProdutoRepository produtoRepository) {
        this.repository = repository;
        this.entradaMercadoriaRepository = entradaMercadoriaRepository;
        this.produtoRepository = produtoRepository;
    }

    @GetMapping
    public List<ItemEntradaMercadoria> listar() {
        return repository.findAll();
    }

    @PostMapping
    public ItemEntradaMercadoria cadastrar(@RequestBody ItemEntradaMercadoriaDTO itemDTO) {
        EntradaMercadoria entradaMercadoria =
                entradaMercadoriaRepository.findById(itemDTO.getIdEntradaMercadoria()).orElse(null);

        Produto produto =
                produtoRepository.findById(itemDTO.getIdProduto()).orElse(null);

        if (entradaMercadoria == null || produto == null) {
            return null;
        }

        ItemEntradaMercadoria item = new ItemEntradaMercadoria();
        aplicarDadosDTO(item, itemDTO, entradaMercadoria, produto);

        return repository.save(item);
    }

    @GetMapping("/{id}")
    public ItemEntradaMercadoria buscarPorId(@PathVariable Integer id) {
        return repository.findById(id).orElse(null);
    }

    @PutMapping("/{id}")
    public ItemEntradaMercadoria atualizar(
            @PathVariable Integer id,
            @RequestBody ItemEntradaMercadoriaDTO itemDTO) {

        ItemEntradaMercadoria item = repository.findById(id).orElse(null);

        if (item == null) {
            return null;
        }

        EntradaMercadoria entradaMercadoria =
                entradaMercadoriaRepository.findById(itemDTO.getIdEntradaMercadoria()).orElse(null);

        Produto produto =
                produtoRepository.findById(itemDTO.getIdProduto()).orElse(null);

        if (entradaMercadoria == null || produto == null) {
            return null;
        }

        aplicarDadosDTO(item, itemDTO, entradaMercadoria, produto);

        return repository.save(item);
    }

    @DeleteMapping("/{id}")
    public void excluir(@PathVariable Integer id) {
        repository.deleteById(id);
    }

    private void aplicarDadosDTO(
            ItemEntradaMercadoria item,
            ItemEntradaMercadoriaDTO itemDTO,
            EntradaMercadoria entradaMercadoria,
            Produto produto) {

        item.setQtd(itemDTO.getQtd());
        item.setVlUnitario(itemDTO.getVlUnitario());
        item.setEntradaMercadoria(entradaMercadoria);
        item.setProduto(produto);
    }
}