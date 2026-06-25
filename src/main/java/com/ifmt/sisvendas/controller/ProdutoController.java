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

import com.ifmt.sisvendas.dto.ProdutoDTO;
import com.ifmt.sisvendas.model.CategoriaProduto;
import com.ifmt.sisvendas.model.Produto;
import com.ifmt.sisvendas.repository.ProdutoRepository;

@RestController
@RequestMapping("/produtos")
public class ProdutoController {

    private final ProdutoRepository repository;

    public ProdutoController(
            ProdutoRepository repository,
            CategoriaProdutoRepository categoriaRepository) {
    public ProdutoController(ProdutoRepository repository) {
        this.repository = repository;
    }

    @GetMapping
    public List<Produto> listar() {
        return repository.findAll();
    }

    @PostMapping
    public ResponseEntity<?> cadastrar(@RequestBody ProdutoDTO produtoDTO) {
        CategoriaProduto categoria = buscarCategoria(produtoDTO);

        if (categoria == null) {
            return ResponseEntity.badRequest()
                    .body(Map.of("message", "CategoriaProduto invalida ou inexistente."));
        }

        Produto produto = new Produto();
        aplicarDadosDTO(produto, produtoDTO, categoria);

        return ResponseEntity.ok(repository.save(produto));
    public Produto cadastrar(@RequestBody Produto produto) {
        return repository.save(produto);
    }

    @GetMapping("/{id}")
    public Produto buscarPorId(@PathVariable Integer id) {
        return repository.findById(id).orElse(null);
    }

    @PutMapping("/{id}")
    public ResponseEntity<?> atualizar(@PathVariable Integer id, @RequestBody ProdutoDTO produtoDTO) {
        Produto produto = repository.findById(id).orElse(null);

        if (produto == null) {
            return ResponseEntity.notFound().build();
        }

        CategoriaProduto categoria = buscarCategoria(produtoDTO);

        if (categoria == null) {
            return ResponseEntity.badRequest()
                    .body(Map.of("message", "CategoriaProduto invalida ou inexistente."));
        }

        aplicarDadosDTO(produto, produtoDTO, categoria);

        return ResponseEntity.ok(repository.save(produto));
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

    private void aplicarDadosDTO(
            Produto produto,
            ProdutoDTO produtoDTO,
            CategoriaProduto categoria) {

        produto.setNome(produtoDTO.getNome());
        produto.setVlCusto(produtoDTO.getVlCusto());
        produto.setQtdEstoque(produtoDTO.getQtdEstoque());
        produto.setQtdReservadaProduto(produtoDTO.getQtdReservadaProduto());
        produto.setQntdMinEstoque(produtoDTO.getQtdMinEstoque());
        produto.setQntdMaxEstoque(produtoDTO.getQtdMaxEstoque());
        produto.setPercentualComissao(produtoDTO.getPercentualComissao());
        produto.setPercentualPromocao(produtoDTO.getPercentualPromocao());
        produto.setMargemLucro(produtoDTO.getMargemLucro());
        produto.setCategoriaProduto(categoria);
    }

    private CategoriaProduto buscarCategoria(ProdutoDTO produtoDTO) {
        Integer idCategoriaProduto = extrairIdCategoria(produtoDTO);

        if (idCategoriaProduto == null) {
            return null;
        }
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

    private Integer extrairIdCategoria(ProdutoDTO produtoDTO) {
        if (produtoDTO.getIdCategoriaProduto() != null) {
            return produtoDTO.getIdCategoriaProduto();
        }

        JsonNode categoriaNode = produtoDTO.getCategoriaProduto();
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
    @DeleteMapping("/{id}")
    public void excluir(@PathVariable Integer id) {
        repository.deleteById(id);
    }
}
