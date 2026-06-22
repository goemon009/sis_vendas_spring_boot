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

import com.ifmt.sisvendas.model.Comissao;
import com.ifmt.sisvendas.repository.ComissaoRepository;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;

@RestController
@RequestMapping("/comissoes")
@Tag(
        name = "Comissões",
        description = "Operações de cadastro, consulta e quitação de lançamentos de comissão dos promotores de venda."
)
public class ComissaoController {

    private final ComissaoRepository repository;

    public ComissaoController(ComissaoRepository repository) {
        this.repository = repository;
    }

    @Operation(summary = "Listar comissões", description = "Retorna todas as comissões cadastradas.")
    @ApiResponse(responseCode = "200", description = "Comissões retornadas com sucesso")
    @GetMapping
    public List<Comissao> listar() {
        return repository.findAll();
    }

    @Operation(summary = "Cadastrar comissão", description = "Cadastra uma nova comissão para um promotor.")
    @ApiResponse(responseCode = "200", description = "Comissão cadastrada com sucesso")
    @ApiResponse(responseCode = "400", description = "Dados inválidos para cadastro da comissão")
    @PostMapping
    public Comissao cadastrar(@RequestBody Comissao comissao) {
        return repository.save(comissao);
    }

    @Operation(summary = "Buscar comissão por ID", description = "Retorna uma comissão pelo seu identificador.")
    @ApiResponse(responseCode = "200", description = "Comissão encontrada")
    @ApiResponse(responseCode = "404", description = "Comissão não encontrada")
    @GetMapping("/{id}")
    public Comissao buscarPorId(@PathVariable Integer id) {
        return repository.findById(id).orElse(null);
    }

    @Operation(summary = "Atualizar comissão", description = "Atualiza os dados de uma comissão existente.")
    @ApiResponse(responseCode = "200", description = "Comissão atualizada com sucesso")
    @ApiResponse(responseCode = "404", description = "Comissão não encontrada")
    @PutMapping("/{id}")
    public Comissao atualizar(@PathVariable Integer id, @RequestBody Comissao dados) {
        Comissao comissao = repository.findById(id).orElse(null);

        if (comissao == null) {
            return null;
        }

        comissao.setValor(dados.getValor());
        comissao.setData(dados.getData());
        comissao.setStatus(dados.getStatus());
        comissao.setPromotor(dados.getPromotor());
        comissao.setPedidoCliente(dados.getPedidoCliente());

        return repository.save(comissao);
    }

    @Operation(
            summary = "Quitar comissão",
            description = "Muda o status de uma comissão de LANCADA para QUITADA, representando o pagamento semanal ao promotor de venda."
    )
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Comissão quitada com sucesso"),
            @ApiResponse(responseCode = "404", description = "Comissão não encontrada")
    })
    @PutMapping("/{id}/quitar")
    public Comissao quitar(@PathVariable Integer id) {
        Comissao comissao = repository.findById(id).orElse(null);

        if (comissao == null) {
            return null;
        }

        comissao.setStatus("QUITADA");

        return repository.save(comissao);
    }

    @Operation(summary = "Excluir comissão", description = "Remove uma comissão pelo seu identificador.")
    @ApiResponse(responseCode = "200", description = "Comissão excluída com sucesso")
    @ApiResponse(responseCode = "404", description = "Comissão não encontrada")
    @DeleteMapping("/{id}")
    public void excluir(@PathVariable Integer id) {
        repository.deleteById(id);
    }
}
