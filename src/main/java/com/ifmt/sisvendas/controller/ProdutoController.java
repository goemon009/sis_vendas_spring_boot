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

import com.ifmt.sisvendas.model.Produto;
import com.ifmt.sisvendas.repository.ProdutoRepository;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;

@RestController
@RequestMapping("/produtos")
@Tag(
        name = "Produtos",
        description = "Operações de cadastro, manutenção e consulta dos produtos comercializados pela empresa."
)
public class ProdutoController {

    private final ProdutoRepository repository;

    public ProdutoController(ProdutoRepository repository) {
        this.repository = repository;
    }

    @Operation(
            summary = "Listar produtos",
            description = "Retorna todos os produtos cadastrados no sistema."
    )
    @ApiResponse(responseCode = "200", description = "Produtos retornados com sucesso")
    @GetMapping
    public List<Produto> listar() {
        return repository.findAll();
    }

    @Operation(
            summary = "Cadastrar produto",
            description = "Cadastra um novo produto com categoria, valores, estoque, comissão e promoção."
    )
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Produto cadastrado com sucesso"),
            @ApiResponse(responseCode = "400", description = "Dados inválidos")
    })
    @PostMapping
    public Produto cadastrar(@RequestBody Produto produto) {
        return repository.save(produto);
    }

    @Operation(
            summary = "Buscar produto por ID",
            description = "Retorna os dados de um produto pelo seu identificador."
    )
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Produto encontrado"),
            @ApiResponse(responseCode = "404", description = "Produto não encontrado")
    })
    @GetMapping("/{id}")
    public Produto buscarPorId(@PathVariable Integer id) {
        return repository.findById(id).orElse(null);
    }

    @Operation(
            summary = "Atualizar produto",
            description = "Atualiza os dados cadastrais, financeiros, promocionais e de estoque de um produto."
    )
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Produto atualizado com sucesso"),
            @ApiResponse(responseCode = "404", description = "Produto não encontrado")
    })
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

    @Operation(
            summary = "Excluir produto",
            description = "Remove um produto pelo seu identificador."
    )
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Produto excluído com sucesso"),
            @ApiResponse(responseCode = "404", description = "Produto não encontrado")
    })
    @DeleteMapping("/{id}")
    public void excluir(@PathVariable Integer id) {
        repository.deleteById(id);
    }
}
