package com.ifmt.sisvendas.controller;

import java.math.BigDecimal;
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

import com.ifmt.sisvendas.model.CategoriaProduto;
import com.ifmt.sisvendas.model.Produto;
import com.ifmt.sisvendas.repository.CategoriaProdutoRepository;
import com.ifmt.sisvendas.repository.ProdutoRepository;

@RestController
@RequestMapping("/produtos")
public class ProdutoController {

    private final ProdutoRepository repository;
    private final CategoriaProdutoRepository categoriaRepository;

    public ProdutoController(
            ProdutoRepository repository,
            CategoriaProdutoRepository categoriaRepository) {
        this.repository = repository;
        this.categoriaRepository = categoriaRepository;
    }

    @GetMapping
    public List<Produto> listar() {
        return repository.findAll();
    }

    @PostMapping
    public ResponseEntity<?> cadastrar(@RequestBody Map<String, Object> payload) {
        CategoriaProduto categoriaProduto = resolverCategoria(extrairIdCategoriaProduto(payload));

        if (categoriaProduto == null) {
            return ResponseEntity.badRequest().body(Map.of("message", "CategoriaProduto invalida ou inexistente."));
        }

        Produto produto = converterProduto(payload, categoriaProduto);
        produto.setCategoriaProduto(categoriaProduto);

        return ResponseEntity.ok(repository.save(produto));
    }

    @GetMapping("/{id}")
    public Produto buscarPorId(@PathVariable Integer id) {
        return repository.findById(id).orElse(null);
    }


    @PutMapping("/{id}")
    public Produto atualizar(@PathVariable Integer id, @RequestBody Produto dados) {
        Produto produto = repository.findById(id).orElse(null);

        if (produto == null) {
            return null;
        }

        produto.setNome(dados.getNome());
        produto.setVlCusto(dados.getVlCusto());
        produto.setQtdEstoque(dados.getQtdEstoque());
        produto.setQtdReservadaProduto(dados.getQtdReservadaProduto());
        produto.setQtdMinEstoque(dados.getQtdMinEstoque());
        produto.setQtdMaxEstoque(dados.getQtdMaxEstoque());
        produto.setPercentualComissao(dados.getPercentualComissao());
        produto.setPercentualPromocao(dados.getPercentualPromocao());
        produto.setMargemLucro(dados.getMargemLucro());
        produto.setCategoriaProduto(dados.getCategoriaProduto());

        return repository.save(produto);
    }

    @GetMapping("/categoria/{idCategoria}")
    public List<Produto> listarPorCategoriaOrdenadoPorNome(@PathVariable Integer idCategoria) {
        return repository.findByCategoriaProdutoIdCategoriaProdutoOrderByNomeAsc(idCategoria);
    }

    @GetMapping("/categoria/{idCategoria}/estoque-desc")
    public List<Produto> listarPorCategoriaOrdenadoPorEstoqueDesc(@PathVariable Integer idCategoria) {
        return repository.findByCategoriaProdutoIdCategoriaProdutoOrderByQtdEstoqueDesc(idCategoria);
    }

    @GetMapping("/estoque-baixo")
    public List<Produto> listarProdutosAbaixoEstoqueMinimo() {
        return repository.buscarProdutosAbaixoDoEstoqueMinimo();
    }

    @DeleteMapping("/{id}")
    public void excluir(@PathVariable Integer id) {
        repository.deleteById(id);
    }

    private Produto converterProduto(Map<String, Object> payload, CategoriaProduto categoriaProduto) {
        Produto produto = new Produto();
        produto.setIdProduto(null);
        produto.setNome(extrairTexto(payload.get("nome")));
        produto.setVlCusto(extrairBigDecimal(payload.get("vlCusto")));
        produto.setQtdEstoque(extrairInteiro(payload.get("qtdEstoque")));
        produto.setQtdReservadaProduto(extrairInteiro(primeiroValor(payload, "qtdReservadaProduto", "qntdReservadaProduto")));
        produto.setQtdMinEstoque(extrairInteiro(primeiroValor(payload, "qtdMinEstoque", "qntdMinEstoque")));
        produto.setQtdMaxEstoque(extrairInteiro(primeiroValor(payload, "qtdMaxEstoque", "qntdMaxEstoque")));
        produto.setPercentualComissao(extrairBigDecimal(payload.get("percentualComissao")));
        produto.setPercentualPromocao(extrairBigDecimal(payload.get("percentualPromocao")));
        produto.setMargemLucro(extrairBigDecimal(payload.get("margemLucro")));
        produto.setCategoriaProduto(categoriaProduto);
        return produto;
    }

    private Object primeiroValor(Map<String, Object> payload, String campoPrincipal, String campoAlternativo) {
        if (payload.containsKey(campoPrincipal)) {
            return payload.get(campoPrincipal);
        }

        return payload.get(campoAlternativo);
    }

    private Integer extrairIdCategoriaProduto(Map<String, Object> payload) {
        Integer idCategoriaProduto = extrairInteiro(payload.get("idCategoriaProduto"));

        if (idCategoriaProduto != null) {
            return idCategoriaProduto;
        }

        Object categoriaProduto = payload.get("categoriaProduto");

        if (categoriaProduto instanceof Map<?, ?> categoriaMap) {
            return extrairInteiro(categoriaMap.get("idCategoriaProduto"));
        }

        return extrairInteiro(categoriaProduto);
    }

    private CategoriaProduto resolverCategoria(Integer idCategoriaProduto) {
        if (idCategoriaProduto == null) {
            return null;
        }

        return categoriaRepository.findById(idCategoriaProduto).orElse(null);
    }

    private Integer extrairInteiro(Object valor) {
        if (valor instanceof Number numero) {
            return numero.intValue();
        }

        if (valor instanceof String texto && !texto.isBlank()) {
            return Integer.valueOf(texto);
        }

        return null;
    }

    private BigDecimal extrairBigDecimal(Object valor) {
        if (valor instanceof BigDecimal decimal) {
            return decimal;
        }

        if (valor instanceof Number numero) {
            return BigDecimal.valueOf(numero.doubleValue());
        }

        if (valor instanceof String texto && !texto.isBlank()) {
            return new BigDecimal(texto);
        }

        return null;
    }

    private String extrairTexto(Object valor) {
        return valor == null ? null : valor.toString();
    }
}
