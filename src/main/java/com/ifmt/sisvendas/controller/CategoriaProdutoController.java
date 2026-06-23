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

import com.ifmt.sisvendas.model.CategoriaProduto;
import com.ifmt.sisvendas.repository.CategoriaProdutoRepository;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;

@RestController
@RequestMapping("/categorias")
@Tag(
        name = "Categorias de Produto",
        description = "Operações de cadastro e manutenção das categorias dos produtos."
)
public class CategoriaProdutoController {

    private final CategoriaProdutoRepository repository;

    public CategoriaProdutoController(CategoriaProdutoRepository repository) {
        this.repository = repository;
    }

    @Operation(
            summary = "Listar categorias",
            description = "Retorna todas as categorias de produto cadastradas."
    )
    @ApiResponse(responseCode = "200", description = "Categorias retornadas com sucesso")
    @GetMapping
    public List<CategoriaProduto> listar() {
        return repository.findAll();
    }

    @Operation(
            summary = "Cadastrar categoria",
            description = "Cadastra uma nova categoria de produto com percentual de comissão e desconto."
    )
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Categoria cadastrada com sucesso"),
            @ApiResponse(responseCode = "400", description = "Dados inválidos")
    })
    @PostMapping
    public CategoriaProduto cadastrar(@RequestBody CategoriaProduto categoriaProduto) {
        return repository.save(categoriaProduto);
    }

    @Operation(
            summary = "Listar categorias ordenadas por nome",
            description = "Retorna todas as categorias de produto cadastradas, ordenadas alfabeticamente pelo nome."
    )
    @ApiResponse(responseCode = "200", description = "Categorias retornadas com sucesso")
    @GetMapping("/ordenadas-por-nome")
    public List<CategoriaProduto> listarOrdenadasPorNome() {
        return repository.findAllByOrderByNomeAsc();
    }

    @Operation(
            summary = "Buscar categoria por ID",
            description = "Retorna os dados de uma categoria de produto pelo seu identificador."
    )
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Categoria encontrada"),
            @ApiResponse(responseCode = "404", description = "Categoria não encontrada")
    })
    @GetMapping("/{id}")
    public CategoriaProduto buscarPorId(@PathVariable Integer id) {
        return repository.findById(id).orElse(null);
    }

    @Operation(
            summary = "Atualizar categoria",
            description = "Atualiza os dados de uma categoria de produto existente."
    )
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Categoria atualizada com sucesso"),
            @ApiResponse(responseCode = "404", description = "Categoria não encontrada")
    })
    @PutMapping("/{id}")
    public CategoriaProduto atualizar(@PathVariable Integer id, @RequestBody CategoriaProduto dados) {
        CategoriaProduto categoria = repository.findById(id).orElse(null);

        if (categoria == null) {
            return null;
        }
        
        categoria.setNome(dados.getNome());
        categoria.setPercentualComissao(dados.getPercentualComissao());
        categoria.setPercentualDesconto(dados.getPercentualDesconto());
        
        return repository.save(categoria);
    }
    
    @Operation(
            summary = "Excluir categoria",
            description = "Remove uma categoria de produto pelo seu identificador."
    )
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Categoria excluída com sucesso"),
            @ApiResponse(responseCode = "404", description = "Categoria não encontrada")
    })
    @DeleteMapping("/{id}")
    public void excluir(@PathVariable Integer id) {
        repository.deleteById(id);
    }

}
