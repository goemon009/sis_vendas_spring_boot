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

import com.ifmt.sisvendas.model.EntradaMercadoria;
import com.ifmt.sisvendas.model.ItemEntradaMercadoria;
import com.ifmt.sisvendas.model.Produto;
import com.ifmt.sisvendas.repository.EntradaMercadoriaRepository;
import com.ifmt.sisvendas.repository.ItemEntradaMercadoriaRepository;
import com.ifmt.sisvendas.repository.ProdutoRepository;

@RestController
@RequestMapping("/entradas-mercadoria")
public class EntradaMercadoriaController {

    private final EntradaMercadoriaRepository repository;
    private final ItemEntradaMercadoriaRepository itemRepository;
    private final ProdutoRepository produtoRepository;

    public EntradaMercadoriaController(
            EntradaMercadoriaRepository repository,
            ItemEntradaMercadoriaRepository itemRepository,
            ProdutoRepository produtoRepository) {
        this.repository = repository;
        this.itemRepository = itemRepository;
        this.produtoRepository = produtoRepository;
    }

    @GetMapping
    public List<EntradaMercadoria> listar() {
        return repository.findAll();
    }

    @PostMapping
    public EntradaMercadoria cadastrar(@RequestBody EntradaMercadoria entradaMercadoria) {
        return repository.save(entradaMercadoria);
    }

    @GetMapping("/{id}")
    public EntradaMercadoria buscarPorId(@PathVariable Integer id) {
        return repository.findById(id).orElse(null);
    }

    @PutMapping("/{id}")
    public EntradaMercadoria atualizar(@PathVariable Integer id, @RequestBody EntradaMercadoria dados) {
        EntradaMercadoria entradaMercadoria = repository.findById(id).orElse(null);

        if (entradaMercadoria == null) {
            return null;
        }

        entradaMercadoria.setNumeroNota(dados.getNumeroNota());
        entradaMercadoria.setDataEntrada(dados.getDataEntrada());
        entradaMercadoria.setStatus(dados.getStatus());
        entradaMercadoria.setFornecedor(dados.getFornecedor());

        return repository.save(entradaMercadoria);
    }

    @DeleteMapping("/{id}")
    public void excluir(@PathVariable Integer id) {
        repository.deleteById(id);
    }

    @PutMapping("/{id}/conferir")
    public EntradaMercadoria conferir(@PathVariable Integer id) {
        EntradaMercadoria entrada = repository.findById(id).orElse(null);

        if (entrada == null) {
            return null;
        }

        entrada.setStatus("CONFERIDA");

        return repository.save(entrada);
    }

    @PutMapping("/{id}/processar")
    public EntradaMercadoria processar(@PathVariable Integer id) {
        EntradaMercadoria entrada = repository.findById(id).orElse(null);

        if (entrada == null) {
            return null;
        }

        if (!"CONFERIDA".equals(entrada.getStatus())) {
            return entrada;
        }

        List<ItemEntradaMercadoria> itens =
                itemRepository.findByEntradaMercadoriaIdEntradaMercadoria(id);

        for (ItemEntradaMercadoria item : itens) {
            Produto produto = item.getProduto();

            Integer estoqueAtual = produto.getQtdEstoque();
            Integer quantidadeEntrada = item.getQtd();

            produto.setQtdEstoque(estoqueAtual + quantidadeEntrada);

            produtoRepository.save(produto);
        }

        entrada.setStatus("PROCESSADA");

        return repository.save(entrada);
    }
}