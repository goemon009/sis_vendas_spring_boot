package com.ifmt.sisvendas.controller;

import java.util.List;
import java.util.Map;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.ifmt.sisvendas.dto.ProdutoRequest;
import com.ifmt.sisvendas.model.CategoriaProduto;
import com.ifmt.sisvendas.model.Produto;
import com.ifmt.sisvendas.repository.CategoriaProdutoRepository;
import com.ifmt.sisvendas.repository.ProdutoRepository;

import tools.jackson.databind.JsonNode;

@RestController
@RequestMapping("/produtos")
public class ProdutoController {

    private final ProdutoRepository repository;
    private final CategoriaProdutoRepository categoriaRepository;

    public ProdutoController(ProdutoRepository repository, CategoriaProdutoRepository categoriaRepository) {
        this.repository = repository;
        this.categoriaRepository = categoriaRepository;
    }

    @GetMapping
    public List<Produto> listar() {
        return repository.findAll();
    }

    @PostMapping
    public ResponseEntity<?> cadastrar(@RequestBody ProdutoRequest dados) {
        CategoriaProduto categoria = buscarCategoria(dados);

        if (categoria == null) {
            return ResponseEntity.badRequest().body(Map.of("message", "CategoriaProduto invalida ou inexistente."));
        }

        Produto produto = new Produto();
        aplicarDados(produto, dados, categoria);

        return ResponseEntity.ok(repository.save(produto));
    }

    @GetMapping("/{id}")
    public Produto buscarPorId(@PathVariable Integer id) {
        return repository.findById(id).orElse(null);
    }

    @PutMapping("/{id}")
    public ResponseEntity<?> atualizar(@PathVariable Integer id, @RequestBody ProdutoRequest dados) {
        Produto produto = repository.findById(id).orElse(null);

        if (produto == null) {
            return ResponseEntity.notFound().build();
        }

        CategoriaProduto categoria = buscarCategoria(dados);

        if (categoria == null) {
            return ResponseEntity.badRequest().body(Map.of("message", "CategoriaProduto invalida ou inexistente."));
        }

        aplicarDados(produto, dados, categoria);

        return ResponseEntity.ok(repository.save(produto));
    }

    @DeleteMapping("/{id}")
    public void excluir(@PathVariable Integer id) {
        repository.deleteById(id);
    }

    private void aplicarDados(Produto produto, ProdutoRequest dados, CategoriaProduto categoria) {
        produto.setNome(dados.getNome());
        produto.setVlCusto(dados.getVlCusto());
        produto.setQtdEstoque(dados.getQtdEstoque());
        produto.setQntdReservadaProduto(dados.getQntdReservadaProduto());
        produto.setQntdMinEstoque(dados.getQntdMinEstoque());
        produto.setQntdMaxEstoque(dados.getQntdMaxEstoque());
        produto.setPercentualComissao(dados.getPercentualComissao());
        produto.setPercentualPromocao(dados.getPercentualPromocao());
        produto.setMargemLucro(dados.getMargemLucro());
        produto.setCategoriaProduto(categoria);
    }

    private CategoriaProduto buscarCategoria(ProdutoRequest dados) {
        Integer idCategoriaProduto = extrairIdCategoria(dados);

        if (idCategoriaProduto == null) {
            return null;
        }

        return categoriaRepository.findById(idCategoriaProduto).orElse(null);
    }

    private Integer extrairIdCategoria(ProdutoRequest dados) {
        if (dados.getIdCategoriaProduto() != null) {
            return dados.getIdCategoriaProduto();
        }

        JsonNode categoriaNode = dados.getCategoriaProduto();
        if (categoriaNode == null || categoriaNode.isNull()) {
            return null;
        }

        if (categoriaNode.isNumber()) {
            return categoriaNode.intValue();
        }

        JsonNode idNode = categoriaNode.get("idCategoriaProduto");
        if (idNode == null || idNode.isNull()) {
            idNode = categoriaNode.get("id");
        }

        if (idNode == null || idNode.isNull()) {
            return null;
        }

        if (idNode.isNumber()) {
            return idNode.intValue();
        }

        if (idNode.isTextual()) {
            try {
                return Integer.valueOf(idNode.asText());
            } catch (NumberFormatException ignored) {
                return null;
            }
        }

        return null;
    }

}
