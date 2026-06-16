package com.ifmt.sisvendas.controller;

import com.ifmt.sisvendas.model.ItemEntradaMercadoria;
import com.ifmt.sisvendas.repository.ItemEntradaMercadoriaRepository;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/itens-entrada-mercadoria")
public class ItemEntradaMercadoriaController {

    private final ItemEntradaMercadoriaRepository repository;

    public ItemEntradaMercadoriaController(ItemEntradaMercadoriaRepository repository) {
        this.repository = repository;
    }

    @GetMapping
    public List<ItemEntradaMercadoria> listar() {
        return repository.findAll();
    }

    @PostMapping
    public ItemEntradaMercadoria cadastrar(@RequestBody ItemEntradaMercadoria item) {
        return repository.save(item);
    }

    @GetMapping("/{id}")
    public ItemEntradaMercadoria buscarPorId(@PathVariable Integer id) {
        return repository.findById(id).orElse(null);
    }

    @PutMapping("/{id}")
    public ItemEntradaMercadoria atualizar(@PathVariable Integer id, @RequestBody ItemEntradaMercadoria dados) {
        ItemEntradaMercadoria item = repository.findById(id).orElse(null);

        if (item == null) {
            return null;
        }

        item.setQtd(dados.getQtd());
        item.setVlUnitario(dados.getVlUnitario());
        item.setEntradaMercadoria(dados.getEntradaMercadoria());
        item.setProduto(dados.getProduto());

        return repository.save(item);
    }

    @DeleteMapping("/{id}")
    public void excluir(@PathVariable Integer id) {
        repository.deleteById(id);
    }
}