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

import com.ifmt.sisvendas.dto.ProdutoDTO;
import com.ifmt.sisvendas.model.CategoriaProduto;
import com.ifmt.sisvendas.model.Produto;
import com.ifmt.sisvendas.repository.CategoriaProdutoRepository;
import com.ifmt.sisvendas.repository.ProdutoRepository;

import tools.jackson.databind.JsonNode;

/**
 * Controller responsável pelos endpoints REST de produtos.
 *
 * Permite manter o cadastro de produtos e executar consultas relacionadas
 * à categoria e ao controle de estoque.
 */
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

    /**
     * Cadastra um novo produto a partir de um DTO.
     *
     * O DTO permite receber os dados do produto e os identificadores
     * das entidades relacionadas, como a categoria.
     */
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
    }

    @GetMapping("/{id}")
    public Produto buscarPorId(@PathVariable Integer id) {
        return repository.findById(id).orElse(null);
    }

    /**
     * Atualiza os dados de um produto existente.
     *
     * Caso o produto não seja encontrado, retorna uma resposta indicando erro.
     */
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

    /**
     * Lista os produtos de uma categoria, ordenados pelo nome.
     */
    @GetMapping("/categoria/{idCategoria}")
    public List<Produto> listarPorCategoriaOrdenadoPorNome(@PathVariable Integer idCategoria) {
        return repository.findByCategoriaProdutoIdCategoriaProdutoOrderByNomeAsc(idCategoria);
    }

    /**
     * Lista os produtos de uma categoria em ordem decrescente de estoque.
     *
     * Essa consulta ajuda a visualizar quais produtos possuem maior quantidade disponível.
     */
    @GetMapping("/categoria/{idCategoria}/estoque-desc")
    public List<Produto> listarPorCategoriaOrdenadoPorEstoqueDesc(@PathVariable Integer idCategoria) {
        return repository.findByCategoriaProdutoIdCategoriaProdutoOrderByQtdEstoqueDesc(idCategoria);
    }

    /**
     * Lista os produtos cuja quantidade em estoque está abaixo do estoque mínimo.
     *
     * Essa consulta apoia o controle de reposição de produtos.
     */
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

        return categoriaRepository.findById(idCategoriaProduto).orElse(null);
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
