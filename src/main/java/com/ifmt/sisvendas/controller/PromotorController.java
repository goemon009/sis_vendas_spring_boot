package com.ifmt.sisvendas.controller;

import java.util.List;

import org.springframework.web.bind.annotation.*;

import com.ifmt.sisvendas.model.Promotor;
import com.ifmt.sisvendas.repository.PromotorRepository;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;

@RestController
@RequestMapping("/promotores")
@Tag(
        name = "Promotores de Venda",
        description = "Operações de cadastro e manutenção dos promotores de venda."
)
public class PromotorController {

    private final PromotorRepository repository;

    public PromotorController(PromotorRepository repository) {
        this.repository = repository;
    }

    @Operation(
            summary = "Listar promotores",
            description = "Retorna todos os promotores de venda cadastrados."
    )
    @ApiResponse(responseCode = "200", description = "Promotores retornados com sucesso")
    @GetMapping
    public List<Promotor> listar() {
        return repository.findAll();
    }

    @Operation(
            summary = "Cadastrar promotor",
            description = "Cadastra um novo promotor de venda."
    )
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Promotor cadastrado com sucesso"),
            @ApiResponse(responseCode = "400", description = "Dados inválidos")
    })
    @PostMapping
    public Promotor cadastrar(@RequestBody Promotor promotor) {
        return repository.save(promotor);
    }

    @Operation(
            summary = "Buscar promotor por ID",
            description = "Retorna os dados de um promotor de venda pelo seu identificador."
    )
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Promotor encontrado"),
            @ApiResponse(responseCode = "404", description = "Promotor não encontrado")
    })
    @GetMapping("/{id}")
    public Promotor buscarPorId(@PathVariable Integer id) {
        return repository.findById(id).orElse(null);
    }

    @Operation(
            summary = "Atualizar promotor",
            description = "Atualiza os dados de um promotor de venda existente."
    )
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Promotor atualizado com sucesso"),
            @ApiResponse(responseCode = "404", description = "Promotor não encontrado")
    })
    @PutMapping("/{id}")
    public Promotor atualizar(@PathVariable Integer id,
                              @RequestBody Promotor dados) {

        Promotor promotor =
                repository.findById(id).orElse(null);

        if (promotor == null) {
            return null;
        }

        promotor.setNome(dados.getNome());
        promotor.setCpf(dados.getCpf());

        return repository.save(promotor);
    }

    @Operation(
            summary = "Excluir promotor",
            description = "Remove um promotor de venda pelo seu identificador."
    )
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Promotor excluído com sucesso"),
            @ApiResponse(responseCode = "404", description = "Promotor não encontrado")
    })
    @DeleteMapping("/{id}")
    public void excluir(@PathVariable Integer id) {
        repository.deleteById(id);
    }
}
