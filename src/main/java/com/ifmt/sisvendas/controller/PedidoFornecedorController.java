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

import com.ifmt.sisvendas.model.PedidoFornecedor;
import com.ifmt.sisvendas.repository.PedidoFornecedorRepository;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;

@RestController
@RequestMapping("/pedidos-fornecedor")
@Tag(
        name = "Pedidos ao Fornecedor",
        description = "Operações de cadastro e manutenção dos pedidos enviados aos fornecedores."
)
public class PedidoFornecedorController {

    private final PedidoFornecedorRepository repository;

    public PedidoFornecedorController(PedidoFornecedorRepository repository) {
        this.repository = repository;
    }

    @Operation(
            summary = "Listar pedidos ao fornecedor",
            description = "Retorna todos os pedidos realizados aos fornecedores."
    )
    @ApiResponse(responseCode = "200", description = "Pedidos ao fornecedor retornados com sucesso")
    @GetMapping
    public List<PedidoFornecedor> listar() {
        return repository.findAll();
    }

    @Operation(
            summary = "Cadastrar pedido ao fornecedor",
            description = "Cadastra um novo pedido de compra enviado a um fornecedor."
    )
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Pedido ao fornecedor cadastrado com sucesso"),
            @ApiResponse(responseCode = "400", description = "Dados inválidos")
    })
    @PostMapping
    public PedidoFornecedor cadastrar(@RequestBody PedidoFornecedor pedidoFornecedor) {
        return repository.save(pedidoFornecedor);
    }

    @Operation(
            summary = "Buscar pedido ao fornecedor por ID",
            description = "Retorna os dados de um pedido ao fornecedor pelo seu identificador."
    )
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Pedido ao fornecedor encontrado"),
            @ApiResponse(responseCode = "404", description = "Pedido ao fornecedor não encontrado")
    })
    @GetMapping("/{id}")
    public PedidoFornecedor buscarPorId(@PathVariable Integer id) {
        return repository.findById(id).orElse(null);
    }

    @Operation(
            summary = "Atualizar pedido ao fornecedor",
            description = "Atualiza os dados de um pedido de compra enviado a fornecedor."
    )
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Pedido ao fornecedor atualizado com sucesso"),
            @ApiResponse(responseCode = "404", description = "Pedido ao fornecedor não encontrado")
    })
    @PutMapping("/{id}")
    public PedidoFornecedor atualizar(@PathVariable Integer id, @RequestBody PedidoFornecedor dados) {
        PedidoFornecedor pedidoFornecedor = repository.findById(id).orElse(null);

        if (pedidoFornecedor == null) {
            return null;
        }

        pedidoFornecedor.setData(dados.getData());
        pedidoFornecedor.setVlTotal(dados.getVlTotal());
        pedidoFornecedor.setStatus(dados.getStatus());
        pedidoFornecedor.setFornecedor(dados.getFornecedor());

        return repository.save(pedidoFornecedor);
    }

    @Operation(
            summary = "Excluir pedido ao fornecedor",
            description = "Remove um pedido ao fornecedor pelo seu identificador."
    )
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Pedido ao fornecedor excluído com sucesso"),
            @ApiResponse(responseCode = "404", description = "Pedido ao fornecedor não encontrado")
    })
    @DeleteMapping("/{id}")
    public void excluir(@PathVariable Integer id) {
        repository.deleteById(id);
    }
}
